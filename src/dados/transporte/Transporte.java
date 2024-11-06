package dados.transporte;

public abstract class Transporte {
    private int numero;
    private String nomeCliente;
    private String descricao;
    private double peso;
    private double latitudeOrigem;
    private double longitudeOrigem;
    private double latitudeDestino;
    private double longitudeDestino;
    private Estado situacao;

    public Transporte(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, Estado situacao) {
                
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.peso = peso;
        this.latitudeOrigem = latitudeOrigem;
        this.longitudeOrigem = longitudeOrigem;
        this.latitudeDestino = latitudeDestino;
        this.longitudeDestino = longitudeDestino;
        this.situacao = situacao;
    }

    public abstract double calculaCusto();

    enum Estado {
        PENDENTE, ALOCADO, TERMINADO, CANCELADO
    }
}
