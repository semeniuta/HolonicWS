package holonicws.leanline.customerholon;

public class OrderEventProvider extends Thread {

	private int interval = 5;
	private OrderEvent event;
	private int fireCounter = 0;
	
	public OrderEventProvider(OrderEvent event) {
		this.event = event;
	}
	
	@Override
	public void run() {
		while (true) {
			
			try {
				Thread.sleep(this.interval * 1000);
				event.fireOrder(fireCounter++);
				System.out.println("Order placed");
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
