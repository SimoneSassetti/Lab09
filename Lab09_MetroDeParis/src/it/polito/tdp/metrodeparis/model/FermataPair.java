package it.polito.tdp.metrodeparis.model;

public class FermataPair {
	private Fermata partenza;
	private Fermata arrivo;
	private int idLinea;
	
	public FermataPair(Fermata partenza, Fermata arrivo, int idLinea) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.idLinea = idLinea;
	}

	public Fermata getPartenza() {
		return partenza;
	}
	public void setF1(Fermata f1) {
		this.partenza = f1;
	}
	public Fermata getArrivo() {
		return arrivo;
	}
	public void setF2(Fermata f2) {
		this.arrivo = f2;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}
}
