package dados.transporte;

import dados.drone.Drone;

public class TransporteCargaViva extends Transporte {
    private double temperaturaMaxima;
    private double temperaturaMinima;

    public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, double temperaturaMaxima, double temperaturaMinima) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino);
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
    }

    @Override
    public double calculaCusto() {
        double distancia = super.getDistancia();
        if(temperaturaMaxima - temperaturaMinima > 10) {
            return (getDrone().calculaCustoKm() * distancia) + 1000;
        }
        return getDrone().calculaCustoKm() * distancia;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public String toString(){
        if (this.getDrone() == null) {
            return super.toString() + ", Temperatura Maxima " + temperaturaMaxima + ", Temperatura Minima: " + temperaturaMinima;
        }
        return super.toString() + ", Temperatura Maxima " + temperaturaMaxima + ", Temperatura Minima: " + temperaturaMinima+ ", Custo: "+ calculaCusto();
    }
}
