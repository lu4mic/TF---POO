package aplicacao;

import aplicacao.janelas.DroneCargaForm;
import aplicacao.janelas.DronePessoalForm;
import aplicacao.janelas.PainelPrincipal;
import aplicacao.janelas.TransporteForm;
import dados.drone.*;
import dados.transporte.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ACMEAirDrones extends JFrame {
    private DronesLista listaD = new DronesLista();
    private TransporteFila filaTransporte = new TransporteFila();
    private PainelPrincipal painelPrincipal;
    private DroneCargaForm droneCargaForm;
    private DronePessoalForm dronePessoalForm;
    private TransporteForm transporteForm;
    private ArrayList<Transporte> transportesAlocados;

    public ACMEAirDrones() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transportesAlocados = new ArrayList<>();
        droneCargaForm = new DroneCargaForm(this);
        dronePessoalForm = new DronePessoalForm(this);
        painelPrincipal = new PainelPrincipal(this);
        transporteForm = new TransporteForm(this);
        setContentPane(painelPrincipal);
    }

    public void executar() {
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
            case 4:
                setTitle("Cadastrar Transportes");
                this.setContentPane(transporteForm);
                this.setSize(920, 600);
                this.pack();
                break;
        }

    }

    public boolean CadastraDrone(int codigo, double autonomia, double custoFixo, double pesoMax, boolean protecao,
                                 boolean climatizacao, boolean cargaViva) {
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

    public String mostrarDronesCarga() {
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

    public boolean CadastraTransporte(int numero, String nome, String descricao, double peso, double latO, double latD, double longO, double longD,
                                      Integer qtdPessoas, boolean cargaperigosa, Double tempMax, Double tempMin) {

        for (Transporte tr : filaTransporte.getFilaTransporte()) {
            if (tr.getNumero() == numero) {
                return false;
            }
        }
        if (tempMax == null || tempMin == null && qtdPessoas == null) {
            //TransporteInanimado
            Transporte t = new TransporteCargaInanimada(numero, nome, descricao, peso, latO, longO, latD, longD, cargaperigosa);
            filaTransporte.addTransporte(t);
            return true;
        } else if (tempMin != null) {
            //TransporteCargaViva
            Transporte t = new TransporteCargaViva(numero, nome, descricao, peso, latO, longO, latD, longD, tempMax, tempMin);
            filaTransporte.addTransporte(t);
            return true;
        } else if (qtdPessoas != null) {
            //CargaPessoal
            Transporte t = new TransportePessoal(numero, nome, descricao, peso, latO, longO, latD, longD, qtdPessoas);
            filaTransporte.addTransporte(t);
            return true;
        }
        return false;
    }

    public void ProcessarTransportesPendentes() {
        if (listaD.getListaDrones().isEmpty() || filaTransporte.getFilaTransporte().isEmpty()) {
            return;
        }

        int tamanho = listaD.getListaDrones().size();
        int count = 0;


        do {
            Transporte first = filaTransporte.getFilaTransporte().peek();   //reseta o candidato e pega o primeiro transporte
            if(first == null){
                break;
            }

            Drone candidatoFinal = null;

            if (listaD.getListaDrones().isEmpty() || filaTransporte.getFilaTransporte().isEmpty()) {
                return;
            }

            for (Drone d : listaD.getListaDrones()) {
                if (!isDisponivel(d)) {           //drone só pode estar em um transporte
                    continue;            //proximo drone
                }
                candidatoFinal = melhorCandidato(first, d, candidatoFinal);
            }

            if (candidatoFinal == null) {
                filaTransporte.getFilaTransporte().remove();   //remove e adiciona no final
                filaTransporte.getFilaTransporte().add(first);
            }
                first.setDrone(candidatoFinal);                          //seta o drone no transporte
                first.setSituacao(Transporte.Estado.ALOCADO);           //seta o transporte como alocado
                filaTransporte.getFilaTransporte().remove();                //remove da fila de transportes
                transportesAlocados.add(first);                             //criei uma nova lista que só tem os alocados, finalizados ou cancelados!

            count++;
        } while (count != tamanho);
    }

    private boolean isDisponivel(Drone d) {
        return d.getCount() == 0;
    }

    private Drone melhorCandidato(Transporte transporte, Drone drone, Drone candidatoAtual) {
        if (transporte instanceof TransportePessoal && drone instanceof DronePessoal) {
            int qtdNecessaria = ((TransportePessoal) transporte).getQtdPessoas();
            int capacidadeDrone = ((DronePessoal) drone).getQtdMaxPessoas();

            if (capacidadeDrone >= qtdNecessaria) {
                if (candidatoAtual == null || capacidadeDrone < ((DronePessoal) candidatoAtual).getQtdMaxPessoas()) {
                    return drone; // Escolhe o drone com capacidade mais próxima
                }
            }
        }
        else if (transporte instanceof TransporteCargaInanimada && drone instanceof DroneCargaInanimada) {
            double pesoNecessario = transporte.getPeso();
            double capacidadeDrone = ((DroneCargaInanimada) drone).getPesoMaximo();

            if (capacidadeDrone >= pesoNecessario) {
                if (candidatoAtual == null || capacidadeDrone < ((DroneCargaInanimada) candidatoAtual).getPesoMaximo()) {
                    return drone; // Escolhe o drone com capacidade mais próxima
                }
            }
        }
        else if (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva) {
            double pesoNecessario = transporte.getPeso();
            double capacidadeDrone = ((DroneCargaViva) drone).getPesoMaximo();

            if (capacidadeDrone >= pesoNecessario) {
                if (candidatoAtual == null || capacidadeDrone < ((DroneCargaViva) candidatoAtual).getPesoMaximo()) {
                    return drone; // Escolhe o drone com capacidade mais próxima
                }
            }
        }

        return candidatoAtual; // Retorna o candidato atual se o drone não for melhor
    }



}