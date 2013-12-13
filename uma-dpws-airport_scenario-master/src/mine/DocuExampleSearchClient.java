package mine;

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
public class DocuExampleSearchClient extends DefaultClient {

	final static String	namespace	= "http://www.mydemo.com/tutorial";

	final static QName	service		= new QName("BasicServices", namespace);

	public static void main(String[] args) {

		// mandatory starting of DPWS framework
		JMEDSFramework.start(args);

		DPWSProperties.getInstance().removeSupportedDPWSVersion(new DPWSProtocolVersion(DPWSConstants2006.DPWS_VERSION2006));

		// create client
		DocuExampleSearchClient client = new DocuExampleSearchClient();

		// define what you are searching for and start search
		SearchParameter search = new SearchParameter();
		search.setDeviceTypes(new QNameSet(new QName("DocuExampleDevice", namespace)));
		SearchManager.searchDevice(search, client, null);

		// search services
		search = new SearchParameter();
		search.setServiceTypes(new QNameSet(service));
		SearchManager.searchService(search, client);
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
				Operation op = servRef.getService().getAnyOperation(service, "DocuExampleComplexOperation");

				// create input value and set string values
				ParameterValue request = op.createInputValue();
				ParameterValueManagement.setString(request, "name", "Bobby");
				ParameterValueManagement.setString(request, "address[0]", "2500 University Blvd.");
				ParameterValueManagement.setString(request, "address[1]", "263 Laurent");

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
			System.out.println("Found DocuExampleDevice: " + device.getFriendlyName(LocalizedString.LANGUAGE_EN));
		} catch (CommunicationException e) {
			e.printStackTrace();
		}

	}
};
