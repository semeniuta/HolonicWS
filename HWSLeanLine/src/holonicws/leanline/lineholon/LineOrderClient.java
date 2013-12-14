package holonicws.leanline.lineholon;

import holonicws.HWSDevice;
import holonicws.HWSParameter;
import holonicws.HWSSimpleCommunicator;
import holonicws.leanline.cellholon.CellHolon;

import java.io.IOException;
import java.util.List;

import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchCallback;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.eventing.ClientSubscription;
import org.ws4d.java.eventing.EventSource;
import org.ws4d.java.eventing.EventingException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.DataStructure;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;
import org.ws4d.java.types.URI;

public class LineOrderClient extends DefaultClient {
	
	private QName eventPortType = new QName("Events", "http://customer.com/holonicws");
	private ClientSubscription notificationSub = null;
	private LineHolon line;
	
	public LineOrderClient(LineHolon line) {
		super();
		
		SearchParameter search = new SearchParameter();
		search.setServiceTypes(new QNameSet(eventPortType));
		SearchCallback callback = this; 
		SearchManager.searchService(search, callback);
		
		this.line = line;
		
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
		
		// Get parameter value from the event
		String valueText = ParameterValueManagement.getString(parameterValue, "order");
		System.out.println("Notification event received: " + valueText);
		int value = Integer.parseInt(valueText);
		
		// Get the reference to the last cell (HWSDevice object)
		List<CellHolon> cells = line.getCells();
		CellHolon lastCell = cells.get(cells.size() - 1); 
		HWSDevice lastCellDevice =  lastCell.getDevice();
		
		// Locate and invoke the operation on the last cell
		try {
			HWSSimpleCommunicator comm = new HWSSimpleCommunicator();
			String serviceURI = "http://manufacturer.no/holonicws/ManufacturingService";
			String opName = "RequestProduction";
			Service manufService = comm.getService(lastCellDevice, serviceURI);
			Operation op = comm.getOperation(manufService, opName);
			ParameterValue response = comm.invokeOperation(op, new HWSParameter("request", value));

			System.out.println(response);
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (InvocationException e) {
			e.printStackTrace();
		}
		 
		
		return super.eventReceived(subscription, actionURI, parameterValue);
	}
		
}
