package group.mpntm.client;

import javax.swing.*;

import group.mpntm.Comunication.Events.ChartInitEvent;
import group.mpntm.Comunication.Events.LoginButtonPressedEvent;
import group.mpntm.Comunication.Events.LoginFailedEvent;
import group.mpntm.Comunication.Events.LoginSuccessfulEvent;

import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;
import java.awt.event.ItemEvent;

public class ClientScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginbtn; 
    private JLabel usernameLabel, passwordLabel, langLabel;
    private JPanel panel,  upperPanel, lowerPanel;
    private String username,password, userLable, passLable, langTxt;
    private JComboBox<String> langDropdown;
    private ResourceBundle bn;
    private LangChooser langChooser = new LangChooser();
    public static String[] lang = {"Português","Deutsch", "Español", "English", "Italiano"};

    public ClientScreen() {

        setLanguageFromTxt();

        LoginSuccessfulEvent.getInstance().AddListener(
            () -> {
                setVisible(false);
                loginbtn.setEnabled(true);
                JOptionPane.showMessageDialog(null, bn.getString("login.successful"), bn.getString("login.login"), JOptionPane.INFORMATION_MESSAGE);
            }
        );
        LoginFailedEvent.getInstance().AddListener(
                    () -> {
                        setVisible(false);
                        loginbtn.setEnabled(true);
                        JOptionPane.showMessageDialog(null, bn.getString("login.failed"), bn.getString("login.login"), JOptionPane.INFORMATION_MESSAGE);
                        setVisible(true);
                    }
                );

        ChartInitEvent.getInstance().AddListener(
            (content) -> {
                Chart chart = new Chart(langChooser);
                chart.go(content);

            }
        );

        langLabel = new JLabel(langTxt);


        langDropdown = new JComboBox<>(lang);
        langDropdown.setSelectedItem(langChooser.getLastLang());
        langDropdown.addItemListener (  new ItemListener()
           {
              public void itemStateChanged( ItemEvent event )
              {
                 if ( event.getStateChange() == ItemEvent.SELECTED ){
                    String op = (String) event.getItem();

                    langChooser.chooseLang(op);
                    langChooser.setLastLang(op);
                    setLanguage();


                 }
              }
           }                           
        );                    
        
        
        // Painel principal
        panel = new JPanel();
        panel.setLayout(new BorderLayout(5,5)); 
        
        // Rótulos e campos de texto
        usernameLabel = new JLabel();
        usernameField = new JTextField(20);
        passwordLabel = new JLabel();
        passwordField = new JPasswordField(20);
        loginbtn = new JButton();
        setLanguage();

        


        loginbtn.addActionListener(e -> {
            try {

                username = usernameField.getText();
                password = String.format("%s", new String(passwordField.getPassword()));

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, bn.getString("login.empty"), bn.getString("login.login"), JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                LoginButtonPressedEvent.getInstance().Invoke(username, password);

                loginbtn.setEnabled(false);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        


        // Configuração das posições dos componentes no frame
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

        c.gridx = 0;
        c.gridy = 1;
        
        lowerPanel.add(passwordLabel,c);

        c.weightx = 1;

        c.gridx = 1;
        c.gridy = 0;

        lowerPanel.add(usernameField,c);

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

    public void setLanguage(){
        bn = langChooser.getBn();
        userLable = bn.getString("login.user.label");
        passLable = bn.getString("login.password.label");
        langTxt = bn.getString("login.language");
        usernameLabel.setText(userLable);
        passwordLabel.setText(passLable);
        langLabel.setText(langTxt);
        this.setTitle(bn.getString("login.login"));
        loginbtn.setText(bn.getString("login.login"));
    }

    public void setLanguageFromTxt(){
        String langS = langChooser.getLastLang();
        langChooser.chooseLang(langS);

    }

}
