package ArvoreSintaticaAbstrata.Fator;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.ExpressaoSimples.SimpleExpression;
import ArvoreSintaticaAbstrata.Operator.RelOperator;

public class Expression extends Fator {
    public SimpleExpression simpleExpression;
    public RelOperator operator;
    public SimpleExpression next;

    public Expression(SimpleExpression simpleExpression) {
        this.simpleExpression = simpleExpression;
        this.operator = null;
        this.next = null;
    }

    public void setOperator(RelOperator operator) {
        this.operator = operator;
    }

    public void setSimpleExpression(SimpleExpression next) {
        this.next = next;
    }

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitExpression(this, arg);
    }
}
