package ArvoreSintaticaAbstrata.Fator.Literal;

import ArvoreSintaticaAbstrata.Visitor;

public class IntegerLiteral extends Literal {

    public IntegerLiteral(String speeling) {
        super();
        this.speeling = speeling;
    }
    
    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitIntegerLiteral(this, arg);
    }
}
