package contadorpassageirosonibus;

import java.util.*;
import javax.swing.JOptionPane;

public class ContadorPassageirosOnibus
{
    public static List<Linha> linhas = new ArrayList<>();
    public static List<Onibus> onibus = new ArrayList<>();
    public static List<Viagem> viagens = new ArrayList<>();
    
    public static void recuperaViagens(String filePath)
    {
        List<String> lines = DataReader.readDataFromFile(filePath);

        for (String line : lines)
        {
            Viagem viagem = Viagem.fromString(line);
            viagens.add(viagem);
        }
    }
    
    public static String toStringViagens(List<Viagem> viagens)
    {
        String listaViagens = "[";
        for(Viagem viagem : viagens)
        {
            listaViagens += viagem.toStringViagem();
        }
        listaViagens += "]";
        return listaViagens;
    }
    
    public static void recuperaLinhas(String filePath)
    {
        List<String> lines = DataReader.readDataFromFile(filePath);

        for (String line : lines)
        {
            Linha linha = Linha.fromString(line);
            linhas.add(linha);
        }
    }
    
    public static String toStringLinhas(List<Linha> linhas)
    {
        String listaLinhas = "[";
        for(Linha linha : linhas)
        {
            listaLinhas += linha.toStringLinha();
        }
        listaLinhas += "]";
        return listaLinhas;
    }
    
    public static void recuperaOnibus(String filePath)
    {
        List<String> lines = DataReader.readDataFromFile(filePath);

        for (String line : lines)
        {
            Onibus onibo = Onibus.fromString(line);
            onibus.add(onibo);
        }
    }
    
    public static String toStringOnibus(List<Onibus> onibuses)
    {
        String listaOnibus = "[";
        for(Onibus onibus : onibuses)
        {
            listaOnibus += onibus.toStringOnibus();
        }
        listaOnibus += "]";
        return listaOnibus;
    }
    
    public static boolean querContinuar(String message)
    {
        int input = JOptionPane.showConfirmDialog(null, message, message, JOptionPane.YES_NO_OPTION);
        if(input == 0)
        {
            return true;
        }else{
            return false;
        }
    }
    
    public static void menuLinha()
    {
        if(querContinuar("Quer adicionar uma Linha?"))
        {
            Linha linha = new Linha();
            linhas.add(linha);
            linha.salvaLinha();
        }
        if(linhas.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Nao existem linhas para o onibus percorrer, adicione uma linha");
            menuLinha();
        }
    }
    
    public static void menuOnibus()
    {
        while(querContinuar("Quer adicionar um Onibus?"))
        {
            Onibus onibo = new Onibus();
            onibus.add(onibo);
            onibo.salvaOnibus();
        }
        if(onibus.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Nao existem onibus para percorrer uma linha, adicione um onibus");
            menuOnibus();
        }
    }
    
    public static void menuViagem()
    {
        while(querContinuar("Quer comecar uma viagem?"))
        {
            String relatorioLinhas = "";
            String relatorioOnibus = "";
            
            for(int i = 0; i<onibus.size(); i++)
            {
                relatorioOnibus += (i+1)+" - "+onibus.get(i).getIdOnibus()+"\n";
            }

            int inputOnibus = Integer.parseInt(JOptionPane.showInputDialog(null,"Qual onibus quer colocar pra viajar?\n"+relatorioOnibus));
            Onibus onibusViagem = onibus.get(inputOnibus-1);
            
            for(int i = 0; i<linhas.size(); i++)
            {
                relatorioLinhas += (i+1)+" - "+linhas.get(i).getidLinha()+"\n";
            }

            int inputLinha = Integer.parseInt(JOptionPane.showInputDialog(null,"Qual linha esse onibus vai percorrer?\n"+relatorioLinhas));
            Linha linhaViagem = linhas.get(inputLinha-1);
            
            Viagem viagemAtual = new Viagem(onibusViagem, linhaViagem);
            viagemAtual.realizaViagem();
            viagens.add(viagemAtual);
        }
    }
    
    public static void main(String[] args)
    {
        recuperaViagens("Viagens.txt");
        recuperaLinhas("Linhas.txt");
        recuperaOnibus("Onibus.txt");
        
        menuLinha();
        menuOnibus();
        menuViagem();
    }
}
