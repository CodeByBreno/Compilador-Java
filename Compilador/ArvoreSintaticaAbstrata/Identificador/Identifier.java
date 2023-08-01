package ArvoreSintaticaAbstrata.Identificador;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.DeclaracaoVariavel.VariableDeclaration;

public class Identifier {
    public String speeling;
    public VariableDeclaration declaration;

    public Identifier(String speeling){
        this.speeling = speeling;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitIdentifier(this, arg);
    }
}
