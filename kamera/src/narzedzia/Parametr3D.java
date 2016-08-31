package narzedzia;

public class Parametr3D {
	
	public float x,y,z;
	private String nazwa = "";
	/*
	public Parametr3D() {		
	}
	*/
	public Parametr3D(String nazwa) {
		this.nazwa = nazwa;
	}
	/*
	public Parametr3D(String nazwa, float x, float y, float z) {
		this.nazwa = nazwa;
		this.x=x;
		this.y=y;
		this.z=z;
	}
	*/
	public void ustaw(Parametr3D parametry) {
    	this.x = parametry.x;
    	this.y = parametry.y;
    	this.z = parametry.z;
    }
	
	public String dajNazwe() {
		return this.nazwa;
	}
}
