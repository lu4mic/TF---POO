package dados.transporte;

import dados.drone.Drone;

public class TransportePessoal extends Transporte {
    private int qtdPessoas;

    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem,
            double longitudeOrigem, double latitudeDestino, double longitudeDestino,
            Estado situacao, int qtdPessoas, Drone drone) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                situacao, drone);
        this.qtdPessoas = qtdPessoas;
    }

    @Override
    public double calculaCusto() {
        double distancia = 0; //FAZER CALCULO DISTANCIA
        return (getDrone().calculaCustoKm() * distancia) + 10;
    }
}
