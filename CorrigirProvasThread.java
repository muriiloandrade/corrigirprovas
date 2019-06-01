import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CorrigirProvasThread extends Thread {

    private final DatagramPacket pctVeio;
    private final String respostas;
    private final int qtdQuestoes;
  //private final Thread parentThread;

    public CorrigirProvasThread(DatagramPacket pctVeio, String respostas, int qtdQuestoes /*, Thread parentThread*/) {
        this.pctVeio = pctVeio;
        this.respostas = respostas;
        this.qtdQuestoes = qtdQuestoes;
      //this.parentThread = parentThread;
    }

    @Override
    public void run() {
        String[] arrayRespostas = respostas.split("[;]"); //vem do server
        String[] arrayQuestoes = new String(pctVeio.getData()).split("[;]"); //vem do cliente
        String respostaThread = "";

        int skipper = 0, acertos = 0, erros = 0;

        for (int i = 0; i < qtdQuestoes; i++) {
            char[] charArrayQuestoes = arrayQuestoes[skipper + 2].toCharArray();
            char[] charArrayResposta = arrayRespostas[skipper + 2].toCharArray();

            for (int m = 0; m < charArrayResposta.length; m++) {
                if (charArrayResposta[m] == charArrayQuestoes[m]) {
                    acertos += 1;
                } else {
                    erros += 1;
                }
            }
            skipper = i + 3;
            respostaThread += i+1 + ";" + acertos + ";" + erros + ";";
            acertos = 0;
            erros = 0;
        }
        
        byte[] msgVai = respostaThread.getBytes();
        DatagramPacket pctVai = new DatagramPacket(
                msgVai,
                msgVai.length,
                pctVeio.getAddress(),
                pctVeio.getPort()
        );

        try {
            DatagramSocket destino = new DatagramSocket();
            destino.send(pctVai);
            destino.close();
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}