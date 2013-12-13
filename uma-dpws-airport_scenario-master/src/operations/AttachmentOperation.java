package operations;

import java.io.InputStream;
import java.util.Random;

import org.ws4d.java.attachment.AttachmentFactory;
import org.ws4d.java.attachment.interfaces.outgoing.OutgoingAttachment;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.constants.MIMEConstants;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.AttachmentValue;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.types.QName;
import org.ws4d.java.util.Log;

public class AttachmentOperation extends Operation {

	public final static char[]	ALPHABET	= "ABCDEFGHIJKLMNOPQRSTUVWXYZabsdefghijklmnopqrstuvwxyz".toCharArray();

	public AttachmentOperation(String name, QName portType) {
		super(name, portType);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		ParameterValue result = createOutputValue();

		AttachmentFactory afac = AttachmentFactory.getInstance();

		if (afac != null) {
			// it's important to send the data as a stream
			OutgoingAttachment oa = afac.createStreamAttachment(new myInputStream(), MIMEConstants.CONTENT_TYPE_APPLICATION_OCTET_STREAM);
			((AttachmentValue) result).setAttachment(oa);
		} else {
			Log.error("No attachment support available.");
		}

		return result;
	}

	private class myInputStream extends InputStream {

		Random	generator	= new Random(42);

		int		count		= 0;

		/*
		 * Create a new read() method, which returns a random character (with
		 * his int representation) for demonstration purposes
		 */
		public int read() {
			// get a random char out of the previously defined alphabet
			char randChar = ALPHABET[generator.nextInt(ALPHABET.length)];

			// printState: just writes a little log entry about the sent data
			// volume
			if (count % 16384 == 0) {
				Log.info(count++ + " characters send");
			} else {
				count++;
			}

			return (int) randChar;
		}
	}
}
