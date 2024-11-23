package aplicacao.janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostrarRelatorioGeral extends JPanel {
    private JPanel mostrarRelatorio;
    private JTextArea textArea1;
    private JButton voltarButton;
    private JButton atualizarBotao;

    public MostrarRelatorioGeral(ACMEAirDrones acme) {
        add(mostrarRelatorio);

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
                textArea1.setText(acme.mostraRelatorioGeralDroneETransporte());
            }
        });
    }
}
