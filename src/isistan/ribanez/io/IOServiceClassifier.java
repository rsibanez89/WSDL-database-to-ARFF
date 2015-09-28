package isistan.ribanez.io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import isistan.ribanez.algorithms.ServiceClassifier;
import isistan.ribanez.structures.Document;
import isistan.ribanez.structures.Word;

public class IOServiceClassifier
{
	public static void saveServiceClassifier(ServiceClassifier classifier, String path)
	{
		try
		{
			PrintWriter writer = new PrintWriter(path, "UTF-8");

			Vector<String> differentTerms = classifier.getDifferentTerms();
			writer.print("NOMBRE" + "\t");
			for (int i = 0; i < differentTerms.size(); i++)
				writer.print(differentTerms.elementAt(i) + "\t");
			writer.print("CLASE" + "\t");
			writer.println();

			Vector<Document> documents = classifier.getDocuments();
			for (int d = 0; d < documents.size(); d++)
			{
				Vector<Word> terms = documents.elementAt(d).getWords();
				writer.print(documents.elementAt(d).getName() + "\t");
				for (int t = 0; t < differentTerms.size(); t++)
				{
					int index = terms.indexOf(new Word(differentTerms.elementAt(t)));
					if (index >= 0)
						writer.print(terms.elementAt(index).getCount() + "\t");
					else
						writer.print("0" + "\t");
				}
				writer.print(documents.elementAt(d).getClase() + "\t");
				writer.println();
			}
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("Problemas escribiendo el archivo: " + path);
			e.printStackTrace();
		}
	}

	public static void generateARFFServiceClassifier(ServiceClassifier classifier, String path)
	{
		try
		{
			// 1 - Crear los Atributos
			FastVector atributos = new FastVector();

			// Agrego un atributo tipo String
			atributos.addElement(new Attribute("NOMBRE", (FastVector) null));

			// Agrego un atributo tipo numeric por cada termino
			Vector<String> differentTerms = classifier.getDifferentTerms();
			for (int i = 0; i < differentTerms.size(); i++)
				atributos.addElement(new Attribute(differentTerms.elementAt(i)));

			// Agrego un atributo tipo nominal para la clase
			FastVector clase = new FastVector();
			for (int c = 0; c < classifier.getCantClases(); c++)
				clase.addElement(String.valueOf(c));
			atributos.addElement(new Attribute("CLASE", clase));

			// 2 - Crear el documento ARFF
			Instances arff = new Instances("Services", atributos, 0);

			// 3 - Cargar los datos
			Vector<Document> documents = classifier.getDocuments();
			for (int d = 0; d < documents.size(); d++)
			{
				double[] tupla = new double[arff.numAttributes()];
				int indice = 0;
				
				tupla[indice] = arff.attribute(0).addStringValue(documents.elementAt(d).getName());
				
				Vector<Word> terms = documents.elementAt(d).getWords();
				for (int t = 0; t < differentTerms.size(); t++)
				{
					indice++;
					int index = terms.indexOf(new Word(differentTerms.elementAt(t)));
					if (index >= 0)
						tupla[t+1] = terms.elementAt(index).getCount();
					else
						tupla[t+1] = 0;
				}
				
				tupla[++indice] = documents.elementAt(d).getClase();
				
				arff.add(new Instance(1.0, tupla));
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(arff.toString());
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("Problemas escribiendo el archivo: " + path);
			e.printStackTrace();
		}
	}

}
