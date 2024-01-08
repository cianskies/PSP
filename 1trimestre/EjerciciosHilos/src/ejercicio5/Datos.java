package ejercicio5;

public class Datos {

	private int sumaPares;
	private int sumaImpares;
	private int sumaAcaban2o3;
	
	public void setSumaPares(int sumaPares) {
		this.sumaPares = sumaPares;
	}
	public void setSumaImpares(int sumaImpares) {
		this.sumaImpares = sumaImpares;
	}
	public void setSumaAcaban2o3(int sumaAcaban2o3) {
		this.sumaAcaban2o3 = sumaAcaban2o3;
	}
	
	public void mostrarResultados() {
		System.out.println("Suma de los pares: "+sumaPares);
		System.out.println("Suma de los impares: "+sumaImpares);
		System.out.println("Suma de los que acaban en 2 o 3: "+sumaAcaban2o3);

	}
	
	
	
}
