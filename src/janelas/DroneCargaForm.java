package janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DroneCargaForm extends JFrame {
    private final ACMEAirDrones acmeAirDrones = new ACMEAirDrones();
    private JPanel painelPrincipal;
    private JCheckBox cargaVivaCheckBox;
    private JFormattedTextField textoAutonomia;
    private JFormattedTextField textoCustoFixo;
    private JFormattedTextField textoPesoMax;
    private JCheckBox protecaoCheckBox;
    private JCheckBox climatizacaoCheckBox;
    private JButton botaoEnviar;
    private JLabel textoErro;
    private JFormattedTextField textoCodigo;

    public DroneCargaForm() {
        setTitle("Carga Form");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setVisible(true);


        botaoEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoErro.setText("");

                if (textoAutonomia.getText().isBlank() || textoCustoFixo.getText().isBlank() ||
                        textoPesoMax.getText().isBlank() || textoCodigo.getText().isBlank()) {
                    textoErro.setText("Todos os campos devem ser preenchidos!");
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
                        textoErro.setText("A carga viva não deve ter proteção");
                    } else if (!cargaViva && climatizacao) {
                        textoErro.setText("A carga inanimada não deve ter climatização");
                    } else {
                        if (!acmeAirDrones.CadastraDrone(codigo, autonomia, custoFixo, pesoMax, protecao, climatizacao, cargaViva)) {
                            textoErro.setText("Esse código já existe!");
                        } else {
                            textoErro.setText("Drone cadastrado com sucesso!");
                        }
                    }
                } catch (NumberFormatException ex) {
                    textoErro.setText("Os campos devem ser preenchidos com números. Utilize . para as casas decimais");
                }
            }
        });
    }
}