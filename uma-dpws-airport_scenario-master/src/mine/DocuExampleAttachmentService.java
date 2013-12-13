package mine;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.URI;

public class DocuExampleAttachmentService extends DefaultService {

	public final static String	namespace	= "http://www.mydemo.com/tutorial";

	public final static String	PORTTYPE	= "AttachmentService";

	public final static QName	QN_PORTTYPE	= new QName(PORTTYPE, DocuExampleAttachmentService.namespace);

	public final static URI		SERVICE_ID	= new URI(DocuExampleAttachmentService.namespace, PORTTYPE);

	public final static String	filePath	= "wiki/code/se/tutorial07/ws4d-logo-transparent.gif";

	public DocuExampleAttachmentService() {
		super();

		setServiceId(SERVICE_ID);

		this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1", false), 5679, PORTTYPE, DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

		// add Operations
		DocuExampleSetAttachmentOperation setAttachmentOperation = new DocuExampleSetAttachmentOperation();
		this.addOperation(setAttachmentOperation);

		DocuExampleGetAttachmentOperation getAttachmentOperation = new DocuExampleGetAttachmentOperation();
		this.addOperation(getAttachmentOperation);
	}

}
