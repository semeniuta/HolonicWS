package mine;

import java.io.IOException;
import java.util.Random;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.util.Log;


public class DocuExampleLifecycleDeviceStarter {
	
	private static final long					TIME_TO_WAIT	= 4000;

	public static void main(String[] args){
		
		JMEDSFramework.start(args);
		// create device for status changes
		DocuExampleDevice device = new DocuExampleDevice();

		// Then we create a service ...
		final DocuExampleService service = new DocuExampleService();

		DocuExampleSimpleEvent notification = new DocuExampleSimpleEvent();
		service.addEventSource(notification);

		// in the end we add our service to the device
		device.addService(service);

		int firmware = 1;
		Random rnd = new Random();

		while (true) {
			try {
				/*
				 * Start the device => send hello
				 */
				device.start();
				Thread.sleep(TIME_TO_WAIT);

				/*
				 * Change metadata of running device => send hello with
				 * incremented metadata version.
				 */
				device.exclusiveLock();
				try {
					/*
					 * To change more than one parameters of a local device at
					 * one time, it is better to use the lock of the device.
					 * This let the device announce the change by a hello only
					 * once and not for each change.
					 */
					device.setFirmwareVersion(String.valueOf(firmware++));
					device.setModelNumber(String.valueOf(rnd.nextLong()));
				} finally {
					device.releaseExclusiveLock();
				}
				Thread.sleep(TIME_TO_WAIT);

				/*
				 * Stop the device => device sends bye.
				 */
				device.stop();
				Thread.sleep(TIME_TO_WAIT);

				/*
				 * Start the device => device sends hello.
				 */
				device.start();
				Thread.sleep(TIME_TO_WAIT);

				/*
				 * Stop the device => device sends bye.
				 */
				device.stop();

				/*
				 * Device is stopped => we don't send anything.
				 */
				device.setModelNumber(String.valueOf(rnd.nextLong()));
				Thread.sleep(TIME_TO_WAIT);

				/*
				 * Start the device => device sends hello with incremented
				 * metadata version.
				 */
				device.start();
				Thread.sleep(TIME_TO_WAIT);

				device.stop();
				Thread.sleep(TIME_TO_WAIT);

			} catch (InterruptedException e) {
				Log.printStackTrace(e);
			} catch (IOException e) {
				Log.printStackTrace(e);
			}

		}
	}
}
