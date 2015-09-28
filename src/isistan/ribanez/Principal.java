package isistan.ribanez;

import java.io.File;
import java.util.Vector;

import isistan.ribanez.algorithms.ServiceClassifier;
import isistan.ribanez.algorithms.Stemmer;
import isistan.ribanez.algorithms.Splitter;
import isistan.ribanez.io.IOServiceClassifier;
import isistan.ribanez.io.IOWSDL;
import isistan.ribanez.structures.Document;
import isistan.ribanez.structures.Service;

public class Principal
{

	public static void main(String[] args)
	{
		try
		{
			File root = new File("dataset/");
			File[] files = root.listFiles();

			ServiceClassifier classifier = new ServiceClassifier(true);
			for (int i = 0; i < files.length; i++)
			{
				Vector<Service> servicios = IOWSDL.getServices(files[i].getAbsolutePath());
				classifier.addServices(servicios, i);
			}

			IOServiceClassifier.saveServiceClassifier(classifier, "servicios.txt");
			IOServiceClassifier.generateARFFServiceClassifier(classifier, "servicios.arff");
		}
		catch (Exception e)
		{
			System.out.println("Problemas leyendo el dataset");
			e.printStackTrace();
		}

	}

}
