package group.mpntm.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ItemEvent;

public class ClientScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginbtn; 
    private JLabel usernameLabel, passwordLabel, langLabel;
    private JPanel panel,  upperPanel, lowerPanel;
    private Login login;
    private String username,password, userLable, passLable, langTxt, index; 
    private JComboBox<String> langDropdown;
    private ResourceBundle bn;

    public ClientScreen(Client client) {
        langTxt = "Idioma";
        langLabel = new JLabel(langTxt);
        String[] lang = {"English","Deutsch", "Español", "Português","日本" }; 
        langDropdown = new JComboBox<>(lang);
        langDropdown.addItemListener
        (  new ItemListener()
           {  // Manipula o evento da JComboBox
              public void itemStateChanged( ItemEvent event )       
              {  // Determine se check box foi selecionado          
                 if ( event.getStateChange() == ItemEvent.SELECTED ){  
                    index = lang[ItemEvent.SELECTED];
                    switch ( index ){
                        case "English":
                            bn = ResourceBundle.getBundle("bundle", Locale.US);
                        break;
                         case  "Português": 
                            bn = ResourceBundle.getBundle("bundle", new Locale("pt", "BR"));
                        break;
                        case  "Deutsch": 
                            bn = ResourceBundle.getBundle("bundle", Locale.GERMAN);
                        break;
                        case  "Español":
                            bn = ResourceBundle.getBundle("bundle", new Locale("es","ES"));
                        break;
                        case  "日本":
                            bn = ResourceBundle.getBundle("bundle", Locale.JAPANESE);
                        break;
                        default: 
                            bn = ResourceBundle.getBundle("bundle", new Locale("pt", "BR"));
                        break;
                    }
                    userLable = bn.getString("userLable");
                    passLable = bn.getString("passLable");
                    langTxt   = bn.getString("lang");
                 }

              }  
           }                           
        );                    
        
        
        // Painel principal
        panel = new JPanel();
        panel.setLayout(new BorderLayout(5,5)); 
        
        // Rótulos e campos de texto
        usernameLabel = new JLabel(userLable);
        usernameField = new JTextField(20);
        passwordLabel = new JLabel(passLable);
        passwordField = new JPasswordField(20);
       
        loginbtn = new JButton("Login");

        login = new Login(client);

        loginbtn.addActionListener(e -> {
                try {
                    username = usernameField.getText();
                    password = String.format("%s", new String(passwordField.getPassword()));
                    if (login.validate(username, password)) {
                        JOptionPane.showMessageDialog(null, "Login Feito com sucesso");
                        setVisible(false);

                    } else {
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Senha Invalida");
                        setVisible(true);
                    }
                   

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

        


        // Configuração das posições dos componentes no painel 
        upperPanel = new JPanel(new FlowLayout());
        
        upperPanel.add(langLabel);
        upperPanel.add(langDropdown);

        lowerPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,2,5,2);

        c.gridx = 0;
        c.gridy = 0;
        lowerPanel.add(usernameLabel,c);

        c.gridx = 1;
        c.gridy = 0;
        
        lowerPanel.add(usernameField,c);

        c.gridx = 0;
        c.gridy = 1;
        
        lowerPanel.add(passwordLabel,c);

        c.gridx = 1;
        c.gridy = 1;
        
        lowerPanel.add(passwordField,c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2; 
        lowerPanel.add(loginbtn,c);
        
        //upperPanel.setBackground(Color.GREEN);
        // lowerPanel.setBackground(Color.RED);

        panel.add(upperPanel, BorderLayout.NORTH);
        panel.add(lowerPanel, BorderLayout.CENTER);

        


        add(panel);
       
        
    
        // configuradoções da tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

        
    }

}
