//Enrique Cipolla Martins - 10427834
//Henrique Ferreira Marciano - 10439797
import java.util.Scanner; // Necessário para a pausa do LISTAR

public class ListaEncadeada {
    NoLinha inicio; // O "head" da lista

    public ListaEncadeada() {
        this.inicio = null;
    }


    public boolean inserir(int numeroLinha, String instrucao) {
        NoLinha novoNo = new NoLinha(numeroLinha, instrucao);
        NoLinha atual = this.inicio;
        NoLinha anterior = null;

        //Percorre a lista para encontrar o ponto de inserção ou a linha existente
        while (atual != null && atual.numeroLinha < numeroLinha) {
            anterior = atual;
            atual = atual.proximo;
        }

        //Verifica se a linha já existe (Atualização)
        if (atual != null && atual.numeroLinha == numeroLinha) {
            atual.instrucao = instrucao; // Apenas atualiza a instrução
            return false; // Indica que foi uma atualização
        }

        //Se não existe, é uma inserção.
        // O novo nó deve ser inserido entre 'anterior' e 'atual'.
        if (anterior == null) {
            // Caso Inserção no início da lista (ou lista vazia)
            novoNo.proximo = this.inicio;
            this.inicio = novoNo;
        } else {
            // Caso Inserção no meio ou no fim da lista
            novoNo.proximo = atual;
            anterior.proximo = novoNo;
        }
        
        return true; // Indica que foi uma nova inserção
    }


    public NoLinha remover(int numeroLinha) {
        NoLinha atual = this.inicio;
        NoLinha anterior = null;

        //Procura o nó a ser removido
        while (atual != null && atual.numeroLinha != numeroLinha) {
            anterior = atual;
            atual = atual.proximo;
        }

        //Se não encontrou (chegou ao fim ou lista vazia)
        if (atual == null) {
            return null; // Retorna null se não achou
        }

        //Se encontrou, remove o nó 'atual'
        if (anterior == null) {
            //O nó a ser removido é o primeiro (this.inicio)
            this.inicio = atual.proximo;
        } else {
            //O nó a ser removido está no meio ou fim
            anterior.proximo = atual.proximo;
        }

        return atual; // Retorna o nó que foi removido
    }


    public ListaEncadeada removerIntervalo(int linhaInicial, int linhaFinal) {
        ListaEncadeada listaRemovidos = new ListaEncadeada(); // Lista para guardar os removidos
        NoLinha atual = this.inicio;
        NoLinha anterior = null;

        //Achar o nó ANTERIOR ao início do intervalo
        while (atual != null && atual.numeroLinha < linhaInicial) {
            anterior = atual;
            atual = atual.proximo;
        }

        // 'atual' agora é o primeiro nó DENTRO do intervalo (ou o primeiro após, ou nulo)
        // 'anterior' é o último nó ANTES do intervalo (ou nulo, se o intervalo começa no início)
        NoLinha noAntesDoIntervalo = anterior;
        NoLinha noInicioIntervalo = atual; // Guarda o início do intervalo que será removido

        //Percorrer os nós DENTRO do intervalo (para remoção)
        while (atual != null && atual.numeroLinha <= linhaFinal) {
            atual = atual.proximo; // Avança para o próximo nó
        }

        // 'atual' agora é o primeiro nó DEPOIS do intervalo
        
        //Se não removeu nada (noInicioIntervalo == atual), retorna a lista vazia
        if (noInicioIntervalo == atual) {
            return listaRemovidos;
        }

        //Conectar o nó 'anterior' ao nó 'atual' (pulando o intervalo)
        if (noAntesDoIntervalo == null) {
            // O intervalo removido começava no início da lista
            this.inicio = atual;
        } else {
            // O intervalo estava no meio ou fim da lista
            noAntesDoIntervalo.proximo = atual;
        }

        //"Fechar" a lista de nós removidos
        // Encontra o último nó do intervalo removido
        NoLinha ultimoDoIntervalo = noInicioIntervalo;
        while(ultimoDoIntervalo.proximo != atual && ultimoDoIntervalo.proximo != null) {
            ultimoDoIntervalo = ultimoDoIntervalo.proximo;
        }
        ultimoDoIntervalo.proximo = null; // Corta a ligação com o resto da lista

        //Atribui a lista de removidos ao 'inicio' da nova lista
        listaRemovidos.inicio = noInicioIntervalo;
        return listaRemovidos;
    }

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
    }
    
    //Método auxiliar para listar os nós removidos sem a pausa de 20 linhas.
    public void listarSemPausa() {
        NoLinha atual = this.inicio;
        while (atual != null) {
            System.out.println(atual.numeroLinha + " " + atual.instrucao);
            atual = atual.proximo;
        }
    }
}
