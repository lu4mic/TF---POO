package dados.transporte;

import dados.drone.Drone;

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
    private Drone drone;

    public Transporte(int numero, String nomeCliente, String descricao, double peso,
            double latitudeOrigem, double longitudeOrigem, double latitudeDestino,
            double longitudeDestino, Estado situacao, Drone drone) {
                
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.peso = peso;
        this.latitudeOrigem = latitudeOrigem;
        this.longitudeOrigem = longitudeOrigem;
        this.latitudeDestino = latitudeDestino;
        this.longitudeDestino = longitudeDestino;
        this.situacao = Estado.PENDENTE;
        this.drone = drone;
    }

    public abstract double calculaCusto();

    enum Estado {
        PENDENTE, ALOCADO, TERMINADO, CANCELADO
    }

    public void setSituacao(Estado situacao) {
        this.situacao = situacao;
    }
    public Estado getSituacao() {
        return situacao;
    }

    public Drone getDrone() {
        return drone;
    }
}
