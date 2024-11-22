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
            double longitudeDestino) {
                
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.peso = peso;
        this.latitudeOrigem = latitudeOrigem;
        this.longitudeOrigem = longitudeOrigem;
        this.latitudeDestino = latitudeDestino;
        this.longitudeDestino = longitudeDestino;
        this.situacao = Estado.PENDENTE;
        this.drone = null;
    }

    public abstract double calculaCusto();

    public enum Estado {
        PENDENTE, ALOCADO, TERMINADO, CANCELADO
    }

    public Drone getDrone() {
        return drone;
    }

    public int getNumero() {
        return numero;
    }

    public double getPeso() {
        return peso;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Estado getSituacao() {
        return situacao;
    }

    public void setSituacao(Estado situacao) {
        this.situacao = situacao;
    }

    public String toString(){
        return "Numero: "+ numero + ", Situacao: " + situacao + ", Nome Cliente: " + nomeCliente + ", Descricao: " + descricao + ", Peso: " + peso
                + ", LatitudeOrigem: " + latitudeOrigem + ", LatitudeDestino: " + latitudeDestino
                + ", LatitudeDestino: " + latitudeDestino +  ", Drone: " + drone;
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

        return (6378140 * distancia) / 1000; // Retorna a distância em metros
    }
}
