package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import database.*;

public class Login extends JFrame implements ActionListener {

    Database db = new Database("");

    JFrame frame = new JFrame();

    JLabel lblLogin, lblUsername, lblPassword;
    JTextField tfUsername, tfPassword;
    JButton btnSignIn;

    public Login(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(700,800);

        frame.add(createLoginPanel());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel createLoginPanel(){

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(245, 245, 245));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(330, 380));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        lblLogin = new JLabel("Log-In", SwingConstants.CENTER);
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblLogin.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        card.add(lblLogin, BorderLayout.NORTH);
        card.add(createUsernameAndPassword(), BorderLayout.CENTER);
        card.add(signInPanel(), BorderLayout.SOUTH);

        background.add(card);

        return background;
    }

    public JPanel createUsernameAndPassword(){
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4,1,4,4));

        tfUsername = new JTextField();
        tfPassword = new JTextField();
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");

        panel.add(lblUsername);
        panel.add(tfUsername);
        panel.add(lblPassword);
        panel.add(tfPassword);

        return panel;
    }

    public JPanel signInPanel(){
        JPanel panel = new JPanel();
        btnSignIn = new JButton("Sign-in");
        btnSignIn.setActionCommand("Sign-in");
        btnSignIn.addActionListener(this);

        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout());

        panel.add(btnSignIn);

        return panel;
    }

    public static void main(String[] args) {
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        File file = new File("src/login/Grant.rec");
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        if(db.checkLogin(input.nextLine(), tfUsername.getText(), tfPassword.getText())){
            System.out.println("pass");
            Employee e1 = new Employee(1,100,"Samuel", "610 HSS", "null","null","null","null");
            frame.dispose();
            new AdminView(e1);
        }
        input.close();
    }
}
