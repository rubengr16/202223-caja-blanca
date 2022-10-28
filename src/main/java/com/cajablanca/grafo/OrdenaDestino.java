package com.cajablanca.grafo;

import java.util.Comparator;

public class OrdenaDestino implements Comparator<Arco>{

	@Override
	public int compare(Arco o1, Arco o2) {		
		return o1.getDestino() - o2.getDestino();
	}

}
