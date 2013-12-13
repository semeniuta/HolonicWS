package holonicws.leanline;

public class Order {
	private String productName;
	private int quantity;
	
	public Order(String productName, int quantity) {
		super();
		this.productName = productName;
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuantity() {
		return quantity;
	}
	
}
