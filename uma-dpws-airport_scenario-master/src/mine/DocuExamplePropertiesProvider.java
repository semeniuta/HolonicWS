package mine;

import java.io.IOException;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.service.DefaultService;

public class DocuExamplePropertiesProvider extends DefaultDevice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(new String[] { System.getProperty("user.dir") + "\\Tutorial 8\\org\\ws4d\\java\\docuExample.properties" });

		// using the 1st device-configId from the properties file
		DefaultDevice device = new DefaultDevice(1);

		// 1st service-configId defines the DefaultService
		DefaultService service = new DefaultService(1);

		// we have to add the service to our device
		device.addService(service);

		try {
			device.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
