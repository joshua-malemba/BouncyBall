package bounceball;

import java.awt.Rectangle;

public class Angle extends Rectangle {


	private static final long serialVersionUID = 1L;
	
	public int id;


	public Angle(int i, int j, int rectwidth, int rectheight) {
		super(i, j, rectwidth, rectheight);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
