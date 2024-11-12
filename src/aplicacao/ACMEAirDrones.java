package aplicacao;

import dados.drone.*;
import janelas.DroneCargaForm;

import java.util.Arrays;
import java.util.Comparator;


public class ACMEAirDrones {
    private DronesLista listaD = new DronesLista();

    public ACMEAirDrones() {
    }

    public void executar(){
        DroneCargaForm form = new DroneCargaForm();
    }

    public boolean CadastraDrone(int codigo ,double autonomia , double custoFixo , double pesoMax , boolean protecao , boolean climatizacao , boolean cargaViva){
        int[] codigos = listaD.getListaDrones().stream()
                .mapToInt(Drone::getCodigo)
                .toArray();

        if(Arrays.binarySearch(codigos,codigo) >= 0){
            return false;
        }

        if(cargaViva){
             Drone d = new DroneCargaViva(codigo,autonomia,custoFixo,pesoMax,climatizacao);
            listaD.addDrone(d);
        }
        if(!cargaViva){
            Drone d = new DroneCargaInanimada(codigo, autonomia, custoFixo, pesoMax, protecao);
            listaD.addDrone(d);
        }

        listaD.getListaDrones().sort(Comparator.comparingInt(Drone::getCodigo));

        return true;
    }
    public String mostrarDronesCarga(){
        StringBuilder texto = new StringBuilder();
        if(listaD.getListaDrones().isEmpty()){
            return "Voce nao tem nenhum Drone de carga cadastrado!";
        }
        for(Drone d : listaD.getListaDrones()){
            if(d instanceof DroneCarga){
                texto.append(d + "\n");
            }
        }
        return texto.toString();
    }
}
