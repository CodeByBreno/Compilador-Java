package ArvoreSintaticaAbstrata.Termo;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Fator.Fator;
import ArvoreSintaticaAbstrata.Operator.MulOperator;

public class Termo {
    public Fator fator;
    public MulOperator operator;
    public Termo next;
    public int line, column;

    public Termo(Fator fator) {
        this.fator = fator;
        this.operator = null;
        this.next = null;
    }

    public void setOperator(MulOperator operator) {
        this.operator = operator;
    }

    public void setTermo(Termo next) {
        this.next = next;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitTermo(this, arg);
    }
}
