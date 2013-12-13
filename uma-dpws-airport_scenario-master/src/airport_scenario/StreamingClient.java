package airport_scenario;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.attachment.AttachmentFactory;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.constants.MIMEConstants;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.HelloData;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;
import org.ws4d.java.util.Log;

import services.ServiceStreaming;

public class StreamingClient extends DefaultClient {

	static final String	opName	= ServiceStreaming.GET_STREAM_OPERATION;

	public static void main(String[] args) {
		Log.setShowTimestamp(true);
		JMEDSFramework.start(args);

		// register MIME types to stream ...
		AttachmentFactory afac = AttachmentFactory.getInstance();

		// it's important to receive the data as a string, be aware of the mime
		// type
		if (afac != null) afac.addStreamingMediaType(MIMEConstants.CONTENT_TYPE_APPLICATION_OCTET_STREAM);

		DefaultClient client = new StreamingClient();

		client.registerHelloListening();

		SearchParameter search = new SearchParameter();
		search.setServiceTypes(new QNameSet(ServiceStreaming.PORT_TYPE));

		SearchManager.searchService(search, client);
	}

	public void helloReceived(HelloData helloData) {
		Log.info("Client: Overwrite helloReceived() to receive and handle the UUIDs of new HelloMessages");
		DeviceReference devRef = SearchManager.getDeviceReference(helloData, SecurityKey.EMPTY_KEY, this);
		try {
			Device device = devRef.getDevice();
			Iterator serviceRefs = device.getServiceReferences(SecurityKey.EMPTY_KEY);
			while (serviceRefs.hasNext()) {
				ServiceReference servRef = (ServiceReference) serviceRefs.next();
				Service service = servRef.getService();
				Iterator pts = service.getPortTypes();
				while (pts.hasNext()) {
					QName portType = (QName) pts.next();
					if (portType.equals(ServiceStreaming.PORT_TYPE)) {
						serviceFound(servRef, null);
						return;
					}
				}
			}
		} catch (CommunicationException e) {
			e.printStackTrace();
		}
	}

	public void serviceFound(ServiceReference servRef, SearchParameter search) {

		try {
			// get our operation
			Operation op = servRef.getService().getOperation(ServiceStreaming.PORT_TYPE, opName, null, null);

			// invoke Operation ~ "create" attachment in our operation object
			ParameterValue result = op.invoke(null, CredentialInfo.EMPTY_CREDENTIAL_INFO);
			// IncomingAttachment stream = ((AttachmentValue)
			// result).getAttachment();

			// "consume" the Attachment ~ read the inputStream and count the
			// size
			new Thread(new StreamConsumer(result)).start();
		} catch (Exception e) {
			Log.printStackTrace(e);
		}
	}

}
