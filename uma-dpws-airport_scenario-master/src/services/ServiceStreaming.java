package services;

import operations.AttachmentOperation;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.service.Operation;
import org.ws4d.java.types.QName;
import org.ws4d.java.util.Log;

public class ServiceStreaming extends DefaultService {

	public static final QName	PORT_TYPE				= new QName("StreamingService", "http://www.gisum.uma.es/jmeds");
	public static final String	GET_STREAM_OPERATION	= "GetStream";

	public static void main(String[] args) throws Exception {
		Log.setShowTimestamp(true);

		// always start the framework first
		JMEDSFramework.start(args);

		// create a simple device ...
		DefaultDevice device = new DefaultDevice();

		// ... and a service
		DefaultService service = new DefaultService();

		Operation streamer = new AttachmentOperation(GET_STREAM_OPERATION, PORT_TYPE);

		Element stream = new Element(new QName("Stream", PORT_TYPE.getNamespace()), SchemaUtil.TYPE_BASE64_BINARY);
		stream.setMinOccurs(1);

		streamer.setOutput(stream);

		service.addOperation(streamer);

		// add service to device in order to support automatic discovery ...
		device.addService(service);

		/*
		 * ... and start the device; this also starts all services located on
		 * top of that device
		 */
		device.start();
	}

}
