package mine;

public class DocuExampleEventProvider extends Thread {

	private static int 	eventCounter	= 0;

	private DocuExampleSimpleEvent	event;

	public DocuExampleEventProvider(DocuExampleSimpleEvent event) {
		this.event = event;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
				event.fireHelloWorldEvent(eventCounter++);
				System.out.println("fire Event");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
