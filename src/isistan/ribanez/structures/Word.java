package isistan.ribanez.structures;

public class Word implements Comparable<Word>
{
	private String word;
	private int count;

	public Word(String word)
	{
		this.word = word;
		this.count = 1;
	}

	public String getWord()
	{
		return word;
	}

	public int getCount()
	{
		return count;
	}

	public void addCount()
	{
		count++;
	}

	@Override
	public int compareTo(Word w)
	{
		if (this.count > w.count)
			return 1;
		else if (this.count < w.count)
			return -1;
		return 0;
	}

	@Override
	public boolean equals(Object w)
	{
		return ((Word)w).word.equals(word);
	}
	
	@Override 
	public String toString()
	{
		return this.word + ": " + this.count;
	}

}
