package ArvoreSintaticaAbstrata.Identificador;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.TipoSimples.SimpleType;

public class Identifier {
    public String speeling;
    public Identifier declarationID;
    public SimpleType simpleType;
    public int address;

    public Identifier(String speeling){
        this.speeling = speeling;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitIdentifier(this, arg);
    }
}
