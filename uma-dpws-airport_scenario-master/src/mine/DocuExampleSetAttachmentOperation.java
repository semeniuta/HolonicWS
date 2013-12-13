package mine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.ws4d.java.attachment.AttachmentException;
import org.ws4d.java.attachment.interfaces.Attachment;
import org.ws4d.java.attachment.interfaces.incoming.IncomingAttachment;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

class DocuExampleSetAttachmentOperation extends Operation {

	public final static String	NAME		= "SetAttachmentOperation";

	public final static String	ATTACHMENT	= "InputAttachment";

	public DocuExampleSetAttachmentOperation() {
		super(NAME, DocuExampleAttachmentService.QN_PORTTYPE);

		Element attachment = new Element(new QName(ATTACHMENT, DocuExampleAttachmentService.namespace), SchemaUtil.TYPE_BASE64_BINARY);

		setInput(attachment);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		final IncomingAttachment attachment = ParameterValueManagement.getAttachment(parameterValue, ATTACHMENT);

		if (attachment != null) {

			try {
				InputStream inputStream = attachment.getInputStream();

				int attachmentType = attachment.getType();
				switch (attachmentType) {
					case Attachment.FILE_ATTACHMENT:
						System.out.println("This is a file attachment");

						// write the inputStream to a FileOutputStream
						OutputStream out = new FileOutputStream(new File("c:\\newfile.png"));

						byte[] array = new byte[1024];

						int length = inputStream.read(array);

						while (length > 0) {
							out.write(array, 0, length);
							length = inputStream.read(array);
						}

						inputStream.close();
						out.flush();
						out.close();

						System.out.println("New file created!");

						break;
					case Attachment.MEMORY_ATTACHMENT:
						System.out.println("This is a memory attachment");
						break;
					case Attachment.STREAM_ATTACHMENT:
						System.out.println("This is a stream attachment");
						break;
					default:
						System.out.println("Error");
				}
			} catch (AttachmentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			attachment.dispose();
		}

		return null;
	}

}
