package ASTPrinter;

import ArvoreSintaticaAbstrata.Visitor;
import ArvoreSintaticaAbstrata.Comando.Attribution;
import ArvoreSintaticaAbstrata.Comando.Command;
import ArvoreSintaticaAbstrata.Comando.CompositeCommand;
import ArvoreSintaticaAbstrata.Comando.Condition;
import ArvoreSintaticaAbstrata.Comando.Iteration;
import ArvoreSintaticaAbstrata.ComandoList.CommandList;
import ArvoreSintaticaAbstrata.Corpo.Body;
import ArvoreSintaticaAbstrata.DeclaracaoVariavel.VariableDeclaration;
import ArvoreSintaticaAbstrata.Declaracoes.Declarations;
import ArvoreSintaticaAbstrata.ExpressaoSimples.SimpleExpression;
import ArvoreSintaticaAbstrata.Fator.Expression;
import ArvoreSintaticaAbstrata.Fator.Fator;
import ArvoreSintaticaAbstrata.Fator.Variable;
import ArvoreSintaticaAbstrata.Fator.Literal.BooleanLiteral;
import ArvoreSintaticaAbstrata.Fator.Literal.FloatLiteral;
import ArvoreSintaticaAbstrata.Fator.Literal.IntegerLiteral;
import ArvoreSintaticaAbstrata.Fator.Literal.Literal;
import ArvoreSintaticaAbstrata.IDList.IDList;
import ArvoreSintaticaAbstrata.Identificador.Identifier;
import ArvoreSintaticaAbstrata.Operator.AdOperator;
import ArvoreSintaticaAbstrata.Operator.MulOperator;
import ArvoreSintaticaAbstrata.Operator.RelOperator;
import ArvoreSintaticaAbstrata.Program.Program;
import ArvoreSintaticaAbstrata.Termo.Termo;
import ArvoreSintaticaAbstrata.TipoSimples.SimpleType;

public class Printer implements Visitor {
    private int level;
    StringBuilder impressao;

    public Printer(){
        level = 0;
        impressao = new StringBuilder();
    }

    public String print(Program program) {
        appendLine("=== Iniciando a Impressao da Arvore Abstrata Sintatica ===\n");
        if(program != null) {
            program.visit(this, null);
        }

        appendLine("\n==================== Fim da impressão ====================");

        System.out.println(impressao.toString());
        return impressao.toString();
    }

    private void ident() {
        for(int i=0 ; i<level ; ++i) {
            impressao.append("|   ");
        }
    }

    private void appendLine(String name, String value) {
        ident();
        impressao.append(name + " " + value + "\n");
    }
    
    private void appendLine(String name) {
        ident();
        impressao.append(name + "\n");
    }

    public Object visitProgram(Program program, Object arg){
        appendLine("Programa");
        level++;

        if(program.id != null) {
            program.id.visit(this, arg);
        }
        
        if(program.body != null) {
            program.body.visit(this, arg);
        }

        level--;
        return null;
    }

    public Object visitBody(Body body, Object arg){
        appendLine("Body");
        level++;
        
        if(body.declarations != null) {
            appendLine("Declarations");
            level++;
            body.declarations.visit(this, arg);
            level--;
        }
        
        if(body.compositeCommand != null) {
            body.compositeCommand.visit(this, arg);
        }

        level--;
        return null;
    }

    public Object visitDeclarations(Declarations declarations, Object arg){
        
        if(declarations.variableDeclaration != null) {
            declarations.variableDeclaration.visit(this, arg);
        }

        if(declarations.next != null) {
            declarations.next.visit(this, arg);
        }

        return null;
    }

    public Object visitVariableDeclaration(VariableDeclaration declaration, Object arg){
        appendLine("VariableDeclaration");
        level++;
        
        if(declaration.idList != null) {
            appendLine("IDList");
            level++;
            declaration.idList.visit(this, arg);
            level--;
        }

        if(declaration.simpleType != null) {
            declaration.simpleType.visit(this, arg);
        }

        level--;
        return null;
    }

    public Object visitIDList(IDList list, Object arg){
        list.idList.forEach(item -> item.visit(this, arg));
        return null;
    }

    public Object visitSimpleType(SimpleType simpleType, Object arg){
        appendLine("SimpleType", simpleType.type.getSpeeling());
        return null;
    }

    public Object visitCommandList(CommandList commands, Object arg){
        if(commands.command != null) {
            appendLine("Command");
            level++;
            commands.command.visit(this, arg);
            level--;
        }

        if(commands.next != null) {
            commands.next.visit(this, arg);
        }

        return null;
    }

    public Object visitCommand(Command command, Object arg){
        return null;
    }

    public Object visitAttribution(Attribution attribution, Object arg){
        appendLine("Attribution");
        level++;

        if(attribution.variable != null) {
            attribution.variable.visit(this, arg);
        }

        if(attribution.expression != null) {
            attribution.expression.visit(this, arg);
        }

        level--;
        return null;
    }

    public Object visitCondition(Condition condition, Object arg){
        appendLine("Condition");
        level++;

        if(condition.expression != null) {
            condition.expression.visit(this, arg);
        }

        if(condition.commandIF != null) {
            appendLine("CommandIF");
            level++;
            condition.commandIF.visit(this, arg);
            level--;
        }

        if(condition.commandELSE != null) {
            appendLine("CommandELSE");
            level++;
            condition.commandELSE.visit(this, arg);
            level--;
        }

        level--;
        return null;
    }

    public Object visitIteration(Iteration iteration, Object arg){
        appendLine("Iteration");
        level++;

        if(iteration.expression != null) {
            iteration.expression.visit(this, arg);
        }

        if(iteration.command != null) {
            appendLine("Command");
            level++;
            iteration.command.visit(this, arg);
            level--;
        }

        level--;
        return null;
    }

    public Object visitCompositeCommand(CompositeCommand command, Object arg){
        appendLine("CompositeCommand");
        level++;

        if(command.commandList != null) {
            appendLine("CommandList");
            level++;
            command.commandList.visit(this, arg);
            level--;
        }

        level--;
        return null;
    }

    public Object visitExpression(Expression expression, Object arg){
        appendLine("Expression");
        level++;

        if(expression.simpleExpression != null) {
            appendLine("SimpleExpression");
            level++;
            expression.simpleExpression.visit(this, arg);
            level--;
        }

        if(expression.operator != null) {
            expression.operator.visit(this, arg);
        }

        if(expression.next != null) {
            appendLine("SimpleExpression");
            level++;
            expression.next.visit(this, arg);
            level--;
        }

        level--;
        return null;
    }
    
    public Object visitAdOperator(AdOperator operator, Object arg){
        appendLine("AdOperator", operator.speeling);
        return null;
    }
    
    public Object visitMulOperator(MulOperator operator, Object arg){
        appendLine("MulOperator", operator.speeling);
        return null;
    }
    
    public Object visitRelOperator(RelOperator operator, Object arg){
        appendLine("RelOperator", operator.speeling);
        return null;
    }

    public Object visitSimpleExpression(SimpleExpression simpleExpression, Object arg){
        if(simpleExpression.termo != null) {
            appendLine("Termo");
            level++;
            simpleExpression.termo.visit(this, arg);
            level--;
        }

        if(simpleExpression.operator != null) {
            simpleExpression.operator.visit(this, arg);
        }

        if(simpleExpression.next != null) {
            simpleExpression.next.visit(this, arg);
        }

        return null;
    }

    public Object visitTermo(Termo termo, Object arg){
        
        if(termo.fator != null) {
            appendLine("Fator");
            level++;
            termo.fator.visit(this, arg);
            level--;
        }

        if(termo.operator != null) {
            termo.operator.visit(this, arg);
        }

        if(termo.next != null) {
            termo.next.visit(this, arg);
        }
        
        return null;
    }

    public Object visitFator(Fator fator, Object arg){
        return null;
    }

    public Object visitVariable(Variable variable, Object arg){
        appendLine("Variable");
        level++;

        if(variable.id != null) {
            variable.id.visit(this, arg);
        }

        level--;
        return null;
    }

    public Object visitLiteral(Literal literal, Object arg){
        return null;
    }
    
    public Object visitIdentifier(Identifier identifier, Object arg){
        appendLine("Identifier", identifier.speeling);
        return null;
    }

    public Object visitBooleanLiteral(BooleanLiteral booleanLiteral, Object arg){
        appendLine("BooleanLiteral", booleanLiteral.speeling);
        return null;
    }

    public Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg){
        appendLine("IntegerLiteral", integerLiteral.speeling);
        return null;
    }

    public Object visitFloatLiteral(FloatLiteral floatLiteral, Object arg){
        appendLine("FloatLiteral", floatLiteral.speeling);
        return null;
    }

}
