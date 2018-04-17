package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.dao.EsameDAO;

public class Model {
	
	private List<Esame> esami; //salvo tutti gli esami 
	private EsameDAO edao;
	private List<Esame> soluzione;
	private double bestAvg; //la salvo per efficienza invece di calcolarla ogni volta dalla soluzione
	
	public Model() {
		this.edao = new EsameDAO();
		this.esami = edao.getTuttiEsami();
//		for(Esame e : esami) {//controllo che ci siano tutti gli esami
//			System.out.println(e);
//		}
	}

	public List<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		//inizializzo qui soluzione perché ogni volta che richiamo questa funzione voglio dimenticare la sol precedente
		soluzione = new ArrayList<Esame>();
		bestAvg = 0.0;
		
		//richiamo funzione ricorsiva
		int step = 0;
		List<Esame> parziale = new ArrayList<Esame>();
		recursive(step,parziale,numeroCrediti);
		
		return soluzione;
	}

	private void recursive(int step, List<Esame> parziale, int numeroCrediti) {
		//void perché voglio la migliore soluzione possibile, boolean se voglio ritornare una soluzione e basta
		
		//Debug
		/*for(Esame e: parziale) {
			System.out.println(e.getCodins()+ " ");
		}
		System.out.println();
		*/
		//Condizione di terminazione: non voglio che parziale abbia numCredTot>numCrediti
		if(totCrediti(parziale)>numeroCrediti) {
			return;
		}
			//se parziale ha stesso numero crediti devo vedere se è migliore della precedente
		if(totCrediti(parziale)== numeroCrediti) {
			if(avg(parziale)>bestAvg) {
				//salvo soluzione parziale creando una deep copy della lista
				soluzione = new ArrayList<Esame>(parziale);
				bestAvg = avg(parziale);
			}
		}
		
		//Generazione di una nuova soluzione parziale
		for(Esame esame : esami) {
			if(!parziale.contains(esame)) {
				parziale.add(esame);
				recursive(step+1, parziale, numeroCrediti);
				parziale.remove(esame);
			}
		}
		
		
	}
	/**
	 * Calcola crediti totale di una soluzione parziale
	 * @param parziale
	 * @return
	 */
	private int totCrediti(List<Esame> parziale) {
		int somma = 0;
		for(Esame e: parziale) {
			somma += e.getCrediti();
		}
		return somma;
	}
	/**
	 * calcola la media di una soluzione parziale
	 * @param parziale
	 * @return
	 */
	private double avg(List<Esame> parziale) {
		double avg = 0;
		
		for(Esame e : parziale) {
			avg += e.getCrediti()*e.getVoto();
		}
		avg /= totCrediti(parziale);
		
		return avg;
	}

	

}
