package application.menuBarDialogs.optionDialog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class OptionWindow extends JFrame {

    private JLabel lblSpeed;
    private JTextField txtPersonalMaxSpeed;
    private JButton btnClose;
    private Container pane;

    private int maxSpeed;

    public OptionWindow () {
        super("Optionen");
        this.setLayout(new FlowLayout());
        this.setSize(550, 100);
        this.setResizable(false);
        this.setLocation(530, 475);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pane = this.getContentPane();
        this.maxSpeed = 130;
        this.initJObjects();
    }

    private void initJObjects () {
        this.lblSpeed = new JLabel("Persönliche Höchstgeschwindigkeit:");
        this.txtPersonalMaxSpeed = new JTextField(20);
        this.txtPersonalMaxSpeed.setText("130");
        this.btnClose = new JButton("Schließen");
        this.btnClose.addActionListener(actionEvent -> this.setVisible(false));
        addDocumentListenerToTextField();
        pane.add(lblSpeed);
        pane.add(txtPersonalMaxSpeed);
        pane.add(btnClose);
    }

    private void addDocumentListenerToTextField() {
        this.txtPersonalMaxSpeed.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                changedMaxSpeed(txtPersonalMaxSpeed.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                changedMaxSpeed(txtPersonalMaxSpeed.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                changedMaxSpeed(txtPersonalMaxSpeed.getText());
            }
        });
    }

    private void changedMaxSpeed (String newMaxSpeed) {
        try {
            int newSpeed = Integer.parseInt(newMaxSpeed);

            if (newSpeed <= 0)
                throw new InvalidMaxSpeedException();
            else if (newSpeed > 300)
                throw new TooHighMaxSpeedException();
            else
                this.maxSpeed = newSpeed;

            this.lblSpeed.setText("Persönliche Höchstgeschwindigkeit:");
            this.btnClose.setEnabled(true);
        } catch (NumberFormatException e) {
            this.lblSpeed.setText("Bitte geben Sie eine Nummer ein ->");
            this.btnClose.setEnabled(false);
        } catch (InvalidMaxSpeedException ae) {
            this.lblSpeed.setText("Es sind nur positve Zahlen möglich ->");
            this.btnClose.setEnabled(false);
        } catch (TooHighMaxSpeedException he) {
            this.lblSpeed.setText("Ein bisschen schnell oder ;) (V <= 300)");
            this.btnClose.setEnabled(false);
        }
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

}
