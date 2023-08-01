package ArvoreSintaticaAbstrata.Operator;

import ArvoreSintaticaAbstrata.Visitor;

public class AdOperator extends Operator {
    public AdOperator(String speeling) {
        super(speeling);
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitAdOperator(this, arg);
    }
}
