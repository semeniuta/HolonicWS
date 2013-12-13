package operations;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

import devices.DeviceVideo;

public class VideoPlayOperation extends Operation {
	/**
	 * Constructor
	 */
	public VideoPlayOperation() {
		super("VideoPlayOperation", new QName("BasicServices", DeviceVideo.namespace));

		// create new Element called "name" (just a simple one in this case)
		Element nameElem = new Element(new QName("play", DeviceVideo.namespace), SchemaUtil.TYPE_STRING);

		// set the input of the operation
		setInput(nameElem);

		// create new element called "reply"
		Element reply = new Element(new QName("playResponse", DeviceVideo.namespace), SchemaUtil.TYPE_STRING);

		// set this element as output
		setOutput(reply);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		// get string value from input
		//String name = ParameterValueManagement.getString(parameterValue, "name");
		System.out.println("Playing video");

		// create output and set value
		ParameterValue result = createOutputValue();
		ParameterValueManagement.setString(result, "reply", "Video is playing");

		return result;
	}
}
