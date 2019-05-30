import java.net.DatagramSocket;
import java.util.Scanner;

public class CorrigirProvasClient {

    public static void main(String[] args) {
        DatagramSocket socket;
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
            respostas += i+1 + ";" + questoes[i].getQtdAlternativas() + ";" + questoes[i].getRespostas();
        }

        System.out.println("Respostas como vão pro servidor: " + respostas);
    }
}