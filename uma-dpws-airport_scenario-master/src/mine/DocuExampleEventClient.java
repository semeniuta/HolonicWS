package mine;

import java.io.IOException;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.DPWSProtocolVersion;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.configuration.DPWSProperties;
import org.ws4d.java.constants.DPWS2006.DPWSConstants2006;
import org.ws4d.java.eventing.ClientSubscription;
import org.ws4d.java.eventing.EventSource;
import org.ws4d.java.message.eventing.SubscriptionEndMessage;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.DataStructure;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;
import org.ws4d.java.types.URI;

/**
 * Sample client for receiving events.
 */
public class DocuExampleEventClient extends DefaultClient {

	final static String namespace = "http://www.mydemo.com/tutorial";

	final static QName service = new QName("BasicServices", namespace);

	private ClientSubscription solicitSub = null;

	private ClientSubscription notificationSub = null;

	private EventSource eventSourceSolicitResponse = null;
	

	public static void main(String[] args) {

		// mandatory starting of DPWS framework
		JMEDSFramework.start(args);

		DPWSProperties.getInstance().removeSupportedDPWSVersion(
				new DPWSProtocolVersion(DPWSConstants2006.DPWS_VERSION2006));

		// create client
		DocuExampleEventClient client = new DocuExampleEventClient();

		// search for event and subscribe
		SearchParameter search = new SearchParameter();
		search.setServiceTypes(new QNameSet(service));
		SearchManager.searchService(search, client);
	}

	public void serviceFound(ServiceReference servRef, SearchParameter search) {

		try {
			// use this code to subscribe to the simple event
			{
				// get event source
				EventSource eventSource = servRef.getService()
						.getAnyEventSource(service, "DocuExampleSimpleEvent");

				if (eventSource != null) {
					// add binding
					DataStructure bindings = new org.ws4d.java.structures.ArrayList();
					HTTPBinding binding = new HTTPBinding(IPNetworkDetection
							.getInstance().getIPAddressOfAnyLocalInterface(
									"127.0.0.1", false), 10235,
							"/EventSink",
							DPWSCommunicationManager.COMMUNICATION_MANAGER_ID);
					bindings.add(binding);

					// subscribe
					notificationSub = eventSource.subscribe(this, 0, bindings, CredentialInfo.EMPTY_CREDENTIAL_INFO);
				}
			}
			// use this code to subscribe to the solicit-response event
			{
				// get event source
				eventSourceSolicitResponse = servRef.getService()
						.getAnyEventSource(service, "DocuExampleComplexEvent");

				if (eventSourceSolicitResponse != null) {
					// add binding
					DataStructure bindings = new org.ws4d.java.structures.ArrayList();
					HTTPBinding binding = new HTTPBinding(IPNetworkDetection
							.getInstance().getIPAddressOfAnyLocalInterface(
									"192.168.1.132", false), 7896, "/EventSink",
							DPWSCommunicationManager.COMMUNICATION_MANAGER_ID);
					bindings.add(binding);

					// subscribe
					solicitSub = eventSourceSolicitResponse.subscribe(this, 0,
							bindings, CredentialInfo.EMPTY_CREDENTIAL_INFO);
				}
			}
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (InvocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ParameterValue eventReceived(ClientSubscription subscription,
			URI actionURI, ParameterValue parameterValue) {

		if (subscription.equals(notificationSub)) {
			String eventText = ParameterValueManagement.getString(
					parameterValue, "name");
			System.out.println("Notification event received from " + eventText);
			return null;
		} else if (subscription.equals(solicitSub)) {
			// solicit-response
			String eventText = ParameterValueManagement.getString(
					parameterValue, "counter");
			System.out.println("Solicit-response event received, counter is "
					+ eventText);

			// create response
			ParameterValue pvResponse = eventSourceSolicitResponse
					.createInputValue();
			ParameterValueManagement.setString(pvResponse, null, "5");
			return pvResponse;
		}
		return null;
	}

	public void subscriptionTimeoutReceived(ClientSubscription subscription) {
		subscriptionEndReceived(subscription,
				SubscriptionEndMessage.WSE_STATUS_UNKNOWN);
	}

	public void subscriptionEndReceived(ClientSubscription subscription,
			int reason) {
		System.err.println("Subscription ended.");
	}
}
