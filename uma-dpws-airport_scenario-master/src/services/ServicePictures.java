package services;

import operations.CameraGetPictureOperation;
import operations.CameraSetPictureOperation;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.URI;

public class ServicePictures extends DefaultService {

	public final static String	namespace	= "http://www.gisum.uma.es/jmeds";
	public final static String	PORTTYPE	= "AttachmentService";
	public final static QName	QN_PORTTYPE	= new QName(PORTTYPE, ServicePictures.namespace);
	public final static URI		SERVICE_ID	= new URI(ServicePictures.namespace, PORTTYPE);
	public final static String	filePath	= "/home/felix/Dropbox/UMA/workspace/eclipse/AirpotExample/attachments/ws4d-logo-transparent.gif";

	public ServicePictures() {
		super();

		setServiceId(SERVICE_ID);

		this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1", false), 5679, PORTTYPE, DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

		// add Operations
		CameraGetPictureOperation setAttachmentOperation = new CameraGetPictureOperation();
		this.addOperation(setAttachmentOperation);

		CameraSetPictureOperation getAttachmentOperation = new CameraSetPictureOperation();
		this.addOperation(getAttachmentOperation);
	}

}
