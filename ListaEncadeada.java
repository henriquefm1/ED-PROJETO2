//Enrique Cipolla Martins
//Henrique Ferreira Marciano - 10439797
import java.util.Scanner; // Necessário para a pausa do LISTAR

public class ListaEncadeada {
    NoLinha inicio; // O "head" da lista

    public ListaEncadeada() {
        this.inicio = null;
    }

    /**
     * Insere uma nova linha de código ou atualiza uma existente.
     * Mantém a lista ordenada pelo número da linha.
     * @param numeroLinha O número da linha a inserir/atualizar.
     * @param instrucao O código assembly da linha.
     * @return true se foi uma nova inserção, false se foi uma atualização.
     */
    public boolean inserir(int numeroLinha, String instrucao) {
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

    /**
     * Remove uma única linha de código da lista.
     * @param numeroLinha O número da linha a ser removida.
     * @return true se a linha foi encontrada e removida, false caso contrário.
     */
    public boolean remover(int numeroLinha) {
        NoLinha atual = this.inicio;
        NoLinha anterior = null;

        // 1. Procura o nó a ser removido
        while (atual != null && atual.numeroLinha != numeroLinha) {
            anterior = atual;
            atual = atual.proximo;
        }

        // 2. Se não encontrou (chegou ao fim ou lista vazia)
        if (atual == null) {
            return false;
        }

        // 3. Se encontrou, remove o nó 'atual'
        if (anterior == null) {
            // Caso 3a: O nó a ser removido é o primeiro (this.inicio)
            this.inicio = atual.proximo;
        } else {
            // Caso 3b: O nó a ser removido está no meio ou fim
            anterior.proximo = atual.proximo;
        }

        return true; // Sucesso na remoção
    }

    /**
     * Remove um intervalo de linhas de código, incluindo os limites.
     * @param linhaInicial A primeira linha do intervalo a remover.
     * @param linhaFinal A última linha do intervalo a remover.
     * @return O número de linhas que foram efetivamente removidas.
     */
    public int removerIntervalo(int linhaInicial, int linhaFinal) {
        int contadorRemovidas = 0;
        NoLinha anterior = null;
        NoLinha atual = this.inicio;

        // 1. Achar o nó ANTERIOR ao início do intervalo
        while (atual != null && atual.numeroLinha < linhaInicial) {
            anterior = atual;
            atual = atual.proximo;
        }

        // 'atual' agora é o primeiro nó DENTRO do intervalo (ou o primeiro após, ou nulo)
        // 'anterior' é o último nó ANTES do intervalo (ou nulo, se o intervalo começa no início)
        
        NoLinha noAntesDoIntervalo = anterior;

        // 2. Percorrer e contar os nós DENTRO do intervalo (para remoção)
        while (atual != null && atual.numeroLinha <= linhaFinal) {
            contadorRemovidas++;
            atual = atual.proximo; // Avança para o próximo nó
        }

        // 'atual' agora é o primeiro nó DEPOIS do intervalo
        
        // 3. Se não removeu nada, retorna 0
        if (contadorRemovidas == 0) {
            return 0;
        }

        // 4. Conectar o nó 'anterior' ao nó 'atual' (pulando o intervalo)
        if (noAntesDoIntervalo == null) {
            // O intervalo removido começava no início da lista
            this.inicio = atual;
        } else {
            // O intervalo estava no meio ou fim da lista
            noAntesDoIntervalo.proximo = atual;
        }

        return contadorRemovidas;
    }

    /**
     * Exibe em tela o conteúdo completo do código-fonte existente na
     * [cite_start]memória, pausando a cada 20 linhas[cite: 23].
     */
    public void listar() {
        if (this.inicio == null) {
            System.out.println("(Nenhum código na memória)");
            return;
        }

        Scanner scannerPausa = new Scanner(System.in);
        NoLinha atual = this.inicio;
        int contadorLinhas = 0;

        while (atual != null) {
            // Imprime a linha atual
            System.out.println(atual.numeroLinha + " " + atual.instrucao);
            contadorLinhas++;
            atual = atual.proximo; // Avança para o próximo

            // Verifica se deve pausar (a cada 20 linhas)
            // Só pausa se não for o final da lista (atual != null)
            if (contadorLinhas % 20 == 0 && atual != null) {
                System.out.print("... Pressione <Enter> para continuar ...");
                scannerPausa.nextLine(); // Espera o usuário pressionar Enter
            }
        }
        
        // Importante: NÃO feche o scannerPausa (scannerPausa.close())
        // pois isso fecharia o System.in e quebraria o REPL principal.
    }
}