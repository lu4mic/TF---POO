package dados.drone;

public class DroneCargaViva extends DroneCarga {
    private boolean climatizado;

    public DroneCargaViva(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean climatizado) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.climatizado = climatizado;
    }

    @Override
    public double calculaCustoKm() {
        if(climatizado){
            return getCustoFixo()  + 20;
        }
        return getCustoFixo() + 10;
    }

    public boolean getClimatizado() {
        return climatizado;
    }

    public String toString(){
        return super.toString() + ", Climatizado: " + climatizado;
    }
}
