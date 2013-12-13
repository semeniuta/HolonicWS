package holonicws.leanline.supplierholon;

import holonicws.HWSDevice;

public class SupplierHolon extends HWSDevice {
	private final static String NAME = "Supplier";
	private final static String NAMESPACE = "http://supplier.de/holonicws";
	
	public SupplierHolon(String iface_name) {
		super(NAME, NAMESPACE, iface_name);
	}

}
