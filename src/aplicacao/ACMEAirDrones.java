package aplicacao;

import aplicacao.janelas.*;
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
    private MostrarTransportes mostrarTransportes;
    private ArrayList<Transporte> transportesAlocados;

    public ACMEAirDrones() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transportesAlocados = new ArrayList<>();
        droneCargaForm = new DroneCargaForm(this);
        dronePessoalForm = new DronePessoalForm(this);
        painelPrincipal = new PainelPrincipal(this);
        transporteForm = new TransporteForm(this);
        mostrarTransportes = new MostrarTransportes(this);
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
            case 5:
                setTitle("Mostrar todos Transportes");
                this.setContentPane(mostrarTransportes);
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
        if (tempMax == null && tempMin == null && qtdPessoas == null) {
            //TransporteInanimado
            Transporte t = new TransporteCargaInanimada(numero, nome, descricao, peso, latO, longO, latD, longD, cargaperigosa);
            filaTransporte.addTransporte(t);
            return true;
        } else if (tempMin != null && tempMax != null) {
            //TransporteCargaViva
            Transporte t = new TransporteCargaViva(numero, nome, descricao, peso, latO, longO, latD, longD, tempMax, tempMin);
            filaTransporte.addTransporte(t);
            return true;
        } else if (qtdPessoas != null){
            //CargaPessoal
            Transporte t = new TransportePessoal(numero, nome, descricao, peso, latO, longO, latD, longD, qtdPessoas);
            filaTransporte.addTransporte(t);
            return true;
        }
        return false;
    }

    public int processarTransportesPendentes() {
        if(filaTransporte.getFilaTransporte().isEmpty()){
            return -1;
        }

        if (listaD.getListaDrones().isEmpty()) {
            return -2;
        }

        int tentativasMax = listaD.getListaDrones().size();
        int tentativas = 0;
        int count = 0;


        do {
            Transporte first = filaTransporte.getFilaTransporte().peek();   //reseta o candidato e pega o primeiro transporte
            if(first == null){
                break;
            }

            Drone candidatoFinal = null;

            for (Drone d : listaD.getListaDrones()) {
                if (!isDisponivel(d)) {           //drone só pode estar em um transporte
                    continue;                 //proximo drone
                }
                candidatoFinal = melhorCandidato(first, d, candidatoFinal);
            }

            if (candidatoFinal == null) {
                filaTransporte.getFilaTransporte().remove();   //remove e adiciona no final
                filaTransporte.getFilaTransporte().add(first);
            }
            else {
                first.setDrone(candidatoFinal);                          //seta o drone no transporte
                first.setSituacao(Transporte.Estado.ALOCADO);           //seta o transporte como alocado
                filaTransporte.getFilaTransporte().remove();                //remove da fila de transportes
                transportesAlocados.add(first);                         //criei uma nova lista que só tem os alocados, finalizados ou cancelados!
                count++;
            }
            tentativas++;
        } while (tentativas != tentativasMax);
        return count;
    }

    private boolean isDisponivel(Drone d) {
        return d.getCount() == 0;
    }

    private Drone melhorCandidato(Transporte transporte, Drone drone, Drone candidatoAtual) {
        if (!isCompativel(transporte, drone)) {
            return candidatoAtual; // Ignora drones incompatíveis
        }

        if (transporte instanceof TransportePessoal && drone instanceof DronePessoal) {
            int qtdNecessaria = ((TransportePessoal) transporte).getQtdPessoas();
            int capacidadeDrone = ((DronePessoal) drone).getQtdMaxPessoas();

            if (capacidadeDrone >= qtdNecessaria) {
                if (candidatoAtual == null || capacidadeDrone < ((DronePessoal) candidatoAtual).getQtdMaxPessoas()) {
                    return drone; // Escolhe o drone com capacidade mais próxima
                }
            }
        } else if (transporte instanceof TransporteCargaInanimada && drone instanceof DroneCargaInanimada) {
            double pesoNecessario = transporte.getPeso();
            double capacidadeDrone = ((DroneCargaInanimada) drone).getPesoMaximo();

            if (capacidadeDrone >= pesoNecessario) {
                if (candidatoAtual == null || capacidadeDrone < ((DroneCargaInanimada) candidatoAtual).getPesoMaximo()) {
                    return drone; // Escolhe o drone com capacidade mais próxima
                }
            }
        } else if (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva) {
            double pesoNecessario = transporte.getPeso();
            double capacidadeDrone = ((DroneCargaViva) drone).getPesoMaximo();

            if (capacidadeDrone >= pesoNecessario) {
                if (candidatoAtual == null || capacidadeDrone < ((DroneCargaViva) candidatoAtual).getPesoMaximo()) {
                    return drone; // Escolhe o drone com capacidade mais próxima
                }
            }
        }

        return candidatoAtual;
    }

    // Novo método para validar compatibilidade
    private boolean isCompativel(Transporte transporte, Drone drone) {
        return (transporte instanceof TransportePessoal && drone instanceof DronePessoal) ||
                (transporte instanceof TransporteCargaInanimada && drone instanceof DroneCargaInanimada) ||
                (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva);
    }


    public String mostrarTodosTransportes(){
        if(transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty()) {
            return null;
        }

        StringBuilder texto = new StringBuilder();
        for (Transporte t : transportesAlocados) {
            texto.append(t + "\n");
        }
        for(Transporte t : filaTransporte.getFilaTransporte()) {
            texto.append(t + "\n");
        }
        return texto.toString();
    }

    public String mostraRelatorioGeralDroneETransporte(){
        StringBuilder str = new StringBuilder();
        if(!(transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty())) {
            str.append("Transportes:\n");

            for (Transporte t : transportesAlocados) {
                str.append(t + "\n");
            }
            for(Transporte t : filaTransporte.getFilaTransporte()) {
                str.append(t +  "\n");
            }
        }
        if(!listaD.getListaDrones().isEmpty()) {
            str.append("Drones:\n");
            for(Drone d : listaD.getListaDrones()) {
                str.append(d + "\n");
            }
        }
        return str.toString();
    }

    public String alteraSituacao(int codigo, int opcao) {
        if(transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty()) {
            return "Lista está vazia";
        }
        for(Transporte t : transportesAlocados) {
            if (t.getNumero() == codigo) {
                if(t.getSituacao() == Transporte.Estado.CANCELADO || t.getSituacao() == Transporte.Estado.TERMINADO) {
                    return "Situacao nao pode ser modificada.";
                }
                switch (opcao) {
                    case 1: //cancelado
                        t.setSituacao(Transporte.Estado.CANCELADO);
                        return "Situacao modificada para cancelado com sucesso \n";
                    case 2: //terminado
                        t.setSituacao(Transporte.Estado.TERMINADO);
                        return "Situacao modificada pra terminado com sucesso \n";
                    default:
                        return "Algo deu errado";
                }
            }else{
                return "Esse codigo nao existe";
            }
        }
            for(Transporte transporte : filaTransporte.getFilaTransporte()) {
                if (transporte.getNumero() == codigo) {
                    if(transporte.getSituacao() == Transporte.Estado.CANCELADO || transporte.getSituacao() == Transporte.Estado.TERMINADO) {
                        return "Situacao nao pode ser modificada.";
                    }
                    switch (opcao) {
                        case 1: //cancelado
                            transporte.setSituacao(Transporte.Estado.CANCELADO);
                            return "Situacao modificada para cancelado com sucesso \n";

                        case 2: //terminado
                            transporte.setSituacao(Transporte.Estado.TERMINADO);
                            return "Situacao modificada pra terminado com sucesso \n";

                        default:
                            return "Algo deu errado";
                    }
                }else{
                    return "Esse codigo nao existe"; //nao sei se precisa desse else ou o default ja faz isso
                }
            }

            return "";
    }

}