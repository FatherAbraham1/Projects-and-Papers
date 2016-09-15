import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class CreateFile {
	public static void main(String[] args) {
		PrintWriter writer;
		try {
		for (int i = 0; i < 1; i++) 
			{
				writer = new PrintWriter("4GB/" + i + ".txt", "UTF-8");
				for (int size=0;size<1073741824;size++)
				writer.print("MEYA");
				writer.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
