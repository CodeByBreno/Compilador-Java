package ArvoreSintaticaAbstrata.Comando;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Fator.Expression;

public class Condition extends Command {
    public Expression expression;
    public Command commandIF;
    public Command commandELSE;

    public Condition(Expression expression, Command commandIF, Command commandELSE) {
        this.expression = expression;
        this.commandIF = commandIF;
        this.commandELSE = commandELSE;
    }

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitCondition(this, arg);
    }
}
