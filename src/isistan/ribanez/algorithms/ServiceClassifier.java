package isistan.ribanez.algorithms;

import isistan.ribanez.structures.Document;
import isistan.ribanez.structures.Operation;
import isistan.ribanez.structures.Service;

import java.util.Collections;
import java.util.Vector;

public class ServiceClassifier
{
	private Vector<Vector<String>> terms;
	private Vector<Document> documents;
	private Vector<String> differentTerms;
	private Vector<Integer> differentClasses;
	private boolean useDocumentDescription;

	public Vector<Vector<String>> getTerms()
	{
		return terms;
	}

	public Vector<Document> getDocuments()
	{
		return documents;
	}

	public Vector<String> getDifferentTerms()
	{
		return differentTerms;
	}

	public int getCantClases()
	{
		return differentClasses.size();
	}

	public ServiceClassifier(boolean useDocumentDescription)
	{
		this.documents = new Vector<Document>();
		this.terms = new Vector<Vector<String>>();
		this.differentTerms = new Vector<String>();
		this.differentClasses = new Vector<Integer>();
		this.useDocumentDescription = useDocumentDescription;
	}

	public void addServices(Vector<Service> services, int clase)
	{
		terms = new Vector<Vector<String>>();
		if (!differentClasses.contains(new Integer(clase)))
			differentClasses.add(clase);
		for (int i = 0; i < services.size(); i++)
		{
			Vector<String> words = serviceToVectorOfTerms(services.elementAt(i));
			StopWordFilter.getInstance().removeStopWords(words);
			terms.add(words);
		}

		for (int i = 0; i < terms.size(); i++)
			documents.add(vectorOfTermsToDocument(terms.elementAt(i), services.elementAt(i).getName(), clase));

		Collections.sort(this.differentTerms);
	}

	private Vector<String> serviceToVectorOfTerms(Service service)
	{
		Vector<String> words = new Vector<String>();
		Vector<Operation> operations = service.getOperations();
		for (int op = 0; op < operations.size(); op++)
			words.addAll(Splitter.spit(operations.elementAt(op).getName()));

		// Agrego el nombre del servicio a la lista de terminos
		words.addAll(Splitter.spit(service.getName()));

		// Agrego la descripción del servicio a la lista de terminos
		if (useDocumentDescription)
		{
			String[] description = service.getDescription().split(" ");
			words.addAll(getVectorOfTerms(description));
			
			// Agrego la descripción de las operaciones a la lista de terminos
			for (int op = 0; op < operations.size(); op++)
				words.addAll(getVectorOfTerms(operations.elementAt(op).getDescription().split(" ")));
		}
		
		// Filtro las palabras
		Vector<String> filteredWords = new Vector<String>();
		for (int w = 0; w < words.size(); w++)
		{
			String s = words.elementAt(w).replace("0","").replace("1","").replace("2","").replace("3","").replace("4","").replace("5","").replace("6","").replace("7","").replace("8","").replace("9","");
			if (s.length() > 1)
				filteredWords.add(s);
		}

		return filteredWords;
	}

	private Document vectorOfTermsToDocument(Vector<String> terms, String name, int clase)
	{
		Document document = new Document(name, clase);
		for (int t = 0; t < terms.size(); t++)
		{
			//String w = terms.elementAt(t);
			String w = Stemmer.getInstance().getStem(terms.elementAt(t));
			document.add(w);
			if (!differentTerms.contains(w))
				differentTerms.add(w);
		}
		return document;
	}
	
	private Vector<String> getVectorOfTerms(String[] description)
	{
		Vector<String> wordsDescription = new Vector<String>();
		Vector<String> words = new Vector<String>();
		for (int t = 0; t < description.length; t++)
		{
			// System.out.println(Splitter.spit(description[t].trim()));
			wordsDescription.addAll(Splitter.spit(description[t].trim()));
		}
		for (int t = 0; t < wordsDescription.size(); t++)
		{
			String add = wordsDescription.elementAt(t);
			if ((!add.contains("/")) && (!add.contains("<")) && (!add.contains(">")) && (!add.contains("=")) && (!add.contains(",")))
				words.add(add);
		}
		
		return words;
	}

}
