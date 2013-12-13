package services;

import devices.DeviceVideo;
import events.MovementEvent;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.URI;

public class ServiceMotionDetector extends DefaultService {
	
	public final static URI	MOTIONDETECTOR_SERVICE_ID	= new URI(DeviceVideo.namespace + "/ServiceMotionDetector");

	/**
	 * Standard Constructor
	 */
	public ServiceMotionDetector() {
		super();
		
		this.setServiceId(MOTIONDETECTOR_SERVICE_ID);

		//this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1", false), 5678, "docuEventService", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));
		
		// add Events to the service
		MovementEvent notification = new MovementEvent();
		addEventSource(notification);
		
			/*
			DocuExampleSimpleOperation simpleOp = new DocuExampleSimpleOperation();
			addOperation(simpleOp);

			DocuExampleComplexOperation complexOp = new DocuExampleComplexOperation();
			addOperation(complexOp);

			DocuExampleAttributeOperation attrOp = new DocuExampleAttributeOperation();
			addOperation(attrOp);
			*/
		
	}

}
