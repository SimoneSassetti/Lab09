package it.polito.tdp.metrodeparis.model;

public class FermataPair {
	private FermataConLinea partenza;
	private FermataConLinea arrivo;
	private int idLinea;
	
	public FermataPair(FermataConLinea partenza, FermataConLinea arrivo, int idLinea) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.idLinea = idLinea;
	}

	public FermataConLinea getPartenza() {
		return partenza;
	}
	public void setF1(FermataConLinea f1) {
		this.partenza = f1;
	}
	public FermataConLinea getArrivo() {
		return arrivo;
	}
	public void setF2(FermataConLinea f2) {
		this.arrivo = f2;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}
}
