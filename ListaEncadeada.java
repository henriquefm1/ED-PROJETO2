// Identificação do grupo (Nome e RA de cada integrante) aqui 

public class ListaEncadeada {
    NoLinha inicio; // O "head" da lista

    public ListaEncadeada() {
        this.inicio = null;
    }

    // --- Métodos a implementar (Próximos dias) ---

    // Método para inserir/atualizar uma linha 
    // Deve manter a lista ordenada pelo numeroLinha
    public void inserir(int numeroLinha, String instrucao) {
        System.out.println("Implementar: Inserir linha " + numeroLinha);
        // Lógica de inserção ordenada aqui...
    }

    // Método para remover uma linha 
    public boolean remover(int numeroLinha) {
        System.out.println("Implementar: Remover linha " + numeroLinha);
        // Lógica de remoção aqui...
        return false; // Retorna true se removeu, false se não encontrou
    }

    // Método para remover um intervalo 
    public void removerIntervalo(int linhaInicial, int linhaFinal) {
        System.out.println("Implementar: Remover de " + linhaInicial + " a " + linhaFinal);
        // Lógica de remoção de intervalo aqui...
    }

    // Método para listar o conteúdo 
    public void listar() {
        System.out.println("Implementar: Listar código");
        // Lógica de percorrer a lista e imprimir (de 20 em 20) aqui...
    }

    // ... outros métodos (salvar, buscarNo, etc.)
}