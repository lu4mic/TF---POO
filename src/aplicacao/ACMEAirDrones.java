package aplicacao;

import aplicacao.janelas.*;
import dados.drone.*;
import dados.transporte.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public boolean cadastraDrone(int codigo, double autonomia, double custoFixo, double pesoMax, boolean protecao,
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

    public boolean cadastraDrone(int codigo, double custoFixo, double autonomia, int qtdMaxPessoas) {
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

    public boolean cadastraTransporte(int numero, String nome, String descricao, double peso, double latO, double latD, double longO, double longD,
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
            boolean perigosa =  ((TransporteCargaInanimada) transporte).isCargaPerigosa();
            double capacidadeDrone = ((DroneCargaInanimada) drone).getPesoMaximo();

            if (capacidadeDrone >= pesoNecessario && perigosa == ((DroneCargaInanimada) drone).getProtecao()) {
                if (candidatoAtual == null || capacidadeDrone < ((DroneCargaInanimada) candidatoAtual).getPesoMaximo()) {
                    return drone; // Escolhe o drone com capacidade mais próxima
                }
            }
        } else if (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva) {
            double pesoNecessario = transporte.getPeso();
            double capacidadeDrone = ((DroneCargaViva) drone).getPesoMaximo();
            boolean climatizado = ((DroneCargaViva) drone).getClimatizado();
            boolean tempMax0 = ((TransporteCargaViva) transporte).getTemperaturaMaxima() != 0;
            boolean tempMin0 = ((TransporteCargaViva) transporte).getTemperaturaMinima() != 0;

            if (capacidadeDrone >= pesoNecessario && climatizado == tempMax0 && climatizado == tempMin0) {
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

        for (Transporte t : transportesAlocados) {
            transportesArray.add(criarJSONTransporte(t));
        }
        for (Transporte t : filaTransporte.getFilaTransporte()) {
            transportesArray.add(criarJSONTransporte(t));
        }
        if (!transportesArray.isEmpty()) {
            salvarArquivo(nomeArquivo + "-TRANSPORTES.json", transportesArray);
        }
    }

    private JSONObject criarJSONDrone(Drone d) {
        JSONObject drone = new JSONObject();
        drone.put("codigo", d.getCodigo());
        drone.put("tipo", getTipoDrone(d));
        drone.put("custo fixo", d.getCustoFixo());
        drone.put("autonomia", d.getAutonomia());

        if (d instanceof DronePessoal) {
            drone.put("quantidade maxima de pessoas", ((DronePessoal) d).getQtdMaxPessoas());
        } else if (d instanceof DroneCargaInanimada) {
            drone.put("peso maximo", ((DroneCargaInanimada) d).getPesoMaximo());
            drone.put("protecao", ((DroneCargaInanimada) d).getProtecao());
        } else if (d instanceof DroneCargaViva) {
            drone.put("peso maximo", ((DroneCargaViva) d).getPesoMaximo());
            drone.put("climatizado", ((DroneCargaViva) d).getClimatizado());
        }

        return drone;
    }

    private JSONObject criarJSONTransporte(Transporte t) {
        JSONObject transporte = new JSONObject();
        transporte.put("tipo", getTipoTransporte(t));
        transporte.put("numero do transporte", t.getNumero());
        transporte.put("situacao", t.getSituacao().toString());
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

    private int getTipoDrone(Drone d) {
        if (d instanceof DronePessoal) {
            return 1;
        }
        if (d instanceof DroneCargaInanimada) {
            return 2;
        }
        if (d instanceof DroneCargaViva) {
            return 3;
        }
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

    public boolean carregarArquivoSimulacao(String nomeArquivoBase) {
        String nomeArquivoDrones = nomeArquivoBase + "-DRONES.csv";
        String nomeArquivoTransportes = nomeArquivoBase + "-TRANSPORTES.csv";

        boolean dronesCarregados = carregarDronesCSV(nomeArquivoDrones);
        boolean transportesCarregados = carregarTransportesCSV(nomeArquivoTransportes);

        return dronesCarregados && transportesCarregados;
    }


    private boolean carregarDronesCSV(String nomeArquivo) {
        boolean sucesso = false;
        if (!carregarTransportesCSV(nomeArquivo)) {
            return false;
        }

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


                if (tipo == 1) { //Drone pessoal
                    cadastraDrone(codigo, custoFixo, autonomia, (int) qtdMaxPessoasPesoMaximo);
                    sucesso = true;
                } else if (tipo == 2) { //Drone carga inanimada
                    boolean protecao = Boolean.parseBoolean(dados[5].trim());
                    cadastraDrone(codigo, custoFixo, autonomia, qtdMaxPessoasPesoMaximo, protecao, false, false);
                    sucesso = true;
                } else if (tipo == 3) { //Drone carga viva
                    boolean climatizado = Boolean.parseBoolean(dados[5].trim());
                    cadastraDrone(codigo, custoFixo, autonomia, qtdMaxPessoasPesoMaximo, false, climatizado, false);
                    sucesso = true;
                }

            }
        } catch (FileNotFoundException e) {

        } catch (IOException | NumberFormatException e) {

        }

        return sucesso;
    }


    private boolean carregarTransportesCSV(String nomeArquivo) {
        boolean sucesso = false;

        if (!carregarDronesCSV(nomeArquivo)) {
            return false;
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

                Transporte t = null;

                if (tipo.equals("1")) {
                    int qtdPessoas = Integer.parseInt(qtdPessoasPerigosaTempMin);
                    t = new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino, qtdPessoas);
                } else if (tipo.equals("2")) {
                    boolean cargaPerigosa = Boolean.parseBoolean(qtdPessoasPerigosaTempMin);
                    t = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino, cargaPerigosa);
                } else if (tipo.equals("3")) {
                    double tempMin = Double.parseDouble(qtdPessoasPerigosaTempMin);
                    double tempMax = Double.parseDouble(dados[10].trim());
                    t = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino, tempMax, tempMin);
                }

                if (t != null) {
                    filaTransporte.addTransporte(t);
                    sucesso = true;
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException | NumberFormatException e) {

        }
        return sucesso;
    }

    public boolean carregarJSON(String nomeArquivoBase) {
        String nomeArquivoDrones = nomeArquivoBase + "-DRONES.json";
        String nomeArquivoTransportes = nomeArquivoBase + "-TRANSPORTES.json";

        boolean dronesCarregados = carregarDronesJSON(nomeArquivoDrones);
        boolean transportesCarregados = carregarTransportesJSON(nomeArquivoTransportes);

        return dronesCarregados && transportesCarregados;
    }

    public boolean carregarDronesJSON(String nomeArquivo) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(nomeArquivo)) {
            Object obj = parser.parse(reader);
            JSONArray dronesLista = (JSONArray) obj;

            dronesLista.forEach(drone -> parserDrones((JSONObject) drone));
            return true;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void parserDrones(JSONObject droneJSON) {
        long tipo = (long) droneJSON.get("tipo");
        int codigo = ((Long) droneJSON.get("codigo")).intValue();
        double custoFixo = (double) droneJSON.get("custo fixo");
        double autonomia = (double) droneJSON.get("autonomia");

        if (tipo == 1) {
            long qtdMaxPessoas = (long) droneJSON.get("quantidade maxima de pessoas");
            cadastraDrone(codigo, custoFixo, autonomia, (int) qtdMaxPessoas);
        } else if (tipo == 2) {
            double peso = (double) droneJSON.get("peso maximo");
            boolean protecao = (boolean) droneJSON.get("protecao");
            cadastraDrone(codigo, custoFixo, autonomia, peso, protecao, false, false);
        } else if (tipo == 3) {
            double peso = (double) droneJSON.get("peso maximo");
            boolean climatizado = (boolean) droneJSON.get("climatizado");
            cadastraDrone(codigo, custoFixo, autonomia, peso, false, climatizado, true);
        }
    }

    public void parserTransportes(JSONObject transporteJSON) {
        int tipo = ((Long) transporteJSON.get("tipo")).intValue();
        int numero = ((Long) transporteJSON.get("numero do transporte")).intValue();
        String nomeCliente = (String) transporteJSON.get("nome do cliente");
        String descricao = (String) transporteJSON.get("descricao");
        double peso = (double) transporteJSON.get("peso");
        double latOrigem = (double) transporteJSON.get("latitude origem");
        double latDestino = (double) transporteJSON.get("latitude destino");
        double lonOrigem = (double) transporteJSON.get("longitude origem");
        double lonDestino = (double) transporteJSON.get("longitude destino");
        String situacao = (String) transporteJSON.get("situacao");

        Transporte t = null;

        //Buscar transporte alocado
        for (Transporte tr : transportesAlocados) {
            if (tr.getNumero() == numero) {
                t = tr;
                break;
            }
        }

        if (t == null) {
            //criar novo transporte se não existir já
            if (tipo == 1) {
                int qtdPessoas = ((Long) transporteJSON.get("quantidade pessoas")).intValue();
                t = new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino,qtdPessoas);
            } else if (tipo == 2) {
                boolean cargaPerigosa = (boolean) transporteJSON.get("carga perigosa");
                t = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino,cargaPerigosa);
            } else if (tipo == 3) {
                double tempMax = (double) transporteJSON.get("temperatura maxima");
                double tempMin = (double) transporteJSON.get("temperatura minima");
                t = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, lonOrigem, latDestino, lonDestino,tempMax,tempMin);
            }
        }

        if (situacao.equals("PENDENTE")) {
            t.setSituacao(Transporte.Estado.PENDENTE);
            filaTransporte.addTransporte(t);
            return;
        } else if (situacao.equals("CANCELADO")) {
            t.setSituacao(Transporte.Estado.CANCELADO);
        } else if (situacao.equals("TERMINADO")) {
            t.setSituacao(Transporte.Estado.TERMINADO);
        } else if (situacao.equals("ALOCADO")) {
            t.setSituacao(Transporte.Estado.ALOCADO);
        }

        //ve se o transporte tem um drone
        if (transporteJSON.containsKey("drone")) {
            JSONObject droneJSON = (JSONObject) transporteJSON.get("drone");
            int codigoDrone = ((Long) droneJSON.get("codigo")).intValue();

            Drone drone = listaD.getListaDrones().stream()
                    .filter(d -> d.getCodigo() == codigoDrone)
                    .findFirst()
                    .orElse(null);

            if (drone != null) {
                t.setDrone(drone);
            }
        }

        if (!transportesAlocados.contains(t)) {
            transportesAlocados.add(t);
        }
    }


    public boolean carregarTransportesJSON(String nomeArquivo) {
        //te amo https://youtu.be/6fCNWNc8rcI?si=kyIQaltcrKkzNYzt
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(nomeArquivo)) {
            BufferedReader br = new BufferedReader(reader);
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }

            Object obj = parser.parse(new StringReader(jsonContent.toString()));
            JSONArray transportesLista = (JSONArray) obj;

            transportesLista.forEach(transporte -> parserTransportes((JSONObject) transporte));
            return true;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


}
