package isistan.ribanez.structures;

public class Operation
{
	private String name;
	private String description;
	private String inputMessage;
	private String outputMessage;
	
	public Operation(String name, String description, String inputMessage, String outputMessage)
	{
		this.name = name;
		this.description = description;
		this.inputMessage = inputMessage;
		this.outputMessage = outputMessage;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getInputMessage()
	{
		return inputMessage;
	}

	public void setInputMessage(String inputMessage)
	{
		this.inputMessage = inputMessage;
	}

	public String getOutputMessage()
	{
		return outputMessage;
	}

	public void setOutputMessage(String outputMessage)
	{
		this.outputMessage = outputMessage;
	}
	
	

}
