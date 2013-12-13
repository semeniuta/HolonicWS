package mine;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

/**
 * Implementation of the first operation described in "An Introduction to WS4D
 * and to JMEDS, a framework for distributed communication between devices in a
 * domotic environment" by Pierre-Alexandre Gagné
 * 
 * @author ajordan
 */
public class DocuExampleSimpleOperation extends Operation {

	private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * Constructor
	 */
	public DocuExampleSimpleOperation() {
		super("DocuExampleSimpleOperation", new QName("BasicServices", namespace));

		// create new Element called "name" (just a simple one in this case)
		Element nameElem = new Element(new QName("name", namespace), SchemaUtil.TYPE_STRING);

		// set the input of the operation
		setInput(nameElem);

		// create new element called "reply"
		Element reply = new Element(new QName("reply", namespace), SchemaUtil.TYPE_STRING);

		// set this element as output
		setOutput(reply);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		// get string value from input
		String name = ParameterValueManagement.getString(parameterValue, "name");
		System.out.println("Hello World " + name);

		// create output and set value
		ParameterValue result = createOutputValue();
		ParameterValueManagement.setString(result, "reply", "How are you " + name);

		return result;
	}

}
