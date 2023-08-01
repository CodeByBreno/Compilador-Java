package AnaliseContexto.IdentificationTable;

import java.util.ArrayList;
import java.util.HashMap;

import ArvoreSintaticaAbstrata.DeclaracaoVariavel.VariableDeclaration;

public class IdentificationTable {
    ArrayList<HashMap<String, VariableDeclaration>> scopes;
    public String log;

    public IdentificationTable() {
        scopes = new ArrayList<HashMap<String, VariableDeclaration>>();
        log = new String();
    }

    public void add(String id, VariableDeclaration declaration) {
        if (scopes.get(scopes.size() - 1).put(id, declaration) != null) {
            System.out.println("ERRO: " + id + " já foi declarado\n");
            // System.out.println("SOURCE: LINHA = " + currentToken.line + " COLUNA = " + currentToken.column);
            // System.out.println("ULTIMO TOKEN LIDO: " + currentToken.spelling);
            System.exit(1);
        }
        log += String.format("scope: %-2d | %-15s | %s\n", scopes.size(), id, declaration.simpleType.type.getSpeeling());
    }

    public VariableDeclaration get(String id) {
        for(int index = scopes.size() - 1 ; index >= 0 ; index--) {
            VariableDeclaration declaration = scopes.get(index).get(id);
            if(declaration != null) return declaration;
        }
        return null;
    }
    
    public void openScope() {
        scopes.add(new HashMap<String, VariableDeclaration>());
    }
    
    public void closeScope() {
        scopes.remove(scopes.size() - 1);
    }
}
