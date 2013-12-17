package holonicws.leanline.customerholon;

import java.util.Random;

import holonicws.HWSService;

import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.DefaultEventSource;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class OrderEventSource extends DefaultEventSource {
	
	private final static String PARAMETER_NAME = "order"; 
	
	public OrderEventSource(String name, QName portType, HWSService service) {
		super(name, portType);
		
		Element output = new Element(new QName(PARAMETER_NAME, service.getNamespace()), SchemaUtil.TYPE_INTEGER);
		this.setOutput(output);
	}
	
	public void fireOrder(int eventCounter) {
		
		ParameterValue paramValue = createOutputValue();
		int orderSize = generateRandomOrder();
		ParameterValueManagement.setString(paramValue, PARAMETER_NAME, Integer.toString(orderSize));
		
		this.fire(paramValue, eventCounter++, CredentialInfo.EMPTY_CREDENTIAL_INFO);
	}
	
	private int generateRandomOrder() {
		int maxOrderSize = 20; 
		
		Random rnd = new Random();
		return rnd.nextInt(maxOrderSize);
	}

}
