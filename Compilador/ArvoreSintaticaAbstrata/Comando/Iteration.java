package ArvoreSintaticaAbstrata.Comando;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Fator.Expression;

public class Iteration extends Command {
    public Expression expression;
    public Command command;

    public Iteration(Expression expression, Command command) {
        this.expression = expression;
        this.command = command;
    }

    @Override
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitIteration(this, arg);
    }
}
