package aplicacao;

import aplicacao.janelas.*;
import dados.drone.*;
import dados.transporte.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class ACMEAirDrones extends JFrame {
    private DronesLista listaD = new DronesLista();
    private TransporteFila filaTransporte = new TransporteFila();
    private PainelPrincipal painelPrincipal;
    private DroneCargaForm droneCargaForm;
    private DronePessoalForm dronePessoalForm;
    private TransporteForm transporteForm;
    private MostrarTransportes mostrarTransportes;
    private ArrayList<Transporte> transportesAlocados;
    private AlteraSituacao alteraSituacao;
    private MostrarRelatorioGeral mostrarRelatorioGeral;

    public ACMEAirDrones() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transportesAlocados = new ArrayList<>();
        alteraSituacao = new AlteraSituacao(this);
        droneCargaForm = new DroneCargaForm(this);
        dronePessoalForm = new DronePessoalForm(this);
        painelPrincipal = new PainelPrincipal(this);
        transporteForm = new TransporteForm(this);
        mostrarTransportes = new MostrarTransportes(this);
        mostrarRelatorioGeral = new MostrarRelatorioGeral(this);
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
            case 6:
                setTitle("Alterar Transportes");
                this.setContentPane(alteraSituacao);
                this.setSize(920, 600);
                this.pack();
                break;
            case 7:
                setTitle("Mostrar Relatorio Geral");
                this.setContentPane(mostrarRelatorioGeral);
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
        } else if (qtdPessoas != null) {
            //CargaPessoal
            Transporte t = new TransportePessoal(numero, nome, descricao, peso, latO, longO, latD, longD, qtdPessoas);
            filaTransporte.addTransporte(t);
            return true;
        }
        return false;
    }

    public int processarTransportesPendentes() {
        if (filaTransporte.getFilaTransporte().isEmpty()) {
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
            if (first == null) {
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
            } else {
                first.setDrone(candidatoFinal);                          //seta o drone no transporte
                first.setSituacao(Transporte.Estado.ALOCADO);           //seta o transporte como alocado
                filaTransporte.getFilaTransporte().remove();                //remove da fila de transportes
                transportesAlocados.add(first);                         // uma nova lista que só tem os alocados, finalizados ou cancelados!
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


    public String mostrarTodosTransportes() {
        if (transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty()) {
            return null;
        }

        StringBuilder texto = new StringBuilder();
        for (Transporte t : transportesAlocados) {
            texto.append(t + "\n");
        }
        for (Transporte t : filaTransporte.getFilaTransporte()) {
            texto.append(t + "\n");
        }
        return texto.toString();
    }

    public String mostraRelatorioGeralDroneETransporte() {
        if (transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty() && listaD.getListaDrones().isEmpty()) {
            return null;
        }

        StringBuilder str = new StringBuilder();
        if (!(transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty())) {
            str.append("Transportes:\n");

            for (Transporte t : transportesAlocados) {
                str.append(t + "\n");
            }
            for (Transporte t : filaTransporte.getFilaTransporte()) {
                str.append(t + "\n");
            }
        }
        if (!listaD.getListaDrones().isEmpty()) {
            str.append("Drones:\n");
            for (Drone d : listaD.getListaDrones()) {
                str.append(d + "\n");
            }
        }
        return str.toString();
    }

    public String alteraSituacao(int codigo, int opcao) {
        if (transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty()) {
            return null;
        }

        for (Transporte t : transportesAlocados) {
            if (t.getNumero() == codigo) {
                if (t.getSituacao() == Transporte.Estado.CANCELADO || t.getSituacao() == Transporte.Estado.TERMINADO) {
                    return "Situacao nao pode ser modificada.";
                }
                switch (opcao) {
                    case 1: //cancelado
                        t.setSituacao(Transporte.Estado.CANCELADO);
                        return "Situacao modificada para cancelado com sucesso \n";
                    case 2: //terminado
                        t.setSituacao(Transporte.Estado.TERMINADO);
                        return "Situacao modificada pra terminado com sucesso \n";
                }
            }
        }
        for (Transporte transporte : filaTransporte.getFilaTransporte()) {
            if (transporte.getNumero() == codigo) {
                if (transporte.getSituacao() == Transporte.Estado.CANCELADO || transporte.getSituacao() == Transporte.Estado.TERMINADO) {
                    return "Situacao nao pode ser modificada.";
                }
                switch (opcao) {
                    case 1: //cancelado
                        transporte.setSituacao(Transporte.Estado.CANCELADO);
                        filaTransporte.getFilaTransporte().remove(transporte);
                        transportesAlocados.add(transporte);
                        return "Situacao modificada para cancelado com sucesso ";

                    case 2: //terminado
                        return "Voce nao pode terminar um tranporte Pendente";

                }
            } else {
                return "Esse codigo nao existe";
            }
        }

        return "";
    }

    public boolean listasVazias() {
        if ((transportesAlocados.isEmpty() && filaTransporte.getFilaTransporte().isEmpty()) && listaD.getListaDrones().isEmpty()) {
            return true;
        }
        return false;
    }

    public void salvarEmJSON(String nomeArquivo) {
        salvarDronesJSON(nomeArquivo);
        salvarTransportesJSON(nomeArquivo);
    }

    private void salvarDronesJSON(String nomeArquivo) {
        if (listaD.getListaDrones().isEmpty()) {
            return;
        }

        JSONArray dronesArray = new JSONArray();

        for (Drone d : listaD.getListaDrones()) {
            JSONObject droneJSON = criarJSONDrone(d);
            dronesArray.add(droneJSON);
        }

        salvarArquivo(nomeArquivo + "-DRONES.json", dronesArray);
    }

    private void salvarTransportesJSON(String nomeArquivo) {
        JSONArray transportesArray = new JSONArray();

        if (!transportesAlocados.isEmpty()) {
            for (Transporte t : transportesAlocados) {
                transportesArray.add(criarJSONTransporte(t));
            }
        }
        if (!listaD.getListaDrones().isEmpty()) {
            for (Transporte t : filaTransporte.getFilaTransporte()) {
                transportesArray.add(criarJSONTransporte(t));
            }
        }

        salvarArquivo(nomeArquivo + "-TRANSPORTES.json", transportesArray);
    }

    private JSONObject criarJSONDrone(Drone d) {
        JSONObject drone = new JSONObject();
        drone.put("codigo", d.getCodigo());
        drone.put("custo fixo", d.getCustoFixo());
        drone.put("autonomia", d.getAutonomia());

        if (d instanceof DronePessoal) {
            drone.put("tipo", 1);
            drone.put("quantidade maxima de pessoas", ((DronePessoal) d).getQtdMaxPessoas());
        } else if (d instanceof DroneCargaInanimada) {
            drone.put("tipo", 2);
            drone.put("peso maximo", ((DroneCargaInanimada) d).getPesoMaximo());
            drone.put("protecao", ((DroneCargaInanimada) d).getProtecao());
        } else if (d instanceof DroneCargaViva) {
            drone.put("tipo", 3);
            drone.put("peso maximo", ((DroneCargaViva) d).getPesoMaximo());
            drone.put("climatizado", ((DroneCargaViva) d).getClimatizado());
        }

        return drone;
    }

    private JSONObject criarJSONTransporte(Transporte t) {
        JSONObject transporte = new JSONObject();
        transporte.put("tipo", getTipoTransporte(t));
        transporte.put("numero do transporte", t.getNumero());
        transporte.put("situacao", t.getSituacao());
        transporte.put("nome do cliente", t.getNomeCliente());
        transporte.put("descricao", t.getDescricao());
        transporte.put("distancia", t.getDistancia());
        transporte.put("peso", t.getPeso());
        transporte.put("longitude origem", t.getLongitudeOrigem());
        transporte.put("latitude origem", t.getLatitudeOrigem());
        transporte.put("longitude destino", t.getLongitudeDestino());
        transporte.put("latitude destino", t.getLatitudeDestino());
        if (t.getDrone() != null) {
            transporte.put("drone", criarJSONDrone(t.getDrone()));
            transporte.put("custo", t.calculaCusto());
        }

        if (t instanceof TransportePessoal) {
            transporte.put("quantidade pessoas", ((TransportePessoal) t).getQtdPessoas());
        } else if (t instanceof TransporteCargaInanimada) {
            transporte.put("carga perigosa", ((TransporteCargaInanimada) t).isCargaPerigosa());
        } else if (t instanceof TransporteCargaViva) {
            transporte.put("temperatura maxima", ((TransporteCargaViva) t).getTemperaturaMaxima());
            transporte.put("temperatura minima", ((TransporteCargaViva) t).getTemperaturaMinima());
        }


        return transporte;
    }

    private int getTipoTransporte(Transporte t) {
        if (t instanceof TransportePessoal) return 1;
        if (t instanceof TransporteCargaInanimada) return 2;
        if (t instanceof TransporteCargaViva) return 3;
        return 0;
    }

    private void salvarArquivo(String caminho, JSONArray dados) {
        try (FileWriter fw = new FileWriter(caminho)) {
            fw.write(dados.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void carregarArquivo(String nomeArquivoBase) {
        String nomeArquivoDrones = nomeArquivoBase + "-DRONES.csv";
        String nomeArquivoTransportes = nomeArquivoBase + "-TRANSPORTES.csv";

        File arquivoDrones = new File(nomeArquivoDrones);
        File arquivoTransportes = new File(nomeArquivoTransportes);

        if (arquivoDrones.exists()) {
            carregarDronesCSV(nomeArquivoDrones);
            JOptionPane.showMessageDialog(null, "Arquivo de drones carregado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else if (arquivoTransportes.exists()) {
            carregarTransportesCSV(nomeArquivoTransportes);
            JOptionPane.showMessageDialog(null, "Arquivo de transportes carregado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo de drones ou transportes encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void carregarDronesCSV(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;

            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                int tipo = Integer.parseInt(dados[0].trim());
                int codigo = Integer.parseInt(dados[1].trim());
                double custoFixo = Double.parseDouble(dados[2].trim());
                double autonomia = Double.parseDouble(dados[3].trim());
                double qtdMaxPessoasPesoMaximo = Double.parseDouble(dados[4].trim());
                boolean protecaoClimatizado = Boolean.parseBoolean(dados[5].trim());

                Drone drone = null;
                if (tipo == 1) {
                    drone = new DronePessoal(codigo, custoFixo, autonomia, (int) qtdMaxPessoasPesoMaximo);
                } else if (tipo == 2) {
                    drone = new DroneCargaInanimada(codigo, custoFixo, autonomia, qtdMaxPessoasPesoMaximo, protecaoClimatizado);
                } else if (tipo == 3) {
                    drone = new DroneCargaViva(codigo, custoFixo, autonomia, qtdMaxPessoasPesoMaximo, protecaoClimatizado);
                }

                if (drone != null) {
                    listaD.getListaDrones().add(drone);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar drones do arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void carregarTransportesCSV(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            JOptionPane.showMessageDialog(null, "O arquivo " + nomeArquivo + " não foi encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;

            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                String tipo = dados[0].trim();
                int numero = Integer.parseInt(dados[1].trim());
                String nomeCliente = dados[2].trim();
                String descricao = dados[3].trim();
                double peso = Double.parseDouble(dados[4].trim());
                double latOrigem = Double.parseDouble(dados[5].trim());
                double lonOrigem = Double.parseDouble(dados[6].trim());
                double latDestino = Double.parseDouble(dados[7].trim());
                double lonDestino = Double.parseDouble(dados[8].trim());
                String qtdPessoasPerigosaTempMin = dados[9].trim();
                double tempMax = Double.parseDouble(dados[10].trim());


                Transporte t = null;
                if (tipo.equals("1")) { // transporte Pessoal
                    try {
                        int qtdPessoas = Integer.parseInt(qtdPessoasPerigosaTempMin);
                        t = new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino, qtdPessoas);
                    }catch (NumberFormatException e) {
                        System.out.println("Erro ao converter: " + qtdPessoasPerigosaTempMin);
                        continue;
                    }
                    } else if (tipo.equals("2")) { // transporte de carga Inanimada
                    try {
                        boolean cargaPerigosa = Boolean.parseBoolean(qtdPessoasPerigosaTempMin);  // Se a carga é perigosa
                        t = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino, cargaPerigosa);
                    } catch (Exception e) {
                        System.out.println("Erro ao converter : " + qtdPessoasPerigosaTempMin);
                        continue;
                    }
                } else if (tipo.equals("3")) { // transporte de carga viva
                    try {
                        double tempMin = Double.parseDouble(qtdPessoasPerigosaTempMin);  // Temperatura mínima
                        t = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino, tempMax, tempMin);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter " + qtdPessoasPerigosaTempMin);
                        continue;
                    }
                }


                if (t != null) {
                    filaTransporte.getFilaTransporte().add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar transportes do arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
