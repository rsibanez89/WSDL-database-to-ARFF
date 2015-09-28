package isistan.ribanez.structures;

import java.util.Vector;

public class Service
{

	private String name;
	private String description;
	private Vector<Operation> operations;
	
	public Service(String name, String description)
	{
		this.name = name;
		this.description = description;
		this.operations = new Vector<Operation>();
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

	public Vector<Operation> getOperations()
	{
		return operations;
	}

	public void addOperation(Operation operation)
	{
		this.operations.add(operation);
	}
	
	@Override 
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append("Service Name: " + this.name);
		ret.append("\nService Description: " + this.description);
		ret.append("\nService Operations: ");
		for(int i = 0; i < operations.size(); i++)
		{
			ret.append("\n   Operation Name: " + operations.elementAt(i).getName());
			ret.append("\n     Operation Description: " + operations.elementAt(i).getDescription());
			ret.append("\n     Operation Input Message: " + operations.elementAt(i).getInputMessage());
			ret.append("\n     Operation Output Message: " + operations.elementAt(i).getOutputMessage());
		}
		return ret.toString();
	}

}
