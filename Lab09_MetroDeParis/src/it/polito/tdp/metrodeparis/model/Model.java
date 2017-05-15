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
	private WeightedGraph<FermataConLinea, Tratta> grafo=null;
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
		System.out.println(grafo);
		return grafo;
	}
	
	private void creaGrafo() {
		MetroDAO dao= new MetroDAO();
		grafo = new  DirectedWeightedMultigraph<FermataConLinea, Tratta>(Tratta.class);
		this.getTutteFermate();
//		for(FermataConLinea f: listaFermate){
//			grafo.addVertex(f);
//		}
		Graphs.addAllVertices(grafo, listaFermate);
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
		Set<FermataConLinea> setFermate=null;
		double tempo=10000000.0;
		
		for(FermataConLinea p: partenze){
			for(FermataConLinea a: arrivi){
				DijkstraShortestPath<FermataConLinea,Tratta> percorsoMinimo=new DijkstraShortestPath<FermataConLinea,Tratta>(grafo,p,a);
				double t=percorsoMinimo.getPathLength()*60.0;//Cosi è in minuti
				if(t<tempo){
					List<Tratta> lista=percorsoMinimo.getPathEdgeList();
					setFermate=new LinkedHashSet<FermataConLinea>();
					for(Tratta tratta:lista){
						FermataConLinea f1=grafo.getEdgeSource(tratta);
						FermataConLinea f2=grafo.getEdgeTarget(tratta);
						setFermate.add(f1);
						setFermate.add(f2);
					}
					tempo=t;
				}
			}
		}
		String percorso="";
		Iterator<FermataConLinea> it=setFermate.iterator();
		int lineaPrec=0;
		int step=0;
		while(it.hasNext()){
			FermataConLinea f=it.next();
			if(step==0){
				percorso+="Prendo Linea: "+f.getIdLinea()+"\n";
				lineaPrec=f.getIdLinea();
			}
			else if(f.getIdLinea()==lineaPrec){
				percorso+=f.getNome()+" - ";
			}else{
				percorso+="\nPrendo Linea: "+f.getIdLinea()+"\n";
			}
			step++;
			lineaPrec=f.getIdLinea();
		}
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
