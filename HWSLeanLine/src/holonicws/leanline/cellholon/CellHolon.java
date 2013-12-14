package holonicws.leanline.cellholon;

import java.io.IOException;

import holonicws.HWSDevice;
import holonicws.HWSService;

public class CellHolon {

	private final static String NAMESPACE = "http://manufacturer.no/holonicws";
	
	private HWSDevice device;
	private HWSService manufacturingService;
	
	public CellHolon(String device_name, String iface_name) throws IOException {
		device = new HWSDevice(device_name, NAMESPACE, iface_name);
		
		manufacturingService = new HWSService("ManufacturingService", device);
		manufacturingService.addOperation(new RequestProductionOperation("RequestProduction", manufacturingService));
		device.addService(manufacturingService);
		
		device.start();
	}
	
	public HWSDevice getDevice() {
		return device;
	}

}
