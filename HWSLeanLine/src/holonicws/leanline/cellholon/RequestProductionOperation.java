package holonicws.leanline.cellholon;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

import holonicws.HWSOperation;
import holonicws.HWSService;

public class RequestProductionOperation extends HWSOperation {

	public RequestProductionOperation(String operation_name, HWSService service) {
		super(operation_name, service);
		
		Element request = new Element(new QName("request", this.getNamespace()), SchemaUtil.TYPE_INT);
		this.setInput(request);
		Element response = new Element(new QName("response", this.getNamespace()), SchemaUtil.TYPE_STRING);
		this.setOutput(response);
	}

	@Override
	protected ParameterValue invokeImpl(ParameterValue parameterValue,
			CredentialInfo credentialInfo) throws InvocationException,
			CommunicationException {
		
		ParameterValue paramValue = createOutputValue();
		int n = Integer.parseInt(ParameterValueManagement.getString(parameterValue, "request"));
		
		System.out.println("Operation invoked. request=" + n);
		
		ParameterValueManagement.setString(paramValue, "response", "Request ("+ n +") added to the queue");
		return paramValue;
		
	}
	
	
	
	
}
