package isistan.ribanez.algorithms;

import isistan.ribanez.io.IOStopWords;

import java.util.Vector;

public class StopWordFilter
{
	private static final String pathPublicStopWords = "res/publicstopwords.txt";
	private static final String pathPrivateStopWords = "res/privatestopwords.txt";
	private Vector<String> stopwords;
	private static StopWordFilter instance;

	public static StopWordFilter getInstance()
	{
		if (instance == null)
			instance = new StopWordFilter();
		return instance;
	}

	private StopWordFilter()
	{
		stopwords = new Vector<String>();
		stopwords.addAll(IOStopWords.getStopWords(pathPublicStopWords));
		stopwords.addAll(IOStopWords.getStopWords(pathPrivateStopWords));
	}

	public void removeStopWords(Vector<String> terms)
	{
		for (String word : stopwords)
			while (terms.contains(word))
				terms.remove(word);
	}

}
