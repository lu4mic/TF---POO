package dados.drone;

public abstract class Drone {
    private int codigo;
    private double custoFixo;
    private double autonomia;

    public Drone(int codigo, double custoFixo, double autonomia) {
        this.codigo = codigo;
        this.custoFixo = custoFixo;
        this.autonomia = autonomia;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public double getCustoFixo() {
        return custoFixo;
    }

    public abstract double calculaCustoKm();
}
