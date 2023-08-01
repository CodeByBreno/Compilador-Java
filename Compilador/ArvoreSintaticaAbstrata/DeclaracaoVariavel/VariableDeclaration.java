package ArvoreSintaticaAbstrata.DeclaracaoVariavel;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.IDList.IDList;
import ArvoreSintaticaAbstrata.TipoSimples.SimpleType;

public class VariableDeclaration {
    public IDList idList;
    public SimpleType simpleType;

    public VariableDeclaration(IDList idList, SimpleType simpleType) {
        this.idList = idList;
        this.simpleType = simpleType;
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitVariableDeclaration(this, arg);
    }
}
