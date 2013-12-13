package mine;

public class DocuExampleComplexEventProvider extends Thread {

	private DocuExampleComplexEvent	event;

	public DocuExampleComplexEventProvider(DocuExampleComplexEvent event) {
		this.event = event;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(6000);
				event.fireComplexEvent();
				System.out.println("fire ComplexEvent");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
