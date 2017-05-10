package it.polito.tdp.metrodeparis.model;

import java.util.*;
import org.jgrapht.*;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.*;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private List<Fermata> fermateSemplici;
	private List<FermataConLinea> listaFermate;
	private WeightedGraph<FermataConLinea, Tratta> grafo;
	private List<FermataPair> listaArchi;
	private List<Linea> listaLinee;
	
	public List<Fermata> getTutteFermateSemplici() {
		MetroDAO dao=new MetroDAO();
		if(fermateSemplici==null){
			fermateSemplici=dao.getAllFermateSemplici();
		}
		return fermateSemplici;
	}
	
	public List<FermataConLinea> getTutteFermate(){
		MetroDAO dao=new MetroDAO();
		if(listaFermate==null){
			listaFermate=dao.getAllFermate();
		}
		return listaFermate;
	}
	
	public WeightedGraph<FermataConLinea, Tratta> getGrafo(){
		if(grafo==null){
			this.creaGrafo();
		}
		return grafo;
	}
	
	private void creaGrafo() {
		grafo = new  DirectedWeightedMultigraph<FermataConLinea, Tratta>(Tratta.class);
		
		for(FermataConLinea f: listaFermate){
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
		for(FermataConLinea f1: listaFermate){
			for(FermataConLinea f2: listaFermate){
				if(f1.getIdFermata()==f2.getIdFermata() && f1.getIdLinea()!=f2.getIdLinea()){
					Tratta t=grafo.addEdge(f1, f2);
					t.setLinea(this.cercaLinea(f2.getIdLinea()));
					grafo.setEdgeWeight(t, this.cercaLinea(f2.getIdLinea()).getIntervallo());
				}
			}
		}
	}

	private double calcolaPeso(Linea linea, FermataConLinea partenza, FermataConLinea arrivo) {
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

	public String creaPercorso(Fermata partenza, Fermata arrivo) {
		
		List<FermataConLinea> partenze=this.cercaFermateSpecifiche(partenza);
		List<FermataConLinea> arrivi=this.cercaFermateSpecifiche(arrivo);
		
		DijkstraShortestPath<FermataConLinea,Tratta> percorsoMinimo= new DijkstraShortestPath<FermataConLinea,Tratta>(grafo, p,a);
		List<Tratta> lista=percorsoMinimo.getPathEdgeList();
		
		//uso un set cosi ho il controllo dei duplicati
		Set<FermataConLinea> setFermate=new LinkedHashSet<FermataConLinea>();
		for(Tratta t: lista){
			FermataConLinea f1=grafo.getEdgeSource(t);
			FermataConLinea f2=grafo.getEdgeTarget(t);
			setFermate.add(f1);
			setFermate.add(f2);
		}
		
		String percorso="";
		Iterator<FermataConLinea> it=setFermate.iterator();		
		while(it.hasNext()){
			percorso+=it.next().getNome()+"\n";
		}
		double temp=percorsoMinimo.getPathLength()*3600.0+(lista.size()-2)*30.0;
		percorso+="\nTempo di percorrenza: "+temp/60+" minuti.";
		
		return percorso;
	}

	private List<FermataConLinea> cercaFermateSpecifiche(Fermata fermata) {
		List<FermataConLinea> temp=new ArrayList<FermataConLinea>();
		for(FermataConLinea f: listaFermate){
			if(f.getIdFermata()==fermata.getIdFermata()){
				temp.add(f);
			}
		}
		return temp;
	}

	
}
