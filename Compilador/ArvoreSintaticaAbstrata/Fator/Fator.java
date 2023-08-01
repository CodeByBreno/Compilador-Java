package ArvoreSintaticaAbstrata.Fator;

import AnaliseContexto.Type;
import ArvoreSintaticaAbstrata.Visitor;

public class Fator {
    public Type type;
    public int line, column;

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitFator(this, arg);
    }
}
