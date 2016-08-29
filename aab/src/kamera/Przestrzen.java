package kamera;

import java.awt.Dimension;

public class Przestrzen {
	
	public class Linia {
	    Punkt3D pocz, kon;	
	    
	    Linia () {}
	    Linia (int x, int y, int z, int r, int s, int t) {
	    	pocz.x = x;
	    	pocz.y = y;
	    	pocz.z = z;
	    	kon.x = r;
	    	kon.y = s;
	    	kon.z = t;
	    }
	}

	public Linia[] P;// {
		{new Punkt3D(20, 30, 20), new Punkt3D(20, 30, 50)},		
		{new Punkt3D(20, 30, 20), new Punkt3D(20, 130, 20)},
		{new Punkt3D(20, 30, 20), new Punkt3D(120, 30, 20)},
		{new Punkt3D(20, 30, 50), new Punkt3D(20, 130, 50)},		
		{new Punkt3D(20, 30, 50), new Punkt3D(120, 30, 50)},
		
		{new Punkt3D(20, 130, 20), new Punkt3D(20, 130, 50)},
		{new Punkt3D(20, 130, 20), new Punkt3D(120, 130, 20)},
		{new Punkt3D(20, 130, 50), new Punkt3D(120, 130, 50)},
		
		{new Punkt3D(120, 30, 20), new Punkt3D(120, 30, 50)},			
		{new Punkt3D(120, 30, 20), new Punkt3D(120, 130, 20)},
		{new Punkt3D(120, 30, 50), new Punkt3D(120, 130, 50)},
		
		{new Punkt3D(120, 130, 20), new Punkt3D(120, 130, 50)}
	};	
	
	public Przestrzen() { 
	   
	    for (int i=0; i<12; i=i+12) {
			P[i] = new Linia();
		    P[i+1] = new Linia();
	    }
   }
}
