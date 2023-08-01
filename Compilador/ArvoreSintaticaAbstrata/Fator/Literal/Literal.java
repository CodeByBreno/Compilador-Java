package ArvoreSintaticaAbstrata.Fator.Literal;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Fator.Fator;

public class Literal extends Fator {
    public String speeling;

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitLiteral(this, arg);
    }
}
