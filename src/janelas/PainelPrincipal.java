package janelas;

import javax.swing.*;

public class PainelPrincipal extends JFrame {

    public PainelPrincipal() {
        DroneCargaForm droneCargaForm = new DroneCargaForm();

        setContentPane(droneCargaForm.getContentPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setVisible(true);
    }
}
