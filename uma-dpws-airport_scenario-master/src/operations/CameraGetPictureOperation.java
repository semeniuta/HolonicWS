package operations;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.ws4d.java.attachment.AttachmentFactory;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.constants.MIMEConstants;
import org.ws4d.java.schema.ComplexType;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

import services.ServicePictures;

public class CameraGetPictureOperation extends Operation {

	public final static String	NAME		= "CameraGetPictureOperation";
	public final static String	ATTACHMENT	= "OutputAttachment";

	public CameraGetPictureOperation() {
		super(NAME, ServicePictures.QN_PORTTYPE);

		Element attachment = new Element(new QName(ATTACHMENT, ServicePictures.namespace));

		ComplexType complexType = new ComplexType(new QName("AttachmentType", ServicePictures.namespace), ComplexType.CONTAINER_SEQUENCE);

		complexType.addElement(new Element(new QName("File", ServicePictures.namespace), SchemaUtil.TYPE_BASE64_BINARY));
		complexType.addElement(new Element(new QName("Memory", ServicePictures.namespace), SchemaUtil.TYPE_BASE64_BINARY));
		complexType.addElement(new Element(new QName("Stream", ServicePictures.namespace), SchemaUtil.TYPE_BASE64_BINARY));

		attachment.setType(complexType);

		setOutput(attachment);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		ParameterValue outputValue = createOutputValue();

		AttachmentFactory attachmentFactory = AttachmentFactory.getInstance();

		if (attachmentFactory != null) {
			// FileAttachment
			ParameterValueManagement.setAttachment(outputValue, "File", attachmentFactory.createFileAttachment(ServicePictures.filePath, MIMEConstants.CONTENT_TYPE_IMAGE_GIF));

			// MemoryAttachment
			ParameterValueManagement.setAttachment(outputValue, "Memory", attachmentFactory.createMemoryAttachment(Util.toByteArray(ServicePictures.filePath), MIMEConstants.CONTENT_TYPE_IMAGE_GIF));

			// StreamAttachment
			ParameterValueManagement.setAttachment(outputValue, "Stream", attachmentFactory.createStreamAttachment(Util.getInputStream(ServicePictures.filePath), MIMEConstants.CONTENT_TYPE_IMAGE_GIF));
		} else {
			System.err.println("Could not get AttachmentFactory instance!");
		}

		return outputValue;
	}

}

class Util {

	public static byte[] toByteArray(String filePath) {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(filePath);
			out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			while (in.read(buf) > 0) {
				out.write(buf);
			}
			out.close();
			in.close();
			return out.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static InputStream getInputStream(String filePath) {
		InputStream in = null;

		try {
			in = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}
}
