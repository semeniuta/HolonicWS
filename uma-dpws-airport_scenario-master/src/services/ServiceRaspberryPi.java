package services;

import operations.GetCityOp;
import operations.GetTimeDateOp;
import operations.HelloPiOp;

import org.ws4d.java.service.DefaultService;
import org.ws4d.java.types.URI;

import devices.DeviceVideo;

public class ServiceRaspberryPi extends DefaultService {
	
	public final static URI	RASPBERRYPI_SERVICE_ID	= new URI(DeviceVideo.namespace + "/ServiceRaspberryPi");

	/**
	 * Standard Constructor
	 */
	public ServiceRaspberryPi() {
		super();
		
		this.setServiceId(RASPBERRYPI_SERVICE_ID);

		//java this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("192.168.1.132", false), 0, "docuService", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));

		// (tutorial 2) add Operations from tutorial 2 to the service
		
			HelloPiOp hello = new HelloPiOp();
			addOperation(hello);
			
			GetCityOp getCity = new GetCityOp();
			addOperation(getCity);
			
			GetTimeDateOp getTime = new GetTimeDateOp();
			addOperation(getTime);
			
	}

}
