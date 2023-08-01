package ArvoreSintaticaAbstrata.Comando;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.ComandoList.CommandList;

public class CompositeCommand extends Command {
    public CommandList commandList;

    public CompositeCommand(CommandList commandList) {
        this.commandList = commandList;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitCompositeCommand(this, arg);
    }
}
