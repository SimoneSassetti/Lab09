package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLng;

public class FermataConLinea {

	private int idFermata;
	private String nome;
	private LatLng coords;
	private int idLinea;
	
	public FermataConLinea(int idFermata, String nome, LatLng coords, int idLinea) {
		super();
		this.idFermata = idFermata;
		this.nome = nome;
		this.coords = coords;
		this.idLinea = idLinea;
	}
	public FermataConLinea(int idFermata) {
		this.idFermata = idFermata;
	}

	public int getIdFermata() {
		return idFermata;
	}

	public void setIdFermata(int idFermata) {
		this.idFermata = idFermata;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LatLng getCoords() {
		return coords;
	}

	public void setCoords(LatLng coords) {
		this.coords = coords;
	}

	@Override
	public int hashCode() {
		return ((Integer) idFermata).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FermataConLinea other = (FermataConLinea) obj;
		if (idFermata != other.idFermata)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
