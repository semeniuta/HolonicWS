package holonicws.clockexample;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchCallback;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.dispatch.DefaultServiceReference;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;
import org.ws4d.java.types.URI;

public class SearchClient extends DefaultClient {
	private int numFound = 0;
	
	public SearchClient() {
		super();
		
		SearchParameter search = new SearchParameter();
		search.setDeviceTypes(new QNameSet(new QName("ClockDevice", "http://sintef.no/holonicws")));
		SearchCallback callback = this; 
		SearchManager.searchDevice(search, callback, null);
	}

	@Override
	public void deviceFound(DeviceReference devRef, SearchParameter search) {
		super.deviceFound(devRef, search);
		
		PrintWriter out;
		try {
			out = new PrintWriter("searchclient.txt");
			out.println(devRef.getEndpointReference());
		
			invokeService(devRef, out);
					
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}	
	}
	
	private void invokeService(DeviceReference devRef, PrintWriter out) {

		try {
			URI serviceId = new URI("http://sintef.no/holonicws/TimeService");
			
			out.println("[MY] Getting device");
			Device device = devRef.getDevice();
			out.println("[MY] Getting service reference");
			DefaultServiceReference servRef = (DefaultServiceReference) device.getServiceReference(serviceId, SecurityKey.EMPTY_KEY);
			out.println("[MY] Getting service");
			Service service = servRef.getService();
			out.println("[MY] Constructing request");
			Operation op = service.getOperation(null, "GetCurrentTime", null, null);
			ParameterValue request = op.createInputValue();
			ParameterValueManagement.setString(request, "input", "");
			out.println("[MY] Invoking operation");
			ParameterValue result = op.invoke(request, null);
			String reply = ParameterValueManagement.getString(result, "time");
			
			out.write(reply);
		} catch (CommunicationException | InvocationException e) {
			e.printStackTrace();
			out.println("Exception occured:");
			out.println(e.toString());
			out.close();
		}
	}
	
	
}
