package holonicws;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPDiscoveryDomain;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.connection.ip.NetworkInterface;
import org.ws4d.java.communication.structures.IPDiscoveryBinding;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.Service;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;

public class HWSDevice extends DefaultDevice {
	
	private String namespace;
	
	/**
	 * Create an instance of HWSDevice 
	 * @param device_name Device name
	 * @param iface_name Name of the interfrace (e.g. eth1), to which the device 
	 *                   will be bound
	 * @param namespace Namespace for the device and hosted services (e.g. "http://example.org/namespace")
	 */
	public HWSDevice(String device_name, String namespace) {
		super();
		
		this.namespace = namespace;
		
		QNameSet portTypes = new QNameSet();
		portTypes.add(new QName(device_name, this.namespace));
		this.setPortTypes(portTypes);
		
	}
	
	public HWSDevice(String device_name, String namespace, String iface_name) {
		this(device_name, namespace);
		setInterface(iface_name);
	}
	
	public void setInterface(String iface_name) {
		IPDiscoveryBinding binding = createBinding(iface_name);
		this.addBinding(binding);
	}
	
	public Service locateService(String serviceURI) {
		return null;
	}
	
	/**
	 * Get the namespace of the device
	 * @return A String object representing the namespace (e.g. "http://example.org/namespace")
	 */
	public String getNamespace() {
		return this.namespace;
	}
	
	protected IPDiscoveryBinding createBinding(String iface_name) {
		NetworkInterface iface = IPNetworkDetection.getInstance().getNetworkInterface(iface_name);
		IPDiscoveryDomain domain = IPNetworkDetection.getInstance().getIPDiscoveryDomainForInterface(iface, false);
		return new IPDiscoveryBinding(DPWSCommunicationManager.COMMUNICATION_MANAGER_ID, domain);
	}
}
