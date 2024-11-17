package aplicacao;

import dados.drone.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;

public class ACMEAirDrones extends JFrame {
    private DronesLista listaD = new DronesLista();
    private PainelPrincipal painelPrincipal;
    private DroneCargaForm droneCargaForm;
    private DronePessoalForm dronePessoalForm;

    public ACMEAirDrones() {
        super();

        droneCargaForm = new DroneCargaForm(this);
        dronePessoalForm = new DronePessoalForm(this);
        painelPrincipal = new PainelPrincipal(this);
        setContentPane(painelPrincipal);
    }

    public void executar(){
        setTitle("Menu Inicial");
        this.setSize(920, 600);
        setVisible(true);
    }


    public void mudaPainel(int painel) {
        switch (painel) {
            case 1: //menu
                setTitle("Menu Inicial");
                this.setContentPane(painelPrincipal);
                this.setSize(920, 600);
                break;
            case 2: //cadastraDrone
                //droneCargaForm.atualiza();
                setTitle("Cadastrar Drones");
                this.setContentPane(droneCargaForm);
                this.setSize(920, 600);
                this.pack();
                break;
            case 3:
                setTitle("Cadastrar Drones");
                this.setContentPane(dronePessoalForm);
                this.setSize(920, 600);
                this.pack();
                break;
        }

    }
        public boolean CadastraDrone ( int codigo, double autonomia, double custoFixo, double pesoMax, boolean protecao,
        boolean climatizacao, boolean cargaViva){
            int[] codigos = listaD.getListaDrones().stream()
                    .mapToInt(Drone::getCodigo)
                    .toArray();

            if (Arrays.binarySearch(codigos, codigo) >= 0) {
                return false;
            }

            if (cargaViva) {
                Drone d = new DroneCargaViva(codigo, autonomia, custoFixo, pesoMax, climatizacao);
                listaD.addDrone(d);
            }
            if (!cargaViva) {
                Drone d = new DroneCargaInanimada(codigo, autonomia, custoFixo, pesoMax, protecao);
                listaD.addDrone(d);
            }

            listaD.getListaDrones().sort(Comparator.comparingInt(Drone::getCodigo));

            return true;
        }

        public String mostrarDronesCarga () {
            StringBuilder texto = new StringBuilder();
            if (listaD.getListaDrones().isEmpty()) {
                return "Voce nao tem nenhum Drone de carga cadastrado!";
            }
            texto.append("codigo - autonomia - custoFixo - pesoMax\n");
            for (Drone d : listaD.getListaDrones()) {
                if (d instanceof DroneCarga) {
                    texto.append(d + "\n");
                }
            }
            return texto.toString();
        }

    public boolean CadastraDrone(int codigo, double custoFixo, double autonomia, int qtdMaxPessoas) {
        int[] cod = listaD.getListaDrones().stream()
                .mapToInt(Drone::getCodigo)
                .toArray();

        if (Arrays.binarySearch(cod, codigo) >= 0) {
            return false;
        }

        Drone dp = new DronePessoal(codigo, custoFixo, autonomia, qtdMaxPessoas);
        listaD.addDrone(dp);

        listaD.getListaDrones().sort(Comparator.comparingInt(Drone::getCodigo));
        for (Drone d : listaD.getListaDrones()) {
            System.out.println(d.getCodigo());
        }
        return true;
    }

    public String mostrarDronesPessoal() {
        StringBuilder mostrarDronesPessoal = new StringBuilder();
        if (listaD.getListaDrones().isEmpty()) {
            return ("Nao tem drone pessoal cadastrado");
        }
        mostrarDronesPessoal.append("codigo - custo fixo - autonomia - quantidade maxima de pessoas\n");
        for (Drone d : listaD.getListaDrones()) {
            if (d instanceof DronePessoal) {
                mostrarDronesPessoal.append(d + "\n");
            }
        }

        return mostrarDronesPessoal.toString();
    }
}

