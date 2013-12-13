package services;

import operations.*;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.URI;

import devices.DeviceVideo;

public class ServiceRecordControl extends DefaultService {
	
	public final static URI	RECORDCONTROL_SERVICE_ID	= new URI(DeviceVideo.namespace + "/ServiceRecordControl");

	/**
	 * Standard Constructor
	 */
	public ServiceRecordControl() {
		super();
		
		this.setServiceId(RECORDCONTROL_SERVICE_ID);

		//java this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("192.168.1.132", false), 0, "docuService", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

		// (tutorial 2) add Operations from tutorial 2 to the service
		
			CameraAuthOperation authOp = new CameraAuthOperation();
			addOperation(authOp);
			
			CameraCaptureOperation captOp = new CameraCaptureOperation();
			addOperation(captOp);
			
			CameraHaltOperation haltOp = new CameraHaltOperation();
			addOperation(haltOp);
			
			CameraMoveOperation moveOp = new CameraMoveOperation();
			addOperation(moveOp);
			
			CameraRecordOperation recOp = new CameraRecordOperation();
			addOperation(recOp);
			
			CameraZoomOperation zoomOp = new CameraZoomOperation();
			addOperation(zoomOp);

		/*
		 * 	DocuExampleComplexOperation complexOp = new DocuExampleComplexOperation();
			addOperation(complexOp);

			DocuExampleAttributeOperation attrOp = new DocuExampleAttributeOperation();
			addOperation(attrOp);
		*/
		
	}

}
