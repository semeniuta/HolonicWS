package devices;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPDiscoveryDomain;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.connection.ip.NetworkInterface;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.communication.structures.IPDiscoveryBinding;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.types.LocalizedString;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;

import config.Configuration;

public class DeviceCamera extends DefaultDevice {

	public final static String	namespace	= "http://www.gisum.uma.es/jmeds";

	/**
	 * Constructor of our device.
	 */
	public DeviceCamera() {
		super();

		/*
		 * The following lines add metadata information to the device to
		 * illustrate how it works. As default values are defined for all of the
		 * fields, you CAN set new values here but you do NOT have to.
		 */

		// set PortType
		this.setPortTypes(new QNameSet(new QName("Camera-UMA", namespace)));

		// add device name (name is language specific)
		this.addFriendlyName("en-EN", "Camera-UMA");
		this.addFriendlyName(LocalizedString.LANGUAGE_EN, "Camera-UMA");

		// add device manufacturer (manufacturer is language specific)
		this.addManufacturer(LocalizedString.LANGUAGE_EN, "LCC - Univ. de Málaga.");
		this.addManufacturer("en-EN", "LCC - Univ. de Málaga");

		// add model name (model name is language specific)
		this.addModelName(LocalizedString.LANGUAGE_EN, "SimulatedCamera");

		// add binding (optional!)
		/*
		 * add discovery binding or change the ip (127.0.0.1) with an ip of a
		 * non loopback interface like eth1 or eth3
		 */
		NetworkInterface iface = IPNetworkDetection.getInstance().getNetworkInterface(Configuration.net_iface);
		IPDiscoveryDomain domain = IPNetworkDetection.getInstance().getIPDiscoveryDomainForInterface(iface, false);
		this.addBinding(new IPDiscoveryBinding(DPWSCommunicationManager.COMMUNICATION_MANAGER_ID, domain));

		//this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1", false), 0, "docuDevice", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));
	
	}
}
