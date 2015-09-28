package isistan.ribanez.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

public class IOStopWords
{
	public static Vector<String> getStopWords(String path)
	{
		Vector<String> ret = new Vector<String>();
		try
		{
			File archivo = new File(path);
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea = "";
			while ((linea = br.readLine()) != null)
				ret.add(linea);
			br.close();
			fr.close();
		}
		catch (Exception e)
		{
			System.out.println("Problemas leyendo el archivo txt: " + path);
			e.printStackTrace();
		}

		return ret;
	}
}
