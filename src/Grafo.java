import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.ext.JGraphXAdapter;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
/**Classe para objetos do tipo Grafo, onde serão contidos os vértices, arestas e métodos da classe.
 * @author Ana Vitória Dias Carvalho
 * @version 1.0
 */
class Grafo {
    public Map<Integer, List<Aresta>> adjacencias;
    private int numVertices;
    /**
     * Método construtor da classe Grafo
     */
    public Grafo() {
        adjacencias = new HashMap<>();
        numVertices = 0;
    }
    /**
     * Método que retorna uma lista de adjacências do vértice passado como parâmetro
     * @param v inteiro que representa o vértice
     * @return List<Aresta>
     */
    public List<Aresta> getAdjacencias(int v) {
        return adjacencias.get(v);
    }
    /**
     * Método que insere um vértice no grafo
     * @param v inteiro que representa o vértice
     * @return boolean
     */
    public boolean inserirVertice(int v) {
        if (!adjacencias.containsKey(v)) {
            adjacencias.put(v, new ArrayList<>());
            numVertices++;
            return true;
        }else{
            return false;
        }
    }
    /**
     * Método que insere uma aresta no grafo
     * @param origem vertice de origem
     * @param destino vertice de destino
     * @param peso peso da aresta
     * @return boolean
     */
    public boolean inserirAresta(int origem, int destino, int peso) {
        try{
            adjacencias.get(origem).add(new Aresta(destino, peso));
            adjacencias.get(destino).add(new Aresta(origem, peso));
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**
     * Método que remove um vértice do grafo
     * @param v inteiro que representa o vértice
     * @return boolean
     */
    public boolean removerVertice(int v) {
        try{
            adjacencias.values().forEach(list -> list.removeIf(aresta -> aresta.destino == v));
            adjacencias.remove(v);
            numVertices--;
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**
     * Método que remove uma aresta do grafo
     * @param origem vertice de origem
     * @param destino vertice de destino
     * @return boolean
     */
    public boolean removerAresta(int origem, int destino) {
        try{
            adjacencias.get(origem).removeIf(aresta -> aresta.destino == destino);
            adjacencias.get(destino).removeIf(aresta -> aresta.destino == origem);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**
     * Método estático utilizado visualizar o grafo passado como parâmetro
     * @param g grafo que será visualizado
     */
    public static void visualizarGrafo(Grafo g) {
        // Criação do grafo JGraphT
        Graph<Integer, DefaultWeightedEdge> jGraphT = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // Adiciona os vértices
        for (Integer vertice : g.adjacencias.keySet()) {
            jGraphT.addVertex(vertice);
        }

        // Adiciona as arestas
        for (Map.Entry<Integer, List<Aresta>> entry : g.adjacencias.entrySet()) {
            int origem = entry.getKey();
            for (Aresta aresta : entry.getValue()) {
                int destino = aresta.destino;
                if (!jGraphT.containsEdge(origem, destino)) {
                    DefaultWeightedEdge edge = jGraphT.addEdge(origem, destino);
                    jGraphT.setEdgeWeight(edge, aresta.peso);
                }
            }
        }

        // Criação do adaptador JGraphX
        JGraphXAdapter<Integer, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(jGraphT);
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        graphComponent.setPreferredSize(new Dimension(600, 600));

        // Configurar os rótulos para exibir pesos
        mxIGraphModel model = graphAdapter.getModel();
        for (Map.Entry<DefaultWeightedEdge, mxICell> entry : graphAdapter.getEdgeToCellMap().entrySet()) {
            DefaultWeightedEdge edge = entry.getKey();
            mxICell cell = entry.getValue();
            double weight = jGraphT.getEdgeWeight(edge);
            model.setValue(cell, String.valueOf(weight));
        }

        // Layout do grafo
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        // Configuração do JFrame para exibir o grafo
        JFrame frame = new JFrame("Visualização do Grafo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    /**
     * Método que calcula o grau do vértice
     * @param vertice inteiro que representa o vértice
     * @return int
     */
    public int calcularGrau(int vertice) {
        if (adjacencias.containsKey(vertice)) {
            return adjacencias.get(vertice).size();
        } else {
            return 0;
        }
    }
    /**
     * Método que verifica se o grafo é conexo ou não
     * @return boolean
     */
    public boolean verificarConexo() {
        if (adjacencias.isEmpty()) {
            return true; // Grafo vazio é considerado conexo.
        }

        // Inicializa uma lista para acompanhar os vértices visitados
        Set<Integer> visitados = new HashSet<>();

        // Começa a DFS a partir do primeiro vértice no grafo
        Integer verticeInicial = adjacencias.keySet().iterator().next();
        dfs(verticeInicial, visitados);

        // Verifica se todos os vértices foram visitados
        return visitados.size() == numVertices;
    }
    /**
     * Método privado de busca em profundidade que auxilia o método "verificarConexo()"
     * @param vertice inteiro que representa o vértice
     * @param visitados lista que irá armazenar os vértices visitados pela busca
     */
    private void dfs(int vertice, Set<Integer> visitados) {
        visitados.add(vertice); // Marca o vértice como visitado

        for (Aresta aresta : adjacencias.get(vertice)) {
            if (!visitados.contains(aresta.destino)) {
                dfs(aresta.destino, visitados); // Visita recursivamente os vizinhos
            }
        }
    }

    /**
     * Método de busca em profundidade
     * @param vertice inteiro que representa o vértice
     * @param visitados lista que irá armazenar os vértices visitados pela busca
     * @param grafoResultado instancia do novo grafo que será criado a partir da busca
     * @return Grafo
     */
    public Grafo dfs(int vertice, Set<Integer> visitados, Grafo grafoResultado) {
        // Adiciona o vértice ao conjunto de visitados
        visitados.add(vertice);

        // Adiciona o vértice ao grafo resultado
        grafoResultado.inserirVertice(vertice);

        List<Aresta> arestas = adjacencias.get(vertice);

        if (arestas != null) {
            for (Aresta aresta : arestas) {
                int destino = aresta.destino;

                // Se o destino ainda não foi visitado
                if (!visitados.contains(destino)) {
                    // Adiciona a aresta ao grafo resultado
                    grafoResultado.inserirAresta(vertice, destino, aresta.peso);

                    // Chama recursivamente DFS para o próximo vértice
                    dfs(destino, visitados, grafoResultado);
                }
            }
        }

        return grafoResultado;  // Retorna o grafo contendo o resultado da DFS
    }

    /**
     * Método que converte uma lista de adjacências em uma matriz de adjacências
     * @return int[][]
     */
    public int[][] converterParaMatrizDeAdjacencia() {
        // Número de vértices no grafo
        int n = numVertices;

        // Cria a matriz de adjacência de tamanho n x n
        int[][] matrizAdjacencia = new int[n][n];

        // Inicializa a matriz com 0 para indicar que não há arestas
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrizAdjacencia[i][j] = 0;
            }
        }

        // Cria um mapeamento de vértice para índice da matriz
        Map<Integer, Integer> verticeParaIndice = new HashMap<>();
        int indice = 0;
        for (Integer vertice : adjacencias.keySet()) {
            verticeParaIndice.put(vertice, indice++);
        }

        // Preenche a matriz com os pesos das arestas
        for (Map.Entry<Integer, List<Aresta>> entry : adjacencias.entrySet()) {
            int origem = entry.getKey();
            int indiceOrigem = verticeParaIndice.get(origem);
            for (Aresta aresta : entry.getValue()) {
                int destino = aresta.destino;
                int indiceDestino = verticeParaIndice.get(destino);
                matrizAdjacencia[indiceOrigem][indiceDestino] = aresta.peso;
            }
        }

        return matrizAdjacencia;
    }

    /**
     * Método que permite imprimir no console a matriz de adjacência gerada no método "converterParaMatrizDeAdjacencia()"
     * @param matrizAdjacencia matriz de adjacência
     */
    public void imprimirMatrizAdjacencia(int[][] matrizAdjacencia) {
        int n = matrizAdjacencia.length;

        // Imprime os índices das colunas (vértices)
        System.out.print("   "); // Espaço para alinhar os índices das linhas
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Imprime uma linha divisória
        System.out.print("   ");
        for (int i = 0; i < n; i++) {
            System.out.print("--");
        }
        System.out.println();

        // Imprime a matriz de adjacência
        for (int i = 0; i < n; i++) {
            System.out.print(i + "| "); // Imprime o índice da linha (vértice)
            for (int j = 0; j < n; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Método de busca em largura
     * @param verticeInicial vértice pelo qual se iniciará a busca
     * @return Grafo
     */
    public Grafo bfs(int verticeInicial) {
        // Cria um novo grafo para armazenar o resultado da BFS
        Grafo grafoResultado = new Grafo();

        // Verifica se o vértice inicial está presente no grafo
        if (!adjacencias.containsKey(verticeInicial)) {
            System.out.println("O vértice inicial não está presente no grafo.");
            return grafoResultado;  // Retorna um grafo vazio
        }

        // Cria um conjunto para armazenar os vértices visitados
        Set<Integer> visitados = new HashSet<>();

        // Cria uma fila para gerenciar a ordem de visita dos vértices
        Queue<Integer> fila = new LinkedList<>();

        // Adiciona o vértice inicial à fila e marca como visitado
        fila.add(verticeInicial);
        visitados.add(verticeInicial);
        grafoResultado.inserirVertice(verticeInicial);

        System.out.println("Iniciando BFS a partir do vértice: " + verticeInicial);

        // Enquanto a fila não estiver vazia
        while (!fila.isEmpty()) {
            // Remove o vértice da frente da fila
            int verticeAtual = fila.poll();
            System.out.println("Visitando o vértice: " + verticeAtual);

            // Para cada aresta conectada ao vértice atual
            for (Aresta aresta : adjacencias.get(verticeAtual)) {
                int destino = aresta.destino;

                // Se o vértice de destino ainda não foi visitado
                if (!visitados.contains(destino)) {
                    // Marca como visitado e adiciona à fila
                    visitados.add(destino);
                    fila.add(destino);

                    // Adiciona o vértice e a aresta ao grafo resultado
                    grafoResultado.inserirVertice(destino);
                    grafoResultado.inserirAresta(verticeAtual, destino, aresta.peso);
                }
            }
        }

        return grafoResultado;  // Retorna o grafo contendo o resultado da BFS
    }

    /**
     * Método que implementa o algoritmo do caminho mínimo de Dijkstra
     * @param origem vértice de origem
     * @return Grafo
     */
    public Grafo dijkstra(int origem) {
        // Verifica se o vértice de origem está presente no grafo
        if (!adjacencias.containsKey(origem)) {
            System.out.println("O vértice de origem não está presente no grafo.");
            return new Grafo(); // Retorna um grafo vazio
        }

        // Cria um novo grafo para armazenar o resultado de Dijkstra
        Grafo grafoResultado = new Grafo();

        // Cria um mapa para armazenar as distâncias mínimas de origem para cada vértice
        Map<Integer, Integer> distancias = new HashMap<>();
        // Mapa para armazenar o vértice anterior no caminho mais curto
        Map<Integer, Integer> anterior = new HashMap<>();
        // Inicializa as distâncias de todos os vértices como infinito
        for (Integer vertice : adjacencias.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
        }
        // A distância da origem para ela mesma é 0
        distancias.put(origem, 0);

        // Cria uma fila de prioridade para o processamento dos vértices
        PriorityQueue<Integer> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        fila.add(origem);

        while (!fila.isEmpty()) {
            // Remove o vértice com a menor distância
            int verticeAtual = fila.poll();

            // Adiciona o vértice atual ao grafo resultado
            grafoResultado.inserirVertice(verticeAtual);

            // Para cada aresta conectada ao vértice atual
            for (Aresta aresta : adjacencias.get(verticeAtual)) {
                int destino = aresta.destino;
                int peso = aresta.peso;

                // Calcula a nova distância possível
                int novaDistancia = distancias.get(verticeAtual) + peso;

                // Se a nova distância for menor, atualiza a distância e o vértice anterior
                if (novaDistancia < distancias.get(destino)) {
                    distancias.put(destino, novaDistancia);
                    anterior.put(destino, verticeAtual);
                    fila.add(destino);
                }
            }
        }

        // Reconstrói o grafo de caminho mínimo a partir do mapa 'anterior'
        for (Map.Entry<Integer, Integer> entrada : anterior.entrySet()) {
            int destino = entrada.getKey();
            int origemVertice = entrada.getValue();

            // Adiciona o vértice de destino ao grafo resultado
            grafoResultado.inserirVertice(destino);

            // Adiciona a aresta do caminho mínimo ao grafo resultado
            int pesoAresta = distancias.get(destino) - distancias.get(origemVertice);
            grafoResultado.inserirAresta(origemVertice, destino, pesoAresta);
        }

        return grafoResultado; // Retorna o grafo contendo o resultado do algoritmo de Dijkstra
    }

    /**
     * Método utilizado para encontrar a árvore geradora mínima do grafo
     * @param origem vértice de origem
     * @return Grafo
     */
    public Grafo prim(int origem) {
        // Verifica se o vértice de origem está presente no grafo
        if (!adjacencias.containsKey(origem)) {
            System.out.println("O vértice de origem não está presente no grafo.");
            return null;
        }

        // Grafo para armazenar a Árvore Geradora Mínima (MST)
        Grafo mst = new Grafo();

        // Mapas para armazenar o peso mínimo de uma aresta que conecta o vértice à MST e o vértice pai na MST
        Map<Integer, Integer> chave = new HashMap<>();
        Map<Integer, Integer> pai = new HashMap<>();

        // Conjunto para manter os vértices incluídos na MST
        Set<Integer> naMST = new HashSet<>();

        // Inicializa todos os pesos como infinito e o pai como nulo
        for (Integer vertice : adjacencias.keySet()) {
            chave.put(vertice, Integer.MAX_VALUE);
            pai.put(vertice, null);
        }

        // A chave da origem é 0, pois começamos por ela
        chave.put(origem, 0);
        pai.put(origem, null);

        // Fila de prioridade para escolher o vértice com o menor peso
        PriorityQueue<Integer> fila = new PriorityQueue<>(Comparator.comparingInt(chave::get));
        fila.add(origem);

        while (!fila.isEmpty()) {
            // Remove o vértice com a menor chave (peso mínimo)
            int verticeAtual = fila.poll();
            naMST.add(verticeAtual);

            // Para cada aresta conectada ao vértice atual
            for (Aresta aresta : adjacencias.get(verticeAtual)) {
                int destino = aresta.destino;
                int peso = aresta.peso;

                // Se o destino não estiver na MST e o peso da aresta for menor que o peso registrado
                if (!naMST.contains(destino) && peso < chave.get(destino)) {
                    // Atualiza a chave e o pai
                    chave.put(destino, peso);
                    pai.put(destino, verticeAtual);

                    // Se o destino não estiver na fila, adiciona
                    if (!fila.contains(destino)) {
                        fila.add(destino);
                    }
                }
            }
        }

        // Construção da Árvore Geradora Mínima (MST) a partir dos pais registrados
        for (Integer vertice : pai.keySet()) {
            mst.inserirVertice(vertice);
            if (pai.get(vertice) != null) {
                mst.inserirAresta(vertice, pai.get(vertice), chave.get(vertice));
            }
        }

        return mst;
    }
}