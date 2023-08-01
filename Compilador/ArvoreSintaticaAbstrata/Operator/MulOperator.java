package ArvoreSintaticaAbstrata.Operator;

import ArvoreSintaticaAbstrata.Visitor;

public class MulOperator extends Operator {
    
    public MulOperator(String speeling) {
        super(speeling);
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitMulOperator(this, arg);
    }
}
