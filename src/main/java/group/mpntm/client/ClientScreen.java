package group.mpntm.client;

import javax.swing.*;
import java.awt.*;


public class ClientScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginbtn; 
    private JLabel usernameLabel, passwordLabel;
    private JPanel panel;
    private Arq arq = new Arq();    
    private Login login = new Login();
    private String username,password;  

    public ClientScreen(Client client) {
        // utilizando arquivo para pegar tabela de login local
        arq.read(".\\src\\main\\java\\group\\mpntm\\client", "test.txt");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); 
        
        // Rótulos e campos de texto
        usernameLabel = new JLabel("Usuário:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField(20);

        loginbtn = new JButton("Login");
        loginbtn.addActionListener(e -> {
                try {
                    username = usernameField.getText();
                    password = String.format("%s", new String(passwordField.getPassword()));
                    if (login.validate(username, password, arq.getMsgList())) {
                       
                        JOptionPane.showMessageDialog(null, "Login Feito com sucesso");
                        if (client.start("admin", "admin") == 0){
                            JOptionPane.showMessageDialog(null, "Erro ao realizar o login!");
                        }
                    } else if (login.getControl() == 1) {
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Senha Invalida");
                        setVisible(true);
                    } else if (login.getControl() == -1) {
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Usuario Invalido");
                        setVisible(true);
                    }
                   

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });


        // Configuração das posições dos componentes no painel 
      
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
       

        c.gridx = 0;
        c.gridy = 0;
        panel.add(usernameLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        panel.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        panel.add(passwordField, c);


        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2; 
        panel.add(loginbtn, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2; 
        panel.add(loginbtn, c);

        add(panel);

        
    }

}
