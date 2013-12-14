package holonicws.leanline.lineholon;

import holonicws.HWSDevice;
import holonicws.leanline.cellholon.CellHolon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineHolon {
	private final static String NAME = "Line";
	private final static String NAMESPACE = "http://manufacturer.no/holonicws";
	private final static int NCELLS = 4;

	private HWSDevice device;
	private LineOrderClient orderClient;
	private ArrayList<CellHolon> cells = new ArrayList<CellHolon>();
	
	private int numberRequested = 0;
	
	public LineHolon(String iface_name) throws IOException {
		device = new HWSDevice(NAME, NAMESPACE, iface_name);
		
		for (int i=0; i < NCELLS; i++) {
			cells.add(new CellHolon("Cell"+ i, iface_name));
		}
		
		orderClient = new LineOrderClient(this);
		
		device.start();
	}
	
	
	public void incrementNumberRequested(int n) {
		numberRequested += n;
	}

	public int getNumberRequested() {
		return numberRequested;
	}

	public List<CellHolon> getCells() {
		return this.cells;
	}
}
