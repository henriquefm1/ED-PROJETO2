// Identificação do grupo (Nome e RA de cada integrante) aqui 

public class NoLinha {
    int numeroLinha;
    String instrucao;
    NoLinha proximo;

    // Construtor
    public NoLinha(int numeroLinha, String instrucao) {
        this.numeroLinha = numeroLinha;
        this.instrucao = instrucao;
        this.proximo = null; // O próximo é nulo por padrão
    }
}