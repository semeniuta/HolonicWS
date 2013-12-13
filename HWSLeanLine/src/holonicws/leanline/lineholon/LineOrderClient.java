package holonicws.leanline.lineholon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchCallback;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.dispatch.DefaultServiceReference;
import org.ws4d.java.eventing.ClientSubscription;
import org.ws4d.java.eventing.EventSource;
import org.ws4d.java.eventing.EventingException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.DataStructure;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;
import org.ws4d.java.types.URI;

public class LineOrderClient extends DefaultClient {
	
	private QName eventPortType = new QName("Events", "http://customer.com/holonicws");
	private ClientSubscription notificationSub = null;
	
	public LineOrderClient() {
		super();
		
		SearchParameter search = new SearchParameter();
		search.setServiceTypes(new QNameSet(eventPortType));
		SearchCallback callback = this; 
		SearchManager.searchService(search, callback);
		
		System.out.println("Client created");
	}

	@Override
	public void serviceFound(ServiceReference servRef, SearchParameter search) {
		
		System.out.println("Event service found");
		
		try {
			EventSource eventSource = servRef.getService().getEventSource(eventPortType, "OrderEvent", null, null);
			
			if (eventSource != null) {
				DataStructure bindings = new org.ws4d.java.structures.ArrayList();
				HTTPBinding binding = new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1", false), 10235, "/EventSink", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID);
				bindings.add(binding);
				
				notificationSub = eventSource.subscribe(this, 0, bindings, CredentialInfo.EMPTY_CREDENTIAL_INFO);
				
				
				System.out.println("Client subscribed to event");
			}
			
			
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (EventingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ParameterValue eventReceived(ClientSubscription subscription,
			URI actionURI, ParameterValue parameterValue) {
		
		String eventText = ParameterValueManagement.getString(parameterValue, "order");
		System.out.println("Notification event received: " + eventText);
		
		return super.eventReceived(subscription, actionURI, parameterValue);
	}
		
}
