import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class CorrigirProvasServer {

    public static void main(String[] args) {
        int porta = 6789;
        DatagramSocket socket;
        Scanner input = new Scanner(System.in);
        Thread parentThread = Thread.currentThread();
        
        System.out.print("Informe a quantidade de questões: ");
        int qtdQuestoes = input.nextInt();
        Questoes[] questoes = new Questoes[qtdQuestoes];
        String respostas = "";
        
        for(int i=0;i<qtdQuestoes;i++){
            System.out.printf("------------------------ %dª questão------------------------\n" 
                            + "Quantidade de alternativas da questão: ", i+1);
            questoes[i] = new Questoes();            
            questoes[i].setQtdAlternativas(input.nextInt());
            
            System.out.print("Respostas do aluno: ");
            questoes[i].setRespostas(input.next());
            respostas += i+1 + ";" + questoes[i].getQtdAlternativas() + ";" + questoes[i].getRespostas() + ";";
        }
        
        try {
            socket = new DatagramSocket(porta);

            System.out.printf("Servidor online e aguardando por cliente na porta %d!\n", socket.getLocalPort());
            byte[] buffer = new byte[1024];
            ArrayList<CorrigirProvasThread> threads = new ArrayList<>();

            while (true) {
                DatagramPacket pctVeio = new DatagramPacket(buffer, buffer.length);
                socket.receive(pctVeio);
                System.out.println("Requisição recebida do cliente: " + pctVeio.getAddress());

                CorrigirProvasThread thread = new CorrigirProvasThread(pctVeio, respostas, qtdQuestoes/*, parentThread*/);
                threads.add(thread);
                thread.start();
            }
        } catch (SocketException e) {
            System.err.println("Erro no Socket: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro de IO: " + e.getMessage());
        }
    }
}