// Identificação do grupo (Nome e RA de cada integrante) aqui
// Referências (Links, livros, etc.)
// Link do YouTube (Dia 25)
// Links do ChatGPT (se usar)

import java.util.Scanner;

/**
 * Classe principal que implementa o REPL (Read-Evaluate-Print-Loop)
 * para o interpretador de Assembly simplificado.
 */
public class Interpretador {

    // Nossos "dados" principais: a lista de código e o estado do arquivo
    private ListaEncadeada codigo;
    private boolean modificado;  // Controla modificações não salvas
    private String arquivoAtual; // Nome do arquivo carregado

    /**
     * Construtor da classe. Inicializa o estado.
     */
    public Interpretador() {
        this.codigo = new ListaEncadeada();
        this.modificado = false;
        this.arquivoAtual = null;
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
            String comando = partes[0].toUpperCase(); [cite_start]// Comandos são case-insensitive [cite: 22]
            String argumentos = (partes.length > 1) ? partes[1] : ""; // O resto da string

            // --- Substituição do switch por if-else if ---
            
            if (comando.equals("LOAD")) {
                processarLoad(argumentos, teclado);
                
            } else if (comando.equals("LIST")) {
                codigo.listar();
                
            } else if (comando.equals("RUN")) {
                processarRun();
                
            } else if (comando.equals("INS")) {
                processarIns(argumentos);
                
            } else if (comando.equals("DEL")) {
                processarDel(argumentos);
                
            } else if (comando.equals("SAVE")) {
                processarSave(argumentos);
                
            } else if (comando.equals("EXIT")) {
                executando = processarExit(teclado);
                
            } else {
                System.out.println("Erro: comando inválido."); [cite_start]// [cite: 95]
            }
        }

        System.out.println("Fim."); [cite_start]// [cite: 212]
        teclado.close();
    }

    // --- MÉTODOS AUXILIARES PARA CADA COMANDO (A implementar nos próximos dias) ---

    private void processarLoad(String argumentos, Scanner teclado) {
        // TODO (Plano: Dia 10 e 11)
        // 1. Verificar 'this.modificado'. [cite_start]Se true, perguntar se quer salvar [cite: 23]
        [cite_start]//    (use o 'teclado' para ler a resposta S/N) [cite: 184]
        [cite_start]// 2. Se for salvar, chamar processarSave(this.arquivoAtual) [cite: 186]
        // 3. Limpar a lista 'codigo' (codigo = new ListaEncadeada();)
        [cite_start]// 4. Abrir e ler o arquivo 'argumentos' (que é o <ARQUIVO.ED1>) [cite: 23]
        [cite_start]// 5. Para cada linha, fazer parse e chamar 'codigo.inserir(...)' [cite: 23]
        // 6. Atualizar 'this.arquivoAtual = argumentos' e 'this.modificado = false'
        [cite_start]// 7. Exibir notificação (sucesso ou erro) [cite: 23, 97, 170]
        System.out.println("Implementar (Dia 10): LOAD " + argumentos);
    }

    private void processarRun() {
        // TODO (Plano: Semana 3, Dia 15)
        [cite_start]// 1. Verificar se 'codigo.inicio' é null [cite: 23]
        [cite_start]// 2. Criar os 26 registradores (ex: int[26]) [cite: 46]
        // 3. Iniciar o interpretador (percorrer a lista e executar instruções)
        System.out.println("Implementar (Dia 15): RUN");
    }

    private void processarIns(String argumentos) {
        // TODO (Plano: Dia 5)
        [cite_start]// 1. Fazer o parse de 'argumentos' para extrair <LINHA> e <INSTRUÇÃO> [cite: 23]
        [cite_start]// 2. Validar se <LINHA> é um número e não é negativa [cite: 23, 166]
        // 3. Chamar 'codigo.inserir(numeroLinha, instrucao)'
        // 4. Se a inserção foi bem-sucedida, 'this.modificado = true'
        [cite_start]// 5. Exibir notificação (inserida ou atualizada) [cite: 23, 110, 135]
        System.out.println("Implementar (Dia 5): INS " + argumentos);
    }

    private void processarDel(String argumentos) {
        // TODO (Plano: Dia 6)
        [cite_start]// 1. Fazer o parse de 'argumentos' para ver se é <LINHA> ou <LINHA_I> <LINHA_F> [cite: 23, 27]
        // 2. Chamar 'codigo.remover(linha)' ou 'codigo.removerIntervalo(linhaI, linhaF)'
        // 3. Se a remoção foi bem-sucedida, 'this.modificado = true'
        [cite_start]// 4. Exibir notificação (removida, inexistente, intervalo inválido) [cite: 23, 27, 128, 126, 164]
        System.out.println("Implementar (Dia 6): DEL " + argumentos);
    }

    private void processarSave(String argumentos) {
        // TODO (Plano: Dia 8 e 9)
        // 1. Determinar o nome do arquivo:
        [cite_start]//    - Se 'argumentos' não estiver vazio, é "SAVE <ARQUIVO.ED1>". [cite: 27]
        //    - Se 'argumentos' estiver vazio, é "SAVE". [cite_start]Usar 'this.arquivoAtual'. [cite: 27]
        [cite_start]// 2. Para "SAVE <ARQUIVO.ED1>", verificar se o arquivo existe e perguntar se quer sobrescrever. [cite: 27]
        // 3. Percorrer a 'codigo' e escrever cada nó no arquivo.
        [cite_start]// 4. Se salvar, 'this.modificado = false' e exibir notificação. [cite: 27, 168]
        System.out.println("Implementar (Dia 8): SAVE " + argumentos);
    }

    private boolean processarExit(Scanner teclado) {
        // TODO (Plano: Dia 12)
        [cite_start]// 1. Verificar 'this.modificado'. [cite: 27]
        [cite_start]// 2. Se true, perguntar se quer salvar (S/N) [cite: 27, 210]
        [cite_start]// 3. Se "S", chamar 'processarSave(this.arquivoAtual)' [cite: 186]
        [cite_start]// 4. Se "N", apenas sair [cite: 211]
        // 5. Retornar 'false' para sinalizar ao loop 'while' que deve parar.
        System.out.println("Implementar (Dia 12): EXIT");
        return false; // Retorna 'false' para parar o loop 'while'
    }


    /**
     * Método main da aplicação.
     * Cria uma instância do Interpretador e inicia o REPL.
     */
    public static void main(String[] args) {
        // O main agora só instancia a classe e chama o método de início.
        Interpretador repl = new Interpretador();
        repl.iniciar();
    }
}