package dados.drone;

public class DroneCargaInanimada extends DroneCarga{
    private boolean protecao;

    public DroneCargaInanimada(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean protecao) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.protecao = protecao;
    }

    @Override
    public double calculaCustoKm() {
        //IMPLEMENTAR
        return 0;
    }
}
