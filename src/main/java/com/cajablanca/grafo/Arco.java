package com.cajablanca.grafo;

class Arco
{
	private int origen, destino, peso;
	 
    
	public Arco(int origen, int destino, int peso) {
		super();
		this.origen = origen;
		this.destino = destino;
		this.peso = peso;
	}

	
	public int getOrigen() {
		return origen;
	}


	public void setOrigen(int origen) {
		this.origen = origen;
	}


	public int getDestino() {
		return destino;
	}


	public void setDestino(int destino) {
		this.destino = destino;
	}


	public int getPeso() {
		return peso;
	}


	public void setPeso(int peso) {
		this.peso = peso;
	}


	@Override
	public String toString() {
		if(peso==0)
			return "(" + origen + "," + destino + ")";
		else
			return "(" + origen + "," + destino + "," + peso + ")";
	}
    
	
    
}