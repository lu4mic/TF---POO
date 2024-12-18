package aplicacao.janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PainelPrincipal extends JPanel {
    private JPanel painelMenu;
    private JButton botaoCadastrarDrone;
    private JButton botaoCadastrarTransporte;
    private JButton botaoProcessarTransportes;
    private JButton botaoMostrarRelatorio;
    private JButton botaoFinalizar;
    private JButton botaoCarregar;
    private JButton botaoSalvar;
    private JButton botaoSimulacao;
    private JButton botaoSituacaoT;
    private JButton botaoMostrarT;

    public PainelPrincipal(ACMEAirDrones acmeAirDrones) {
        this.add(painelMenu);

        botaoCadastrarDrone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Drone de Carga", "Drone Pessoal", "Voltar"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Escolha o tipo de drone que deseja cadastrar:",
                        "Cadastrar Drone",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == 0) { // Drone de Carga
                    acmeAirDrones.mudaPainel(2);
                } else if (choice == 1) { // Drone Pessoal
                    acmeAirDrones.mudaPainel(3);
                }
            }
        });

        botaoFinalizar.addActionListener(new ActionListener() {  //terminar Programa,

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        botaoCadastrarTransporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeAirDrones.mudaPainel(4);
            }
        });
        botaoProcessarTransportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantidade = acmeAirDrones.processarTransportesPendentes();
                String mensagem = "";
                if (quantidade == -1) {
                    mensagem = "Nenhum transporte esta pendente!";
                } else if (quantidade == -2) {
                    mensagem = "Nenhum drone foi cadastrado";
                } else if (quantidade == 0) {
                    mensagem = "Nenhum transporte foi procesado";
                } else mensagem = "Foram processados " + quantidade + " transportes";

                JOptionPane.showMessageDialog(null, mensagem, "Processar Transportes", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botaoMostrarT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (acmeAirDrones.mostrarTodosTransportes() == null) {
                    JOptionPane.showMessageDialog(null, "Nenhum transporte cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    acmeAirDrones.mudaPainel(5);
                }

            }
        });

        botaoSituacaoT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (acmeAirDrones.alteraSituacao(-1, -1) == null) {
                    JOptionPane.showMessageDialog(null, "Nenhum transporte cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    acmeAirDrones.mudaPainel(6);
                }
            }
        });

        botaoMostrarRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (acmeAirDrones.mostraRelatorioGeralDroneETransporte() == null) {
                    JOptionPane.showMessageDialog(null, "Nenhum transporte ou drone cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    acmeAirDrones.mudaPainel(7);
                }
            }
        });
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeArquivo = JOptionPane.showInputDialog(null,
                        "Digite o nome do arquivo para salvar:",
                        "Salvar como",
                        JOptionPane.QUESTION_MESSAGE);

                if (nomeArquivo != null && !nomeArquivo.trim().isEmpty()) {
                    acmeAirDrones.salvarEmJSON(nomeArquivo.trim().toUpperCase());
                    JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Nome do arquivo invalido ou cancelado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoSimulacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeArquivoBase = JOptionPane.showInputDialog(null,
                        "Digite o nome do arquivo base:",
                        "Carregar Arquivo",
                        JOptionPane.QUESTION_MESSAGE);

                if (nomeArquivoBase != null && !nomeArquivoBase.trim().isEmpty()) {

                    if (acmeAirDrones.carregarArquivoSimulacao(nomeArquivoBase.trim())) {
                        JOptionPane.showMessageDialog(null, "Arquivos carregados com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        acmeAirDrones.mudaPainel(7);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nome nao encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nome do arquivo invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeArquivoBase = JOptionPane.showInputDialog(null,
                        "Digite o nome do arquivo base (sem sufixo):",
                        "Carregar Arquivo",
                        JOptionPane.QUESTION_MESSAGE);

                if (nomeArquivoBase != null && !nomeArquivoBase.trim().isEmpty()) {

                    if (acmeAirDrones.carregarJSON(nomeArquivoBase.trim())) {
                        JOptionPane.showMessageDialog(null, "Arquivos carregados com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nome nao encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nome do arquivo invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
