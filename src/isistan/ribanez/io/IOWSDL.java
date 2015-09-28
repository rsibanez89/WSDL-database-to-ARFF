package isistan.ribanez.io;

import isistan.ribanez.structures.Operation;
import isistan.ribanez.structures.Service;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IOWSDL
{

	private static void clean(Node node)
	{
		if (node != null && node.hasChildNodes())
		{
			NodeList childNodes = node.getChildNodes();

			for (int n = childNodes.getLength() - 1; n >= 0; n--)
			{
				Node child = childNodes.item(n);
				short nodeType = child.getNodeType();

				if (nodeType == Node.ELEMENT_NODE)
					clean(child);
				else if (nodeType == Node.TEXT_NODE)
				{
					String trimmedNodeVal = child.getNodeValue().trim();
					if (trimmedNodeVal.length() == 0)
						node.removeChild(child);
					else
						child.setNodeValue(trimmedNodeVal);
				}
				else if (nodeType == Node.COMMENT_NODE)
					node.removeChild(child);
			}
		}
	}

	public static Service getService(String path)
	{
		try
		{
			File wsdl = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(wsdl);

			NodeList servicesNodeList = doc.getElementsByTagName("service");
			if(servicesNodeList.getLength() == 0)
				servicesNodeList = doc.getElementsByTagName("wsdl:service");
			Node serviceNode = servicesNodeList.item(0);
			clean(serviceNode);
			// System.out.println("Service Name: " + ((Element)
			// serviceNode).getAttribute("name"));
			String serviceName = ((Element) serviceNode).getAttribute("name");
			String serviceDocumentation = "";

			NodeList childService = serviceNode.getChildNodes();
			for (int i = 0; i < childService.getLength(); i++)
				if (childService.item(i).getNodeName().equals("documentation") || childService.item(i).getNodeName().equals("wsdl:documentation"))
					// System.out.println("Documentation: " +
					// childService.item(i).getTextContent());
					serviceDocumentation = childService.item(i).getTextContent();

			Service retorno = new Service(serviceName, serviceDocumentation);

			NodeList portTypeNodeList = doc.getElementsByTagName("portType");
			if(portTypeNodeList.getLength() == 0)
				portTypeNodeList = doc.getElementsByTagName("wsdl:portType");
			Node portTypeNode = portTypeNodeList.item(0);
			clean(portTypeNode);
			// System.out.println(((Element)
			// portTypeNode).getAttribute("name"));

			NodeList childPortType = portTypeNode.getChildNodes();
			for (int i = 0; i < childPortType.getLength(); i++)
			{
				if (childPortType.item(i).getNodeName().equals("operation") || childPortType.item(i).getNodeName().equals("wsdl:operation") )
				{
					Element operation = (Element) childPortType.item(i);
					// System.out.println("   Operation Name: " +
					// operation.getAttribute("name"));
					String operationName = operation.getAttribute("name");
					String operationDocumentation = "";
					String operationInput = "";
					String operationOutput = "";

					NodeList childOperation = childPortType.item(i).getChildNodes();
					for (int j = 0; j < childOperation.getLength(); j++)
					{
						if (childOperation.item(j).getNodeName().equals("documentation") || childOperation.item(j).getNodeName().equals("wsdl:documentation"))
						{
							// System.out.println("      Operation Description: "
							// + childOperation.item(j).getTextContent());
							operationDocumentation = childOperation.item(j).getTextContent();
						}
						if (childOperation.item(j).getNodeName().equals("input") || childOperation.item(j).getNodeName().equals("wsdl:input"))
						{
							Element input = (Element) childOperation.item(j);
							// System.out.println("      Operation Input Message: "
							// + input.getAttribute("message"));
							operationInput = input.getAttribute("message");
						}
						if (childOperation.item(j).getNodeName().equals("output") || childOperation.item(j).getNodeName().equals("wsdl:output"))
						{
							Element output = (Element) childOperation.item(j);
							// System.out.println("      Operation Output Message: "
							// + output.getAttribute("message"));
							operationOutput = output.getAttribute("message");
						}
					}
					retorno.addOperation(new Operation(operationName, operationDocumentation, operationInput, operationOutput));
				}
			}
			return retorno;
		}
		catch (Exception e)
		{
			System.out.println("Problemas leyendo el archivo wsdl: " + path);
			e.printStackTrace();
		}

		return null;
	}

	public static Vector<Service> getServices(String folderPath)
	{
		Vector<Service> retorno = new Vector<Service>();
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles)
		{
			if (file.isFile())
			{
				String relativePath = file.getName();
				if (relativePath.endsWith(".wsdl"))
					retorno.add(getService(file.getPath()));
			}
		}
		return retorno;
	}
}
