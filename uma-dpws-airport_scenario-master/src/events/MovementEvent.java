package events;

import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.DefaultEventSource;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class MovementEvent extends DefaultEventSource {

	public final static String	namespace	= "http://www.gisum.uma.es/jmeds";

	/**
	 * Create a simple Notification.
	 */
	public MovementEvent() {
		super("MovementEvent", new QName("BasicServices", namespace));

		Element name = new Element(new QName("name", namespace), SchemaUtil.TYPE_STRING);

		setOutput(name);
	}

	/**
	 * This method sets the paramValue and fires the event
	 */
	public void fireMovementEvent(int eventCounter) {
		ParameterValue paramValue = createOutputValue();
		if (eventCounter % 2 == 0) {
			ParameterValueManagement.setString(paramValue, "name", "me (" + eventCounter + ")");
		} else
			ParameterValueManagement.setString(paramValue, "name", "me again (" + eventCounter + ")");
		fire(paramValue, eventCounter++, CredentialInfo.EMPTY_CREDENTIAL_INFO);
	}
}
