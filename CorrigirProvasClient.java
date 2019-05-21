import java.net.DatagramSocket;
import java.util.Scanner;

public class CorrigirProvasClient {

    public static void main(String[] args) {
        DatagramSocket socket;
        Scanner input = new Scanner(System.in);
        
        System.out.print("Informe a quantidade de questões: ");
        int qntQuestoes = input.nextInt();
        
        int[] questoes = new int[qntQuestoes];
        /*
            TODO: 
            Itera por todas as questões
            Insere a quantidade de alternativas
            Insere as alternativas
            Itera de novo e concatena todo mundo
        */
    }
}