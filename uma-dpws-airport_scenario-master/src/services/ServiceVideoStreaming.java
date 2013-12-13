package services;

import operations.VideoOnOperation;
import operations.VideoPlayOperation;
import operations.VideoStopOperation;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.URI;

import devices.DeviceVideo;

public class ServiceVideoStreaming extends DefaultService {
	
	public final static URI	VIDEOSTREAMING_SERVICE_ID	= new URI(DeviceVideo.namespace + "/ServiceVideoStreaming");

	/**
	 * Standard Constructor
	 */
	public ServiceVideoStreaming() {
		super();
		
		this.setServiceId(VIDEOSTREAMING_SERVICE_ID);

		//this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("192.168.1.132", false), 0, "docuService", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

		// (tutorial 2) add Operations from tutorial 2 to the service
		
			VideoOnOperation onOp = new VideoOnOperation();
			addOperation(onOp);
			
			VideoPlayOperation playOp = new VideoPlayOperation();
			addOperation(playOp);
			
			VideoStopOperation stopOp = new VideoStopOperation();
			addOperation(stopOp);

		/*
		 * 	DocuExampleComplexOperation complexOp = new DocuExampleComplexOperation();
			addOperation(complexOp);

			DocuExampleAttributeOperation attrOp = new DocuExampleAttributeOperation();
			addOperation(attrOp);
		*/
		
	}

}
