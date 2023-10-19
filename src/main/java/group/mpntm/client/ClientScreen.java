package group.mpntm.client;

import javax.swing.*;

public class ClientScreen extends JPanel {
    private JLabel label;


    public ClientScreen() {
        label = new JLabel("TODO: Implementar Login ");
        add(label);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientScreen");
        frame.setContentPane(new ClientScreen());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
