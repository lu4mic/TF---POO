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

    public abstract double calculaCusto();

    enum Estado{
        PENDENTE, ALOCADO, TERMINADO, CANCELADO
    }
}
