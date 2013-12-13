package mine;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.structures.HashSet;
import org.ws4d.java.types.HelloData;
import org.ws4d.java.util.Log;

/**
 * This example shows how the device behaves if it changes metadata and its
 * running state. The example contains a local client receiving the device
 * changes.
 */
public class DocuExampleDeviceLifecycleClient extends DefaultClient {

	private static HashSet	devicesWeKnow	= new HashSet();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// only show output of device changes
		Log.setLogLevel(Log.DEBUG_LEVEL_NO_LOGGING);
		JMEDSFramework.start(args);

		// create client who listens to device status changes
		DocuExampleDeviceLifecycleClient lifecycleClient = new DocuExampleDeviceLifecycleClient();
		// register hello listening
		lifecycleClient.registerHelloListening();
	}

	public void helloReceived(HelloData helloData) {

		// maybe we are already listening to this device. Then we can ignore
		// this hello message...
		if (!devicesWeKnow.contains(helloData.getEndpointReference())) {

			// we will get the device reference and by doing this we will tell
			// the device that we are interested and want to listen to status
			// changes
			DeviceReference deviceReference = getDeviceReference(helloData, SecurityKey.EMPTY_KEY);
			try {
				// build up device (otherwise device is running but not built
				// up)
				deviceReference.getDevice();
				// add it to our set so we can look up if we already know it
				devicesWeKnow.add(deviceReference.getEndpointReference());
				System.out.println("Listening to changes of " + deviceReference + " now.");
			} catch (CommunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void deviceRunning(DeviceReference deviceRef) {
		System.out.println(deviceRef.getEndpointReference() + " - Device running.");
	}

	public void deviceChanged(DeviceReference deviceRef) {
		System.out.println(deviceRef.getEndpointReference() + " - Device changed to metadata version.");
	}

	public void deviceBuiltUp(DeviceReference deviceRef, Device device) {
		System.out.println(deviceRef.getEndpointReference() + " - Device built up");
	}

	public void deviceBye(DeviceReference deviceRef) {
		System.out.println(deviceRef.getEndpointReference() + " - Device has send bye message");
	}

	@Override
	public void deviceCommunicationErrorOrReset(DeviceReference deviceRef) {
		System.out.println("An error occured or device has been reset.");
	}
}
