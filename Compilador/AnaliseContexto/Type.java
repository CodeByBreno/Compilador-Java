package AnaliseContexto;

public class Type {
    private byte kind;
    public int size;

    public static final byte BOOL = 0, INT = 1, FLOAT = 2, ERROR = -1;

    public Type(byte kind, int size) {
        this.kind = kind;
        this.size = size;
    }

    public String getSpeeling() {
        switch(kind) {
            case BOOL: return "Boolean";
            case INT: return "Integer";
            case FLOAT: return "Real";
            default: return "ERROR";
        }
    }

    public boolean equals(Object other) {
        Type aux = (Type) other;
        return (this.kind == aux.kind
                || this.kind == ERROR
                || aux.kind == ERROR);
    }

    public static Type Bool = new Type(BOOL, 1);
    public static Type Int = new Type(INT, 2);
    public static Type Float = new Type(FLOAT, 4);
    public static Type Error = new Type(ERROR, -1);
}
