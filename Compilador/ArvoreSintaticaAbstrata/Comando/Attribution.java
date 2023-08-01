package ArvoreSintaticaAbstrata.Comando;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Fator.Expression;
import ArvoreSintaticaAbstrata.Fator.Variable;

public class Attribution extends Command {
    public Variable variable;
    public Expression expression;

    public Attribution(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitAttribution(this, arg);
    }
}
