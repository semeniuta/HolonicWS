package mine;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultService;

public class DocuExampleService extends DefaultService {

	/**
	 * Standard Constructor
	 */
	public DocuExampleService() {
		super();

		this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("192.168.1.132", false), 0, "docuService", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

		// (tutorial 2) add Operations from tutorial 2 to the service
		
			DocuExampleSimpleOperation simpleOp = new DocuExampleSimpleOperation();
			addOperation(simpleOp);

			DocuExampleComplexOperation complexOp = new DocuExampleComplexOperation();
			addOperation(complexOp);

			DocuExampleAttributeOperation attrOp = new DocuExampleAttributeOperation();
			addOperation(attrOp);
		
	}

}
