package aplicacao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        botaoCadastrarDrone.addActionListener(new ActionListener() {  //Botão de criar drone carga
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeAirDrones.mudaPainel(2);
            }
        });

        botaoFinalizar.addActionListener(new ActionListener() {  //terminar Programa,
                                                                // acho q quando a gente tiver a opcao de salvar, a gente podia verificar se salvou, se não tiver salvo ao tenta//clicar esse botao aparece um pop up se quer salvar ou nao, pra n perder os dados
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


}
