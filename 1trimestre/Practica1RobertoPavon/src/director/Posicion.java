package director;

public class Posicion {
	int ancho;
	int alto;
	int color;
	public Posicion(int ancho, int alto){
		this.alto=alto;
		this.ancho=ancho;
	}
	public Posicion(int ancho, int alto, int color) {
		this.ancho=ancho;
		this.alto=alto;
		this.color=color;
	}
	public String ToString(){
		return this.ancho+","+this.alto+" Color: "+this.color;
	}
	public void setColor(int color){
		this.color=color;
	}
	public int GetColor() {
		return this.color;
	}
	public int GetAncho() {
		return this.ancho;
	}
	public int GetAlto() {
		return this.alto;
	}
	public void setAncho(int ancho){
		this.ancho=ancho;
	}
	public void setAlto(int alto){
		this.alto=alto;
	}
	

}
