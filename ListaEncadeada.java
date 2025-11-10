//Enrique Cipolla Martins
//Henrique Ferreira Marciano - 10439797
<<<<<<< HEAD
=======
import java.util.Scanner; // Necessário para o listar()
>>>>>>> 46839954434c32bfe889c45ede32ee258e64c50f

public class ListaEncadeada {
    NoLinha inicio; // O "head" da lista

    public ListaEncadeada() {
        this.inicio = null;
    }

<<<<<<< HEAD
    // --- Métodos a implementar (Próximos dias) ---

    // Método para inserir/atualizar uma linha 
    // Deve manter a lista ordenada pelo numeroLinha
    public boolean inserir(int numeroLinha, String instrucao) {
        System.out.println("Implementar: Inserir linha " + numeroLinha);
=======
    // --- Seu método (correto) ---
    public boolean inserir(int numeroLinha, String instrucao) {
>>>>>>> 46839954434c32bfe889c45ede32ee258e64c50f
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

<<<<<<< HEAD
    // Método para remover uma linha 
    public boolean remover(int numeroLinha) {
        System.out.println("Implementar: Remover linha " + numeroLinha);
        // Lógica de remoção aqui...
        return false; // Retorna true se removeu, false se não encontrou
    }

    // Método para remover um intervalo 
    public int removerIntervalo(int linhaInicial, int linhaFinal) {
        System.out.println("Implementar: Remover de " + linhaInicial + " a " + linhaFinal);
        // Lógica de remoção de intervalo aqui...
        return 0;
    }

    // Método para listar o conteúdo 
    public void listar() {
        System.out.println("Implementar: Listar código");
        // Lógica de percorrer a lista e imprimir (de 20 em 20) aqui...
    }

    // ... outros métodos (salvar, buscarNo, etc.)
}
=======
    // --- Métodos implementados ---

    /**
     * Remove uma linha específica da lista.
     * 
     * @param numeroLinha O número da linha a ser removida.
     * @return true se a remoção foi bem-sucedida, false se a linha não foi encontrada.
     */
    public boolean remover(int numeroLinha) {
        NoLinha atual = this.inicio;
        NoLinha anterior = null;

        // 1. Procura o nó a ser removido
        while (atual != null && atual.numeroLinha != numeroLinha) {
            anterior = atual;
            atual = atual.proximo;
        }

        // 2. Se não encontrou (chegou ao fim da lista)
        if (atual == null) {
            return false; // Linha não encontrada
        }

        // 3. Se encontrou, remove o nó 'atual'
        if (anterior == null) {
            // Caso 3a: Remoção do início da lista
            this.inicio = atual.proximo;
        } else {
            // Caso 3b: Remoção do meio ou fim
            anterior.proximo = atual.proximo;
        }

        return true; // Remoção bem-sucedida
    }

    /**
     * Remove um intervalo de linhas, inclusivo.
     * 
     * @param linhaInicial A linha inicial do intervalo.
     * @param linhaFinal A linha final do intervalo.
     * @return true se pelo menos uma linha foi removida, false caso contrário.
     */
    public boolean removerIntervalo(int linhaInicial, int linhaFinal) {
        // A especificação pede para notificar quais linhas foram removidas 
        // Esta implementação é O(N) e mais eficiente que chamar remover() em loop.

        NoLinha atual = this.inicio;
        NoLinha anterior = null;
        boolean removeuAlgo = false;

        // 1. Acha o nó ANTERIOR ao início do intervalo
        while (atual != null && atual.numeroLinha < linhaInicial) {
            anterior = atual;
            atual = atual.proximo;
        }

        // Se 'atual' é null, não há nada no intervalo para remover
        if (atual == null) {
            return false;
        }
        
        // 'atual' agora é o primeiro nó dentro do intervalo (ou logo após)

        // 2. Acha o nó FINAL do intervalo e vai imprimindo
        NoLinha noInicioDoIntervalo = atual; // Guarda para onde o 'anterior' vai apontar
        
        System.out.println("Linhas removidas:"); // [cite: 152]
        
        while (atual != null && atual.numeroLinha <= linhaFinal) {
            System.out.println(atual.numeroLinha + " " + atual.instrucao); // [cite: 27, 153, 154, 155]
            removeuAlgo = true;
            atual = atual.proximo;
        }

        // 'atual' agora é o primeiro nó DEPOIS do intervalo

        // 3. Se nada foi removido (ex: DEL 25 28 e só tinha linha 30)
        if (!removeuAlgo) {
            System.out.println("Nenhuma linha encontrada no intervalo [" + linhaInicial + ", " + linhaFinal + "].");
            return false;
        }

        // 4. Conecta o 'anterior' ao 'atual' (pulando o intervalo)
        if (anterior == null) {
            // O intervalo removido começa do 'inicio'
            this.inicio = atual;
        } else {
            // O intervalo estava no meio/fim
            anterior.proximo = atual;
        }

        return true;
    }

    /**
     * Exibe o conteúdo completo do código-fonte na memória,
     * pausando de 20 em 20 linhas. 
     */
    public void listar() {
        if (this.inicio == null) {
            System.out.println("(Nenhum código na memória)");
            return;
        }
        
        Scanner tecladoPausa = new Scanner(System.in);
        NoLinha atual = this.inicio;
        int contadorLinhas = 0;

        while (atual != null) {
            // [cite: 33] Exibe o número da linha e a instrução
            System.out.println(atual.numeroLinha + " " + atual.instrucao); 
            contadorLinhas++;
            atual = atual.proximo;

            // Lógica da pausa de 20 em 20 
            if (contadorLinhas % 20 == 0 && atual != null) {
                System.out.print("-- Pressione ENTER para continuar --");
                tecladoPausa.nextLine();
            }
        }
    }
}
>>>>>>> 46839954434c32bfe889c45ede32ee258e64c50f
