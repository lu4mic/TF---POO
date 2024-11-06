package dados.transporte;

public class TransportePessoal extends Transporte {
    private int qtdPessoas;

    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem,
            double longitudeOrigem, double latitudeDestino, double longitudeDestino,
            Estado situacao, int qtdPessoas) {

        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                situacao);
        this.qtdPessoas = qtdPessoas;
    }

    @Override
    public double calculaCusto() {
        // IMPLEMENTAR
        return 0;
    }
}
