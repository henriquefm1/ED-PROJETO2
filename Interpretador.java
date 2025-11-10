// Identificação do grupo (Nome e RA de cada integrante) aqui
// Referências (Links, livros, etc.)
// Link do YouTube (Dia 25)
// Links do ChatGPT (se usar)

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe principal que implementa o REPL (Read-Evaluate-Print-Loop)
 * para o interpretador de Assembly simplificado.
 */
public class Interpretador {

    // Nossos "dados" principais: a lista de código e o estado do arquivo
    private ListaEncadeada codigo;
    private boolean modificado;  // Controla modificações não salvas
    private String arquivoAtual; // Nome do arquivo carregado

    // Registradores (A-Z) e sinalizadores de inicialização
    private int[] registradores;
    private boolean[] inicializados;

    /**
     * Construtor da classe. Inicializa o estado.
     */
    public Interpretador() {
        this.codigo = new ListaEncadeada();
        this.modificado = false;
        this.arquivoAtual = null;
        // Os arrays de registradores são inicializados no início do processarRun()
    }

    /**
     * Método principal que inicia e executa o REPL.
     */
    public void iniciar() {
        Scanner teclado = new Scanner(System.in);
        boolean executando = true;

        System.out.println("Interpretador de Assembly Simplificado (Apl2 - Estrutura de Dados)");

        // REPL: Read-Evaluate-Print-Loop
        while (executando) {
            System.out.print("> ");
            String entrada = teclado.nextLine().trim();
            
            if (entrada.isEmpty()) { // Ignora linhas em branco
                continue; 
            }

            // Separa o comando (ex: "LOAD") dos argumentos (ex: "c:\tmp\teste1.ed1")
            String[] partes = entrada.split(" ", 2);
            String comando = partes[0].toUpperCase(); // Comandos são case-insensitive
            String argumentos = (partes.length > 1) ? partes[1] : ""; // O resto da string

            // --- Substituição do switch por if-else if ---
            
            if (comando.equals("LOAD")) {
                processarLoad(argumentos, teclado);
                
            } else if (comando.equals("LIST")) {
                // Lembre-se que o PDF pede para 'listar()'
                // pausar a cada 20 linhas.
                codigo.listar(); 
                
            } else if (comando.equals("RUN")) {
                processarRun();
                
            } else if (comando.equals("INS")) {
                processaIns(argumentos);
                
            } else if (comando.equals("DEL")) {
                processaDel(argumentos);
                
            } else if (comando.equals("SAVE")) {
                processaSave(argumentos, teclado);
                
            } else if (comando.equals("EXIT")) {
                executando = processarExit(teclado);
                
            } else {
                System.out.println("Erro: comando inválido.");
            }
        }

        System.out.println("Fim.");
        teclado.close();
    }

    // --- MÉTODOS AUXILIARES PARA CADA COMANDO ---

    /**
     * Carrega um arquivo .ed1 na memória (ListaEncadeada).
     */
    private void processarLoad(String argumentos, Scanner teclado) {
        String caminho = argumentos;
        if (caminho.isEmpty()) {
            System.out.println("Erro: O comando LOAD precisa de um nome de arquivo.");
            return;
        }

        // 1. Verificar 'this.modificado'.
        if (this.modificado) {
            System.out.print("Arquivo atual contém alterações não salvas. Deseja salvar? (S/N) ");
            String resposta = teclado.nextLine().toUpperCase();
            if (resposta.equals("S")) {
                processaSave(this.arquivoAtual, teclado);
            }
        }

        // 3. Limpar a lista 'codigo' e preparar para carregar
        // (SÓ SUBSTITUI SE O LOAD FOR BEM SUCEDIDO)
        ListaEncadeada novaLista = new ListaEncadeada();
        
        // 4. Abrir e ler o arquivo
        File arquivo = new File(caminho);
        Scanner leitorArquivo = null;
        
        try {
            leitorArquivo = new Scanner(arquivo);
            
            // 5. Para cada linha, fazer parse e chamar 'codigo.inserir(...)'
            while (leitorArquivo.hasNextLine()) {
                String linhaDoArquivo = leitorArquivo.nextLine().trim();
                
                if (linhaDoArquivo.isEmpty()) continue; 

                String[] partesLinha = linhaDoArquivo.split(" ", 2);
                
                try {
                    int numeroLinha = Integer.parseInt(partesLinha[0]);
                    String instrucao = (partesLinha.length > 1) ? partesLinha[1] : "";
                    
                    if(numeroLinha < 0) { // (validação de linha negativa)
                         System.out.println("Erro ao carregar (linha negativa inválida): " + linhaDoArquivo);
                    } else {
                         novaLista.inserir(numeroLinha, instrucao);
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao processar linha (número inválido): " + linhaDoArquivo);
                } 
            }
            
            // 6. SÓ ATUALIZA SE DEU TUDO CERTO
            this.codigo = novaLista;
            this.arquivoAtual = caminho;
            this.modificado = false;
            
            // 7. Exibir notificação (sucesso)
            System.out.println("Arquivo '" + caminho + "' carregado com sucesso.");

        } catch (IOException e) {
            // 7. Exibir notificação (erro)
            System.out.printf("Erro ao abrir o arquivo: %s\n", e.getMessage());
            // Se deu erro, a lista 'this.codigo' antiga é mantida.
        } finally {
            if (leitorArquivo != null) {
                leitorArquivo.close();
            }
        }
    }

    /**
     * Executa (interpreta) o código Assembly em memória.
     */
    private void processarRun() {
        if (codigo.inicio == null) {
            System.out.println("Erro: Não há código na memória para executar.");
            return;
        }
        
        // Inicializa os registradores a cada execução
        this.registradores = new int[26];
        this.inicializados = new boolean[26];

        NoLinha atual = codigo.inicio;
        boolean executando = true;

        while (executando && atual != null) {
            String linhaCompleta = atual.numeroLinha + " " + atual.instrucao;
            
            try {
                String[] partes = atual.instrucao.trim().split("\\s+", 2);
                String comandoInst = (partes.length > 0) ? partes[0].toUpperCase() : ""; // (case insensitive)
                String args = (partes.length > 1) ? partes[1] : "";
                
                String[] argsPartes = args.split("\\s+");
                String arg1 = (argsPartes.length > 0) ? argsPartes[0] : "";
                String arg2 = (argsPartes.length > 1) ? argsPartes[1] : "";

                NoLinha proximoNo = atual.proximo; 

                // --- INÍCIO DA LÓGICA (if-else if) ---
                
                if (comandoInst.equals("MOV")) { // mov x y
                    setValor(arg1, getValor(arg2));
                
                } else if (comandoInst.equals("INC")) { // inc x
                    setValor(arg1, getValor(arg1) + 1);
                
                } else if (comandoInst.equals("DEC")) { // dec x
                    setValor(arg1, getValor(arg1) - 1);
                
                } else if (comandoInst.equals("ADD")) { // add x y
                    setValor(arg1, getValor(arg1) + getValor(arg2));
                
                } else if (comandoInst.equals("SUB")) { // sub x y
                    setValor(arg1, getValor(arg1) - getValor(arg2));
                
                } else if (comandoInst.equals("MUL")) { // mul x y
                    setValor(arg1, getValor(arg1) * getValor(arg2));
                
                } else if (comandoInst.equals("DIV")) { // div x y
                    int divisor = getValor(arg2);
                    if (divisor == 0) {
                        throw new InterpretadorException("Erro: Divisão por zero.");
                    }
                    setValor(arg1, getValor(arg1) / divisor);
                
                } else if (comandoInst.equals("OUT")) { // out x
                    System.out.println(getValor(arg1));
                
                } else if (comandoInst.equals("JNZ")) { // jnz x y
                    if (getValor(arg1) != 0) {
                        int numLinhaPulo = getValor(arg2); // 'y' pode ser registrador ou const.
                        proximoNo = pularPara(numLinhaPulo); 
                        
                        if (proximoNo == null) {
                            throw new InterpretadorException("Erro: Linha " + numLinhaPulo + " não existe."); // (salto inválido)
                        }
                    }
                    // Se for zero, apenas continua (proximoNo já é atual.proximo)
                
                } else if (comandoInst.equals("")) { // Linha vazia (só número)
                    // Não faz nada, apenas avança
                
                } else {
                    // 'default'
                    throw new InterpretadorException("Erro: instrução inválida '" + comandoInst + "'.");
                }
                
                // --- FIM DA LÓGICA ---
                
                atual = proximoNo; // Avança para o próximo nó

            } catch (InterpretadorException e) {
                System.out.println(e.getMessage());
                System.out.println("Linha: " + linhaCompleta); // (mostra erro coerente)
                executando = false; 
            } catch (Exception e) {
                // Pega outros erros (ex: ArrayIndexOutOfBounds se 'inc' vier sem arg)
                System.out.println("Erro fatal de execução: " + e.getMessage());
                System.out.println("Linha: " + linhaCompleta);
                executando = false; 
            }
        }
    }

    /**
     * Insere ou atualiza uma linha de código.
     */
    private void processaIns(String argumentos) {
        try {
            String[] partes = argumentos.split(" ", 2);
            int numeroLinha = Integer.parseInt(partes[0]);
            String instrucao = (partes.length > 1) ? partes[1] : "";

            if (numeroLinha < 0) {
                System.out.println("Erro: linha " + numeroLinha + " inválida.");
                return;
            }

            // Assumindo que ListaEncadeada.inserir() retorna 'true' para inserção
            // e 'false' para atualização.
            boolean foiInserido = codigo.inserir(numeroLinha, instrucao);
            
            this.modificado = true; 

            if (foiInserido) {
                System.out.println("Linha inserida:");
            } else {
                System.out.println("Linha atualizada:");
            }
            System.out.println(numeroLinha + " " + instrucao);

        } catch (NumberFormatException e) {
            System.out.println("Erro: O número da linha é inválido.");
        } catch (ArrayIndexOutOfBoundsException e) {
             System.out.println("Erro: Formato inválido. Use: INS <LINHA> <INSTRUÇÃO>");
        }
    }

    /**
     * Remove uma linha ou um intervalo de linhas.
     */
    private void processaDel(String argumentos) {
        try {
            String[] partes = argumentos.split("\\s+");
            
            if (partes.length == 1) {
                // --- Caso DEL <LINHA> ---
                int numeroLinha = Integer.parseInt(partes[0]);
                
                // (Assumindo que 'remover' retorna boolean)
                boolean foiRemovido = codigo.remover(numeroLinha);

                if (foiRemovido) {
                    System.out.println("Linha removida:");
                    this.modificado = true;
                } else {
                    System.out.println("Erro: linha " + numeroLinha + " inexistente.");
                }
                
            } else if (partes.length == 2) {
                // --- Caso DEL <LINHA_I> <LINHA_F> ---
                int linhaI = Integer.parseInt(partes[0]);
                int linhaF = Integer.parseInt(partes[1]);
                
                if (linhaI > linhaF) {
                    System.out.println("Erro: intervalo inválido de linhas.");
                    return;
                }

                // (Assumindo que 'removerIntervalo' retorna o n° de linhas removidas)
                int removidas = codigo.removerIntervalo(linhaI, linhaF);
                
                if (removidas > 0) {
                    System.out.println(removidas + " linhas removidas no intervalo [" + linhaI + ", " + linhaF + "].");
                    this.modificado = true;
                } else {
                    System.out.println("Nenhuma linha encontrada no intervalo [" + linhaI + ", " + linhaF + "].");
                }
                
            } else {
                System.out.println("Erro: Formato inválido. Use: DEL <LINHA> ou DEL <LINHA_I> <LINHA_F>");
            }
            
        } catch (NumberFormatException e) {
             System.out.println("Erro: O número da linha é inválido.");
        }
    }

    /**
     * Salva o código-fonte da memória para um arquivo.
     */
    private void processaSave(String argumentos, Scanner teclado) {
        String caminho = argumentos;
        boolean isSaveAs = !caminho.isEmpty(); // É 'SAVE <ARQUIVO>' ou só 'SAVE'?
        
        if (!isSaveAs) { // (SAVE sem argumentos)
            if (this.arquivoAtual == null) {
                System.out.println("Erro: Nenhum arquivo aberto. Use 'SAVE <ARQUIVO.ED1>'.");
                return;
            }
            caminho = this.arquivoAtual;
        } else {
            // (SAVE <ARQUIVO.ED1>)
            File f = new File(caminho);
            // Pergunta se o arquivo existe E não é o que já está aberto
            if (f.exists() && !caminho.equals(this.arquivoAtual)) {
                System.out.print("Arquivo '" + caminho + "' já existe. Deseja sobrescrever? (S/N) ");
                String resposta = teclado.nextLine().toUpperCase();
                if (!resposta.equals("S")) {
                    System.out.println("Arquivo não salvo.");
                    return;
                }
            }
        }
        
        // 3. Percorrer a 'codigo' e escrever cada nó no arquivo.
        try (PrintWriter escritor = new PrintWriter(caminho)) {
            
            NoLinha atual = this.codigo.inicio;
            while (atual != null) {
                escritor.println(atual.numeroLinha + " " + atual.instrucao);
                atual = atual.proximo;
            }
            
            // 4. Se salvar, 'this.modificado = false' e exibir notificação.
            this.modificado = false;
            this.arquivoAtual = caminho; // Atualiza o arquivo atual
            System.out.println("Arquivo '" + caminho + "' salvo com sucesso.");
            
        } catch (IOException e) {
            System.out.printf("Erro ao gravar o arquivo: %s\n", e.getMessage());
        }
    }

    /**
     * Pergunta se deseja salvar antes de sair.
     */
    private boolean processarExit(Scanner teclado) {
        // 1. Verificar 'this.modificado'.
        if (this.modificado) {
            // 2. Se true, perguntar se quer salvar (S/N)
            System.out.print("Arquivo atual contém alterações não salvas. Deseja salvar? (S/N) ");
            String resposta = teclado.nextLine().toUpperCase();
            
            if (resposta.equals("S")) {
                // 3. Se "S", chamar 'processaSave'
                processaSave(this.arquivoAtual, teclado);
            }
            // 4. Se "N", apenas sair
        }
        
        // 5. Retornar 'false' para sinalizar ao loop 'while' que deve parar.
        return false; // Retorna 'false' para parar o loop 'while'
    }


    // --- MÉTODOS AUXILIARES DO 'RUN' (ADICIONADOS) ---
    // (Estes eram os métodos que faltavam e causavam o erro 'cannot find symbol')

    /**
     * Converte um nome de registrador (ex: "A") para seu índice no array (ex: 0).
     * Valida se é uma letra única, tratando erros como 'inc 10'.
     */
    private int getIndice(String registrador) throws InterpretadorException {
        if (registrador.isEmpty() || registrador.length() > 1 || !Character.isLetter(registrador.charAt(0))) {
            throw new InterpretadorException("Erro: Argumento '" + registrador + "' não é um registrador válido (A-Z).");
        }
        // Converte 'A' -> 0, 'B' -> 1, etc.
        return Character.toUpperCase(registrador.charAt(0)) - 'A';
    }

    /**
     * Obtém o valor de um argumento, que pode ser um número (constante)
     * ou um registrador.
     * Valida se registradores foram inicializados antes de ler.
     */
    private int getValor(String arg) throws InterpretadorException {
        if (arg.isEmpty()) {
            throw new InterpretadorException("Erro: Argumento ausente.");
        }
        
        // Tenta ser um número (constante)
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            // Se não for número, tenta ser um registrador
            int indice = getIndice(arg);
            
            // REGRA DO PDF: Não pode ler registrador não inicializado
            if (!this.inicializados[indice]) {
                throw new InterpretadorException("Erro: Registrador '" + arg.toUpperCase() + "' usado antes de ser inicializado.");
            }
            return this.registradores[indice];
        }
    }

    /**
     * Define o valor de um registrador e marca-o como inicializado.
     */
    private void setValor(String arg, int valor) throws InterpretadorException {
        int indice = getIndice(arg); // Valida se 'arg' é um registrador
        this.registradores[indice] = valor;
        this.inicializados[indice] = true; // Marca como inicializado
    }

    /**
     * Encontra o nó da lista encadeada correspondente a um número de linha.
     * Usado pelo JNZ.
     */
    private NoLinha pularPara(int numeroLinha) {
        NoLinha atual = codigo.inicio;
        while (atual != null) {
            if (atual.numeroLinha == numeroLinha) {
                return atual;
            }
            // Otimização: Como a lista é ordenada, se já passamos, não vamos encontrar
            if (atual.numeroLinha > numeroLinha) { 
                return null; 
            }
            atual = atual.proximo;
        }
        return null; // Linha não encontrada
    }

    // --- FIM DOS MÉTODOS AUXILIARES ---


    /**
     * Método main da aplicação.
     * Cria uma instância do Interpretador e inicia o REPL.
     */
    public static void main(String[] args) {
        // O main agora só instancia a classe e chama o método de início.
        Interpretador repl = new Interpretador();
        repl.iniciar();
    }

    /**
     * Exceção personalizada usada pelo interpretador para sinalizar erros de execução
     */
    private static class InterpretadorException extends Exception {
        public InterpretadorException(String message) {
            super(message);
        }
    }
}