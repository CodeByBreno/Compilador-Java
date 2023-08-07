import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import ASTPrinter.Printer;
import AnaliseContexto.Checker;
import AnaliseSintatica.Parser;
import ArvoreSintaticaAbstrata.Program.Program;
import GeracaoCodigo.CodeGenerator;

public class CodeGenerator_Test {
    public static void main(String args[]) throws IOException {
        String path = "Entrada.txt";
        System.out.println("===========================\n");
        Parser parser = new Parser(path);
        Program program = parser.parse();

        Checker checker = new Checker();
        checker.check(program);

        CodeGenerator encoder = new CodeGenerator();
        String codeString = encoder.encode(program);
        
        OutputStream os = (OutputStream) new FileOutputStream( new File("code.txt") );
        OutputStreamWriter osw = new OutputStreamWriter( os, "UTF8" );
        PrintWriter pw = new PrintWriter( osw );
        pw.println( codeString );
        pw.close(); 
        osw.close();
        os.close();
	}
}
