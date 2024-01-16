package prueba;
// Generated 18 dic 2023 10:56:59 by Hibernate Tools 6.3.1.Final

/**
 * Puerto generated by hbm2java
 */
public class Puerto implements java.io.Serializable {

	private String nompuerto;
	private Ciclista ciclista;
	private Etapa etapa;
	private Integer altura;
	private String categoria;
	private Integer pendiente;

	public Puerto() {
	}

	public Puerto(String nompuerto, Ciclista ciclista, Etapa etapa, Integer altura, String categoria,
			Integer pendiente) {
		this.nompuerto = nompuerto;
		this.ciclista = ciclista;
		this.etapa = etapa;
		this.altura = altura;
		this.categoria = categoria;
		this.pendiente = pendiente;
	}

	public String getNompuerto() {
		return this.nompuerto;
	}

	public void setNompuerto(String nompuerto) {
		this.nompuerto = nompuerto;
	}

	public Ciclista getCiclista() {
		return this.ciclista;
	}

	public void setCiclista(Ciclista ciclista) {
		this.ciclista = ciclista;
	}

	public Etapa getEtapa() {
		return this.etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Integer getAltura() {
		return this.altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getPendiente() {
		return this.pendiente;
	}

	public void setPendiente(Integer pendiente) {
		this.pendiente = pendiente;
	}

}