package mine;

import java.io.IOException;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.eventing.EventSource;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.types.QName;

public class DocuExampleEventServiceProvider {

	private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(args);

		// first we need a device
		DefaultDevice device = new DefaultDevice();

		// then we create a service
		final DocuExampleEventService service = new DocuExampleEventService();

		// we get the event source from the service to start the event thread
		EventSource event = service.getEventSource(new QName("BasicServices", namespace), "DocuExampleSimpleEvent", null, "DocuExampleSimpleEvent");
		DocuExampleEventProvider eventProvider = new DocuExampleEventProvider((DocuExampleSimpleEvent) event);
		eventProvider.start();

		// we get the complex event source from the service to start the event
		// thread
		EventSource solicitEvent = service.getEventSource(new QName("BasicServices", namespace), "DocuExampleComplexEvent", "DocuExampleComplexEventResponse", "DocuExampleComplexEventSolicit");
		DocuExampleComplexEventProvider solicitEventProvider = new DocuExampleComplexEventProvider((DocuExampleComplexEvent) solicitEvent);
		solicitEventProvider.start();

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
