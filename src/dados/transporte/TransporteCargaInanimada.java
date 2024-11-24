package dados.transporte;

public class TransporteCargaInanimada extends Transporte {
    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, boolean cargaPerigosa) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino);
        this.cargaPerigosa = cargaPerigosa;
    }

    public String toString(){
        if(this.getDrone()==null){
            return super.toString() + ", Carga Perigosa: " + cargaPerigosa;
        }
        return super.toString() + ", Carga Perigosa: " + cargaPerigosa + " Custo: "+ calculaCusto();
    }

    public boolean isCargaPerigosa() {
        return cargaPerigosa;
    }

    @Override
    public double calculaCusto() {
        double distancia = super.getDistancia();
        if(cargaPerigosa) {
            return (getDrone().getCustoFixo() * distancia) + 500;
        }
        return getDrone().getCustoFixo() * distancia;
    }
}
