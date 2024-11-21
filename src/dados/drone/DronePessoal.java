package dados.drone;

public class DronePessoal extends Drone{
    private int qtdMaxPessoas;

    public DronePessoal(int codigo, double custoFixo, double autonomia, int qtdMaxPessoas) {
        super(codigo, custoFixo, autonomia);
        this.qtdMaxPessoas = qtdMaxPessoas;
    }

    @Override
    public double calculaCustoKm() {
        return getCustoFixo() + 2;
    }

    public int getQtdMaxPessoas() {
        return qtdMaxPessoas;
    }

    public String toString(){
        return super.toString() + " - " + qtdMaxPessoas;
    }
}
