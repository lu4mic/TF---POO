package janelas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelPrincipal extends JFrame {

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

    public PainelPrincipal() {
        DroneCargaForm droneCargaForm = new DroneCargaForm();

        setTitle("Menu Inicial");
        setContentPane(painelMenu);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 600);
        setVisible(true);
        botaoCadastrarDrone.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {   //Botão de criar drone carga
                setContentPane(droneCargaForm.getContentPane());
                setTitle("Drones de Carga");
                setVisible(true);
            }
        });
        botaoFinalizar.addActionListener(new ActionListener() {  //terminar Programa,
                                                                // acho q quando a gente tiver a opcao de salvar, a gente podia verificar se salvou, se não tiver salvo ao tentar
                                                                //clicar esse botao aparece um pop up se quer salvar ou nao, pra n perder os dados
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
