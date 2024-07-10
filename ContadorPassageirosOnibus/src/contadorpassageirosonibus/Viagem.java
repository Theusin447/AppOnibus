package contadorpassageirosonibus;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JOptionPane;

public class Viagem
{
    private static String[] inicioFim = new String[2];
    
    public static Linha rota;
    public static Onibus busao;
    public int qtdTotalPassageiros = 0;
    private int qtdAtualPassageiros = 0;
    private int qtdOverflowPassageiros = 0;
    public static List<Integer> pontosDeOverFlow = new ArrayList<Integer>();
    
    public Viagem(Onibus busao, Linha rota)
    {
        this.busao = busao;
        this.rota = rota;
    }
    
    public Viagem(String[] inicioFim, Onibus busao, Linha rota, int qtdTotalPassageiros, int qtdAtualPassageiros, int qtdOverflowPassageiros, List pontosDeOverFlow)
    {
        this.inicioFim = inicioFim;
        this.busao = busao;
        this.rota = rota;
        this.qtdTotalPassageiros = qtdTotalPassageiros;
        this.qtdAtualPassageiros = qtdAtualPassageiros;
        this.qtdOverflowPassageiros = qtdOverflowPassageiros;
        this.pontosDeOverFlow = pontosDeOverFlow;
    }
    
    public Viagem realizaViagem()
    {
        iniciaViagem();
        for(int i = 0; i<this.rota.getQtdParadas(); i++)
        {
            this.adicionaPassageiros(i);
            this.removePassageiros(i);
        }
        terminaViagem();
        salvaViagem();
        return null;
    }
    
    private void adicionaPassageiros(int iteration)
    {
        int n = Integer.parseInt(JOptionPane.showInputDialog(null, "Ponto "+(iteration+1)+"/"+rota.getQtdParadas()+" - Quantas pessoas querem subir?"));
        int vagas = this.busao.getQtdMaxPassageiros()-this.qtdAtualPassageiros;
        if(n<=vagas)
        {
            this.qtdAtualPassageiros += n;
            this.pontosDeOverFlow.add(0);
            qtdTotalPassageiros += n;
        }else{
            this.qtdOverflowPassageiros += n-vagas;
            this.pontosDeOverFlow.add(n-vagas);
            this.qtdAtualPassageiros += vagas;
            qtdTotalPassageiros += vagas;
        }  
    }
    
    private Viagem removePassageiros(int iteration)
    {
        int n = Integer.parseInt(JOptionPane.showInputDialog(null, "Ponto "+(iteration+1)+"/"+rota.getQtdParadas()+" - Quantas pessoas querem descer?"));
        if(this.qtdAtualPassageiros-n>=0)
        {
            this.qtdAtualPassageiros-=n;
        }else{
            JOptionPane.showMessageDialog(null, "Voce esta tentando tirar mais passageiros do que ha no onibus, sairam somente os que estavam no onibus");
            this.removePassageiros(iteration);
        }
        return null;
    }
    
    private void salvaViagem()
    {
        registraDado(toStringViagem(), "Viagens.txt");
    }
    
    private void iniciaViagem()
    {
        this.inicioFim[0] = registraData();
    }
    
    private void terminaViagem()
    {
        this.inicioFim[1] = registraData();
    }
    
    private String registraData()
    {
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter meuFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dataAtual.format(meuFormato);
        return dataFormatada;
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
    
    public String toStringViagem()
    {
        return inicioFim[0]
                +"=" + inicioFim[1]
                +"=" + this.rota.getidLinha()
                +"=" + this.rota.getQtdParadas()
                +"=" + busao.getIdOnibus()
                +"=" + qtdTotalPassageiros
                +"=" + this.busao.getQtdMaxPassageiros()
                +"=" + qtdAtualPassageiros
                +"=" + qtdOverflowPassageiros
                +"=" +pontosDeOverFlow;
    }
    
     public static Viagem fromString(String line)
     {
         if(!line.isBlank())
         {
            String[] parts = line.split("=");
            String inicio = parts[0];
            String fim = parts[1];
            String idLinha = parts[2];
            int qtdParadas = Integer.parseInt(parts[3]);
            String idOnibus = parts[4];
            int qtdTotalPassageiros = Integer.parseInt(parts[5]);
            int qtdMaxPassageiros = Integer.parseInt(parts[6]);
            int qtdAtualPassageiros = Integer.parseInt(parts[7]);
            int qtdOverflowPassageiros = Integer.parseInt(parts[8]);
            String[] splitArray = parts[9].split(",");
            List<String> pontosDeOverflow = new ArrayList<>(Arrays.asList(splitArray));
            String[] inicioFim = {inicio, fim};
            Linha linha = new Linha(idLinha, qtdParadas);
            Onibus onibus = new Onibus(qtdMaxPassageiros, idOnibus);
            //Viagem viagemRecuperada = new Viagem(inicioFim, onibus, linha, qtdTotalPassageiros, qtdAtualPassageiros, qtdOverflowPassageiros, (1));
            Viagem viagemRecuperada = new Viagem(onibus,linha);
            return viagemRecuperada;
         }
         return null;
    }
}