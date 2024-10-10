
package appbusao;

class Viagem {
    //Variáveis viagem
    private String nome;
    private String horario;
    private Onibus onibus;
    private Linha linha;

    //Armazenamento das informações
    public Viagem(String nome, String horario, Onibus onibus, Linha linha) {
        this.nome = nome;
        this.horario = horario;
        this.onibus = onibus;
        this.linha = linha;
    }

    public String getNome() {
        return nome;
    }

    public String getHorario() {
        return horario;
    }

    public Onibus getOnibus() {
        return onibus;
    }

    public Linha getLinha() {
        return linha;
    }
}
