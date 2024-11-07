package janelas;

import javax.swing.*;

public class DroneCargaForm extends JFrame {
    private JPanel painelPrincipal;
    private JCheckBox cargaVivaCheckBox;
    private JFormattedTextField textoAutonomia;
    private JFormattedTextField textoCustoFixo;
    private JFormattedTextField textoPesoMax;
    private JCheckBox protecaoCheckBox;
    private JCheckBox climatizacaoCheckBox;

    public DroneCargaForm() {
        setTitle("Carga Form");
        setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setVisible(true);
    }
}
