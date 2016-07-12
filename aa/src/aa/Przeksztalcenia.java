package aa;

import java.awt.Point;

public class Przeksztalcenia {
	
	private final static int ogniskowa = 50;
	
	Przeksztalcenia() {
		
	}

	public Point na3D(Punkt3D punkt3D) {
		return new Point (punkt3D.x/(1+punkt3D.z/ogniskowa), punkt3D.y/(1+punkt3D.z/ogniskowa));
	}
}
