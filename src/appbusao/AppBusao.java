/**
 *
 * @author matheus_sd_santos
 */
package appbusao;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppBusao {
    public static void main(String[] args) {
        List<Onibus> onibusList = new ArrayList<>();
        List<Linha> linhaList = new ArrayList<>();
        List<Viagem> viagemList = new ArrayList<>();
        boolean running = true;

        while (running) {
            String[] options = {"Cadastrar Ônibus", "Cadastrar Linha", "Cadastrar Viagem", "Escolher Viagem", "Sair"};
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    cadastrarOnibus(onibusList);
                    break;
                case 1:
                    cadastrarLinha(linhaList);
                    break;
                case 2:
                    cadastrarViagem(viagemList, onibusList, linhaList);
                    break;
                case 3:
                    escolherViagem(viagemList);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void cadastrarOnibus(List<Onibus> onibusList) {
        String placa = JOptionPane.showInputDialog("Digite a placa do ônibus:");
        int capacidade = Integer.parseInt(JOptionPane.showInputDialog("Digite a capacidade do ônibus:"));
        onibusList.add(new Onibus(placa, capacidade));
    }

    private static void cadastrarLinha(List<Linha> linhaList) {
        String nomeLinha = JOptionPane.showInputDialog("Digite o nome da linha:");
        int quantidadeParadas = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de paradas:"));
        linhaList.add(new Linha(nomeLinha, quantidadeParadas));
    }

    private static void cadastrarViagem(List<Viagem> viagemList, List<Onibus> onibusList, List<Linha> linhaList) {
        if (onibusList.isEmpty() || linhaList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "É necessário cadastrar ao menos um ônibus e uma linha antes de cadastrar uma viagem.");
            return;
        }

        String nomeViagem = JOptionPane.showInputDialog("Digite o nome da viagem:");
        String horarioViagem = JOptionPane.showInputDialog("Digite o horário da viagem:");

        String[] onibusOptions = new String[onibusList.size()];
        for (int i = 0; i < onibusList.size(); i++) {
            onibusOptions[i] = onibusList.get(i).getPlaca();
        }
        int onibusChoice = JOptionPane.showOptionDialog(null, "Escolha um ônibus", "Ônibus",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, onibusOptions, onibusOptions[0]);
        Onibus onibusEscolhido = onibusList.get(onibusChoice);

        String[] linhaOptions = new String[linhaList.size()];
        for (int i = 0; i < linhaList.size(); i++) {
            linhaOptions[i] = linhaList.get(i).getNome();
        }
        int linhaChoice = JOptionPane.showOptionDialog(null, "Escolha uma linha", "Linha",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, linhaOptions, linhaOptions[0]);
        Linha linhaEscolhida = linhaList.get(linhaChoice);

        viagemList.add(new Viagem(nomeViagem, horarioViagem, onibusEscolhido, linhaEscolhida));
    }

    private static void escolherViagem(List<Viagem> viagemList) {
        if (viagemList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma viagem cadastrada.");
            return;
        }

        String[] viagemOptions = new String[viagemList.size()];
        for (int i = 0; i < viagemList.size(); i++) {
            viagemOptions[i] = viagemList.get(i).getNome();
        }
        int viagemChoice = JOptionPane.showOptionDialog(null, "Escolha uma viagem", "Viagem",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, viagemOptions, viagemOptions[0]);
        Viagem viagemEscolhida = viagemList.get(viagemChoice);

        Linha linha = viagemEscolhida.getLinha();
        Onibus onibus = viagemEscolhida.getOnibus();
        int passageirosAtuais = 0;
        int passageirosNaoEntraram = 0;

        for (int i = 1; i <= linha.getQuantidadeParadas(); i++) {
            int passageirosSubiram = Integer.parseInt(JOptionPane.showInputDialog("Parada " + i + ": Quantos passageiros subiram?"));
            int passageirosDesceram = Integer.parseInt(JOptionPane.showInputDialog("Parada " + i + ": Quantos passageiros desceram?"));
            passageirosAtuais = passageirosAtuais + passageirosSubiram - passageirosDesceram;
            if (passageirosAtuais > onibus.getCapacidade()) {
                passageirosNaoEntraram += passageirosAtuais - onibus.getCapacidade();
                passageirosAtuais = onibus.getCapacidade();
            }
        }

        String resultado = "Ônibus: " + onibus.getPlaca() + " | Capacidade: " + onibus.getCapacidade() + "\n" +
                           "Linha: " + linha.getNome() + " | Paradas: " + linha.getQuantidadeParadas() + "\n" +
                           "Viagem: " + viagemEscolhida.getNome() + " | Horário: " + viagemEscolhida.getHorario() + "\n" +
                           "Não puderam entrar: " + passageirosNaoEntraram;
                           
        JOptionPane.showMessageDialog(null, resultado);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultado_viagem.txt", true))) {
            writer.write(resultado);
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

