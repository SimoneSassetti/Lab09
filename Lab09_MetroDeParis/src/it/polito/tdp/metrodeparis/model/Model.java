package it.polito.tdp.metrodeparis.model;

import java.util.*;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private List<Fermata> listaFermate;
	private WeightedGraph<Fermata, DefaultWeightedEdge> grafo;
	
	public List<Fermata> getTutteFermate(){
		MetroDAO dao=new MetroDAO();
		if(listaFermate==null){
			listaFermate=dao.getAllFermate();
		}
		return listaFermate;
	}
	
	public WeightedGraph<Fermata, DefaultWeightedEdge> getGrafo(){
		if(grafo==null){
			this.creaGrafo();
		}
		return grafo;
	}
	
	private void creaGrafo() {
		
		
	}
	
}
