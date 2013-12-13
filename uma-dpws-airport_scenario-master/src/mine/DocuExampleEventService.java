package mine;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultService;

/**
 * 
 */
public class DocuExampleEventService extends DefaultService {

	/**
	 * Standard Constructor
	 */
	public DocuExampleEventService() {
		super();

		this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("192.168.1.132", false), 5678, "docuEventService", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

		// add Events to the service
		DocuExampleSimpleEvent notification = new DocuExampleSimpleEvent();
		addEventSource(notification);

		DocuExampleComplexEvent solicit = new DocuExampleComplexEvent();
		addEventSource(solicit);

	}

}
