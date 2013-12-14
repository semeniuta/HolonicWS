package holonicws;

public class HWSParameter {
	private String name;
	private Object value;
	
	public HWSParameter(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

}
