package ArvoreSintaticaAbstrata.Fator.Literal;

import ArvoreSintaticaAbstrata.Visitor;

public class FloatLiteral extends Literal {

    public FloatLiteral(String speeling) {
        super();
        this.speeling = speeling;
    }
    
    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitFloatLiteral(this, arg);
    }
}
