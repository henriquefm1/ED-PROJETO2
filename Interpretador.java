// Identificação do grupo (Nome e RA de cada integrante) aqui
// Referências (Links, livros, etc.)
// Link do YouTube (Dia 25)
// Links do ChatGPT (se usar)

import java.util.Scanner;
// --- Imports adicionados do código do professor ---
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
// ----------------------------------------------------

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
            String comando = partes[0].toUpperCase(); // [cite: 22] Comandos são case-insensitive
            String argumentos = (partes.length > 1) ? partes[1] : ""; // O resto da string

            // --- Substituição do switch por if-else if ---
            
            if (comando.equals("LOAD")) {
                processarLoad(argumentos, teclado);
                
            } else if (comando.equals("LIST")) {
                codigo.listar();
                
            } else if (comando.equals("RUN")) {
                processarRun();
                
            } else if (comando.equals("INS")) {
                processaIns(argumentos); // Renomeei para evitar conflito de nome
                
            } else if (comando.equals("DEL")) {
                processaDel(argumentos); // Renomeei para evitar conflito de nome
                
            } else if (comando.equals("SAVE")) {
                processaSave(argumentos); // Renomeei para evitar conflito de nome
                
            } else if (comando.equals("EXIT")) {
                executando = processarExit(teclado);
                
            } else {
                System.out.println("Erro: comando inválido."); // [cite: 95]
            }
        }

        System.out.println("Fim."); // [cite: 212]
        teclado.close();
    }

    // --- MÉTODOS AUXILIARES PARA CADA COMANDO (A implementar nos próximos dias) ---

    /**
     * Carrega um arquivo .ed1 na memória (ListaEncadeada).
     * Lógica adaptada do método 'leArquivo' do professor.
     */
    private void processarLoad(String argumentos, Scanner teclado) {
        String caminho = argumentos;
        if (caminho.isEmpty()) {
            System.out.println("Erro: O comando LOAD precisa de um nome de arquivo.");
            return;
        }

        // 1. Verificar 'this.modificado'. [cite: 23]
        if (this.modificado) {
            System.out.print("Arquivo atual contém alterações não salvas. Deseja salvar? (S/N) "); // [cite: 184]
            String resposta = teclado.nextLine().toUpperCase();
            if (resposta.equals("S")) {
                processaSave(this.arquivoAtual); // [cite: 186]
            }
        }

        // 3. Limpar a lista 'codigo' e preparar para carregar
        this.codigo = new ListaEncadeada();
        
        // 4. Abrir e ler o arquivo 'argumentos' (que é o <ARQUIVO.ED1>) [cite: 23]
        // (Lógica do 'leArquivo' do professor)
        File arquivo = new File(caminho);
        Scanner leitorArquivo = null;
        
        try {
            leitorArquivo = new Scanner(arquivo);
            
            // 5. Para cada linha, fazer parse e chamar 'codigo.inserir(...)' [cite: 23]
            while (leitorArquivo.hasNextLine()) {
                String linhaDoArquivo = leitorArquivo.nextLine().trim();
                
                // Pula linhas em branco no arquivo
                if (linhaDoArquivo.isEmpty()) continue; 

                // Tenta separar a linha no primeiro espaço
                String[] partesLinha = linhaDoArquivo.split(" ", 2);
                
                try {
                    int numeroLinha = Integer.parseInt(partesLinha[0]);
                    String instrucao = (partesLinha.length > 1) ? partesLinha[1] : "";
                    
                    this.codigo.inserir(numeroLinha, instrucao);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao processar linha (número inválido): " + linhaDoArquivo);
                } catch (Exception e) {
                    System.out.println("Erro ao inserir linha: " + linhaDoArquivo);
                }
            }
            
            // 6. Atualizar 'this.arquivoAtual = argumentos' e 'this.modificado = false'
            this.arquivoAtual = caminho;
            this.modificado = false;
            
            // 7. Exibir notificação (sucesso) [cite: 23, 97]
            System.out.println("Arquivo '" + caminho + "' carregado com sucesso.");

        } catch (IOException e) {
            // 7. Exibir notificação (erro) [cite: 23, 170]
            System.out.printf("Erro ao abrir o arquivo: %s\n", e.getMessage());
            // Se deu erro, limpamos a lista para não deixar lixo
            this.codigo = new ListaEncadeada();
            this.arquivoAtual = null;
        } finally {
            if (leitorArquivo != null) {
                leitorArquivo.close();
            }
        }
    }

    private void processarRun() {
        // TODO (Plano: Semana 3, Dia 15)
        // 1. Verificar se 'codigo.inicio' é null [cite: 23]
        // 2. Criar os 26 registradores (ex: int[26]) [cite: 46]
        // 3. Iniciar o interpretador (percorrer a lista e executar instruções)
        System.out.println("Implementar (Dia 15): RUN");
    }

    private void processaIns(String argumentos) {
        // TODO (Plano: Dia 5)
        // 1. Fazer o parse de 'argumentos' para extrair <LINHA> e <INSTRUÇÃO> [cite: 23]
        // 2. Validar se <LINHA> é um número e não é negativa [cite: 23, 166]
        // 3. Chamar 'codigo.inserir(numeroLinha, instrucao)'
        // 4. Se a inserção foi bem-sucedida, 'this.modificado = true'
        // 5. Exibir notificação (inserida ou atualizada) [cite: 23, 110, 135]
        
        // (Implementação de exemplo baseada no TODO e no PDF)
        try {
            String[] partes = argumentos.split(" ", 2);
            int numeroLinha = Integer.parseInt(partes[0]);
            String instrucao = (partes.length > 1) ? partes[1] : "";

            if (numeroLinha < 0) {
                 System.out.println("Erro: linha " + numeroLinha + " inválida."); // [cite: 166]
                 return;
            }

            // Usando o retorno 'boolean' do método 'inserir' da ListaEncadeada
            boolean foiInserido = codigo.inserir(numeroLinha, instrucao);
            
            this.modificado = true; // [cite: 23] (qualquer operação bem sucedida marca como modificado)

            if (foiInserido) {
                System.out.println("Linha inserida:"); // [cite: 110]
            } else {
                System.out.println("Linha atualizada:"); // [cite: 135]
            }
            System.out.println(numeroLinha + " " + instrucao);

        } catch (NumberFormatException e) {
            System.out.println("Erro: O número da linha é inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao processar INS: " + e.getMessage());
        }
    }

    private void processaDel(String argumentos) {
        // TODO (Plano: Dia 6)
        // 1. Fazer o parse de 'argumentos' para ver se é <LINHA> ou <LINHA_I> <LINHA_F> [cite: 23, 27]
        // 2. Chamar 'codigo.remover(linha)' ou 'codigo.removerIntervalo(linhaI, linhaF)'
        // 3. Se a remoção foi bem-sucedida, 'this.modificado = true'
        // 4. Exibir notificação (removida, inexistente, intervalo inválido) [cite: 23, 27, 128, 126, 164]
        
        // (Implementação de exemplo para DEL <LINHA>)
        try {
            int numeroLinha = Integer.parseInt(argumentos);
            
            // Usando o retorno 'boolean' do método 'remover' da ListaEncadeada
            boolean foiRemovido = codigo.remover(numeroLinha);

            if (foiRemovido) {
                System.out.println("Linha removida:"); // [cite: 128]
                this.modificado = true;
            } else {
                System.out.println("Erro: linha " + numeroLinha + " inexistente."); // [cite: 126]
            }
            
        } catch (NumberFormatException e) {
             System.out.println("Erro: O número da linha é inválido.");
        } catch (Exception e) {
            System.out.println("Erro ao processar DEL: " + e.getMessage());
        }
        
        // System.out.println("Implementar (Dia 6): DEL " + argumentos);
    }

    /**
     * Salva o código-fonte da memória (ListaEncadeada) para um arquivo.
     * Lógica adaptada do método 'gravaArquivo' do professor.
     */
    private void processaSave(String argumentos) {
        // 1. Determinar o nome do arquivo:
        String caminho = argumentos;
        if (caminho.isEmpty()) {
            if (this.arquivoAtual == null) {
                System.out.println("Erro: Nenhum arquivo aberto. Use 'SAVE <ARQUIVO.ED1>'.");
                return;
            }
            caminho = this.arquivoAtual; // [cite: 27] (SAVE sem argumentos usa o arquivo atual)
        }
        
        // 2. Para "SAVE <ARQUIVO.ED1>", verificar se o arquivo existe... (TODO)
        // [cite: 27] (Essa lógica de perguntar se quer sobrescrever precisa ser implementada)
        
        
        // 3. Percorrer a 'codigo' e escrever cada nó no arquivo.
        // (Lógica do 'gravaArquivo' do professor)
        try (PrintWriter escritor = new PrintWriter(caminho)) {
            
            NoLinha atual = this.codigo.inicio;
            while (atual != null) {
                escritor.println(atual.numeroLinha + " " + atual.instrucao);
                atual = atual.proximo;
            }
            
            // 4. Se salvar, 'this.modificado = false' e exibir notificação. [cite: 27, 168]
            this.modificado = false;
            this.arquivoAtual = caminho; // Atualiza o arquivo atual se foi um 'SAVE <novo_nome>'
            System.out.println("Arquivo '" + caminho + "' salvo com sucesso.");
            
        } catch (IOException e) {
            System.out.printf("Erro ao gravar o arquivo: %s\n", e.getMessage());
        }
    }

    private boolean processarExit(Scanner teclado) {
        // 1. Verificar 'this.modificado'. [cite: 27]
        if (this.modificado) {
            // 2. Se true, perguntar se quer salvar (S/N) [cite: 27, 210]
            System.out.print("Arquivo atual contém alterações não salvas. Deseja salvar? (S/N) ");
            String resposta = teclado.nextLine().toUpperCase();
            
            if (resposta.equals("S")) {
                // 3. Se "S", chamar 'processaSave(this.arquivoAtual)' [cite: 186]
                processaSave(this.arquivoAtual);
            }
            // 4. Se "N", apenas sair [cite: 211]
        }
        
        // 5. Retornar 'false' para sinalizar ao loop 'while' que deve parar.
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
