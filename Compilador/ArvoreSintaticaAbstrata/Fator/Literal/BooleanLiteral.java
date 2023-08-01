package ArvoreSintaticaAbstrata.Fator.Literal;

import ArvoreSintaticaAbstrata.Visitor;

public class BooleanLiteral extends Literal {

    public BooleanLiteral(String speeling) {
        super();
        this.speeling = speeling;
    }

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitBooleanLiteral(this, arg);
    }
}
