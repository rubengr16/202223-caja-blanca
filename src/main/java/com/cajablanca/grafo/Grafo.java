package com.cajablanca.grafo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.TreeMap;

import com.cajanegra.SingleLinkedListImpl;

class Grafo {

	private Map<Integer, List<Arco>> listAdy;
	private List<Arco> listaArcos;
	private boolean esDirigido, conPeso, visitados[];

	public Grafo() {
		listAdy = new TreeMap<Integer, List<Arco>>();
		listaArcos = new ArrayList<Arco>();
		esDirigido = false;
		conPeso = false;
	}

	public Grafo(boolean esDirigido, boolean conPeso) {
		listAdy = new TreeMap<Integer, List<Arco>>();
		this.esDirigido = esDirigido;
		listaArcos = new ArrayList<Arco>();
		this.conPeso = conPeso;
	}

	private int primerVertice() {
		Iterator<Integer> it = this.listAdy.keySet().iterator();
		return it.next();
	}

	public Grafo(String nombreFichero) {
		this.esDirigido = false;
		this.conPeso = false;
		listAdy = new TreeMap<Integer, List<Arco>>();
		listaArcos = new ArrayList<Arco>();

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String regex = "\\s+";
		try {
			archivo = new File(nombreFichero);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String cadena;
			cadena = br.readLine();
			this.esDirigido = false;
			this.conPeso = false;
			if (cadena.toUpperCase().equals("DIRIGIDO SI")) {
				this.esDirigido = true;
			}
			cadena = br.readLine();
			if (cadena.toUpperCase().equals("CONPESO SI")) {
				this.conPeso = true;
			}
			while ((cadena = br.readLine()) != null) {
				String[] valores = cadena.split(regex);
				Arco arco;
				if (this.conPeso)
					arco = new Arco(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]),
							Integer.parseInt(valores[2]));
				else
					arco = new Arco(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]), 0);
				this.addArco(arco);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public boolean existeVertice (int vertice) {
		for(Integer v : this.listAdy.keySet()) {
			if(v==vertice) {
				return true;
			}
		}
		return false;
	}
	public void addVertice(int vertice) {
		if (!this.listAdy.containsKey(vertice)) {
			this.listAdy.put(vertice, new ArrayList<Arco>());
		}
	}

	public void addArco(Arco arco) {
		int origen = arco.getOrigen();
		int dest = arco.getDestino();
		if (!this.listAdy.containsKey(origen)) {
			addVertice(origen);
		}
		if (!this.listAdy.containsKey(dest)) {
			addVertice(dest);
		}
		List<Arco> listaAdy = this.listAdy.get(origen);
		listaAdy.add(arco);
		this.listaArcos.add(arco);
		Collections.sort(listaAdy, new OrdenaDestino());
		this.listAdy.put(origen, listaAdy);

		if (!esDirigido) {
			Arco edge_ = new Arco(arco.getDestino(), arco.getOrigen(), arco.getPeso());
			listaAdy = this.listAdy.get(dest);
			listaAdy.add(edge_);
			Collections.sort(listaAdy, new OrdenaDestino());
			this.listAdy.put(dest, listaAdy);
		}
	}

	public int numVertices() {
		return this.listAdy.size();
	}

	public boolean verticeVisitado(int vertice) {
		return this.visitados[vertice];
	}
	
	public void printGraph() {
		for (Integer src : this.listAdy.keySet()) {
			System.out.printf("%d ——>", src);
			for (Arco edge : listAdy.get(src)) {
				System.out.printf("%s ", edge);
			}

			System.out.println();
		}
	}

	private void initVisitados() {
		for (int i = 0; i < this.visitados.length; i++) {
			this.visitados[i] = false;
		}
	}

	private boolean todosVisitados() {
		for (Integer clave : this.listAdy.keySet()) {
			if (this.visitados[clave] == false) {
				return false;
			}
		}
		return true;
	}

	public void printArcos() {
		System.out.print("[");
		for (Arco a : this.listaArcos) {
			System.out.print(a + " ");
		}
		System.out.println("]");
	}

	public void printListaAdyacentes(int v) {
		List<Arco> listaAdy = this.listAdy.get(v);
		System.out.print("Adyacentes de " + v + ": ");
		for (Arco arco : listaAdy) {
			System.out.print(arco.getDestino() + " ");
		}
		System.out.println();
	}

	public String componentsRelated() {
		Queue<Integer> queue = new LinkedList<Integer>();
		int nVert;
		nVert = this.listAdy.size();
		this.visitados = new boolean[nVert + 1];
		initVisitados();
		String components = "";
		while (!todosVisitados()) {
			int i = primerVertice();
			boolean found = false;
			while (i < this.visitados.length && !found) {
				if (!this.visitados[i]) {
					queue.add(i);
					this.visitados[i] = true;
					found = true;
					components += "[";
				} else
					i++;
			}
			while (!queue.isEmpty()) {
				int current = queue.remove();
				components += current + " ";
				List<Arco> listaAdy = this.listAdy.get(current);
				printListaAdyacentes(current);
				for (Arco arco : listaAdy) {
					if (!this.visitados[arco.getDestino()]) {
						queue.add(arco.getDestino());
						this.visitados[arco.getDestino()] = true;
					} // end if
				} // end for
			} // end while
			components = components.substring(0, components.length() - 1) + "]";
		}
		return components;
	}

	/**
	 * Recorrido en anchura de un grafo desde un vértice
	 * 
	 * @param vertice
	 * @return La componente a la que pertenece el vértice
	 */
	public String BFS(int vertice) {
		if (!this.listAdy.containsKey(vertice)) {
			throw new NoSuchElementException("No existe ese vertice en el grafo");
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		int nVert;
		nVert = this.listAdy.size();
		this.visitados = new boolean[nVert + 1];
		initVisitados();
		String components = "[";
		queue.add(vertice);
		this.visitados[vertice] = true;
		while (!queue.isEmpty()) {
			int current = queue.remove();
			components += current + " ";
			List<Arco> listaAdy = this.listAdy.get(current);
			for (Arco arco : listaAdy) {
				if (!this.visitados[arco.getDestino()]) {
					queue.add(arco.getDestino());
					this.visitados[arco.getDestino()] = true;
				} // end if
			} // end for
		} // end while
		components = components.substring(0, components.length() - 1) + "]";

		return components;
	}

	private List<Arco> copiaListaArcos() {
		List<Arco> lista = new ArrayList<Arco>();
		for (Arco arco : listaArcos) {
			lista.add(arco);
		}
		return lista;
	}

	public Grafo kruskal() {
		Grafo g = new Grafo();
		for(Arco a : this.listaArcos) {
			g.addVertice(a.getOrigen());
		}
		List<Arco> lista = this.copiaListaArcos();
		int numArcos = 0;
		while (numArcos < this.numVertices()-1) {
			Arco menor = new Arco(1, 1, Integer.MAX_VALUE);
			int pos = -1;
			for (int i = 0; i < lista.size(); i++) {
				Arco arco = lista.get(i);
				if (arco.getPeso() <= menor.getPeso()) {
					menor = arco;
					pos = i;
				}
			}
			if (pos != -1) {
				int a = menor.getOrigen(), b = menor.getDestino();							
				if(!g.existeVertice(a) || !g.existeVertice(b)) {
					g.addArco(menor);
					numArcos++;
				}else {
					g.BFS(a);
					if (!g.verticeVisitado(b)) {
						g.addArco(menor);
						numArcos++;
					}

				}				
				lista.remove(pos);
			}			
		}
		return g;
	}

}
