import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import ASTPrinter.Printer;
import AnaliseSintatica.Parser;
import ArvoreSintaticaAbstrata.Program.Program;

public class AST_Printer_Test {
    public static void main(String args[]) throws IOException {
		String path = "Entrada.txt";
		System.out.println("===========================\n");
		Parser parser = new Parser(path);
        Program program = parser.parse();
        Printer printer = new Printer();
        String treeString = printer.print(program);

        // FileWriter file = new FileWriter(new File("out.txt"));
        // file.write(treeString);
        // file.close();

        OutputStream os = (OutputStream) new FileOutputStream( new File("out_AST.txt") );
        OutputStreamWriter osw = new OutputStreamWriter( os, "UTF8" );
        PrintWriter pw = new PrintWriter( osw );
        System.out.println("Prestes a printar a arvore: ");
        pw.println( treeString );
        pw.close(); 
        osw.close();
        os.close();
	}

}
