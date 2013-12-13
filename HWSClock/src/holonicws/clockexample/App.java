package holonicws.clockexample;

import java.io.IOException;

import holonicws.HWSDevice;
import holonicws.HWSOperation;
import holonicws.HWSService;

import org.ws4d.java.JMEDSFramework;

public class App {

	public static void main(String[] args) throws IOException {
		
		JMEDSFramework.start(args);
		
		HWSDevice clockDevice = new HWSDevice("ClockDevice", "http://sintef.no/holonicws", "eth9");
		clockDevice.addFriendlyName("en-US", "Clock Device");
		clockDevice.addManufacturer("en-US", "SINTEF Raufoss Manufacturing AS");
		clockDevice.addModelName("en-US", "Simulated clock");
		
		HWSService timeService = new HWSService("TimeService", clockDevice);
		HWSOperation op = new GetCurrentTimeOperation("GetCurrentTime", timeService);
		timeService.addOperation(op);
		clockDevice.addService(timeService);
		
		clockDevice.start();
		SearchClient client = new SearchClient();

	}

}
