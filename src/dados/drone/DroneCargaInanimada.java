package dados.drone;

public class DroneCargaInanimada extends DroneCarga{
    private boolean protecao;

    public DroneCargaInanimada(int codigo, double autonomia, double custoFixo, double pesoMaximo, boolean protecao) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.protecao = protecao;
    }

    @Override
    public double calculaCustoKm() {
        if(protecao){
            return getCustoFixo()  + 10;
        }
        return getCustoFixo()  + 5;
    }

    public String toString(){
        return super.toString() + " Protecao: " + protecao;
    }
}
