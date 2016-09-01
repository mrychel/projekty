package narzedzia;

import java.util.ArrayList;

public class Odcinek {
    
	public class odc {
		public Punkt3D a, b;
		public odc(Punkt3D a, Punkt3D b) {
			this.a = a;
			this.b = b;
		}
	}
	Punkt3D[] odcinek = new Punkt3D[2];
	ArrayList<odc> odcinki = new ArrayList();
	
	public Odcinek(Punkt3D a, Punkt3D b) {
		odcinki.add(new odc(a, b)); 
		for (odc o : odcinki) {
			o.a;
		}
	}
}
