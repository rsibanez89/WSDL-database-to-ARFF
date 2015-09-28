package isistan.ribanez;

import java.util.Vector;

import isistan.ribanez.algorithms.Splitter;
import isistan.ribanez.algorithms.Stemmer;
import isistan.ribanez.algorithms.StopWordFilter;

public class TestAlgorithms
{
	public static void main(String[] args)
	{
		Vector<String> text = new Vector<String>();
		text.add("GetBibleWordsByChapterAndVerse");
		text.add("GetBibleWordsbyKeyWord");
		text.add("GetBookTitles");
		text.add("GetBibleWordsByBookTitleAndChapter");
		
		Vector<String> terms = new Vector<String>();
		for (String word : text)
			terms.addAll(Splitter.spit(word));
		
		for (String word : terms)
			System.out.println(word);
		
		System.out.println("---------------------------------");
		
		StopWordFilter.getInstance().removeStopWords(terms);
		
		for (String word : terms)
			System.out.println(word);
		
		System.out.println("---------------------------------");
		
		Vector<String> stemmTerms = new Vector<String>();
		
		for (String word : terms)
			stemmTerms.add(Stemmer.getInstance().getStem(word));

		for (String word : stemmTerms)
			System.out.println(word);
		
		System.out.println("---------------------------------");
	}

}
