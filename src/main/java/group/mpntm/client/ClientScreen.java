package group.mpntm.client;

import javax.swing.*;

public class ClientScreen extends JFrame {
    private JLabel label;


    public ClientScreen(Client client) {
        label = new JLabel("TODO: Implementar Login ");
        add(label);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);

        setLocationRelativeTo(null);
    }

}
