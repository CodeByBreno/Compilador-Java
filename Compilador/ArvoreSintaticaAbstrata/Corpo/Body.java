package ArvoreSintaticaAbstrata.Corpo;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Comando.CompositeCommand;
import ArvoreSintaticaAbstrata.Declaracoes.Declarations;

public class Body {
    public Declarations declarations;
    public CompositeCommand compositeCommand;

    public Body(Declarations declarations, CompositeCommand compositeCommand) {
        this.declarations = declarations;
        this.compositeCommand = compositeCommand;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitBody(this, arg);
    }
}
