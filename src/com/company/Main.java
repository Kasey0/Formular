package com.company;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class CarForm {
    private JFrame frame;
    private JTextField textFieldModel;
    private JCheckBox checkBoxAutomatic;
    private JRadioButton radioButtonMotorina, radioButtonBenzina;
    private JComboBox<String> comboBoxProducator;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CarForm window = new CarForm();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CarForm() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(7, 2));

        frame.getContentPane().add(new JLabel("Model:"));
        textFieldModel = new JTextField();
        frame.getContentPane().add(textFieldModel);

        frame.getContentPane().add(new JLabel("Automatic:"));
        checkBoxAutomatic = new JCheckBox();
        frame.getContentPane().add(checkBoxAutomatic);

        frame.getContentPane().add(new JLabel("Combustibil:"));
        ButtonGroup fuelTypeGroup = new ButtonGroup();
        radioButtonMotorina = new JRadioButton("Motorina");
        radioButtonBenzina = new JRadioButton("Benzina");
        fuelTypeGroup.add(radioButtonMotorina);
        fuelTypeGroup.add(radioButtonBenzina);
        frame.getContentPane().add(radioButtonMotorina);
        frame.getContentPane().add(radioButtonBenzina);

        frame.getContentPane().add(new JLabel("Producator:"));
        String[] makes = {"Toyota", "Honda", "Ford"};
        comboBoxProducator = new JComboBox<>(makes);
        frame.getContentPane().add(comboBoxProducator);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToJson();
            }
        });
        frame.getContentPane().add(btnSave);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnCancel);
    }

    private void saveDataToJson() {
        String model = textFieldModel.getText();
        boolean automatic = checkBoxAutomatic.isSelected();
        String combustibil = radioButtonMotorina.isSelected() ? "Motorina" : "Benzina";
        String producator = comboBoxProducator.getSelectedItem().toString();

        JSONObject carObject = new JSONObject();
        carObject.put("Model", model);
        carObject.put("Automatic", automatic);
        carObject.put("FuelType", combustibil);
        carObject.put("Make", producator);

        try (FileWriter fileWriter = new FileWriter("cars.json", true)) {
            fileWriter.write(carObject.toJSONString() + "\n");
            JOptionPane.showMessageDialog(frame, "Date salvate cu succes!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Eroare la salvare!");
        }
    }
}
