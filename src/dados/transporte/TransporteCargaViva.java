package dados.transporte;

import dados.drone.Drone;

public class TransporteCargaViva extends Transporte {
    private double temperaturaMaxima;
    private double temperaturaMinima;

    public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, Estado situacao, double temperaturaMaxima, double temperaturaMinima, Drone drone) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                situacao, drone);
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
    }

    @Override
    public double calculaCusto() {
        double distancia = 0; //fazer calculo
        if(temperaturaMaxima - temperaturaMinima > 10) {
            return (getDrone().getCustoFixo() * distancia) + 1000;
        }

        return getDrone().getCustoFixo() * distancia;
    }
}
