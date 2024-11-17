package aplicacao;
import dados.drone.DronesLista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DronePessoalForm extends JPanel {
    private JPanel painelPrincipal;
    private JTextField campoCodigo, campoQuantidadePessoas, campoCustoFixo, campoAutonomia;
    private JButton botaoMostrarDados, botaoSair, botaoCadastrar, botaoLimpar ;
    private JTextArea areaTexto;


    public DronePessoalForm(ACMEAirDrones acme) {
        BoxLayout layout = new BoxLayout(painelPrincipal,BoxLayout.Y_AXIS);
        painelPrincipal.setLayout(layout);

        JLabel lTitulo = new JLabel("Drone Pessoal");
        JPanel painelTitulo = new JPanel();

        FlowLayout layoutTitulo = new FlowLayout(FlowLayout.CENTER);
        painelTitulo.setLayout(layoutTitulo);
        painelTitulo.add(lTitulo);
        JLabel lCodigo = new JLabel("Codigo");
        campoCodigo = new JTextField(10);
        JLabel lQtdPessoas = new JLabel("Quantidade Pessoas");
        campoQuantidadePessoas = new JTextField(10);
        JLabel lcustoFixo = new JLabel("Custo Fixo");
        campoCustoFixo = new JTextField(10);
        JLabel lAutonomia = new JLabel("Autonomia");
        campoAutonomia = new JTextField(10);
        botaoCadastrar = new JButton("Cadastrar");
        botaoLimpar = new JButton("Limpar");
        botaoMostrarDados = new JButton("Mostrar Dados");
        botaoSair = new JButton("Sair");
        JPanel painelForm = new JPanel(new GridLayout(6,3));
        painelForm.add(lCodigo);
        painelForm.add(campoCodigo);
        painelForm.add(lQtdPessoas);
        painelForm.add(campoQuantidadePessoas);
        painelForm.add(lcustoFixo);
        painelForm.add(campoCustoFixo);
        painelForm.add(lAutonomia);
        painelForm.add(campoAutonomia);
        painelForm.add(botaoMostrarDados);
        painelForm.add(botaoSair);
        painelForm.add(botaoCadastrar);
        painelForm.add(botaoLimpar);
        botaoMostrarDados.setVisible(false);

        painelPrincipal.add(painelTitulo);
        painelPrincipal.add(painelForm);

        areaTexto = new JTextArea(5,20);
        areaTexto.setBackground(Color.WHITE);
        areaTexto.setForeground(Color.BLACK);
        JScrollPane painelScroll = new JScrollPane(areaTexto);
        areaTexto.setEditable(false);
        painelScroll.setVisible(true);
        painelPrincipal.add(painelScroll);

        this.add(painelPrincipal);



        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(campoCodigo.getText().isBlank() || campoAutonomia.getText().isBlank() ||
                        campoQuantidadePessoas.getText().isBlank() || campoCustoFixo.getText().isBlank()){
                    areaTexto.setText("Todos campos devem ser preenchidos");
                    return;
                }

                try {

                    int codigo = Integer.parseInt(campoCodigo.getText());
                    int qtdPessoas = Integer.parseInt(campoQuantidadePessoas.getText());
                    double custoFixo = Double.parseDouble(campoCustoFixo.getText());
                    double autonomia = Double.parseDouble(campoAutonomia.getText());


                    if(acme.CadastraDrone(codigo,custoFixo,autonomia,qtdPessoas)) {

                        areaTexto.setText("Drone cadastrado com sucesso!");
                        botaoMostrarDados.setVisible(true);

                    } else{
                        areaTexto.setText("Erro: esse codigo ja existe.");
                    }


                }

                catch(NumberFormatException ex){
                    areaTexto.setText("Erro: Os campos devem ser preenchidos com numeros");
                }
            }
        });

        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        botaoMostrarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaTexto.setText("");
                String mostra = acme.mostrarDronesPessoal();
                areaTexto.setText(mostra);
            }
        });

        botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpaCampos();
            }
        });

    }

    public void limpaCampos(){
        campoCodigo.setText("");
        campoCustoFixo.setText("");
        campoAutonomia.setText("");
        campoQuantidadePessoas.setText("");
        campoCustoFixo.setText("");
        areaTexto.setText("");
    }
}
