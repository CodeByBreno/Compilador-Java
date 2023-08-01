package ArvoreSintaticaAbstrata.Fator;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Identificador.Identifier;

public class Variable extends Fator {
    public Identifier id;

    public Variable(Identifier id) {
        this.id = id;
    }

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitVariable(this, arg);
    }
}
