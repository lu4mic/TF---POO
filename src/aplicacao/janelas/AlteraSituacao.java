package aplicacao.janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlteraSituacao extends JPanel {
    private JPanel AlteraS;
    private JButton voltarButton;
    private JButton alterarBotao;
    private JRadioButton cancelarBotao;
    private JRadioButton terminarBotao;
    private JPanel alteraSituacao;
    private JFormattedTextField textoCodigo;
    private JLabel textoErro;

    public AlteraSituacao(ACMEAirDrones acmeAirDrones){
        add(alteraSituacao);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeAirDrones.mudaPainel(1);
                terminarBotao.setSelected(false);
                cancelarBotao.setSelected(false);
                textoErro.setText("");
                textoCodigo.setText("");
            }
        });
        cancelarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terminarBotao.setSelected(false);
            }
        });
        terminarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarBotao.setSelected(false);
            }
        });
        alterarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(textoCodigo.getText().isBlank()){
                        textoErro.setText("Voce nao digitou o codigo para alterar");
                    }

                    if(cancelarBotao.isSelected()){
                        int codigo = Integer.parseInt(textoCodigo.getText());
                        textoErro.setText(acmeAirDrones.alteraSituacao(codigo,1));
                    }
                    if(terminarBotao.isSelected()){
                        int codigo = Integer.parseInt(textoCodigo.getText());
                        textoErro.setText(acmeAirDrones.alteraSituacao(codigo,2));
                    }
                }catch(NumberFormatException ex){
                    textoErro.setText("O codigo eh numerico");
                }
            }
        });
    }
}
