package ArvoreSintaticaAbstrata;

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

public interface Visitor {
    public Object visitProgram(Program program, Object arg);

    public Object visitBody(Body body, Object arg);

    public Object visitDeclarations(Declarations declarations, Object arg);
    public Object visitVariableDeclaration(VariableDeclaration declaration, Object arg);
    public Object visitIDList(IDList list, Object arg);
    public Object visitSimpleType(SimpleType simple_type, Object arg);

    public Object visitCommandList(CommandList commands, Object arg);
    public Object visitCommand(Command command, Object arg);
    public Object visitAttribution(Attribution attribution, Object arg);
    public Object visitCondition(Condition condition, Object arg);
    public Object visitIteration(Iteration iteration, Object arg);
    public Object visitCompositeCommand(CompositeCommand command, Object arg);

    public Object visitExpression(Expression expression, Object arg);
    
    public Object visitAdOperator(AdOperator operator, Object arg);
    public Object visitMulOperator(MulOperator operator, Object arg);
    public Object visitRelOperator(RelOperator operator, Object arg);
    public Object visitSimpleExpression(SimpleExpression simpleExpression, Object arg);
    public Object visitTermo(Termo termo, Object arg);
    public Object visitFator(Fator fator, Object arg);

    public Object visitLiteral(Literal literal, Object arg);
    public Object visitVariable(Variable variable, Object arg);
    public Object visitIdentifier(Identifier identifier, Object arg);
    public Object visitBooleanLiteral(BooleanLiteral booleanLiteral, Object arg);
    public Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg);
    public Object visitFloatLiteral(FloatLiteral floatLiteral, Object arg);
}
