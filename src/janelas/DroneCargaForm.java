package janelas;

import aplicacao.ACMEAirDrones;
import dados.drone.DronesLista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DroneCargaForm extends JFrame {
    private final ACMEAirDrones acmeAirDrones = new ACMEAirDrones();
    private DronesLista listaDrones;
    private JPanel painelPrincipal;
    private JCheckBox cargaVivaCheckBox;
    private JFormattedTextField textoAutonomia;
    private JFormattedTextField textoCustoFixo;
    private JFormattedTextField textoPesoMax;
    private JCheckBox protecaoCheckBox;
    private JCheckBox climatizacaoCheckBox;
    private JButton botaoEnviar;
    private JFormattedTextField textoCodigo;
    private JButton limparButton;
    private JButton terminarButton;
    private JScrollPane textoErroCaixa;
    private JButton buttonMostrar;
    private JTextArea textoErro1;

    public DroneCargaForm() {
        setTitle("Carga Form");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setVisible(true);
        climatizacaoCheckBox.setVisible(false);


        botaoEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoErro1.setText("");

                if (textoAutonomia.getText().isBlank() || textoCustoFixo.getText().isBlank() ||
                        textoPesoMax.getText().isBlank() || textoCodigo.getText().isBlank()) {
                    textoErro1.setText("Todos os campos devem ser preenchidos!");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(textoCodigo.getText());
                    double autonomia = Double.parseDouble(textoAutonomia.getText());
                    double custoFixo = Double.parseDouble(textoCustoFixo.getText());
                    double pesoMax = Double.parseDouble(textoPesoMax.getText());
                    boolean protecao = protecaoCheckBox.isSelected();
                    boolean climatizacao = climatizacaoCheckBox.isSelected();
                    boolean cargaViva = cargaVivaCheckBox.isSelected();

                    if (cargaViva && protecao) {
                        textoErro1.setText("A carga viva não deve ter proteção");
                    } else if (!cargaViva && climatizacao) {
                        textoErro1.setText("A carga inanimada não deve ter climatização");
                    } else {
                        if (!acmeAirDrones.CadastraDrone(codigo, autonomia, custoFixo, pesoMax, protecao, climatizacao, cargaViva)) {
                            textoErro1.setText("Esse código já existe!");
                        } else {
                            textoErro1.setText("Drone cadastrado com sucesso!");
                        }
                    }
                } catch (NumberFormatException ex) {
                    textoErro1.setText("Os campos devem ser preenchidos com números. Utilize . para as casas decimais");
                }
            }
        });
        cargaVivaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!cargaVivaCheckBox.isSelected()) {
                    climatizacaoCheckBox.setVisible(false);
                    protecaoCheckBox.setVisible(true);
                    climatizacaoCheckBox.setSelected(false);
                }
                else{
                    climatizacaoCheckBox.setVisible(true);
                    protecaoCheckBox.setVisible(false);
                    protecaoCheckBox.setSelected(false);
                }
            }
        });
        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                climatizacaoCheckBox.setSelected(false);
                protecaoCheckBox.setSelected(false);
                cargaVivaCheckBox.setSelected(false);
                textoCustoFixo.setText("");
                textoPesoMax.setText("");
                textoCodigo.setText("");
                textoAutonomia.setText("");
            }
        });
        terminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoErro1.setText(acmeAirDrones.mostrarDronesCarga());
            }
        });
    }
}