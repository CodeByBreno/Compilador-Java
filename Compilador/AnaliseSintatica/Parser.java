package AnaliseSintatica;

import java.io.IOException;
import AnaliseLexica.Scanner;
import AnaliseLexica.Token;

public class Parser {
    private Token currentToken;
    private Scanner scanner;
    private boolean state;

    public Parser(String path) throws IOException{
        this.scanner = new Scanner(path);
        this.state = true;
    }

    private void erroToken(){
        this.state = false;
        System.out.println("ERRO: Token inesperado detectado\n");
        System.out.println("SOURCE: LINHA = " + currentToken.line + " COLUNA = " + currentToken.column);
        System.out.println("ULTIMO TOKEN LIDO: " + currentToken.spelling);
        System.exit(1);
    }

    private void erroTokenExpected(byte expectedToken){
        this.state = false;
        System.out.println("ERRO: Token inesperado detectado\n");
        System.out.println("SOURCE: LINHA = " + currentToken.line + " COLUNA = " + currentToken.column);
        System.out.println("ULTIMO TOKEN LIDO: " + currentToken.spelling);       
        System.out.println("TOKEN ESPERADO : " + Token.spellings[expectedToken]);
        System.exit(1);
    }

    private void accept(byte expectedToken){
        if (currentToken.kind == expectedToken){
            currentToken = this.scanner.scan();
        } else{
            erroTokenExpected(expectedToken);
        }
    }

    private void acceptIt(){
        currentToken = this.scanner.scan();
    }

    public void parse(){
        currentToken = this.scanner.scan();
        parseProgram();
        if (this.currentToken.kind != Token.EOT){
            this.state = false;
            System.out.println("ERRO: Finalizacao da leitura nao termina com EOT\n");
            System.out.println("SOURCE: LINHA = " + currentToken.line + " COLUNA = " + currentToken.column + "\n");
            System.out.println("ULTIMO TOKEN LIDO: " + currentToken.spelling);
        } else{
            if (this.state){
                System.out.println("SUCESSO: Arquivo lido. O codigo tem Sintaxe adequada\n");
            } else {
                System.out.println("ERRO: Ocorreu algum problema durante a compilacao do codigo\n");
            }
        }
    }

    public boolean state(){
        return this.state;
    }

    private void parseProgram(){
        // <programa> ::= program <id> ; <corpo> .
        accept(Token.PROGRAMA);
        accept(Token.IDENTIFIER);
        accept(Token.SEMICOLON);
        parseCorpo();
        accept(Token.DOT);
    }

    private void parseCorpo(){
        // <corpo> ::= <declaracoes> <comando-composto>
        parseDeclaracoes();
        parseComandoComposto();
    }

    private void parseDeclaracoes(){
        // <declaracoes> ::= ( <declaracao-de-variavel> ; )*
        while(currentToken.kind == Token.VAR){
            parseDeclaracaoDeVariavel();
            accept(Token.SEMICOLON);
        }
    }

    private void parseComando(){
        // <comando> ::= <atribuição> | <condicional> | <iterativo> | <comando-composto>
        switch(currentToken.kind){
            case Token.IDENTIFIER:
                parseAtribuicao();
                break;
            case Token.IF:
                parseCondicional();
                break;
            case Token.WHILE:
                parseIterativo();
                break;
            case Token.BEGIN:
                parseComandoComposto();
                break;
            default:
                erroToken();
        }
    }

    private void parseAtribuicao(){
        // <atribuição> ::= <variável> := <expressão>
        parseVariavel();
        accept(Token.BECOMES);
        parseExpressao();
    }

    private void parseCondicional(){
        // <condicional> ::= if <expressão> then <comando> ( else <comando> | <vazio )
        accept(Token.IF);
        parseExpressao();
        accept(Token.THEN);
        parseComando();

        if (currentToken.kind == Token.ELSE){
            acceptIt();
            parseComando();
        }
    }

    private void parseIterativo(){
        // <iterativo> ::= while <expressão> do <comando>
        accept(Token.WHILE);
        parseExpressao();
        accept(Token.DO);
        parseComando();
    }

    private void parseComandoComposto(){
        // <comando-composto> ::= begin <lista-de-comandos> end
        accept(Token.BEGIN);
        parseListaDeComandos();
        accept(Token.END);
    }

    private void parseExpressao(){
        // <expressão> ::= <expressão-simples> ( <op-rel> <expressão-simples> | <vazio )
        parseExpressaoSimples();
        if (currentToken.kind == Token.OPERATOR_REL){
            acceptIt();
            parseExpressaoSimples();
        }
    }

    private void parseExpressaoSimples(){
        // <expressao-simples> ::= <termo> ( <op-ad> <termo> )*
        parseTermo();
        while (currentToken.kind == Token.OPERATOR_ADD){
            acceptIt();
            parseTermo();
        }
    }

    private void parseTermo(){
        // <termo> ::= <fator> ( <op-mul> <fator> )*
        parseFator();
        while(currentToken.kind == Token.OPERATOR_MUL){
            acceptIt();
            parseFator();
        }
    }

    private void parseFator(){
        // <fator> ::= <variável> | <bool-lit> | <int-lit> | <float-lit> | "(" <expressao> ")"
        switch(currentToken.kind){
            case Token.IDENTIFIER:
            case Token.BOOL_LITERAL:
            case Token.FLOAT_LITERAL:
            case Token.INT_LITERAL:
                acceptIt();
                break;
            case Token.LPAR:
                acceptIt();
                parseExpressao();
                accept(Token.RPAR);
                break;
            default:
                erroToken();
        }
    }

    private void parseVariavel(){
        // <variavel> ::= <id>
        accept(Token.IDENTIFIER);
    }

    private void parseListaDeComandos(){
        // <lista-de-comandos> ::= ( <comando> ; )*
        while((currentToken.kind == Token.IDENTIFIER) ||
              (currentToken.kind == Token.IF) || 
              (currentToken.kind == Token.WHILE) ||
              (currentToken.kind == Token.BEGIN)){
                parseComando();
                accept(Token.SEMICOLON);
              }
    }

    private void parseDeclaracaoDeVariavel(){
        // <declaracao-de-variavel> ::= var <lista-de-ids> : <tipo-simples>
        accept(Token.VAR);
        parseListaDeIds();
        accept(Token.COLON);
        accept(Token.TIPO_SIMPLES); // Vou fazer logo direto aqui
    }

    private void parseListaDeIds(){
        // <lista-de-ids> ::= <id> ( , <id> )*
        accept(Token.IDENTIFIER);
        while(currentToken.kind == Token.COMMA){
            acceptIt();
            accept(Token.IDENTIFIER);
        }
    }
}
