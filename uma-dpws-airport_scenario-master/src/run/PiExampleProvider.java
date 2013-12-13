package run;

import java.io.IOException;

import org.ws4d.java.JMEDSFramework;

import services.ServiceRaspberryPi;
import devices.RaspberryPi;

public class PiExampleProvider {

	//private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(args);

		// first we need a device
		RaspberryPi pi = new RaspberryPi();

		// then we create a service
		ServiceRaspberryPi srvPi = new ServiceRaspberryPi ();
		
		/*service.addOperation(new Operation("DocuHelloWorldOp", new QName("BasicServices", namespace)) {

			// we have to implement the invoke method
			public ParameterValue invokeImpl(ParameterValue pv, CredentialInfo localCredentialInfo) throws InvocationException {
				// all we want to do is to print Hello World!
				System.out.println("HelloWorld!");
				return pv;
			}
		});*/


		/* we get the complex event source from the service to start the event
		// thread
		EventSource solicitEvent = service.getEventSource(new QName("BasicServices", namespace), "DocuExampleComplexEvent", "DocuExampleComplexEventResponse", "DocuExampleComplexEventSolicit");
		DocuExampleComplexEventProvider solicitEventProvider = new DocuExampleComplexEventProvider((DocuExampleComplexEvent) solicitEvent);
		solicitEventProvider.start();*/

		// in the end we add our service to the device
		pi.addService(srvPi);

		// do not forget to start the device!
		try {
			pi.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
