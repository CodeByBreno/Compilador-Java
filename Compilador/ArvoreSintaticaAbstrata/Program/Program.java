package ArvoreSintaticaAbstrata.Program;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Corpo.Body;
import ArvoreSintaticaAbstrata.Identificador.Identifier;

public class Program {
    public Identifier id;
    public Body body;

    public Program(Identifier id, Body body) {
        this.id = id;
        this.body = body;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitProgram(this, arg);
    }
}
