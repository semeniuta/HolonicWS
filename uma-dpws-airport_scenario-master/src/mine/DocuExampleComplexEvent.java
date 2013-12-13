package mine;

import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.DefaultEventSource;
import org.ws4d.java.service.ServiceSubscription;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class DocuExampleComplexEvent extends DefaultEventSource {

	private final static String	namespace		= "http://www.mydemo.com/tutorial";

	private static int			eventCounter	= 0;

	private static int			exampleCounter	= 0;

	/**
	 * Create a SolicitResponse event.
	 */
	public DocuExampleComplexEvent() {
		super("DocuExampleComplexEvent", new QName("BasicServices", namespace));

		Element count = new Element(new QName("counter", namespace), SchemaUtil.TYPE_INT);
		setOutput(count);

		Element add = new Element(new QName("addToCounter", namespace), SchemaUtil.TYPE_INT);
		setInput(add);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ws4d.java.service.DefaultEventSource#solicitResponseReceived(org.
	 * ws4d.java.service.parameter.ParameterValue, int,
	 * org.ws4d.java.service.ServiceSubscription)
	 */
	public void solicitResponseReceived(ParameterValue paramValue, int eventNumber, ServiceSubscription subscription) {
		// Get the solicit response
		String solicitResponse = ParameterValueManagement.getString(paramValue, "addToCounter");
		System.out.println("Solicit Response received: " + solicitResponse);

		if (solicitResponse != null) {
			exampleCounter = exampleCounter + Integer.parseInt(solicitResponse);
		} else {
			exampleCounter++;
		}
	}

	/**
	 * This method sets the output ParameterValue and fires the event
	 */
	public void fireComplexEvent() {
		ParameterValue paramValue = createOutputValue();
		ParameterValueManagement.setString(paramValue, "counter", String.valueOf(exampleCounter));

		fire(paramValue, eventCounter++, CredentialInfo.EMPTY_CREDENTIAL_INFO);
	}

}
