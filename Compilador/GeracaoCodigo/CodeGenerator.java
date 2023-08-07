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

import java.util.Map;
import java.util.HashMap;

public class CodeGenerator implements Visitor {
    private Instruction[] code = new Instruction[1024];
    private Map<Integer, String> labels = new HashMap<Integer, String>();
    private short labelID = 0;
    private short nextInstrAddr = 0;
    private int globalBytes;

    public String encode (Program program) {
        System.out.println("Iniciando geracao de codigo.");
        program.visit(this, null);

        StringBuilder strB = new StringBuilder();

        strB.append(program.id.speeling + ":\n");

        for(int i=0 ; i<nextInstrAddr ; ++i) {
            String label = labels.get(i);
            if(label != null) {
                strB.append("\t" + label + ":\n");
            }

            switch(code[i].op_code) {
                case Instruction.CALLop: 
                    strB.append(String.format("\tCALL %s\n", code[i].operand));
                    break;
                case Instruction.HALTop: 
                    strB.append(String.format("\tHALT\n"));
                    break;
                case Instruction.JUMPop: 
                    strB.append(String.format("\tJUMP %s\n", code[i].operand));
                    break;
                case Instruction.JUMPIFop: 
                    strB.append(String.format("\tJUMPIF (%d) %s\n", code[i].length, code[i].operand));
                    break;
                case Instruction.LOADLop: 
                    strB.append(String.format("\tLOADL %s\n", code[i].operand));
                    break;
                case Instruction.LOADop: 
                    strB.append(String.format("\tLOAD (%d) %s[%s]\n", code[i].length, code[i].operand, Instruction.getRegisterSpeeling(code[i].register)));
                    break;
                case Instruction.POPop: 
                    strB.append(String.format("\tPOP (%d) %s\n", code[i].length, code[i].operand));
                    break;
                case Instruction.PUSHop: 
                    strB.append(String.format("\tPUSH %s\n", code[i].operand));
                    break;
                case Instruction.STOREop: 
                    strB.append(String.format("\tSTORE (%d) %s[%s]\n", code[i].length, code[i].operand, Instruction.getRegisterSpeeling(code[i].register)));
                    break;
                default: break;
            }
        }

        System.out.println("Geracao de codigo finalizada!");
        return strB.toString();
    }

    private String getNewLabel() {
        return String.format("R_%04d", labelID++);
    }

    private void encodeAssign(Variable var) {
        emit(Instruction.STOREop, var.type.size, Instruction.SBr, Integer.toString(var.id.declarationID.address));
    }

    private void encodeFetch(Variable var) {
        emit(Instruction.LOADop, var.type.size, Instruction.SBr, Integer.toString(var.id.declarationID.address));
    }

    private void emit(byte op, int n, byte r, String d) {
        code[nextInstrAddr++] = new Instruction(op, r, n, d);
    }

    public Object visitProgram(Program program, Object arg) {
        program.body.visit(this, arg);
        emit(Instruction.HALTop, 0, (byte) 0, "0");
        return null;
    }

    public Object visitBody(Body body, Object arg) {
        body.declarations.visit(this, arg);
        body.compositeCommand.visit(this, arg);

        emit(Instruction.POPop, (byte) 0, (byte) 0, Integer.toString(globalBytes));
        return null;
    }

    public Object visitDeclarations(Declarations declarations, Object arg) {
        declarations.variableDeclaration.visit(this, arg);
        if(declarations.next != null) {
            declarations.next.visit(this, arg);
        }
        return null;
    }
    public Object visitVariableDeclaration(VariableDeclaration declaration, Object arg) {
        int bytes = (int) declaration.simpleType.visit(this, arg);
        int size = (int) declaration.idList.visit(this, bytes);
        
        emit(Instruction.PUSHop, (byte) 0, (byte) 0, Integer.toString(size*bytes));
        return size*bytes;
    }
    public Object visitIDList(IDList list, Object args) {
        list.idList.forEach(id -> id.visit(this, args));
        return list.idList.size();
    }
    public Object visitSimpleType(SimpleType simple_type, Object arg) {
        return simple_type.type.size;
    }

    public Object visitCommandList(CommandList commands, Object arg) {
        commands.command.visit(this, arg);
        if(commands.next != null)
            commands.next.visit(this, arg);
        return null;
    }
    public Object visitCommand(Command command, Object arg) {
        return null;
    }
    public Object visitAttribution(Attribution attribution, Object arg) {
        attribution.expression.visit(this, arg);
        encodeAssign(attribution.variable);
        return null;
    }
    public Object visitCondition(Condition condition, Object arg) {
        condition.expression.visit(this, arg);

        String g = getNewLabel();
        emit(Instruction.JUMPIFop, (byte) 0, Instruction.CBr, g);

        condition.commandIF.visit(this, arg);

        String h = getNewLabel();
        emit(Instruction.JUMPop, (byte) 0, Instruction.CBr, h);

        labels.put((int) nextInstrAddr, g);

        condition.commandELSE.visit(this, arg);

        labels.put((int) nextInstrAddr, h);
        
        return null;
    }
    public Object visitIteration(Iteration iteration, Object arg) {
        String h = getNewLabel();
        emit(Instruction.JUMPop, (byte) 0, Instruction.CBr, h);

        String g = getNewLabel();
        labels.put((int) nextInstrAddr, g);
        iteration.command.visit(this, arg);

        labels.put((int) nextInstrAddr, h);

        iteration.expression.visit(this, arg);
        emit(Instruction.JUMPIFop, (byte) 1, Instruction.CBr, g);

        return null;
    }
    public Object visitCompositeCommand(CompositeCommand command, Object arg) {
        command.commandList.visit(this, arg);
        return null;
    }

    public Object visitExpression(Expression expression, Object arg) {
        expression.simpleExpression.visit(this, arg);
        
        if(expression.next != null) {
            expression.next.visit(this, arg);
            expression.operator.visit(this, arg);
        }

        return null;
    }
    
    public Object visitAdOperator(AdOperator operator, Object arg) {
        switch(operator.speeling) {
            case "+":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "add");
                break;
            case "-":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "sub");
                break;
            case "or":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "or");
                break;
        }
        return null;
    }
    public Object visitMulOperator(MulOperator operator, Object arg) {
        switch(operator.speeling) {
            case "*":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "mult");
                break;
            case "/":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "div");
                break;
            case "and":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "and");
                break;
        }
        return null;
    }
    public Object visitRelOperator(RelOperator operator, Object arg) {
        switch(operator.speeling) {
            case ">":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "gt");
                break;
            case ">=":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "ge");
                break;
            case "<":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "lt");
                break;
            case "<=":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "le");
                break;
            case "=":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "eq");
                break;
            case "<>":
                emit(Instruction.CALLop, (byte) 0, (byte) 0, "ne");
                break;
        }
        return null;
    }
    public Object visitSimpleExpression(SimpleExpression simpleExpression, Object arg) {
        simpleExpression.termo.visit(this, arg);

        if(simpleExpression.next != null) {
            simpleExpression.next.visit(this, arg);
            simpleExpression.operator.visit(this, arg);
        }

        return null;
    }
    public Object visitTermo(Termo termo, Object arg) {
        termo.fator.visit(this, arg);
        
        if(termo.next != null) {
            termo.next.visit(this, arg);
            termo.operator.visit(this, arg);
        }

        return null;
    }
    public Object visitFator(Fator fator, Object arg) {
        return null;
    }

    public Object visitLiteral(Literal literal, Object arg) {
        return null;
    }
    public Object visitVariable(Variable variable, Object arg) {
        encodeFetch(variable);
        return null;
    }
    public Object visitIdentifier(Identifier identifier, Object args) {
        int typeSize = (int) args;

        identifier.address = globalBytes;
        globalBytes += typeSize;
        
        return null;
    }
    public Object visitBooleanLiteral(BooleanLiteral booleanLiteral, Object arg) {
        emit(Instruction.LOADLop, (byte) 0, (byte) 0, booleanLiteral.speeling);
        return null;
    }
    public Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg) {
        emit(Instruction.LOADLop, (byte) 0, (byte) 0, integerLiteral.speeling);
        return null;
    }
    public Object visitFloatLiteral(FloatLiteral floatLiteral, Object arg) {
        emit(Instruction.LOADLop, (byte) 0, (byte) 0, floatLiteral.speeling);
        return null;
    }
}
