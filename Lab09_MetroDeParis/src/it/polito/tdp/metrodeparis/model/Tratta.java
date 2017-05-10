package it.polito.tdp.metrodeparis.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Tratta extends DefaultWeightedEdge{
	private Linea linea;

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}
}
