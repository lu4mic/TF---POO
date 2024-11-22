package dados.transporte;

import dados.drone.Drone;

public class TransportePessoal extends Transporte {
    private int qtdPessoas;

    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem,
            double longitudeOrigem, double latitudeDestino, double longitudeDestino, int qtdPessoas) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino);
        this.qtdPessoas = qtdPessoas;
    }

    @Override
    public double calculaCusto() {
        double distancia = super.getDistancia();
        return (getDrone().calculaCustoKm() * distancia) + 10 * qtdPessoas;
    }

    public int getQtdPessoas() {
        return qtdPessoas;
    }

    public String toString(){
        if(this.getDrone()== null){
            return super.toString() + ", Quantidade de pessoal: " + qtdPessoas;
        }
        return super.toString() + ", Quantidade de pessoal: " + qtdPessoas + ", Custo: "+ calculaCusto();
    }

}
