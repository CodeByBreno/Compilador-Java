package GeracaoCodigo;

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

public class CodeGenerator implements Visitor {
    public Object visitProgram(Program program, Object arg) {
        if(program != null) {
            program.visit(this, arg);
            // emit(Instruction.);
        }
        return null;
    }

    public Object visitBody(Body body, Object arg) {
        return null;
    }

    public Object visitDeclarations(Declarations declarations, Object arg) {
        return null;
    }
    public Object visitVariableDeclaration(VariableDeclaration declaration, Object arg) {
        return null;
    }
    public Object visitIDList(IDList list, Object arg) {
        return null;
    }
    public Object visitSimpleType(SimpleType simple_type, Object arg) {
        return null;
    }

    public Object visitCommandList(CommandList commands, Object arg) {
        return null;
    }
    public Object visitCommand(Command command, Object arg) {
        return null;
    }
    public Object visitAttribution(Attribution attribution, Object arg) {
        return null;
    }
    public Object visitCondition(Condition condition, Object arg) {
        return null;
    }
    public Object visitIteration(Iteration iteration, Object arg) {
        return null;
    }
    public Object visitCompositeCommand(CompositeCommand command, Object arg) {
        return null;
    }

    public Object visitExpression(Expression expression, Object arg) {
        return null;
    }
    
    public Object visitAdOperator(AdOperator operator, Object arg) {
        return null;
    }
    public Object visitMulOperator(MulOperator operator, Object arg) {
        return null;
    }
    public Object visitRelOperator(RelOperator operator, Object arg) {
        return null;
    }
    public Object visitSimpleExpression(SimpleExpression simpleExpression, Object arg) {
        return null;
    }
    public Object visitTermo(Termo termo, Object arg) {
        return null;
    }
    public Object visitFator(Fator fator, Object arg) {
        return null;
    }

    public Object visitLiteral(Literal literal, Object arg) {
        return null;
    }
    public Object visitVariable(Variable variable, Object arg) {
        return null;
    }
    public Object visitIdentifier(Identifier identifier, Object arg) {
        return null;
    }
    public Object visitBooleanLiteral(BooleanLiteral booleanLiteral, Object arg) {
        return null;
    }
    public Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg) {
        return null;
    }
    public Object visitFloatLiteral(FloatLiteral floatLiteral, Object arg) {
        return null;
    }
}
