/**
 * Encapsula os dados da mensagem para o algoritmo de Lamport.
 * Contém o valor do relógio lógico do remetente e o conteúdo da mensagem.
 */
class MensagemLamport {
    final int relogio;
    final String conteudo;

    public MensagemLamport(int relogio, String conteudo) {
        this.relogio = relogio;
        this.conteudo = conteudo;
    }
}