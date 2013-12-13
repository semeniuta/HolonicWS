package mine;

import java.io.IOException;

import org.ws4d.java.*;
//import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.types.QName;

public class DocuExampleServiceProvider {

	private final static String	namespace = "http://www.mydemo.com/tutorial";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework.
		JMEDSFramework.start(args);

		// First we need a device.
		DocuExampleDevice device = new DocuExampleDevice();

		// Then we create a service.
		final DocuExampleService service = new DocuExampleService();

		// Add a very very simple operation to the service.
		service.addOperation(new Operation("DocuHelloWorldOp", new QName("BasicServices", namespace)) {

			// we have to implement the invoke method
			public ParameterValue invokeImpl(ParameterValue pv, CredentialInfo localCredentialInfo) throws InvocationException {
				// all we want to do is to print Hello World!
				System.out.println("HelloWorld!");
				return pv;
			}
		});

		// In the end we add our service to the device.
		device.addService(service);

		// Do not forget to start the device!
		try {
			device.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
