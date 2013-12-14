package holonicws;

import java.util.ArrayList;
import java.util.List;

import org.ws4d.java.authorization.AuthorizationException;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.URI;

public class HWSSimpleCommunicator {
	
	public Service getService(Device device, String serviceURI) throws CommunicationException {
		return device.getServiceReference(new URI(serviceURI), SecurityKey.EMPTY_KEY).getService();
	}
	
	public Operation getOperation(Service service, String opName) {
		return service.getOperation(null, opName, null, null);
	} 
	
	public ParameterValue invokeOperation(Operation op, HWSParameter parameter) throws AuthorizationException, InvocationException, CommunicationException {
		List<HWSParameter> params = new ArrayList<HWSParameter>();
		params.add(parameter);
		return invokeOperation(op, params);
	}
	
	public ParameterValue invokeOperation(Operation op, List<HWSParameter> parameters) throws AuthorizationException, InvocationException, CommunicationException {
		ParameterValue paramVal = op.createInputValue();
		for (HWSParameter p : parameters) {
			String name = p.getName();
			Object value = p.getValue();
			ParameterValueManagement.setString(paramVal, name, String.valueOf(value));
		}
		
		ParameterValue response = op.invoke(paramVal, CredentialInfo.EMPTY_CREDENTIAL_INFO);	
		return response;
	}
	
}
