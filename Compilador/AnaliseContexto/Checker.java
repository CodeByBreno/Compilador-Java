package AnaliseContexto;

import java.util.List;

import AnaliseContexto.IdentificationTable.IdentificationTable;
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
import ArvoreSintaticaAbstrata.Operator.Operator;
import ArvoreSintaticaAbstrata.Operator.RelOperator;
import ArvoreSintaticaAbstrata.Program.Program;
import ArvoreSintaticaAbstrata.Termo.Termo;
import ArvoreSintaticaAbstrata.TipoSimples.SimpleType;

public class Checker implements Visitor {
    IdentificationTable idTable;

    public Checker(){
        idTable = new IdentificationTable();
    }

    public void check(Program program) {
        System.out.println("Iniciando analise de contexto.");

        if(program != null) {
            program.visit(this, null);
        }

        System.out.println(idTable.log);

        System.out.println("Analise de contexto completa!");
    }

    private void erroDeclarationNotExists(Identifier id, int line, int column){
        System.out.println("ERRO: " + id.speeling + " nao foi declarado\n");
        System.out.println("SOURCE: LINHA = " + line + " COLUNA = " + column);
        System.exit(1);
    }

    private void erroConditionType(Type type, int line, int column){
        System.out.println("ERRO: A condiçao espera  um tipo booleano. Mas o tipo identificado foi " + type.getSpeeling() + "\n");
        System.out.println("SOURCE: LINHA = " + line + " COLUNA = " + column);
        System.exit(1);
    }

    private void erroAssignTypeError(Type varType, Type expType, int line ,int column){
        System.out.println("ERRO: O tipo " + expType.getSpeeling() + " nao corresponde ao tipo " + varType.getSpeeling() + "\n");
        System.out.println("SOURCE: LINHA = " + line + " COLUNA = " + column);
        System.exit(1);
    }

    private void erroExpressionTypeError(Type varType, Type expType, Operator op, int line, int column){
        System.out.println("ERRO: Tipo dos operandos invalidos para a operaçao: " + varType.getSpeeling() + op.speeling + expType.getSpeeling() + "\n");
        System.out.println("SOURCE: LINHA = " + line + " COLUNA = " + column);
        System.exit(1);
    }

    public Object visitProgram(Program program, Object arg){

        if(program.body != null) {
            program.body.visit(this, arg);
        }

        return null;
    }

    public Object visitBody(Body body, Object arg){
        idTable.openScope();

        if(body.declarations != null) {
            body.declarations.visit(this, arg);
        }
        
        if(body.compositeCommand != null) {
            body.compositeCommand.visit(this, arg);
        }

        idTable.closeScope();
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
        
        if(declaration.idList != null) {
            List<?> list = (List<?>) declaration.idList.visit(this, arg);
            list.forEach(item -> idTable.add((( Identifier )item).speeling, declaration));
        }

        if(declaration.simpleType != null) {
            declaration.simpleType.visit(this, arg);
        }

        return null;
    }

    public Object visitIDList(IDList list, Object arg){
        return list.idList;
    }

    public Object visitSimpleType(SimpleType simpleType, Object arg){
        return null;
    }

    public Object visitCommandList(CommandList commands, Object arg){
        if(commands.command != null) {
            commands.command.visit(this, arg);
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
        // Test if type of variable and expression is the same
        if(attribution.variable != null && attribution.expression != null) {
            Type attrType = (Type) attribution.variable.visit(this, arg);
            Type exprType = (Type) attribution.expression.visit(this, arg);

            if(!attrType.equals(exprType)) {
                erroAssignTypeError(attrType, exprType, attribution.expression.line, attribution.expression.column);
            }
        }

        return null;
    }

    public Object visitCondition(Condition condition, Object arg){

        Type expType = (Type) condition.expression.visit(this, arg);

        if(!expType.equals(expType)) {
            erroConditionType(expType, condition.expression.line, condition.expression.column);
        }

        condition.commandIF.visit(this, arg);
        condition.commandELSE.visit(this, arg);

        return null;
    }

    public Object visitIteration(Iteration iteration, Object arg){

        Type expType = (Type) iteration.expression.visit(this, arg);
        if(!expType.equals(Type.Bool)) {
            erroConditionType(expType, iteration.expression.line, iteration.expression.column);
        }

        iteration.command.visit(this, arg);

        return null;
    }

    public Object visitCompositeCommand(CompositeCommand command, Object arg){

        if(command.commandList != null) {
            command.commandList.visit(this, arg);
        }

        return null;
    }

    public Object visitAdOperator(AdOperator operator, Object arg){
        return null;
    }
    
    public Object visitMulOperator(MulOperator operator, Object arg){
        return null;
    }
    
    public Object visitRelOperator(RelOperator operator, Object arg){
        return null;
    }

    public Object visitExpression(Expression expression, Object arg){
        Type st1Type = (Type) expression.simpleExpression.visit(this, arg);

        if(expression.next != null && expression.operator != null) {
            Type st2Type = (Type) expression.next.visit(this, arg);
            
            Type aux = Type.Bool;
            switch(expression.operator.speeling) {
                case ">": case "<": case ">=": case "<=":
                    if(st1Type.equals(aux) || st2Type.equals(aux)) {
                        erroExpressionTypeError(st1Type, st2Type, expression.operator, expression.line, expression.column);
                    }
                    break;
                case "=": case "<>":
                    if(st1Type.equals(aux) ^ st2Type.equals(aux)) {
                        erroExpressionTypeError(st1Type, st2Type, expression.operator, expression.line, expression.column);
                    }
            }

            return Type.Bool;
        }

        return st1Type;
    }

    public Object visitSimpleExpression(SimpleExpression simpleExpression, Object arg){
        Type t1Type = (Type) simpleExpression.termo.visit(this, arg);

        if(simpleExpression.next != null && simpleExpression.operator != null) {
            Type t2Type = (Type) simpleExpression.next.visit(this, arg);

            Type aux = Type.Bool;
            switch(simpleExpression.operator.speeling) {
                case "+": case "-": 
                    if(t1Type.equals(aux) || t2Type.equals(aux)) {
                        erroExpressionTypeError(t1Type, t2Type, simpleExpression.operator, simpleExpression.line, simpleExpression.column);
                    }
                    if(t1Type.equals(Type.Float) || t2Type.equals(Type.Float)) {
                        return Type.Float;
                    }
                    break;
                case "or":
                    if(!t1Type.equals(aux) || !t2Type.equals(aux)) {
                        erroExpressionTypeError(t1Type, t2Type, simpleExpression.operator, simpleExpression.line, simpleExpression.column);
                    }
            }
        }

        return t1Type;
    }

    public Object visitTermo(Termo termo, Object arg){
        Type f1Type = (Type) termo.fator.visit(this, arg);
        if(termo.next != null && termo.operator != null) {
            Type f2Type = (Type) termo.next.visit(this, arg);

            Type aux = Type.Bool;
            switch(termo.operator.speeling) {
                case "*": case "/": 
                    if(f1Type.equals(aux) || f2Type.equals(aux)) {
                        erroExpressionTypeError(f1Type, f2Type, termo.operator, termo.line, termo.column);
                    }
                    if(f1Type.equals(Type.Float) || f2Type.equals(Type.Float)) {
                        return Type.Float;
                    }
                    break;
                case "and":
                    if(!f1Type.equals(aux) || !f2Type.equals(aux)) {
                        erroExpressionTypeError(f1Type, f2Type, termo.operator, termo.line, termo.column);
                    }
            }
        }

        return f1Type;
    }

    public Object visitFator(Fator fator, Object arg){
        return null;
    }

    public Object visitVariable(Variable variable, Object arg){

        if(variable.id != null) {
            VariableDeclaration declaration = (VariableDeclaration) variable.id.visit(this, arg);
            if(declaration == null) {
                erroDeclarationNotExists(variable.id, variable.line, variable.column);
            }
            variable.type = declaration.simpleType.type;
        }

        return variable.type;
    }

    public Object visitLiteral(Literal literal, Object arg){
        return null;
    }
    
    public Object visitIdentifier(Identifier identifier, Object arg){
        // GET table reference and put on declaration
        identifier.declaration = idTable.get(identifier.speeling);
        return identifier.declaration;
    }

    public Object visitBooleanLiteral(BooleanLiteral booleanLiteral, Object arg){
        return Type.Bool;
    }

    public Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg){
        return Type.Int;
    }

    public Object visitFloatLiteral(FloatLiteral floatLiteral, Object arg){
        return Type.Float;
    }

}
