package group.mpntm.client;

import javax.swing.*;

public class ClientScreen extends JFrame {
    private JLabel label;
    private JButton button;


    public ClientScreen(Client client) {
        label = new JLabel("TODO: Implementar Login ");
        button = new JButton("Login");
        add(label);

        button.addActionListener(e -> {
            try {
                if (client.start("admin", "admin") == 0){
                    JOptionPane.showMessageDialog(null, "Erro ao realizar o login!");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        add(button);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);

        setLocationRelativeTo(null);
    }

}
