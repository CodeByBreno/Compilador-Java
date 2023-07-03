import AnaliseLexica.*;
import LeitorArquivo.ManipuladorArquivo;

import java.io.IOException;

//Compilar:
//javac Main.java LeitorArquivo.ManipuladorArquivo CompiladorJava.Scanner CompiladorJava.Token

public class Main {
	public static void main(String args[]) throws IOException {
		String path = "Entrada.txt", destiny = "Saida.txt";
		Scanner scanner = new Scanner(path);
		String tipo = "breno";

		while (tipo != "<eot>"){
			Token aux = scanner.scan();
			tipo = Token.spellings[(int) aux.kind];

			System.out.println("Token lido : " + (String) aux.spelling);
			System.out.println("Tipo = " + tipo);
			System.out.println("LINHA = " + aux.line + " COLUNA = " + aux.column);
			System.out.println("\n");

			ManipuladorArquivo.writeLine(destiny, "Token lido : " + (String) aux.spelling);
			ManipuladorArquivo.writeLine(destiny, "Tipo = " + tipo);
			ManipuladorArquivo.writeLine(destiny, "LINHA = " + aux.line + " COLUNA = " + aux.column);
			ManipuladorArquivo.writeLine(destiny, "\n");
		}
	}

}