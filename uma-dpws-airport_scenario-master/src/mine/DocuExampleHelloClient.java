package mine;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.service.Device;
import org.ws4d.java.types.HelloData;

/**
 * Sample client for the processing of hello messages.
 */
public class DocuExampleHelloClient extends DefaultClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// mandatory starting of DPWS framework
		JMEDSFramework.start(args);
		
		/*// First we need a device.
		DocuExampleDevice device = new DocuExampleDevice();

		// Then we create a service.
		final DocuExampleService service = new DocuExampleService();
		
		// In the end we add our service to the device.
		device.addService(service);

		// Do not forget to start the device!
		try {
			device.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		// create client
		DocuExampleHelloClient client = new DocuExampleHelloClient();

		// register hello listening for all possible domains
		client.registerHelloListening();
	}

	public void helloReceived(HelloData helloData) {
		try {
			// get device
			Device device = getDeviceReference(helloData).getDevice();
			System.out.println("Hello received from: ");

			// get metadata

			// EndpointReference(String)
			System.out.println("EndpointReference\t" + (device.getEndpointReference() != null ? device.getEndpointReference().getAddress().toString() : null));

			// XAddresses (URISet)
			System.out.println("XAddresses\t\t" + (device.getTransportXAddressInfos().hasNext() ? device.getTransportXAddressInfos().next() : null));

			// Scopes (ScopeSet)
			System.out.println("Scopes\t\t\t" + (device.getScopes().hasNext() ? device.getScopes().next() : null));

			// Metadata Version (long)
			System.out.println("Metadata Version\t" + device.getMetadataVersion());

			// Firmware Version (String)
			System.out.println("Firmware Version\t" + device.getFirmwareVersion());

			// Friendly Names (Collection)
			System.out.println("Friendly Names\t\t" + (device.getFriendlyNames().hasNext() ? device.getFriendlyNames().next() : null));

			// Manufacturer Names (Collection)
			System.out.println("Manufacturer Names\t" + (device.getManufacturers().hasNext() ? device.getManufacturers().next() : null));

			// Manufacturer URL (String/Link)
			System.out.println("Manufacturer URL\t" + device.getManufacturerUrl());

			// Model Names (Collection)
			System.out.println("Model Names\t\t" + (device.getModelNames().hasNext() ? device.getModelNames().next() : null));

			// ModelNumber (String)
			System.out.println("ModelNumber\t\t" + device.getModelNumber());

			// Model URL (String/Link)
			System.out.println("Model URL\t\t" + device.getModelUrl());

			// PresentationURL (String/Link)
			System.out.println("PresentationURL\t\t" + device.getPresentationUrl());

			// Serial Number (String)
			System.out.println("Serial Number\t\t" + device.getSerialNumber());
		} catch (CommunicationException e) {
			e.printStackTrace();
		}
	}
}
