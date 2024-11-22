package dados.drone;

public abstract class DroneCarga extends Drone{
    private double pesoMaximo;

    public DroneCarga(int codigo, double custoFixo, double autonomia, double pesoMaximo) {
        super(codigo, custoFixo, autonomia);
        this.pesoMaximo = pesoMaximo;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }
    public String toString(){
        return super.toString() + ", Peso Maximo: " + pesoMaximo;
    }
}
