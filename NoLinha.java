//Enrique Cipolla Martins - 10427834
//Henrique Ferreira Marciano - 10439797

//Representa um nó da lista encadeada, contendo uma linha de código.
public class NoLinha {
    int numeroLinha;
    String instrucao;
    NoLinha proximo;

    public NoLinha(int numeroLinha, String instrucao) {
        this.numeroLinha = numeroLinha;
        this.instrucao = instrucao;
        this.proximo = null;
    }
}
