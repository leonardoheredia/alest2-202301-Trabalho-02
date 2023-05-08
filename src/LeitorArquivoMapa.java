import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LeitorArquivoMapa {
    private String caminhoArquivo;

    private int numeroLinhas;
    private int numeroColunas;
    private char[][] mapaEmChar;

    public LeitorArquivoMapa() {
        numeroLinhas = 0;
        numeroColunas = 0;
    }
    public boolean ler(String caminhoArquivo) {
        File myObj = new File(caminhoArquivo);
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
            int linha = 0;
            int coluna = 0;
            String dadosLinha = myReader.nextLine();
            numeroLinhas = Integer.parseInt(dadosLinha.split(" ")[0]);
            numeroColunas = Integer.parseInt(dadosLinha.split(" ")[1]);
            mapaEmChar = new char[numeroLinhas][numeroColunas];
            while (myReader.hasNextLine()) {
                dadosLinha = myReader.nextLine();
                for (char c : dadosLinha.toCharArray()) {
                    mapaEmChar[linha][coluna] = c;
                    coluna++;
                }
                linha++;
                coluna = 0;
            }
            myReader.close();
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public int getNumeroColunas() {
        return numeroColunas;
    }

    public char[][] getMapaEmChar() {
        return mapaEmChar;
    }
}
