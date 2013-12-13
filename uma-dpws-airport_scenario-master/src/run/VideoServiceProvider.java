package run;

import java.io.IOException;

import services.ServiceVideoStreaming;
import devices.DeviceVideo;

import org.ws4d.java.JMEDSFramework;

public class VideoServiceProvider {

	//private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(args);

		// first we need a device
		DeviceVideo deviceVideo = new DeviceVideo();

		// then we create a service
		final ServiceVideoStreaming service = new ServiceVideoStreaming ();
		
		/*// we get the event source from the service to start the event thread
		EventSource event = service.getEventSource(new QName("BasicServices", namespace), "MovementEvent", null, "MovementEvent");
		MovementEventProvider eventProvider = new MovementEventProvider((MovementEvent) event);
		eventProvider.start();

		we get the complex event source from the service to start the event
		// thread
		EventSource solicitEvent = service.getEventSource(new QName("BasicServices", namespace), "DocuExampleComplexEvent", "DocuExampleComplexEventResponse", "DocuExampleComplexEventSolicit");
		DocuExampleComplexEventProvider solicitEventProvider = new DocuExampleComplexEventProvider((DocuExampleComplexEvent) solicitEvent);
		solicitEventProvider.start();
		*/

		// in the end we add our service to the device
		deviceVideo.addService(service);

		// do not forget to start the device!
		try {
			deviceVideo.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
