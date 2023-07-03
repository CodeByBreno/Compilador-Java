package LeitorArquivo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ManipuladorArquivo {
	private static RandomAccessFile arquivo;
	private static BufferedWriter writer;

	public static char readChar(String path, int posicao) throws IOException {
		char currentChar;

		arquivo = new RandomAccessFile(path, "r");
		arquivo.seek(posicao);
		currentChar = (char) arquivo.readByte();
		arquivo.close();

        return currentChar;
	}

	public static void writeLine(String path, String line) throws IOException{
		writer = new BufferedWriter(new FileWriter(path, true));		
		writer.write(line);
		writer.newLine();
		writer.close();
	}

	public static long sizeFile(String path){
		File aux = new File(path);
		return aux.length();
	}	

}