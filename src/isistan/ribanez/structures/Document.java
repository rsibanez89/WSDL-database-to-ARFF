package isistan.ribanez.structures;

import java.util.Collections;
import java.util.Vector;

public class Document
{
	private Vector<Word> document;
	private int clase;
	private String name;
	
	public int getClase()
	{
		return clase;
	}
	
	public String getName()
	{
		return name;
	}

	public Document(String name, int clase)
	{
		this.name = name;
		this.document = new Vector<Word>();
		this.clase = clase;
	}
	
	public Vector<Word> getWords()
	{
		return document;
	}

	public void add(String word)
	{
		Word w = new Word(word);
		int index = document.indexOf(w);
		if (index != -1)
			document.elementAt(index).addCount();
		else
			document.add(w);
	}

	public void sortWords()
	{
		Collections.sort(this.document);
	}

	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		for (int w = 0; w < document.size(); w++)
			ret.append(document.elementAt(w).toString() + "\n");

		return ret.toString();
	}

}
