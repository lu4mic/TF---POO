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

    public double getDistancia(){
        double dlon, dlat, a, distancia;

        double latO = Math.toRadians(latitudeOrigem);
        double lonO = Math.toRadians(longitudeOrigem);
        double latD = Math.toRadians(latitudeDestino);
        double lonD = Math.toRadians(longitudeDestino);

        dlon = lonD - lonO;
        dlat = latD - latO;

        // Fórmula de Haversine
        a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(latO) * Math.cos(latD) * Math.pow(Math.sin(dlon / 2), 2);
        distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6378140 * distancia; // Retorna a distância em metros
    }
}
