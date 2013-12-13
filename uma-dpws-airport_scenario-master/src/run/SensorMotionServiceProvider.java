package run;

import java.io.IOException;

import services.ServiceMotionDetector;
import devices.DeviceSensorMotion;
import events.MovementEvent;
import events.MovementEventProvider;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.eventing.EventSource;
//import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.types.QName;

public class SensorMotionServiceProvider {

	public final static String	namespace	= "http://www.gisum.uma.es/jmeds";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(args);

		// first we need a device
		DeviceSensorMotion deviceSensorMotion = new DeviceSensorMotion();

		// then we create a service
		final ServiceMotionDetector service = new ServiceMotionDetector ();

		// we get the event source from the service to start the event thread
		EventSource event = service.getEventSource(new QName("BasicServices", namespace), "MovementEvent", null, "MovementEvent");
		MovementEventProvider eventProvider = new MovementEventProvider((MovementEvent) event);
		eventProvider.start();

		/* we get the complex event source from the service to start the event
		// thread
		EventSource solicitEvent = service.getEventSource(new QName("BasicServices", namespace), "DocuExampleComplexEvent", "DocuExampleComplexEventResponse", "DocuExampleComplexEventSolicit");
		DocuExampleComplexEventProvider solicitEventProvider = new DocuExampleComplexEventProvider((DocuExampleComplexEvent) solicitEvent);
		solicitEventProvider.start();*/

		// in the end we add our service to the device
		deviceSensorMotion.addService(service);

		// do not forget to start the device!
		try {
			deviceSensorMotion.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
