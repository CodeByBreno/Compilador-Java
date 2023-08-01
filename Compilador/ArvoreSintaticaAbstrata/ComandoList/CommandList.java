package ArvoreSintaticaAbstrata.ComandoList;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Comando.Command;

public class CommandList {
    public Command command;
    public CommandList next;

    public CommandList(Command command, CommandList next) {
        this.command = command;
        this.next = next;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitCommandList(this, arg);
    }
}
