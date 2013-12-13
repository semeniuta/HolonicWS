package mine;

import java.io.IOException;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.service.DefaultDevice;

public class DocuExampleAttachmentServiceProvider {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(args);

		// first we need a device
		DefaultDevice device = new DefaultDevice();

		// then we create a service
		DocuExampleAttachmentService service = new DocuExampleAttachmentService();

		// in the end we add our service to the device
		device.addService(service);

		// do not forget to start the device!
		try {
			device.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
