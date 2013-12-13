package holonicws;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.types.QName;

public class HWSOperation extends Operation {
		
	private HWSService service;
	
	public HWSOperation(String operation_name, HWSService service) {
		super(operation_name, service.getServiceType());
		this.service = service;
	}
	
	public HWSOperation(String operation_name, HWSService service, QName port_type) {
		super(operation_name, port_type);
		this.service = service;
	}
	
	public String getNamespace() {
		return this.service.getNamespace();
	}
	
	public Element createSimpleStringElement(String name) {
		Element input = new Element(new QName(name, this.getNamespace()), SchemaUtil.TYPE_STRING);
		return input;
	}
		
	@Override
	protected ParameterValue invokeImpl(ParameterValue parameterValue,
			CredentialInfo credentialInfo) throws InvocationException,
			CommunicationException {
		
		return null;
	}
	
}
