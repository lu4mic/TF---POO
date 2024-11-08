package janelas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DroneCargaForm extends JFrame {
    private JPanel painelPrincipal;
    private JCheckBox cargaVivaCheckBox;
    private JFormattedTextField textoAutonomia;
    private JFormattedTextField textoCustoFixo;
    private JFormattedTextField textoPesoMax;
    private JCheckBox protecaoCheckBox;
    private JCheckBox climatizacaoCheckBox;
    private JButton botaoEnviar;
    private JLabel textoErro;

    public DroneCargaForm() {
        setTitle("Carga Form");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setVisible(true);
        botaoEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cargaVivaCheckBox.isSelected() && protecaoCheckBox.isSelected()) {
                        textoErro.setText("A carga viva nao deve ter proteção");
                    }
                    if (!cargaVivaCheckBox.isSelected() && climatizacaoCheckBox.isSelected()) {
                        textoErro.setText("A carga inanimada nao deve ter climatizacao");
                    }
                    if (textoAutonomia.getText().isBlank() || textoCustoFixo.getText().isBlank() || textoPesoMax.getText().isBlank()) {
                        textoErro.setText("Todos os campos devem ser preenchidos!");
                    } else {
                        textoErro.setText("");
                        double autonomia = Double.parseDouble(textoAutonomia.getText());
                        double custoFixo = Double.parseDouble(textoCustoFixo.getText());
                        double pesoMax = Double.parseDouble(textoPesoMax.getText());
                        boolean protecao = protecaoCheckBox.isSelected();
                        boolean climatizacao = climatizacaoCheckBox.isSelected();
                        boolean cargaViva = cargaVivaCheckBox.isSelected();
                    }
                }
                catch (NumberFormatException ex) {
                    textoErro.setText("Os campos devem ser preenchidos com numeros, utilize . para as casas decimais");
                }
            }
        });
    }
}
