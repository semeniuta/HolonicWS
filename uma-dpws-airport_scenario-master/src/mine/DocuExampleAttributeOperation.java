package mine;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.constants.SchemaConstants;
import org.ws4d.java.schema.Attribute;
import org.ws4d.java.schema.ComplexType;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.ExtendedSimpleContent;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class DocuExampleAttributeOperation extends Operation {

	private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * Constructor
	 */
	public DocuExampleAttributeOperation() {
		super("DocuExampleAttributeOperation", new QName("BasicServices", namespace));

		/* use this code to add an attribute to a simple type element. */
		{
			// create new Attribute called "sex"
			Attribute sexAttr = new Attribute(new QName("sex", namespace), SchemaUtil.TYPE_STRING);
			sexAttr.setUse(SchemaConstants.USE_OPTIONAL);

			// create extended simple content type
			ExtendedSimpleContent simpleCon = new ExtendedSimpleContent("simpleContent", namespace);
			simpleCon.setBase(SchemaUtil.TYPE_STRING);
			simpleCon.addAttribute(sexAttr);

			// // create new element with the extended simple content type
			// Element simpleConNameElem = new Element(new QName("firstName",
			// namespace), simpleCon);
			//
			// // set the input of the operation
			// setInput(simpleConNameElem);
		}

		/* use this code to add an attribute to a complex type element. */
		{
			// create inner elements
			Element street = new Element(new QName("street", namespace), SchemaUtil.TYPE_STRING);
			Element zip = new Element(new QName("zip", namespace), SchemaUtil.TYPE_INT);
			Element city = new Element(new QName("city", namespace), SchemaUtil.TYPE_STRING);
			Element country = new Element(new QName("country", namespace), SchemaUtil.TYPE_STRING);

			// create attribute
			Attribute name = new Attribute(new QName("name", namespace), SchemaUtil.TYPE_STRING);
			name.setUse(SchemaUtil.USE_REQUIRED);

			// create complex type
			ComplexType addressType = new ComplexType(new QName("addressType", namespace), ComplexType.CONTAINER_SEQUENCE);
			// add inner elements
			addressType.addElement(street);
			addressType.addElement(zip);
			addressType.addElement(city);
			addressType.addElement(country);
			// add attribute
			addressType.addAttribute(name);

			// create element of type address
			Element adress = new Element(new QName("address", namespace), addressType);

			// set the input of the operation
			setInput(adress);
		}
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {
		// use this code to get an attribute from simple type element
		// String attribute = ParameterUtil.getAttributeValue(parameterValue,
		// null, "sex");

		// use this code to get an attribute from complex type element
		String attribute = ParameterValueManagement.getAttributeValue(parameterValue, null, "name");

		System.out.println("attribute: " + attribute);
		return null;
	}

}
