package AnaliseContexto.IdentificationTable;

import java.util.ArrayList;
import java.util.HashMap;

import ArvoreSintaticaAbstrata.Identificador.Identifier;

public class IdentificationTable {
    ArrayList<HashMap<String, Identifier>> scopes;
    public String log;

    public IdentificationTable() {
        scopes = new ArrayList<HashMap<String, Identifier>>();
        log = new String();
    }

    public void add(String idSpeeling, Identifier declarationID) {
        if (scopes.get(scopes.size() - 1).put(idSpeeling, declarationID) != null) {
            System.out.println("ERRO: " + idSpeeling + " ja foi declarado\n");
            // System.out.println("SOURCE: LINHA = " + currentToken.line + " COLUNA = " + currentToken.column);
            // System.out.println("ULTIMO TOKEN LIDO: " + currentToken.spelling);
            System.exit(1);
        }
        log += String.format("scope: %-2d | %-15s | %s\n", scopes.size(), idSpeeling, declarationID.simpleType.type.getSpeeling());
    }

    public Identifier get(String id) {
        for(int index = scopes.size() - 1 ; index >= 0 ; index--) {
            Identifier declarationID = scopes.get(index).get(id);
            if(declarationID != null) return declarationID;
        }
        return null;
    }
    
    public void openScope() {
        scopes.add(new HashMap<String, Identifier>());
    }
    
    public void closeScope() {
        scopes.remove(scopes.size() - 1);
    }
}
