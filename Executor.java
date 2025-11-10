public class Executor {

    private int[] registradores;
    private boolean[] inicializados;
    private ListaEncadeada codigo; // Guarda a referência da lista

    public Executor() {
        this.registradores = new int[26];
        this.inicializados = new boolean[26];
    }

    // O método que o REPL vai chamar
    public void executar(ListaEncadeada codigo) {
        this.codigo = codigo; // Salva a referência
        NoLinha atual = codigo.inicio;
        boolean executando = true;

        while (executando && atual != null) {
            try {
                // ... toda a lógica do 'if (comandoInst.equals("MOV"))' ...
                // ... 'else if (comandoInst.equals("INC"))' ...
                
                // (O 'pularPara' agora pode buscar na 'this.codigo')
                
                atual = atual.proximo; // ou o 'proximoNo' do JNZ
                
            } catch (InterpretadorException e) {
                System.out.println(e.getMessage());
                System.out.println("Linha: " + atual.numeroLinha + " " + atual.instrucao);
                executando = false;
            }
        }
    }

    // --- Métodos Auxiliares Privados ---
    
    private int getIndice(String registrador) throws InterpretadorException {
        if (registrador == null || registrador.trim().isEmpty()) {
            throw new InterpretadorException("Registrador inválido: " + registrador);
        }
        String s = registrador.trim();
        if (s.length() != 1 || !Character.isLetter(s.charAt(0))) {
            throw new InterpretadorException("Registrador inválido: " + registrador);
        }
        char c = Character.toUpperCase(s.charAt(0));
        if (c < 'A' || c > 'Z') {
            throw new InterpretadorException("Registrador inválido: " + registrador);
        }
        return c - 'A';
    }
    private int getValor(String arg) throws InterpretadorException {
        if (arg == null || arg.trim().isEmpty()) {
            throw new InterpretadorException("Argumento inválido para getValor: '" + arg + "'");
        }
        arg = arg.trim();
        // Se for um registrador (uma letra), retorna o valor guardado
        if (arg.length() == 1 && Character.isLetter(arg.charAt(0))) {
            char c = Character.toUpperCase(arg.charAt(0));
            if (c < 'A' || c > 'Z') {
                throw new InterpretadorException("Registrador inválido: " + arg);
            }
            int idx = c - 'A';
            if (!inicializados[idx]) {
                throw new InterpretadorException("Registrador não inicializado: " + arg);
            }
            return registradores[idx];
        }
        // Caso contrário, tenta interpretar como inteiro literal
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new InterpretadorException("Valor inválido: " + arg);
        }
    }
    private void setValor(String arg, int valor) throws InterpretadorException { /*...*/ }
    
    private NoLinha pularPara(int numeroLinha) {
        NoLinha atual = this.codigo.inicio;
        while (atual != null) {
            if (atual.numeroLinha == numeroLinha) {
                return atual;
            }
            atual = atual.proximo;
        }
        return null; // Linha não encontrada
    }
    
    // Exceção personalizada usada pelo interpretador
    public static class InterpretadorException extends RuntimeException {
        public InterpretadorException(String message) {
            super(message);
        }
    }
}
