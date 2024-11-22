package dados.drone;

public abstract class Drone {
    private int codigo;
    private double custoFixo;
    private double autonomia;
    private int countT;

    public Drone(int codigo, double custoFixo, double autonomia) {
        this.codigo = codigo;
        this.custoFixo = custoFixo;
        this.autonomia = autonomia;
        this.countT = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCount(int count) {
        this.countT = count;
    }
    public int getCount() {
        return countT;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public double getCustoFixo() {
        return custoFixo;
    }

    public abstract double calculaCustoKm();

    public String toString() {
        return  "Codigo: " + codigo + ", Custo Fixo: " + custoFixo + ", Autonomia: " + autonomia;
    }
}
