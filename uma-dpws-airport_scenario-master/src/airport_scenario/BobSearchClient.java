package airport_scenario;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.communication.DPWSProtocolVersion;
import org.ws4d.java.configuration.DPWSProperties;
import org.ws4d.java.constants.DPWS2006.DPWSConstants2006;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.types.LocalizedString;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;

/**
 * Sample client for searching devices and services.
 */
public class BobSearchClient extends DefaultClient {

	public final static String	namespace	= "http://www.gisum.uma.es/jmeds";
	final static QName	service		= new QName("BasicServices", namespace);

	public static void main(String[] args) {

		// mandatory starting of DPWS framework
		JMEDSFramework.start(args);

		DPWSProperties.getInstance().removeSupportedDPWSVersion(new DPWSProtocolVersion(DPWSConstants2006.DPWS_VERSION2006));

		// create client
		BobSearchClient client = new BobSearchClient();

		// define what you are searching for and start search
		SearchParameter search = new SearchParameter();
		search.setDeviceTypes(new QNameSet(new QName("Camera-UMA", namespace)));
		SearchManager.searchDevice(search, client, null);

		// search services
		search = new SearchParameter();
		search.setServiceTypes(new QNameSet(new QName("BasicServices" , namespace)));
		SearchManager.searchService(search, client);
		
		/*// Use without discovery
		EndpointReference epr = new EndpointReference(new AttributedURI("urn:uuid:4e125b40-6632-11e2-80d5-fcbdecddf1fb"));
		XAddressInfo xAddress = new XAddressInfo(new URI("http://139.2.61.217:63597/4e293ea0-6632-11e2-80d8-fcbdecddf1fb"));
		xAddress.setProtocolInfo(new DPWSProtocolInfo(DPWSProtocolVersion.DPWS_VERSION_2009));
		XAddressInfoSet addresses = new XAddressInfoSet(xAddress);

		DeviceReference defRef = DeviceServiceRegistry.getDeviceReference(epr, addresses, true);
		try {
			Device dev = defRef.getDevice();
			// we know the searched service's id
			DefaultServiceReference sRef = (DefaultServiceReference) dev.getServiceReference(DocuExampleService.DOCU_EXAMPLE_SERVICE_ID, SecurityKey.EMPTY_KEY);
			Service serv = sRef.getService();

			// we are looking for an Operation by its name
			Operation op = serv.getOperation(null, "DocuExampleSimpleOperation", null, null);

			ParameterValue input = op.createInputValue();
			ParameterValueManagement.setString(input, DocuExampleSimpleOperation.NAME, "Number One");

			ParameterValue result;
			try {
				result = op.invoke(input, CredentialInfo.EMPTY_CREDENTIAL_INFO);
				System.out.println("Response from the DocuExampleSimpleOperation: " + result.toString());
			} catch (AuthorizationException e) {
				e.printStackTrace();
			} catch (InvocationException e) {
				e.printStackTrace();
			}
		} catch (CommunicationException e) {
			e.printStackTrace();
		}*/
	}

	public void serviceFound(ServiceReference servRef, SearchParameter search) {

		try {
			// use this code to call the DokuExampleSimplestOperation
			// {
			// // get Operation
			// Operation op = servRef.getService().getAnyOperation(service,
			// "DocuExampleSimpleOperation");
			//
			// // create input value and set string value
			// ParameterValue request = op.createInputValue();
			// ParameterValueManagement.setString(request, "name", "Bobby");
			//
			// System.err.println("Invoking HelloWorldOp...");
			//
			// // invoke operation with prepared input
			// ParameterValue result = op.invoke(request);
			//
			// // get string value from answer
			// String reply = ParameterValueManagement.getString(result,
			// "reply");
			//
			// System.err.println(reply);
			// }

			// use this code to call the DokuExampleComplexOperation

			{
				// get operation
				Operation op = servRef.getService().getOperation(service, "CameraRecordOperation", null, null);

				// create input value and set string values
				ParameterValue request = op.createInputValue();
				ParameterValueManagement.setString(request, "name", "Bobby");
				//ParameterValueManagement.setString(request, "address[0]", "2500 University Blvd.");
				//ParameterValueManagement.setString(request, "address[1]", "263 Laurent");

				System.err.println("Invoking HelloWorldOp...");

				// invoke operation with prepared input
				ParameterValue result = op.invoke(request, CredentialInfo.EMPTY_CREDENTIAL_INFO);

				System.err.println("Finished invoking HelloWorldOp...");
				// get string value from answer
				String reply = ParameterValueManagement.getString(result, "reply");

				System.out.println(reply);
			}

		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (InvocationException e) {
			e.printStackTrace();
		}

	}

	public void deviceFound(DeviceReference devRef, SearchParameter search) {
		try {
			Device device = devRef.getDevice();
			System.err.println("Found Camera-UMA: " + device.getFriendlyName(LocalizedString.LANGUAGE_EN));
		} catch (CommunicationException e) {
			e.printStackTrace();
		}

	}
}