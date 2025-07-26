import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Classe principal para a Parte 2.
 * Configura e inicia três processos que utilizam o Relógio Lógico de Lamport.
 * A comunicação segue o mesmo padrão de anel.
 */
public class MainParte2 {
    public static void main(String[] args) {
        // Os canais agora transportam objetos MensagemLamport.
        BlockingQueue<MensagemLamport> q12 = new LinkedBlockingQueue<>();
        BlockingQueue<MensagemLamport> q23 = new LinkedBlockingQueue<>();
        BlockingQueue<MensagemLamport> q31 = new LinkedBlockingQueue<>();

        Thread p1 = new Thread(new ProcessoLamport(1, q31, q12));
        Thread p2 = new Thread(new ProcessoLamport(2, q12, q23));
        Thread p3 = new Thread(new ProcessoLamport(3, q23, q31));

        System.out.println("Iniciando simulação com Relógio Lógico de Lamport...");
        System.out.println("------------------------------------------------------");

        p1.start();
        p2.start();
        p3.start();
    }
}