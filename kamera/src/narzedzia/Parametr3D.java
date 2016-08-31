package narzedzia;

public class Parametr3D {
	
	float x,y,z;
	
	Parametr3D() {		
	}
	
	Parametr3D(float x, float y, float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public void ustaw(Parametr3D parametry) {
    	this.x = parametry.x;
    	this.y = parametry.y;
    	this.z = parametry.z;
    }
}
