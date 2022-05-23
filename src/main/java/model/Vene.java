package model;

public class Vene {
	private String nimi, merkkimalli;
	private Double pituus, leveys;
	private int tunnus, hinta;
	
	public Vene() {
		super();
	}
	public Vene(String nimi, String merkkimalli, Double pituus, Double leveys, int hinta) {
		super();
		this.nimi = nimi;
		this.merkkimalli = merkkimalli;
		this.pituus = pituus;
		this.leveys = leveys;
		this.hinta = hinta;
	}
	public String getNimi() {
		return nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	public String getMerkkimalli() {
		return merkkimalli;
	}
	public void setMerkkimalli(String merkkimalli) {
		this.merkkimalli = merkkimalli;
	}
	public Double getPituus() {
		return pituus;
	}
	public void setPituus(Double pituus) {
		this.pituus = pituus;
	}
	public Double getLeveys() {
		return leveys;
	}
	public void setLeveys(Double leveys) {
		this.leveys = leveys;
	}
	public int getTunnus() {
		return tunnus;
	}
	public void setTunnus(int tunnus) {
		this.tunnus = tunnus;
	}
	public int getHinta() {
		return hinta;
	}
	public void setHinta(int hinta) {
		this.hinta = hinta;
	}
	@Override
	public String toString() {
		return "Vene [nimi=" + nimi + ", merkkimalli=" + merkkimalli + ", pituus=" + pituus + ", leveys=" + leveys
				+ ", tunnus=" + tunnus + ", hinta=" + hinta + "]";
	}
}
