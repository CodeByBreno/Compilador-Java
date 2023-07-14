package AnaliseLexica;

//
// Classe que possue objetos que representam um Token da Linguagem
// Feito por Breno Gabriel
//

public class Token {
    public byte kind;
    public String spelling;
    public int line;
    public int column;

    public Token (byte kind, String spelling, int line, int column) {
        //Código para criar um token da lingaugem
        this.kind = kind;
        this.spelling = spelling;
        this.line = line;
        this.column = column;

        //Caso o tipo do token seja IDENTIFICADOR, então vamos varrer a lista de identificadores (palavras-chave)
        //de interesse e ver se ele é algum deles. Se for, colocamos seu tipo como o número referente à esse 
        //identificador localizado
        if (kind == IDENTIFIER){
            for (int k = BEGIN; k <= PROGRAMA; k++){
                if (spelling.equals(spellings[k])){
                    this.kind = (byte) k;
                    break;
                }
            }

            if (spelling.equals("true") || spelling.equals("false")){
                this.kind = BOOL_LITERAL;
            }

            if (spelling.equals("integer") || 
                spelling.equals("real") ||
                spelling.equals("boolean")){
                this.kind = TIPO_SIMPLES;
            }

            if (spelling.equals("or")){
                this.kind = OPERATOR_ADD;
            }

            if (spelling.equals("and")){
                this.kind = OPERATOR_MUL;
            }
        }
    }

    //Constantes que enumeram os diferentes tipos de token
    public final static byte 
        IDENTIFIER = 0, EOT = 1, OPERATOR_ADD = 2, OPERATOR_MUL = 3,
        OPERATOR_REL = 4, TIPO_SIMPLES = 5, OUTROS = 6, BEGIN = 7, END = 8,
        IF = 9, THEN = 10, ELSE = 11, VAR = 12, WHILE = 13, DO = 14, PROGRAMA = 15, 
        BECOMES = 16, LPAR = 17, RPAR = 18, COLON = 19, SEMICOLON = 20, COMMA = 21, 
        DOT = 22, BOOL_LITERAL = 23, INT_LITERAL = 24, FLOAT_LITERAL = 25;

    //Lista com os tipos de token colocados acima
    public final static String[] spellings = {
        "<identifier>", "<eot>", "<op-ad>", "<op-mul>", "<op-rel>", "<tipo-simples>",
        "<outros>", "begin", "end", "if", "then", "else", "var", "while", "do", "programa",
        "becomes", "(", ")", ":", ";", ",", ".", "<bool-lit>", "<int-lit>", "<float-lit>"
    };

}