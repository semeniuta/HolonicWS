package run;

import java.io.IOException;

import services.ServicePictures;
import services.ServiceRecordControl;
import services.ServiceStreaming;
import devices.DeviceCamera;

import org.ws4d.java.JMEDSFramework;

public class CameraServiceProvider {

	//private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(args);

		// first we need a device
		DeviceCamera deviceCamera = new DeviceCamera();

		// then we create a service
		ServiceRecordControl service = new ServiceRecordControl ();
		ServicePictures serviceP = new ServicePictures();
		ServiceStreaming serviceS = new ServiceStreaming();
		
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
		deviceCamera.addService(service);
		deviceCamera.addService(serviceP);
		deviceCamera.addService(serviceS);

		// do not forget to start the device!
		try {
			deviceCamera.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
