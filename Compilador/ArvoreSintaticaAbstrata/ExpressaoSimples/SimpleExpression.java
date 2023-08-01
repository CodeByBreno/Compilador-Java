package ArvoreSintaticaAbstrata.ExpressaoSimples;

import AnaliseContexto.Type;
import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Operator.AdOperator;
import ArvoreSintaticaAbstrata.Termo.Termo;

public class SimpleExpression {
    public Termo termo;
    public AdOperator operator;
    public SimpleExpression next;
    public Type type;
    public int line, column;

    public SimpleExpression(Termo termo) {
        this.termo = termo;
        this.operator = null;
        this.next = null;
    }

    public void setOperator(AdOperator operator) {
        this.operator = operator;
    }

    public void setSimpleExpression(SimpleExpression next) {
        this.next = next;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitSimpleExpression(this, arg);
    }
}
