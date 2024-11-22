package aplicacao.janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DronePessoalForm extends JPanel {
    private JPanel painelPrincipal;
    private JTextField campoCodigo, campoQuantidadePessoas, campoCustoFixo, campoAutonomia;
    private JButton botaoMostrarDados, botaoVoltar, botaoCadastrar, botaoLimpar;
    private JTextArea areaTexto;

    public DronePessoalForm(ACMEAirDrones acme) {
        painelPrincipal = new JPanel(); // Inicializa o painel principal
        BoxLayout layout = new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS);
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
        botaoVoltar = new JButton("Voltar");

        JPanel painelForm = new JPanel(new GridLayout(6, 3));
        painelForm.add(lCodigo);
        painelForm.add(campoCodigo);
        painelForm.add(lcustoFixo);
        painelForm.add(campoCustoFixo);
        painelForm.add(lAutonomia);
        painelForm.add(campoAutonomia);
        painelForm.add(lQtdPessoas);
        painelForm.add(campoQuantidadePessoas);
        painelForm.add(botaoMostrarDados);
        painelForm.add(botaoVoltar);
        painelForm.add(botaoCadastrar);
        painelForm.add(botaoLimpar);
        botaoMostrarDados.setVisible(false);

        painelPrincipal.add(painelTitulo);
        painelPrincipal.add(painelForm);

        areaTexto = new JTextArea(10, 30);
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
                if (campoCodigo.getText().isBlank() || campoAutonomia.getText().isBlank() ||
                        campoQuantidadePessoas.getText().isBlank() || campoCustoFixo.getText().isBlank()) {
                    areaTexto.setText("Todos os campos devem ser preenchidos");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(campoCodigo.getText());
                    double custoFixo = Double.parseDouble(campoCustoFixo.getText());
                    double autonomia = Double.parseDouble(campoAutonomia.getText());
                    int qtdPessoas = Integer.parseInt(campoQuantidadePessoas.getText());

                    if (acme.CadastraDrone(codigo, custoFixo, autonomia, qtdPessoas)) {
                        areaTexto.setText("Drone cadastrado com sucesso!");
                        botaoMostrarDados.setVisible(true);
                    } else {
                        areaTexto.setText("Erro: esse código já existe.");
                    }

                } catch (NumberFormatException ex) {
                    areaTexto.setText("Erro: Os campos devem ser preenchidos com números");
                }
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acme.mudaPainel(1);

                campoCodigo.setText("");
                campoCustoFixo.setText("");
                campoAutonomia.setText("");
                campoQuantidadePessoas.setText("");
                areaTexto.setText("");
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

        this.setPreferredSize(new Dimension(920, 600));
    }

    public void limpaCampos() {
        campoCodigo.setText("");
        campoCustoFixo.setText("");
        campoAutonomia.setText("");
        campoQuantidadePessoas.setText("");
        areaTexto.setText("");
    }
}
