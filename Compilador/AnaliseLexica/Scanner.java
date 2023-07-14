package AnaliseLexica;
import LeitorArquivo.ManipuladorArquivo;

import java.io.IOException;

public class Scanner{
    //Falta guardar a linha a coluna do token lido
    
    private String arquivo;  //Path do arquivo de entrada com o código
    private long fileSize;

    private int line = 0;
    private int column = 0;
    private int charPosition = 0;  //Posição do cursor de leitura no arquivo
    private char currentChar;      //Caractere atualmente lido
    private byte currentKind;      //Tipo do Token
    private StringBuffer currentSpelling;  //Valor do Token - texto que possui -

    public Scanner(String path) throws IOException{
        this.arquivo = path;
        this.fileSize = ManipuladorArquivo.sizeFile(path);
        this.currentChar = ManipuladorArquivo.readChar(this.arquivo, charPosition);
    }

    //Captura um caractere se ele for o esperado
    private void take(char expectedChar) {
        if (this.currentChar == expectedChar){
            currentSpelling.append(currentChar);
            
            if (this.charPosition++ != this.fileSize - 1){
                try {
                    this.line++;
                    if (this.currentChar == '\n'){
                        this.line = 0;
                        this.column++;
                    }

                    currentChar = ManipuladorArquivo.readChar("Entrada.txt", charPosition);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERRO: Ocorreu um erro na leitura do Arquivo\n");
                }
            } else{
                System.out.println("\ncabo o arquivo\n");
            }

        } else{
            System.out.println("ERRO: Simbolo incompatível com a estrutura léxica da linguagem. LINHA = \n" + (char) this.line + " COLUNA = " + (char) this.column);
        }
    }

    private void takeIt(){
        currentSpelling.append(this.currentChar);
            
        if (this.charPosition++ != this.fileSize - 1){
            try {
                this.column++;
                if (this.currentChar == '\n'){
                    this.column = 0;
                    this.line++;
                }

                currentChar = ManipuladorArquivo.readChar("Entrada.txt", charPosition);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERRO: Ocorreu um erro na leitura do Arquivo\n");
            }
        } else{
            this.currentChar = 0;
        }
    }

    private boolean ehDigito (char entrada){
        if ((entrada > 47) && (entrada < 58)){
            return true;
        } else {
            return false;
        }
    }

    private boolean ehLetra (char entrada) {
        if (((entrada > 64) && (entrada < 91)) || ((entrada > 96) && (entrada < 123))){
            return true;
        } else {
            return false;
        }
    }

    private boolean ehGrafico (char entrada) {
        if (entrada > 31 && entrada != 127){
            return true;
        } else {
            return false;
        }
    }

    private byte scanToken(){
        if (ehLetra(this.currentChar)){
            takeIt();
            while (ehLetra(this.currentChar) || ehDigito(this.currentChar)){
                takeIt();
            }
            return Token.IDENTIFIER;
        }

        if (ehDigito(this.currentChar)){
            takeIt();
            while(ehDigito(this.currentChar)){
                takeIt();
            }
            if (this.currentChar == '.'){
                takeIt();
                while(ehDigito(this.currentChar)){
                    takeIt();
                }
                return Token.FLOAT_LITERAL;
            }else{
                return Token.INT_LITERAL;
            }
        }

        if (this.currentChar == '.'){
            boolean flag = true; // Vai ser usado para verificar se houve apenas um ponto sozinho
                                 // nesse caso, o Token é do tipo Ponto (DOT)
            takeIt();
            while(ehDigito(this.currentChar)){
                takeIt();
                flag = false;
            }

            if (flag){
                return Token.DOT;
            }

            return Token.FLOAT_LITERAL;
        }

        if (this.currentChar == ':'){
            takeIt();
            if (this.currentChar == '='){
                takeIt();
                return Token.BECOMES;
            }
            return Token.COLON;
        }

        if (this.currentChar == '('){
            takeIt();
            return Token.LPAR;
        }

        if (this.currentChar == ')'){
            takeIt();
            return Token.RPAR;
        }

        if (this.currentChar == ';'){
            takeIt();
            return Token.SEMICOLON;
        }

        if (this.currentChar == ','){
            takeIt();
            return Token.COMMA;
        }

        if (this.currentChar == '>'){
            takeIt();
            if (this.currentChar == '='){
                takeIt();
            }
            return Token.OPERATOR_REL;
        }

        if (this.currentChar == '<'){
            takeIt();
            if (this.currentChar == '=' || this.currentChar == '>'){
                takeIt();
            }
            return Token.OPERATOR_REL;         
        }

        if (this.currentChar == '='){
            takeIt();
            return Token.OPERATOR_REL;
        }

        if (this.currentChar == '+' || this.currentChar == '-'){
            takeIt();
            return Token.OPERATOR_ADD;
        }

        if (this.currentChar == '*' || this.currentChar == '/'){
            takeIt();
            return Token.OPERATOR_MUL;
        }

        if (this.currentChar == '\000'){
            takeIt();
            return Token.EOT;
        }
        takeIt();
        return Token.OUTROS;
    }

    private void scanSeparator(){
        if (this.currentChar == '!'){
            takeIt();
            while (ehGrafico(this.currentChar)){
                takeIt();
            }
            take('\n');
        }
        if (this.currentChar == ' ' || this.currentChar == '\n' || this.currentChar == '\t' || this.currentChar == '\r'){
            takeIt();
        }
    }

    public Token scan(){
        int line, column;
        
        while (this.currentChar == '!' || this.currentChar == ' ' || this.currentChar == '\n' || this.currentChar == '\t' || this.currentChar == '\r'){
            scanSeparator();
        }

        this.currentSpelling = new StringBuffer("");
        line = this.line;
        column = this.column;
        
        if (this.currentChar == 0){
            this.currentKind = Token.EOT;
        }else{    
            this.currentKind = this.scanToken();
        }
        
        return new Token(this.currentKind, this.currentSpelling.toString(), line, column);
    }
}