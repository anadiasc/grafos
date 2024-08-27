//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.*;

/**Classe Principal que implementa a classe Grafo e seus métodos
 * @author Ana Vitória Dias Carvalho
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        Grafo g = new Grafo();

        g.inserirVertice(1);
        g.inserirVertice(2);
        g.inserirVertice(3);
        g.inserirVertice(4);
        g.inserirVertice(5);

        g.inserirAresta(1,2,3);
        g.inserirAresta(2,3,4);
        g.inserirAresta(3,4,5);
        g.inserirAresta(4,1,6);
        g.inserirAresta(5,4,2);
        g.inserirAresta(5,1,10);

        do {
            System.out.println("======================================");
            System.out.println("|                GRAFO               |");
            System.out.println("======================================");
            System.out.println("| 1. Inserir Vértice                 |");
            System.out.println("| 2. Inserir Aresta                  |");
            System.out.println("| 3. Remover Vértice                 |");
            System.out.println("| 4. Remover Aresta                  |");
            System.out.println("| 5. Visualizar Grafo                |");
            System.out.println("| 6. Informar Grau de um Vértice     |");
            System.out.println("| 7. Verificar se o Grafo é Conexo   |");
            System.out.println("| 8. Caminhamento em Profundidade    |");
            System.out.println("| 9. Converter para Matriz de Adj    |");
            System.out.println("| 10. Caminhamento em Amplitude      |");
            System.out.println("| 11. Caminho Mínimo (Dijkstra)      |");
            System.out.println("| 12. Árvore Geradora Mínima (Prim)  |");
            System.out.println("| 0. Sair                            |");
            System.out.println("======================================");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Informe um número inteiro: ");
                    int num = sc.nextInt();

                    if(g.inserirVertice(num)){
                        System.out.println("Vértice Inserido com sucesso!");
                    }else {
                        System.out.println("O vétice "+num+" já está no grafo, tente outro número!");
                    }
                    break;
                case 2:
                    System.out.print("Informe o vértice origem: ");
                    int origem = sc.nextInt();
                    System.out.println("");
                    System.out.print("Informe o vértice destino: ");
                    int destino = sc.nextInt();
                    System.out.println("");
                    System.out.print("Informe o peso da aresta: ");
                    int peso = sc.nextInt();

                    if(g.inserirAresta(origem, destino, peso)){
                        System.out.println("Aresta inserida com sucesso!");
                    }else {
                        System.out.println("Não foi possível inserir a aresta, tente novamente!");
                    }
                    break;
                case 3:
                    System.out.print("Informe o vétice: ");
                    num = sc.nextInt();

                    if(g.removerVertice(num)){
                        System.out.println("Vértice removido com sucesso!");
                    }else {
                        System.out.println("Não foi possível remover o vértice, tente novamente!");
                    }

                    break;
                case 4:
                    System.out.print("Informe o vértice origem: ");
                    origem = sc.nextInt();
                    System.out.println("");
                    System.out.print("Informe o vértice destino: ");
                    destino = sc.nextInt();

                    if(g.removerAresta(origem,destino)){
                        System.out.println("Aresta removida com sucesso!");
                    }else {
                        System.out.println("Não foi possível remover a aresta, tente novamente!");
                    }

                    break;
                case 5:
                    Grafo.visualizarGrafo(g);
                    break;
                case 6:
                    System.out.print("Informe o vétice: ");
                    num = sc.nextInt();

                    System.out.println("O grau do vértice "+num+" é: "+g.calcularGrau(num));
                    break;
                case 7:
                    if(g.verificarConexo()){
                        System.out.println("O grafo é conexo");
                    }else{
                        System.out.println("O grafo não é conexo");
                    }
                    break;
                case 8:
                    Set<Integer> visitados = new HashSet<>();
                    List<Integer> caminho = new ArrayList<>();

                    System.out.println("Informe o vértice: ");
                    int vertice = sc.nextInt();

                    Grafo grafoResultado = new Grafo();

                    grafoResultado = g.dfs(vertice, visitados, grafoResultado);

                    Grafo.visualizarGrafo(grafoResultado);
                    break;
                case 9:
                    g.imprimirMatrizAdjacencia(g.converterParaMatrizDeAdjacencia());
                    break;
                case 10:
                    System.out.println("Informe o vértice: ");
                    vertice = sc.nextInt();

                    grafoResultado = new Grafo();

                    grafoResultado = g.bfs(vertice);
                    Grafo.visualizarGrafo(grafoResultado);

                    break;
                case 11: //verificar os resultados, não parece correto
                    System.out.println("Informe o vértice de origem: ");
                    origem = sc.nextInt();

                    grafoResultado = new Grafo();

                    grafoResultado = g.dijkstra(origem);
                    Grafo.visualizarGrafo(grafoResultado);

                    break;
                case 12:
                    System.out.println("Informe o vértice de origem: ");
                    origem = sc.nextInt();

                    Grafo.visualizarGrafo(g.prim(origem));
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha novamente.");
            }
            System.out.println();

        } while (opcao != 0);

        sc.close();
    }
}