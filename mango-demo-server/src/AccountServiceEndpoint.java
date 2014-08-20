import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Endpoint
public class AccountServiceEndpoint {

	private static final String TARGET_NAMESPACE = "http://mangodemo/entity1";

	@PayloadRoot(localPart = "entity", namespace = TARGET_NAMESPACE)
	public @ResponsePayload Element entity(@RequestPayload Element element1) throws ParserConfigurationException {
		
		System.out.println(element1.toString());

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.newDocument();

		// create the root element node
		Element element = doc.createElement("root");
		doc.appendChild(element);

		// create a comment node given the specified string
		Comment comment = doc.createComment("This is a comment");
		doc.insertBefore(comment, element);

		// add element after the first child of the root element
		Element itemElement = doc.createElement("item");
		element.appendChild(itemElement);

		// add an attribute to the node
		itemElement.setAttribute("myattr", "attrvalue");

		// create text for the node
		itemElement.insertBefore(doc.createTextNode("text"), itemElement.getLastChild());

		return element;
	}

}