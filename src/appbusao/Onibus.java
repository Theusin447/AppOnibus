
package appbusao;

class Onibus {
    //Variáveis onibus
    private String placa;
    private int capacidade;

    public Onibus(String placa, int capacidade) {
        this.placa = placa;
        this.capacidade = capacidade;
    }

    public String getPlaca() {
        return placa;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
