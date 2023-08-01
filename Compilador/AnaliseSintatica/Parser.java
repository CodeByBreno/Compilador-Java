package AnaliseSintatica;

import java.io.IOException;

import AnaliseContexto.Type;
import AnaliseLexica.Scanner;
import AnaliseLexica.Token;
import ArvoreSintaticaAbstrata.Comando.Attribution;
import ArvoreSintaticaAbstrata.Comando.Command;
import ArvoreSintaticaAbstrata.Comando.CompositeCommand;
import ArvoreSintaticaAbstrata.Comando.Condition;
import ArvoreSintaticaAbstrata.Comando.Iteration;
import ArvoreSintaticaAbstrata.ComandoList.CommandList;
import ArvoreSintaticaAbstrata.Corpo.Body;
import ArvoreSintaticaAbstrata.DeclaracaoVariavel.VariableDeclaration;
import ArvoreSintaticaAbstrata.Declaracoes.Declarations;
import ArvoreSintaticaAbstrata.ExpressaoSimples.SimpleExpression;
import ArvoreSintaticaAbstrata.Fator.Expression;
import ArvoreSintaticaAbstrata.Fator.Fator;
import ArvoreSintaticaAbstrata.Fator.Variable;
import ArvoreSintaticaAbstrata.Fator.Literal.BooleanLiteral;
import ArvoreSintaticaAbstrata.Fator.Literal.FloatLiteral;
import ArvoreSintaticaAbstrata.Fator.Literal.IntegerLiteral;
import ArvoreSintaticaAbstrata.IDList.IDList;
import ArvoreSintaticaAbstrata.Identificador.Identifier;
import ArvoreSintaticaAbstrata.Operator.AdOperator;
import ArvoreSintaticaAbstrata.Operator.MulOperator;
import ArvoreSintaticaAbstrata.Operator.RelOperator;
import ArvoreSintaticaAbstrata.Program.Program;
import ArvoreSintaticaAbstrata.Termo.Termo;
import ArvoreSintaticaAbstrata.TipoSimples.SimpleType;

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

    public Program parse(){
        currentToken = this.scanner.scan();
        Program programa = parseProgram();
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

        return programa;
    }

    public boolean state(){
        return this.state;
    }

    private Program parseProgram(){
        // <programa> ::= program <id> ; <corpo> .
        accept(Token.PROGRAMA);
        String speeling = currentToken.spelling;
        accept(Token.IDENTIFIER);
        Identifier id = new Identifier(speeling);
        accept(Token.SEMICOLON);
        Body body = parseCorpo();
        accept(Token.DOT);

        return new Program(id, body);
    }

    private Body parseCorpo(){
        // <corpo> ::= <declaracoes> <comando-composto>
        Declarations declarations = parseDeclaracoes();
        CompositeCommand compositeCommand = parseComandoComposto();

        return new Body(declarations, compositeCommand);
    }

    private Declarations parseDeclaracoes(){
        // <declaracoes> ::= ( <declaracao-de-variavel> ; )*
        Declarations declarations = null, aux = null;

        while(currentToken.kind == Token.VAR){
            VariableDeclaration varDeclaration = parseDeclaracaoDeVariavel();

            if(declarations == null) {
                declarations = new Declarations(varDeclaration, null);
                aux = declarations;
            } else {
                aux.next = new Declarations(varDeclaration, null);
                aux = aux.next;
            }

            accept(Token.SEMICOLON);
        }

        return declarations;
    }

    private Command parseComando(){
        // <comando> ::= <atribuição> | <condicional> | <iterativo> | <comando-composto>
        Command command = null;
        switch(currentToken.kind){
            case Token.IDENTIFIER:
                command = parseAtribuicao();
                break;
            case Token.IF:
                command = parseCondicional();
                break;
            case Token.WHILE:
                command = parseIterativo();
                break;
            case Token.BEGIN:
                command = parseComandoComposto();
                break;
            default:
                erroToken();
        }

        return command;
    }

    private Attribution parseAtribuicao(){
        // <atribuição> ::= <variável> := <expressão>
        Identifier identifier = parseIdentifier();
        Variable variable = new Variable(identifier);
        accept(Token.BECOMES);
        Expression expression = parseExpressao();

        return new Attribution(variable, expression);
    }

    private Condition parseCondicional(){
        // <condicional> ::= if <expressão> then <comando> ( else <comando> | <vazio )
        accept(Token.IF);
        Expression expression = parseExpressao();
        accept(Token.THEN);
        Command commandIF = parseComando(), commandELSE = null;

        if (currentToken.kind == Token.ELSE){
            acceptIt();
            commandELSE = parseComando();
        }

        return new Condition(expression, commandIF, commandELSE);
    }

    private Iteration parseIterativo(){
        // <iterativo> ::= while <expressão> do <comando>
        accept(Token.WHILE);
        Expression expression = parseExpressao();
        accept(Token.DO);
        Command command = parseComando();

        return new Iteration(expression, command);
    }

    private CompositeCommand parseComandoComposto(){
        // <comando-composto> ::= begin <lista-de-comandos> end
        accept(Token.BEGIN);
        CommandList commandList = parseListaDeComandos();
        accept(Token.END);
        return new CompositeCommand(commandList);
    }

    private Expression parseExpressao(){
        // <expressão> ::= <expressão-simples> ( <op-rel> <expressão-simples> | <vazio )
        SimpleExpression simpleExpression = parseExpressaoSimples();
        Expression expression = new Expression(simpleExpression);
        if (currentToken.kind == Token.OPERATOR_REL){
            String speeling = currentToken.spelling;
            expression.line = currentToken.line;
            expression.column = currentToken.column;
            expression.setOperator(new RelOperator(speeling));
            acceptIt();
            expression.setSimpleExpression(parseExpressaoSimples());
        }

        return expression;
    }

    private SimpleExpression parseExpressaoSimples(){
        // <expressao-simples> ::= <termo> ( <op-ad> <termo> )*
        Termo termo = parseTermo();
        SimpleExpression simpleExpression = new SimpleExpression(termo);
        while (currentToken.kind == Token.OPERATOR_ADD){
            String speeling = currentToken.spelling;
            simpleExpression.setOperator(new AdOperator(speeling));
            acceptIt();
            simpleExpression.setSimpleExpression(parseExpressaoSimples());
        }

        return simpleExpression;
    }

    private Termo parseTermo(){
        // <termo> ::= <fator> ( <op-mul> <fator> )*
        Fator fator = parseFator();
        Termo termo = new Termo(fator);
        while(currentToken.kind == Token.OPERATOR_MUL){
            String speeling = currentToken.spelling;
            termo.setOperator(new MulOperator(speeling));
            acceptIt();
            termo.setTermo(parseTermo());
        }

        return termo;
    }

    private Fator parseFator(){
        // <fator> ::= <variável> | <bool-lit> | <int-lit> | <float-lit> | "(" <expressao> ")"
        Fator fator = null;
        switch(currentToken.kind){
            case Token.IDENTIFIER:
                Identifier identifier = new Identifier(currentToken.spelling);
                fator = new Variable(identifier);
                fator.line = currentToken.line;
                fator.column = currentToken.column;
                acceptIt();
                break;
            case Token.BOOL_LITERAL:
                fator = new BooleanLiteral(currentToken.spelling);
                fator.line = currentToken.line;
                fator.column = currentToken.column;
                acceptIt();
                break;
            case Token.FLOAT_LITERAL:
                fator = new FloatLiteral(currentToken.spelling);
                fator.line = currentToken.line;
                fator.column = currentToken.column;
                acceptIt();
                break;
            case Token.INT_LITERAL:
                fator = new IntegerLiteral(currentToken.spelling);
                fator.line = currentToken.line;
                fator.column = currentToken.column;
                acceptIt();
                break;
            case Token.LPAR:
                acceptIt();
                fator = parseExpressao();
                fator.line = currentToken.line;
                fator.column = currentToken.column;
                accept(Token.RPAR);
                break;
            default:
                erroToken();
        }

        return fator;
    }

    private CommandList parseListaDeComandos(){
        // <lista-de-comandos> ::= ( <comando> ; )*
        CommandList commandList = null, aux = null;
        while((currentToken.kind == Token.IDENTIFIER) ||
              (currentToken.kind == Token.IF) || 
              (currentToken.kind == Token.WHILE) ||
              (currentToken.kind == Token.BEGIN)){
                Command command = parseComando();

                if(commandList == null) {
                    commandList = new CommandList(command, null);
                    aux = commandList;
                } else {
                    aux.next = new CommandList(command, null);
                    aux = aux.next;
                }

                accept(Token.SEMICOLON);
              }
        return commandList;
    }

    private SimpleType parseTipoSimples(){
        String tipo = currentToken.spelling;
        accept(Token.TIPO_SIMPLES);

        switch(tipo) {
            case "integer": return new SimpleType(Type.Int);
            case "boolean": return new SimpleType(Type.Bool);
            default: return new SimpleType(Type.Float);
        }
    }

    private VariableDeclaration parseDeclaracaoDeVariavel(){
        // <declaracao-de-variavel> ::= var <lista-de-ids> : <tipo-simples>
        accept(Token.VAR);
        IDList idList = parseListaDeIds();
        accept(Token.COLON);
        // accept(Token.TIPO_SIMPLES); // Vou fazer logo direto aqui
        SimpleType simpleType = parseTipoSimples();
        return new VariableDeclaration(idList, simpleType);
    }

    private Identifier parseIdentifier(){
        String speeling = currentToken.spelling;
        accept(Token.IDENTIFIER);
        return new Identifier(speeling);
    }

    private IDList parseListaDeIds(){
        // <lista-de-ids> ::= <id> ( , <id> )*
        IDList idList = new IDList(parseIdentifier());
        while(currentToken.kind == Token.COMMA){
            acceptIt();
            idList.idList.add(parseIdentifier());
        }

        return idList;
    }
}
