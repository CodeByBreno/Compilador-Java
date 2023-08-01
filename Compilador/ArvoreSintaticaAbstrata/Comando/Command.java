package ArvoreSintaticaAbstrata.Comando;

import ArvoreSintaticaAbstrata.Visitor;

public class Command {
    
    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitCommand(this, arg);
    }
}
