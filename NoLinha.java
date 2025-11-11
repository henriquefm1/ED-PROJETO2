//Enrique Cipolla Martins - 1042
//Henrique Ferreira Marciano - 10439797

/**
 * Representa um nó da lista encadeada, contendo uma linha de código.
 */
public class NoLinha {
    int numeroLinha;
    String instrucao;
    NoLinha proximo;

    /**
     * Construtor do Nó.
     * @param numeroLinha O número da linha.
     * @param instrucao A instrução de Assembly.
     */
    public NoLinha(int numeroLinha, String instrucao) {
        this.numeroLinha = numeroLinha;
        this.instrucao = instrucao;
        this.proximo = null;
    }
}
