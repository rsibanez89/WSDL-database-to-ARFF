package isistan.ribanez.algorithms;

import java.util.Vector;

public class Splitter
{

	public static Vector<String> spit(String str)
	{
		Vector<String> retorno = new Vector<String>();
		String firstWord = "";

		while (str.length() > 0)
		{
			firstWord = getFirstWord(str);
			str = str.substring(firstWord.length(), str.length());
			if ((firstWord.length() > 0) && (firstWord.charAt(0) != '-') && (firstWord.charAt(0)!= '_') )
				retorno.add(firstWord.toLowerCase());
		}

		return retorno;
	}

	private static int nextSplitCaseIndex(int startIndex, String str)
	{
		for (int i = startIndex; i < str.length(); i++)
			if (Character.isUpperCase(str.charAt(i)) || (str.charAt(i) == '-') || (str.charAt(i) == '_'))
				return i;
		return -1;
	}

	private static String getFirstWord(String str)
	{
		if (str.length() <= 2)
			return str;

		int endIndex = 1;

		while (endIndex < str.length() && (Character.isUpperCase(str.charAt(endIndex)) || Character.isDigit(str.charAt(endIndex))) )
			endIndex++;

		if (endIndex == str.length())
			return str;

		if (endIndex == 1)
		{
			endIndex = nextSplitCaseIndex(endIndex, str);
			if (endIndex > 0)
				return str.substring(0, endIndex);
			else
				return str;
		}

		return str.substring(0, endIndex - 1);
	}

	public static void main(String[] args)
	{
		// String str = "IPAlgoQue--Se_Yo";
		// String str = "empiezaEnMinusculasIPAlgoQue--Se_Yo";
		 String str = "empiezaEnMinusculasIPAlgoQue--Se_YoMAYUSCULAS";
		//String str = "SearchMP3";
		/*
		 * String firstWord = "";
		 * 
		 * while (str.length() > 0) { firstWord = getFirstWord(str);
		 * System.out.println(firstWord); str =
		 * str.substring(firstWord.length(), str.length()); //
		 * System.out.println(str); }
		 */

		Vector<String> palabras = spit(str);
		for (int i = 0; i < palabras.size(); i++)
			System.out.println(palabras.elementAt(i));
		
	}

}
