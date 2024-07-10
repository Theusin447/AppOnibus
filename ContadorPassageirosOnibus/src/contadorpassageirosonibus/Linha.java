package contadorpassageirosonibus;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class Linha
{
    //declaracao de variaveis da Linha
    private String idLinha;
    private int qtdParadas;
    
    //metodo construtor da Linha
    public Linha()
    {
        this.idLinha = JOptionPane.showInputDialog(null, "Qual o nome da linha?");
        this.qtdParadas = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantas paradas existem nessa linha?"));
    }
    
    public Linha(String idLinha, int qtdParadas)
    {
        this.idLinha = idLinha;
        this.qtdParadas = qtdParadas;
    }
    
    public void salvaLinha()
    {
        registraDado(toStringLinha(), "Linhas.txt");
    }
    
    public void registraDado(Object dado, String file)
    {
        try
        {
            //Cria arquivo das viagens
            FileWriter arquivo = new FileWriter(file, true);//Abre o arquivo em modo anexao
            PrintWriter gravador = new PrintWriter(arquivo);
            
            //Escreve no arquivo as informacoes que desejamos
            gravador.println(dado);
            gravador.close();
            arquivo.close();
        } catch (IOException e){
        }
    }
    
    public String toStringLinha()
    {
        return this.idLinha
                +"=" + this.qtdParadas;
    }
    
    public static Linha fromString(String line)
    {
        String[] parts = line.split("=");
        String idLinha = parts[0];
        int qtdParadas = Integer.parseInt(parts[1]);


        Linha linhaRecuperada = new Linha(idLinha, qtdParadas);

        return linhaRecuperada;
    }

    public String getidLinha() {
        return idLinha;
    }

    public int getQtdParadas() {
        return qtdParadas;
    }
}
