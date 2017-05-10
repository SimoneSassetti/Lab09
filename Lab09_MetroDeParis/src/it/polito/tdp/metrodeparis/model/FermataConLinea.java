package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLng;

public class FermataConLinea extends Fermata{

	private int idLinea;
	
	public FermataConLinea(int idFermata, String nome, LatLng coords, int idLinea) {
		super(idFermata, nome, coords);
		this.idLinea = idLinea;
	}
	
	public int getIdLinea() {
		return idLinea;
	}
	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + idLinea;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FermataConLinea other = (FermataConLinea) obj;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}
	
}
