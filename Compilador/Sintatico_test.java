import AnaliseSintatica.Parser;
import ArvoreSintaticaAbstrata.Program.Program;

import java.io.IOException;

//Compilar:
//javac Main.java LeitorArquivo.ManipuladorArquivo CompiladorJava.Scanner CompiladorJava.Token

public class Sintatico_test {
	public static void main(String args[]) throws IOException {
		String path = "Entrada.txt";
		System.out.println("===========================\n");
		Parser parser = new Parser(path);
        Program aux = parser.parse();
	}

}