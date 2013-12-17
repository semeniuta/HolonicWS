package holonicws.leanline.customerholon;

import org.ws4d.java.types.QName;

import holonicws.HWSDevice;
import holonicws.HWSService;

public class OrderEventService extends HWSService {
	
	private static final String NAME = "OrderEvent";
	
	public OrderEventService(HWSDevice device) {
		super(NAME, device);
		
		OrderEventSource orderEvent = new OrderEventSource(NAME, new QName("Events", this.getNamespace()), this);
		this.addEventSource(orderEvent);
		
	}

}
