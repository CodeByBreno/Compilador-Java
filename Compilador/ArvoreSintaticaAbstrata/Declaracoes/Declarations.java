package ArvoreSintaticaAbstrata.Declaracoes;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.DeclaracaoVariavel.VariableDeclaration;

public class Declarations {
    public VariableDeclaration variableDeclaration;
    public Declarations next;

    public Declarations(VariableDeclaration varDeclaration, Declarations next) {
        this.variableDeclaration = varDeclaration;
        this.next = next;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitDeclarations(this, arg);
    }
}
