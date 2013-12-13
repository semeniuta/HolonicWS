package run;

import java.io.IOException;

import services.ServiceMotionDetector;
import services.ServicePictures;
import services.ServiceRecordControl;
import services.ServiceVideoStreaming;
import devices.DeviceCamera;
import devices.DeviceSensorMotion;
import devices.DeviceVideo;
import events.MovementEvent;
import events.MovementEventProvider;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.eventing.EventSource;
import org.ws4d.java.types.QName;

public class FullExampleProvider {

	private final static String	namespace	= "http://www.gisum.uma.es/jmeds";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// mandatory: Starting the DPWS Framework
		JMEDSFramework.start(args);

		// first we need a device
		DeviceVideo deviceVideo = new DeviceVideo();
		DeviceSensorMotion deviceSensorMotion = new DeviceSensorMotion();
		DeviceCamera deviceCamera = new DeviceCamera();
		
		// then we create a service
		final ServiceVideoStreaming serviceVideo = new ServiceVideoStreaming ();
		final ServiceRecordControl serviceCamera = new ServiceRecordControl ();
		final ServiceMotionDetector serviceMotion = new ServiceMotionDetector ();
		final ServicePictures servicePictures = new ServicePictures();

		// we get the event source from the service to start the event thread
		EventSource event = serviceMotion.getEventSource(new QName("BasicServices", namespace), "MovementEvent", null, "MovementEvent");
		MovementEventProvider eventProvider = new MovementEventProvider((MovementEvent) event);
		eventProvider.start();
		

		// in the end we add our service to the device
		deviceVideo.addService(serviceVideo);
		deviceSensorMotion.addService(serviceMotion);
		deviceCamera.addService(serviceCamera);
		deviceCamera.addService(servicePictures);

		// do not forget to start the device!
		try {
			deviceVideo.start();
			deviceSensorMotion.start();
			deviceCamera.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
