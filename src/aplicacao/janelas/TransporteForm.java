package aplicacao.janelas;

import aplicacao.ACMEAirDrones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransporteForm extends JPanel {

    private JRadioButton radioPessoal;
    private JRadioButton radioCargaInanimada;
    private JRadioButton radioCargaViva;
    private JFormattedTextField textoNumero;
    private JFormattedTextField textoCliente;
    private JFormattedTextField textoPeso;
    private JFormattedTextField textoDescricao;
    private JFormattedTextField textoLatO;
    private JFormattedTextField textoLongO;
    private JFormattedTextField textoLatD;
    private JFormattedTextField textoLongD;
    private JFormattedTextField textoQtdPessoas;
    private JCheckBox checkBoxCargaPerigosa;
    private JFormattedTextField textoTempMax;
    private JFormattedTextField textoTempMin;
    private JButton botaoLimpar;
    private JButton botaoVoltar;
    private JButton botaoEnviar;
    private JLabel tempMax;
    private JLabel tempMin;
    private JLabel qtdPessoas;
    private JPanel painelTransportes;
    private JLabel textoErro;

    public TransporteForm(ACMEAirDrones acmeAirDrones) {

        add(painelTransportes);

        radioPessoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioPessoal.isSelected()){
                    radioCargaInanimada.setSelected(false);
                    radioCargaViva.setSelected(false);
                    qtdPessoas.setVisible(true);
                    textoQtdPessoas.setVisible(true);

                    checkBoxCargaPerigosa.setVisible(false);
                    checkBoxCargaPerigosa.setSelected(false);
                    textoTempMax.setVisible(false);
                    textoTempMax.setText("");
                    textoTempMin.setVisible(false);
                    textoTempMin.setText("");
                    tempMax.setVisible(false);
                    tempMin.setVisible(false);
                }
            }
        });
        radioCargaInanimada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioCargaInanimada.isSelected()){
                    radioPessoal.setSelected(false);
                    radioCargaViva.setSelected(false);
                    checkBoxCargaPerigosa.setVisible(true);
                    checkBoxCargaPerigosa.setVisible(true);

                    textoQtdPessoas.setVisible(false);
                    textoQtdPessoas.setText("");
                    qtdPessoas.setVisible(false);
                    textoTempMax.setVisible(false);
                    textoTempMax.setText("");
                    textoTempMin.setVisible(false);
                    textoTempMin.setText("");
                    tempMax.setVisible(false);
                    tempMin.setVisible(false);
                }
            }
        });
        radioCargaViva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioCargaViva.isSelected()){
                    radioCargaInanimada.setSelected(false);
                    radioPessoal.setSelected(false);
                    tempMax.setVisible(true);
                    tempMin.setVisible(true);
                    textoTempMin.setVisible(true);
                    textoTempMax.setVisible(true);

                    qtdPessoas.setVisible(false);
                    textoQtdPessoas.setVisible(false);
                    checkBoxCargaPerigosa.setVisible(false);
                    checkBoxCargaPerigosa.setSelected(false);
                    textoQtdPessoas.setText("");

                }
            }
        });
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeAirDrones.mudaPainel(1);
                checkBoxCargaPerigosa.setSelected(false);
                radioPessoal.setSelected(false);
                radioCargaViva.setSelected(false);
                radioCargaInanimada.setSelected(false);
                textoCliente.setText("");
                textoPeso.setText("");
                textoDescricao.setText("");
                textoLatO.setText("");
                textoLongO.setText("");
                textoLatD.setText("");
                textoQtdPessoas.setText("");
                textoNumero.setText("");
                textoLongD.setText("");
                textoTempMax.setText("");
                textoTempMin.setText("");
                textoErro.setText("");
            }
        });
        botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxCargaPerigosa.setSelected(false);
                radioPessoal.setSelected(false);
                radioCargaViva.setSelected(false);
                radioCargaInanimada.setSelected(false);
                textoCliente.setText("");
                textoPeso.setText("");
                textoDescricao.setText("");
                textoLatO.setText("");
                textoLongO.setText("");
                textoLatD.setText("");
                textoQtdPessoas.setText("");
                textoNumero.setText("");
                textoLongD.setText("");
                textoTempMax.setText("");
                textoTempMin.setText("");
                textoErro.setText("");
            }
        });
        botaoEnviar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                textoErro.setText("");

                if (!radioPessoal.isSelected() && !radioCargaViva.isSelected() && !radioCargaInanimada.isSelected()) {
                    textoErro.setText("Voce deve escolher um tipo de Transporte!");
                    return;
                }
                if (textoLongD.getText().isBlank() || textoNumero.getText().isBlank() || textoDescricao.getText().isBlank()
                        || textoLatO.getText().isBlank() || textoCliente.getText().isBlank() || textoPeso.getText().isBlank()
                        || textoLatD.getText().isBlank() || textoLongO.getText().isBlank()) {
                    textoErro.setText("Todos os campos devem ser preenchidos!");
                }
                if (radioPessoal.isSelected() && textoQtdPessoas.getText().isBlank()) {
                    textoErro.setText("Todos os campos devem ser preenchidos!");
                }
                if (radioCargaViva.isSelected() && (textoTempMax.getText().isBlank() || textoTempMin.getText().isBlank())) {
                    textoErro.setText("Todos os campos devem ser preenchidos!");
                }

                try{
                    int numero = Integer.parseInt(textoNumero.getText());
                    String descricao = textoDescricao.getText();
                    String nome = textoCliente.getText();
                    double peso = Double.parseDouble(textoPeso.getText());
                    double latO = Double.parseDouble(textoLatO.getText());
                    double longO = Double.parseDouble(textoLongO.getText());
                    double latD = Double.parseDouble(textoLatD.getText());
                    double longD = Double.parseDouble(textoLongD.getText());
                    Integer qtdPessoas = null;
                    boolean cargaPerigosa = false;
                    Double tempMax = null;
                    Double tempMin = null;

                    if(radioPessoal.isSelected()){
                        qtdPessoas = Integer.parseInt(textoQtdPessoas.getText());
                    }
                    if(radioCargaViva.isSelected()){
                        tempMax = Double.parseDouble(textoTempMax.getText());
                        tempMin = Double.parseDouble(textoTempMin.getText());

                        if(tempMax < tempMin){
                            textoErro.setText("A temperatura maxima deve ser maior do que a minima!");
                            return;
                        }
                    }
                    if(radioCargaInanimada.isSelected()){
                        cargaPerigosa = checkBoxCargaPerigosa.isSelected();
                    }

                    if (!acmeAirDrones.CadastraTransporte(numero,nome,descricao,peso,latO,latD,longO,longD,qtdPessoas,cargaPerigosa,tempMax,tempMin)) {
                        textoErro.setText("Esse codigo já existe!");
                    }
                    else{
                        textoErro.setText("Transporte Cadastrado!");
                    }

                }
                catch(NumberFormatException ex){
                    textoErro.setText("Os campos devem ser preenchidos corretamente!!");
                }

            }
        });
    }
}
