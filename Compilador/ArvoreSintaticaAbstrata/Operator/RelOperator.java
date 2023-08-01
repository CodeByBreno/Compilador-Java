package ArvoreSintaticaAbstrata.Operator;

import ArvoreSintaticaAbstrata.Visitor;

public class RelOperator extends Operator {
    
    public RelOperator(String speeling) {
        super(speeling);
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitRelOperator(this, arg);
    }
}
