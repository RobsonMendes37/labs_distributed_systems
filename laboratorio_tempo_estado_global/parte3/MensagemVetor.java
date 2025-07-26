import java.util.Arrays;

/**
 * Encapsula os dados da mensagem para o algoritmo de Relógio Vetorial.
 * Contém o vetor de relógios do remetente.
 */
class MensagemVetor {
    final int[] vetorRelogio;
    final String conteudo;

    public MensagemVetor(int[] vetor, String conteudo) {
        // Cria uma cópia para garantir que o remetente e a mensagem
        // não compartilhem a mesma referência do array.
        this.vetorRelogio = Arrays.copyOf(vetor, vetor.length);
        this.conteudo = conteudo;
    }
}