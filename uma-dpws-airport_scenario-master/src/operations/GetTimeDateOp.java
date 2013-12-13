package operations;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

import devices.RaspberryPi;

public class GetTimeDateOp extends Operation {

	/**
	 * Constructor
	 */
	public GetTimeDateOp() {
		super("GetTimeDateOp", new QName("BasicServices", RaspberryPi.namespace));

		setOutput(new Element(new QName("TimeOutput", RaspberryPi.namespace),
				SchemaUtil.TYPE_STRING));
		setInputName("TimeInput");

	}

	public ParameterValue invokeImpl(ParameterValue parameterValue,
			CredentialInfo credentialInfo) throws InvocationException,
			CommunicationException {
		ParameterValue paramTimeVal = createOutputValue();
		ParameterValueManagement.setString(paramTimeVal, null,
				getCurrentTime(null));
		return paramTimeVal;
	}
	
	static String getCurrentTime(String format) throws IllegalArgumentException {
		if (format == null || format.length() == 0) format = "HH:mm:ss";
		SimpleDateFormat dateformat = new SimpleDateFormat(format);

		return dateformat.format(new Date());
	};
}
