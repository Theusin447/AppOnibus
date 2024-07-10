package contadorpassageirosonibus;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class Onibus
{
    private int qtdMaxPassageiros;
    private String idOnibus;
    
    public Onibus()
    {
        this.qtdMaxPassageiros = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos passageiros cabem no Onibus?"));
        this.idOnibus = JOptionPane.showInputDialog(null, "Qual a placa do Onibus?");
    }
    
    public Onibus(int qtdMaxPassageiros, String idOnibus)
    {
        this.qtdMaxPassageiros = qtdMaxPassageiros;
        this.idOnibus = idOnibus;
    }
    
    public void salvaOnibus()
    {
        registraDado(toStringOnibus(), "Onibus.txt");
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
    
    public String toStringOnibus()
    {
        return this.qtdMaxPassageiros
                +"=" + this.idOnibus;
    }
    
    public static Onibus fromString(String line)
    {
        String[] parts = line.split("=");
        String idOnibus = parts[1];
        int qtdMaxPassageiros = Integer.parseInt(parts[0]);
        
        Onibus onibusRecuperado = new Onibus(qtdMaxPassageiros, idOnibus);

        return onibusRecuperado;
    }

    public int getQtdMaxPassageiros() {
        return qtdMaxPassageiros;
    }
    

    public String getIdOnibus() {
        return idOnibus;
    }
}