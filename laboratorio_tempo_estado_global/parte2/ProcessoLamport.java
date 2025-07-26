import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Representa um processo que implementa o algoritmo de Relógio Lógico de Lamport.
 */
class ProcessoLamport implements Runnable {
    private final int id;
    private int relogioLogico;
    private final BlockingQueue<MensagemLamport> canalEntrada;
    private final BlockingQueue<MensagemLamport> canalSaida;

    public ProcessoLamport(int id, BlockingQueue<MensagemLamport> entrada, BlockingQueue<MensagemLamport> saida) {
        this.id = id;
        this.relogioLogico = 0; // Relógio lógico começa em 0.
        this.canalEntrada = entrada;
        this.canalSaida = saida;
    }

    @Override
    public void run() {
        try {
            if (id == 1) { // P1 inicia a comunicação.
                Thread.sleep(500);
                eventoEnvio("Mensagem inicial para P2");
            }

            for (int i = 0; i < 1; i++) {
                eventoRecebimento();
                // Simula processamento antes de responder.
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
                eventoInterno(); // Um evento interno antes de enviar a resposta.
                eventoEnvio("Resposta do Processo " + id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Processo " + id + " foi interrompido.");
        }
    }

    // Regra 1 de Lamport: Incrementa o relógio para um evento interno.
    private void eventoInterno() {
        relogioLogico++;
        System.out.printf("[P%d] Evento Interno  | Relógio Lamport: %d\n", id, relogioLogico);
    }

    // Regra 2 de Lamport: Incrementa e envia o relógio na mensagem.
    private void eventoEnvio(String conteudo) throws InterruptedException {
        relogioLogico++; // Incrementa o relógio antes de enviar.
        MensagemLamport msg = new MensagemLamport(relogioLogico, conteudo);
        canalSaida.put(msg);
        System.out.printf("[P%d] ENVIO           | Relógio Lamport: %d | Conteúdo: '%s'\n",
                id, relogioLogico, conteudo);
    }

    // Regra 3 de Lamport: Atualiza o relógio ao receber uma mensagem.
    private void eventoRecebimento() throws InterruptedException {
        MensagemLamport msgRecebida = canalEntrada.take();
        int relogioRecebido = msgRecebida.relogio;

        // L(p) = max(L(p), L(msg)) + 1
        relogioLogico = Math.max(relogioLogico, relogioRecebido) + 1;

        System.out.printf("[P%d] RECEBIMENTO     | Relógio Lamport: %d (Msg tinha: %d) | Conteúdo: '%s'\n",
                id, relogioLogico, relogioRecebido, msgRecebida.conteudo);
    }
}