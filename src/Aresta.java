/**Classe para objetos do tipo Aresta que auxiliará na construção do grafo.
 * @author Ana Vitória Dias Carvalho
 * @version 1.0
 */
class Aresta {
    int destino;
    int peso;

    /**
     * Método construtor da classe aresta
     * @param destino vértice de destino
     * @param peso o peso da aresta entre o v(O) e v(D)
     */
    public Aresta(int destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }
}