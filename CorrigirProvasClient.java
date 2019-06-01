import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class CorrigirProvasClient {

    public static void main(String[] args) {
        DatagramSocket socket;
        int portDst = 6789;
        Scanner input = new Scanner(System.in);
        
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

        System.out.println("Respostas como vão pro servidor: " + respostas);
        
        try {
            socket = new DatagramSocket();
            byte[] message = respostas.getBytes();
            InetAddress host = InetAddress.getByName("localhost");
            
            DatagramPacket pctVai = new DatagramPacket(message, message.length, host, portDst);
            socket.setSoTimeout(30000);
            socket.send(pctVai);
            System.out.println("Prova enviada para correção! Aguarde resposta.");
            
            byte[] buffer = new byte[1024];
            DatagramPacket pctVeio = new DatagramPacket(buffer, buffer.length);
            socket.receive(pctVeio);
            Thread.sleep(10000);
            System.out.println("Resposta:" + new String(pctVeio.getData()).trim());
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}