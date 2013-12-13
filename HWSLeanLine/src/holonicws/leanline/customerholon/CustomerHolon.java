package holonicws.leanline.customerholon;

import java.io.IOException;

import org.ws4d.java.eventing.EventSource;
import org.ws4d.java.types.QName;

import holonicws.HWSDevice;
import holonicws.HWSService;

public class CustomerHolon {

	private final static String NAME = "Customer";
	private final static String NAMESPACE = "http://customer.com/holonicws";
	
	private HWSDevice device;
	private OrderEventProvider eventProvider;
	
	public CustomerHolon(String iface_name) throws IOException {
		
		// Initialize the device
		device = new HWSDevice(NAME, NAMESPACE, iface_name);
		
		// Initialize an event service (producting orders)
		// and add it to the device
		HWSService service = new OrderEventService(device); 
		device.addService(service);
		
		// Extract event source from the event service
		// and start the event provider
		EventSource event = service.getEventSource(new QName("Events", service.getNamespace()), "OrderEvent", null, "OrderEvent");
		eventProvider = new OrderEventProvider((OrderEvent) event);
		eventProvider.start();
		
		// Start the device
		device.start();
	}

}
