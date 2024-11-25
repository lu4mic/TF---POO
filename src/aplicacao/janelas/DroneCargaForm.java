package aplicacao.janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DroneCargaForm extends JPanel{
    private JPanel painelDroneCarga;
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

    public DroneCargaForm(ACMEAirDrones acmeAirDrones) {
        add(painelDroneCarga);
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

                    if (!acmeAirDrones.cadastraDrone(codigo, autonomia, custoFixo, pesoMax, protecao, climatizacao, cargaViva)) {
                        textoErro1.setText("Esse codigo já existe!");
                    } else {
                        textoErro1.setText("Drone cadastrado com sucesso!");
                    }
                } catch (NumberFormatException ex) {
                    textoErro1.setText("Os campos devem ser preenchidos com números. Utilize . para as casas decimais");
                }
            }
        });
        cargaVivaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cargaVivaCheckBox.isSelected()) {
                    climatizacaoCheckBox.setVisible(false);
                    protecaoCheckBox.setVisible(true);
                    climatizacaoCheckBox.setSelected(false);
                } else {
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
                textoErro1.setText("");
            }
        });
        terminarButton.addActionListener(new ActionListener() {  //voltar pro painel principal
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeAirDrones.mudaPainel(1);
                climatizacaoCheckBox.setSelected(false);
                protecaoCheckBox.setSelected(false);
                cargaVivaCheckBox.setSelected(false);
                textoCustoFixo.setText("");
                textoPesoMax.setText("");
                textoCodigo.setText("");
                textoAutonomia.setText("");
                textoErro1.setText("");
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