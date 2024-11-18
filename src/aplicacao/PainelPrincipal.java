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

        botaoCadastrarDrone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Drone de Carga", "Drone Pessoal", "Voltar"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Escolha o tipo de drone que deseja cadastrar:",
                        "Cadastrar Drone",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == 0) { // Drone de Carga
                    acmeAirDrones.mudaPainel(2);
                } else if (choice == 1) { // Drone Pessoal
                    acmeAirDrones.mudaPainel(3);
                }
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
