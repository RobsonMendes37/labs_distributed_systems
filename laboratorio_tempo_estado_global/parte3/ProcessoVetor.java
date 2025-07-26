import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Representa um processo que implementa o algoritmo de Relógio Vetorial.
 */
class ProcessoVetor implements Runnable {
    private final int id; // ID do processo (0, 1, ou 2)
    private final int numProcessos;
    private final int[] vetorRelogio;
    private final BlockingQueue<MensagemVetor> canalEntrada;
    private final BlockingQueue<MensagemVetor> canalSaida;

    public ProcessoVetor(int id, int numProcessos, BlockingQueue<MensagemVetor> entrada, BlockingQueue<MensagemVetor> saida) {
        this.id = id;
        this.numProcessos = numProcessos;
        this.vetorRelogio = new int[numProcessos]; // Vetor inicializado com [0, 0, 0]
        this.canalEntrada = entrada;
        this.canalSaida = saida;
    }

    @Override
    public void run() {
        try {
            if (id == 0) { // Processo 0 (P1) inicia a comunicação.
                Thread.sleep(500);
                eventoEnvio("Mensagem inicial para P2");
            }

            for (int i = 0; i < 1; i++) {
                eventoRecebimento();
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
                eventoInterno();
                eventoEnvio("Resposta do Processo P" + (id + 1));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Processo " + (id + 1) + " foi interrompido.");
        }
    }

    // Regra 1: Incrementa sua própria posição no vetor.
    private void eventoInterno() {
        vetorRelogio[id]++;
        System.out.printf("[P%d] Evento Interno  | Relógio Vetorial: %s\n",
                (id + 1), Arrays.toString(vetorRelogio));
    }

    // Regra 2: Incrementa e envia uma cópia do vetor.
    private void eventoEnvio(String conteudo) throws InterruptedException {
        vetorRelogio[id]++; // Incrementa sua posição no vetor antes de enviar.
        MensagemVetor msg = new MensagemVetor(vetorRelogio, conteudo);
        canalSaida.put(msg);
        System.out.printf("[P%d] ENVIO           | Relógio Vetorial: %s | Conteúdo: '%s'\n",
                (id + 1), Arrays.toString(vetorRelogio), conteudo);
    }

    // Regra 3: Recebe, atualiza o vetor e incrementa sua posição.
    private void eventoRecebimento() throws InterruptedException {
        MensagemVetor msgRecebida = canalEntrada.take();
        int[] vetorRecebido = msgRecebida.vetorRelogio;

        // Regra 3.1: Atualiza o vetor local com o máximo de cada posição.
        for (int i = 0; i < numProcessos; i++) {
            vetorRelogio[i] = Math.max(vetorRelogio[i], vetorRecebido[i]);
        }
        // Regra 3.2: Incrementa sua própria posição no vetor.
        vetorRelogio[id]++;

        System.out.printf("[P%d] RECEBIMENTO     | Relógio Vetorial: %s (Msg tinha %s)\n",
                (id + 1), Arrays.toString(vetorRelogio), Arrays.toString(vetorRecebido));
    }
}