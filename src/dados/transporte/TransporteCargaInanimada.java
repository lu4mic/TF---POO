package dados.transporte;

public class TransporteCargaInanimada extends Transporte {
    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, Estado situacao, boolean cargaPerigosa) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                situacao);
        this.cargaPerigosa = cargaPerigosa;
    }

    @Override
    public double calculaCusto() {
        // IMPLEMENTAR
        return 0;
    }
}
