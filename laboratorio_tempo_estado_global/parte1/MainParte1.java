import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Classe principal para a Parte 1.
 * Configura e inicia três processos que se comunicam em um anel (P1->P2, P2->P3, P3->P1).
 * O objetivo é observar as inconsistências de ordenação usando apenas o relógio físico.
 */
public class MainParte1 {

    public static void main(String[] args) {
        // Filas para simular os canais de comunicação unidirecionais.
        // BlockingQueue é thread-safe, ideal para comunicação entre threads.
        BlockingQueue<Long> q12 = new LinkedBlockingQueue<>(); // Canal de P1 para P2
        BlockingQueue<Long> q23 = new LinkedBlockingQueue<>(); // Canal de P2 para P3
        BlockingQueue<Long> q31 = new LinkedBlockingQueue<>(); // Canal de P3 para P1

        // Cria as threads, cada uma representando um processo.
        // Cada processo recebe seu canal de entrada e de saída.
        Thread p1 = new Thread(new ProcessoFisico(1, q31, q12));
        Thread p2 = new Thread(new ProcessoFisico(2, q12, q23));
        Thread p3 = new Thread(new ProcessoFisico(3, q23, q31));

        System.out.println("Iniciando simulação com Relógios Físicos...");

        // Inicia a execução das threads.
        p1.start();
        p2.start();
        p3.start();
    }
}