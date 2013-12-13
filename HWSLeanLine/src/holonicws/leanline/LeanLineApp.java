package holonicws.leanline;

import java.io.IOException;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.eventing.EventSource;
import org.ws4d.java.types.QName;

import holonicws.HWSDevice;
import holonicws.HWSService;
import holonicws.leanline.customerholon.CustomerHolon;
import holonicws.leanline.customerholon.OrderEvent;
import holonicws.leanline.customerholon.OrderEventService;
import holonicws.leanline.customerholon.OrderEventProvider;
import holonicws.leanline.lineholon.LineHolon;
import holonicws.leanline.lineholon.LineOrderClient;
import holonicws.leanline.supplierholon.SupplierHolon;

public class LeanLineApp {
	
	public static void main(String[] args) throws IOException {
	
		JMEDSFramework.start(args);
		
		String iface_name = "net3";
		
		CustomerHolon customer = new CustomerHolon(iface_name);
		LineHolon line = new LineHolon(iface_name);
		
	}
	

}
