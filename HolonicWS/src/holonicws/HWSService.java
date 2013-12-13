package holonicws;

import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.URI;

public class HWSService extends DefaultService {
 
	private URI serviceId;
	private QName serviceType;
	private HWSDevice device;
	
	/**
	 * Create an instance of HWSService
	 * @param service_name Service name. Further used for constructing serice ID 
	 *                     ("http://example.org/namespace/ServiceName")
	 *                     and service type  
	 * @param device The device hosting the service
	 */
	public HWSService(String service_name, HWSDevice device) {
		super();
		
		this.device = device;
		
		this.serviceId = composeServiceId(service_name);
		this.setServiceId(this.serviceId);
		
		this.serviceType = new QName(service_name, this.getNamespace());
		
	}
	
	/**
	 * Get the namespace of the service (inherited from the hosting device)
	 * @return A String object representing the namespace (e.g. "http://example.org/namespace")
	 */
	public String getNamespace() {
		return this.device.getNamespace();
	}
	
	/**
	 * Get service type
	 * @return
	 */
	public QName getServiceType() {
		return this.serviceType;
	}
	
	public HWSDevice getHostingDevice() {
		return this.device;
	}
	
	/**
	 * Compose service ID in a format "http://example.org/namespace/ServiceName"
	 * @param service_name
	 * @return URI object representing service ID
	 */
	protected URI composeServiceId(String service_name) {
		return new URI(this.getNamespace() + "/" + service_name);
	}
}
