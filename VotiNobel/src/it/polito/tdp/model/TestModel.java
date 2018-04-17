package it.polito.tdp.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		List<Esame> soluzione = model.calcolaSottoinsiemeEsami(22);
		
		System.out.println("Migliore combinazione di esami: ");
		for(Esame e : soluzione) {
			System.out.println(e);
		}
		
	}

}
