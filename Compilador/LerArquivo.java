import LeitorArquivo.ManipuladorArquivo;

import java.io.IOException;

//Compilar:
//javac Main.java Compilador.LeitorArquivo.ManipuladorArquivo Compilador.AnaliseLexica.Scanner Compilador.AnaliseLexica.Token

public class LerArquivo {
	public static void main(String args[]) throws IOException {
		int i = 0;
		String path = "Entrada1.txt";
		char currentChar;
		long sizeFile = ManipuladorArquivo.sizeFile(path);

		while (i++ < sizeFile - 1){
			currentChar = ManipuladorArquivo.readChar(path, i);
			System.out.println(currentChar);
		}
	}

}