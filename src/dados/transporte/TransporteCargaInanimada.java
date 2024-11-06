package dados.transporte;

import dados.drone.Drone;

public class TransporteCargaInanimada extends Transporte {
    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, Estado situacao, boolean cargaPerigosa, Drone drone) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                situacao, drone);
        this.cargaPerigosa = cargaPerigosa;
    }

    @Override
    public double calculaCusto() {
        double distancia = 0; //fazer calculo
        if(cargaPerigosa) {
            return (getDrone().getCustoFixo() * distancia) + 500;
        }
        return getDrone().getCustoFixo() * distancia;
    }
}
