package dados.transporte;

public class TransporteCargaViva extends Transporte {
    private double temperaturaMaxima;
    private double temperaturaMinima;

    public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, Estado situacao, double temperaturaMaxima, double temperaturaMinima) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                situacao);
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
    }

    @Override
    public double calculaCusto() {
        // IMPLEMENTAR
        return 0;
    }
}
