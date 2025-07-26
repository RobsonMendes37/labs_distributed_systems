import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Classe principal para a Parte 3.
 * Configura e inicia três processos que utilizam Relógios Vetoriais.
 */
public class MainParte3 {
    // Constante para o número total de processos na simulação.
    private static final int NUM_PROCESSOS = 3;

    public static void main(String[] args) {
        BlockingQueue<MensagemVetor> q12 = new LinkedBlockingQueue<>();
        BlockingQueue<MensagemVetor> q23 = new LinkedBlockingQueue<>();
        BlockingQueue<MensagemVetor> q31 = new LinkedBlockingQueue<>();

        // Os IDs dos processos agora são baseados em índice 0 (0, 1, 2)
        // para facilitar a manipulação do vetor de relógios.
        Thread p1 = new Thread(new ProcessoVetor(0, NUM_PROCESSOS, q31, q12));
        Thread p2 = new Thread(new ProcessoVetor(1, NUM_PROCESSOS, q12, q23));
        Thread p3 = new Thread(new ProcessoVetor(2, NUM_PROCESSOS, q23, q31));

        System.out.println("Iniciando simulação com Relógios Vetoriais...");
        System.out.println("-------------------------------------------------");

        p1.start();
        p2.start();
        p3.start();
    }
}