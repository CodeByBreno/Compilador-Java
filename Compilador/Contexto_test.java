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

public class Contexto_test {
  public static void main(String args[]) throws IOException {
    String path = "Entrada.txt";
    System.out.println("===========================\n");
    Parser parser = new Parser(path);
    Program program = parser.parse();
    Printer printer = new Printer();
    String treeString = printer.print(program);
    Checker checker = new Checker();

    // FileWriter file = new FileWriter(new File("out.txt"));
    // file.write(treeString);
    // file.close();

        OutputStream os = (OutputStream) new FileOutputStream( new File("out_Checker.txt") );
        OutputStreamWriter osw = new OutputStreamWriter( os, "UTF8" );
        PrintWriter pw = new PrintWriter( osw );
        pw.println( treeString );
        pw.close(); 
        osw.close();
        os.close();

    checker.check(program);
	}
}
