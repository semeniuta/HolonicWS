package events;


public class MovementEventProvider extends Thread {

	private static int 	eventCounter	= 0;

	private MovementEvent event;

	public MovementEventProvider(MovementEvent event) {
		this.event = event;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
				event.fireMovementEvent(eventCounter++);
				System.out.println("Movement detected");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
