package GeracaoCodigo;

public class Instruction {
    public byte op_code;
    public byte register;
    public int length;
    public String operand;

    public static final byte
        LOADop = 0, LOADAop = 1,
        LOADIop = 2, LOADLop = 3,
        STOREop = 4, STOREIop = 5,
        CALLop = 6, CALLIop = 7,
        RETURNop = 8, 
        PUSHop = 10, POPop = 11,
        JUMPop = 12, JUMPIop = 13,
        JUMPIFop = 14, HALTop = 15;

    public static final byte
        CBr = 0, CTr = 1, PBr = 2, PTr = 3,
        SBr = 4, STr = 5, HBr = 6, HTr = 7,
        LBr = 8, L1r = 9, L2r = 10, L3r = 11,
        L4r = 12, L5r = 13, L6r = 14, CPr = 15;

    public Instruction(byte op, byte r, int n, String d) {
        op_code = op;
        register = r;
        length = n;
        operand = d;
    }

    public static String getRegisterSpeeling(byte reg) {
        switch(reg) {
            case 0: return "CB";
            case 1: return "CT";
            case 2: return "PB";
            case 3: return "PT";
            case 4: return "SB";
            case 5: return "ST";
            case 6: return "HB";
            case 7: return "HT";
            case 8: return "LB";
            case 9: return "L1";
            case 10: return "L2";
            case 11: return "L3";
            case 12: return "L4";
            case 13: return "L5";
            case 14: return "L6";
            case 15: return "CP";
            default: return null;
        }
    }
}
