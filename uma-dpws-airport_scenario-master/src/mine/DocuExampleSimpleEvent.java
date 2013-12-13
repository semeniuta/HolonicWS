package mine;

import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.DefaultEventSource;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class DocuExampleSimpleEvent extends DefaultEventSource {

	private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * Create a simple Notification.
	 */
	public DocuExampleSimpleEvent() {
		super("DocuExampleSimpleEvent", new QName("BasicServices", namespace));

		Element name = new Element(new QName("name", namespace), SchemaUtil.TYPE_STRING);

		setOutput(name);
	}

	/**
	 * This method sets the paramValue and fires the event
	 */
	public void fireHelloWorldEvent(int eventCounter) {
		ParameterValue paramValue = createOutputValue();
		if (eventCounter % 2 == 0) {
			ParameterValueManagement.setString(paramValue, "name", "me (" + eventCounter + ")");
		} else
			ParameterValueManagement.setString(paramValue, "name", "me again (" + eventCounter + ")");
		fire(paramValue, eventCounter++, CredentialInfo.EMPTY_CREDENTIAL_INFO);
	}
}
