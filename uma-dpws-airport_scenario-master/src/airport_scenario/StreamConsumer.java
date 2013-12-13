package airport_scenario;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.ws4d.java.service.parameter.AttachmentValue;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.util.Log;

class StreamConsumer implements Runnable {

	private final ParameterValue	param;

	StreamConsumer(ParameterValue param) {
		super();
		this.param = param;
	}

	public void run() {
		InputStream in = null;
		try {
			in = ((AttachmentValue) param).getAttachment().getInputStream();
			InputStreamReader reader = new InputStreamReader(in);
			int count = 0;
			int i;

			// read the input char by char
			while ((i = reader.read()) != -1) {
				Log.info("Read character: " + (char) i);
				// just writes a little log entry about the received data volume
				if (count % 10 == 0)
					Log.info(count++ + " characters received till now");
				else
					count++;
			}
		} catch (Exception e) {
			Log.printStackTrace(e);
		} finally {
			// it's important to dispose all attachments on client side
			param.disposeAllAttachments();
		}
	}
}