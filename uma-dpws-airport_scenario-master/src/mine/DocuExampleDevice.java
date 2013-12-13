package mine;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.*;
import org.ws4d.java.types.LocalizedString;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;

public class DocuExampleDevice extends DefaultDevice {

	private final static String	namespace	= "http://www.mydemo.com/tutorial";

	/**
	 * Constructor of our device.
	 */
	public DocuExampleDevice() {
		super();

		/*
		 * The following lines add metadata information to the device to
		 * illustrate how it works. As default values are defined for all of the
		 * fields, you CAN set new values here but you do NOT have to.
		 */

		// set PortType
		this.setPortTypes(new QNameSet(new QName("DocuExampleDevice", namespace)));

		// add device name (name is language specific)
		this.addFriendlyName("en-EN", "DocuDevice");
		this.addFriendlyName(LocalizedString.LANGUAGE_EN, "DocuDevice");

		// add device manufacturer (manufacturer is language specific)
		this.addManufacturer(LocalizedString.LANGUAGE_EN, "LCC - Univ. de Málaga.");
		this.addManufacturer("en-EN", "LCC - Univ. de Málaga");

		// add model name (model name is language specific)
		this.addModelName(LocalizedString.LANGUAGE_EN, "DocuModelUMA");

		// add binding
		this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("192.168.1.132", false), 0, "docuDevice", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

	}
}
