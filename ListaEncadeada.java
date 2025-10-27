//Enrique Cipolla Martins
//Henrique Ferreira Marciano - 10439797

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
        NoLinha novoNo = new NoLinha(numeroLinha, instrucao);
        NoLinha atual = this.inicio;
        NoLinha anterior = null;

        // 1. Percorre a lista para encontrar o ponto de inserção ou a linha existente
        while (atual != null && atual.numeroLinha < numeroLinha) {
            anterior = atual;
            atual = atual.proximo;
        }

        // 2. Verifica se a linha já existe (Atualização)
        if (atual != null && atual.numeroLinha == numeroLinha) {
            atual.instrucao = instrucao; // Apenas atualiza a instrução
            return false; // Indica que foi uma atualização
        }

        // 3. Se não existe, é uma inserção.
        // O novo nó deve ser inserido entre 'anterior' e 'atual'.
        
        if (anterior == null) {
            // Caso 3a: Inserção no início da lista (ou lista vazia)
            novoNo.proximo = this.inicio;
            this.inicio = novoNo;
        } else {
            // Caso 3b: Inserção no meio ou no fim da lista
            novoNo.proximo = atual;
            anterior.proximo = novoNo;
        }
        
        return true; // Indica que foi uma nova inserção
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