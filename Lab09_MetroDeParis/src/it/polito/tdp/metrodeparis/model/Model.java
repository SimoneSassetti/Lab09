package it.polito.tdp.metrodeparis.model;

import java.util.*;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private List<Fermata> listaFermate;
	private WeightedGraph<Fermata, Tratta> grafo;
	private List<FermataPair> listaArchi;
	private List<Linea> listaLinee;
	
	public List<Fermata> getTutteFermate(){
		MetroDAO dao=new MetroDAO();
		if(listaFermate==null){
			listaFermate=dao.getAllFermate();
		}
		return listaFermate;
	}
	
	public WeightedGraph<Fermata, Tratta> getGrafo(){
		if(grafo==null){
			this.creaGrafo();
		}
		return grafo;
	}
	
	private void creaGrafo() {
		grafo = new  WeightedMultigraph<Fermata, Tratta>(Tratta.class);
		
		for(Fermata f: listaFermate){
			grafo.addVertex(f);
		}
		MetroDAO dao= new MetroDAO();
		listaArchi=dao.getCoppieAdiacenti();
		listaLinee=dao.getLinee();
		
		for(FermataPair a: listaArchi){
			Tratta t=grafo.addEdge(a.getPartenza(), a.getArrivo());
			if(t!=null){
				t.setLinea(this.cercaLinea(a.getIdLinea()));
				grafo.setEdgeWeight(t, calcolaPeso(t.getLinea(),a.getPartenza(),a.getArrivo()));
			}
		}
	}

	private double calcolaPeso(Linea linea, Fermata partenza, Fermata arrivo) {
		double dis= LatLngTool.distance(partenza.getCoords(), arrivo.getCoords(), LengthUnit.KILOMETER);
		return (dis/linea.getVelocita());
	}

	private Linea cercaLinea(int id) {
		for(Linea l: listaLinee){
			if(l.getId()==id){
				return l;
			}
		}
		return null;
	}

	public String creaPercorso(Fermata p, Fermata a) {
		
		DijkstraShortestPath<Fermata,Tratta> percorsoMinimo= new DijkstraShortestPath<Fermata,Tratta>(grafo, p,a);
		List<Tratta> lista=percorsoMinimo.getPathEdgeList();
		
		//uso un set cosi ho il controllo dei duplicati
		Set<Fermata> setFermate=new LinkedHashSet<Fermata>();
		for(Tratta t: lista){
			Fermata f1=grafo.getEdgeSource(t);
			Fermata f2=grafo.getEdgeTarget(t);
			setFermate.add(f1);
			setFermate.add(f2);
		}
		
		String percorso="";
		Iterator<Fermata> it=setFermate.iterator();		
		while(it.hasNext()){
			percorso+=it.next().getNome()+"\n";
		}
		double temp=percorsoMinimo.getPathLength()*3600.0+(lista.size()-2)*30.0;
		percorso+="\nTempo di percorrenza: "+temp/60+" minuti.";
		
		return percorso;
	}
}
