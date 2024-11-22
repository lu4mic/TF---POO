package aplicacao.janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostrarTransportes extends JPanel {
    private JButton voltarButton;
    private JButton atualizarBotao;
    private JPanel mostrarT;
    private JTextArea textArea1;

    public MostrarTransportes(ACMEAirDrones acme) {
        add(mostrarT);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acme.mudaPainel(1);
                textArea1.setText("");
            }
        });
        atualizarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(acme.mostrarTodosTransportes());
            }
        });
    }
}
