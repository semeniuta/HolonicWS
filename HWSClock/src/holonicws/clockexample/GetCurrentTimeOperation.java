package holonicws.clockexample;

import holonicws.HWSOperation;
import holonicws.HWSService;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.schema.Type;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class GetCurrentTimeOperation extends HWSOperation {

	public GetCurrentTimeOperation(String operation_name, HWSService service) {
		super(operation_name, service);

		setIOParatemers();
	}
	
	protected void setIOParatemers() {
		Element input = this.createSimpleStringElement("input");
		this.setInput(input);
		
		Element output = this.createSimpleStringElement("output");
		this.setOutput(output);
	}

	protected ParameterValue invokeImpl(ParameterValue parameterValue,
			CredentialInfo credentialInfo) throws InvocationException,
			CommunicationException {
		
		ParameterValue paramTimeVal = createOutputValue();
		String value = getCurrentTime();
		ParameterValueManagement.setString(paramTimeVal, "output", value);
		
		try {
			PrintWriter out = new PrintWriter("invoke.txt");
			out.println("Operation invoked");
			out.println(value);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return paramTimeVal;
	}
	
	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime());
	}
}
