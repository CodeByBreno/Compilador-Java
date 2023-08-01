package ArvoreSintaticaAbstrata.TipoSimples;

import AnaliseContexto.Type;
import ArvoreSintaticaAbstrata.Visitor;

public class SimpleType {
    // public String type;
    public Type type;

    public SimpleType(Type type){
        this.type = type;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitSimpleType(this, arg);
    }
}
