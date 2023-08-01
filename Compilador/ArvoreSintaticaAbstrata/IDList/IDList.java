package ArvoreSintaticaAbstrata.IDList;

import java.util.ArrayList;
import java.util.List;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Identificador.Identifier;

public class IDList {
    public List<Identifier> idList;

    public IDList() {
        idList = new ArrayList<Identifier>();
    }

    public IDList(Identifier id) {
        idList = new ArrayList<Identifier>();
        idList.add(id);
    }

    public Object visit(Visitor visitor, Object arg) {
        return visitor.visitIDList(this, arg);
    }
}
