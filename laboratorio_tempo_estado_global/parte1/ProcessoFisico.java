import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Representa um processo que utiliza o relógio físico do sistema (System.currentTimeMillis()).
 * Cada processo executa um ciclo de recebimento e envio de mensagens.
 */
class ProcessoFisico implements Runnable {
    private final int id;
    private final BlockingQueue<Long> canalEntrada;
    private final BlockingQueue<Long> canalSaida;

    public ProcessoFisico(int id, BlockingQueue<Long> entrada, BlockingQueue<Long> saida) {
        this.id = id;
        this.canalEntrada = entrada;
        this.canalSaida = saida;
    }

    @Override
    public void run() {
        try {
            // O Processo 1 é o único que inicia a comunicação para evitar um deadlock.
            if (id == 1) {
                // Simula um tempo de processamento inicial.
                Thread.sleep(500);
                eventoEnvio("Mensagem inicial para P2");
            }

            // Cada processo irá receber uma mensagem e depois enviar uma resposta.
            // O loop garante que cada processo participe de um ciclo completo.
            for (int i = 0; i < 1; i++) {
                eventoRecebimento();

                // Simula um tempo de processamento interno variável.
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000));

                eventoEnvio("Resposta do Processo " + id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Processo " + id + " foi interrompido.");
        }
    }

    private void eventoEnvio(String conteudo) throws InterruptedException {
        long timestampFisico = System.currentTimeMillis();
        System.out.printf("[P%d] ENVIO  | Timestamp Físico: %d | Conteúdo: '%s'\n",
                id, timestampFisico, conteudo);
        // Envia apenas o timestamp para o próximo processo.
        canalSaida.put(timestampFisico);
    }

    private void eventoRecebimento() throws InterruptedException {
        // take() bloqueia a thread até que uma mensagem esteja disponível no canal de entrada.
        long timestampRecebido = canalEntrada.take();
        long timestampFisicoLocal = System.currentTimeMillis();

        System.out.printf("[P%d] RECEBI | Timestamp Físico: %d | Timestamp da msg: %d\n",
                id, timestampFisicoLocal, timestampRecebido);

        // Esta análise é crucial para o objetivo do exercício.
        // Um valor negativo indica uma inconsistência: o recebimento foi registrado ANTES do envio.
        System.out.printf("      -> Diferença (Local - Remetente): %d ms\n", (timestampFisicoLocal - timestampRecebido));
    }
}