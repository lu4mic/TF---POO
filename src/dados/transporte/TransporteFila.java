package dados.transporte;

import java.util.LinkedList;
import java.util.Queue;

public class TransporteFila {
    private Queue<Transporte> filaTransporte;

    public TransporteFila() {
        filaTransporte = new LinkedList<Transporte>();
    }
    public boolean addTransporte(Transporte t) {
        return filaTransporte.add(t);
    }
    public Queue<Transporte> getFilaTransporte() {
        return filaTransporte;
    }
}
