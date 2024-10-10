
package appbusao;

class Linha {
    //Variáveis linha
    private String nome;
    private int quantidadeParadas;

    public Linha(String nome, int quantidadeParadas) {
        this.nome = nome;
        this.quantidadeParadas = quantidadeParadas;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeParadas() {
        return quantidadeParadas;
    }
}

