package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView extends JFrame implements ActionListener {

    JFrame frame = new JFrame();

    JComboBox<String> combo1;

    JTextArea taTask;

    JButton btnCreate;
    JRadioButton rbEmployee, rbAdmin;

    JTextField tfId, tfName, tfAddress, tfSalary,
            tfDateOfHire, tfDateOfBirth, tfDepartment, tfRole;

    public AdminView(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(1200, 700);

        frame.add(createMainAdminView());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel createMainAdminView(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));

        panel.add(createAdminView_LEFT());
        panel.add(createAdminView_RIGHT());

        return panel;
    }

    public JPanel createAdminView_RIGHT(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(createAssignTask_TOP(), BorderLayout.NORTH);
        panel.add(createTaskPanel(), BorderLayout.CENTER);
        return panel;
    }

    public JLabel createAssignTask_TOP(){
        JLabel lblLogin = new JLabel("Create Tasks", SwingConstants.LEFT);
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblLogin.setBorder(BorderFactory.createEmptyBorder(0, 10, 30, 0));
        return lblLogin;
    }

    public JPanel createTaskPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        JLabel lblTask = new JLabel("Task:");
        lblTask.setFont(new Font("SansSerif", Font.BOLD, 16));
        taTask = new JTextArea(5, 40);
        taTask.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JScrollPane scrollTask = new JScrollPane(taTask);

        JButton btnAddTask = new JButton("Add Task");
        btnAddTask.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAddTask.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAddTask.addActionListener(this);
        btnAddTask.setActionCommand("AddTask");

        JLabel tasks = new JLabel("Tasks:");
        tasks.setFont(new Font("SansSerif", Font.BOLD, 16));
        combo1 = new JComboBox<>();
        combo1.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel lblEmployee = new JLabel("Select Employee:");
        lblEmployee.setFont(new Font("SansSerif", Font.BOLD, 16));
        String[] employees = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> comboEmployee = new JComboBox<>(employees);
        comboEmployee.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel lblGroup = new JLabel("Select Group:");
        lblGroup.setFont(new Font("SansSerif", Font.BOLD, 16));
        String[] groups = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> comboGroup = new JComboBox<>(groups);
        comboGroup.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton btnSubmitTask = new JButton("Submit Task");
        btnSubmitTask.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSubmitTask.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTask);
        panel.add(scrollTask);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnAddTask);
        panel.add(Box.createVerticalStrut(15));

        panel.add(tasks);
        panel.add(combo1);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblEmployee);
        panel.add(comboEmployee);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblGroup);
        panel.add(comboGroup);
        panel.add(Box.createVerticalStrut(15));

        panel.add(btnSubmitTask);

        for (Component c : panel.getComponents()) {
            if (c instanceof JComponent) {
                ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);
            }
        }

        return panel;
    }

    public JPanel createAdminView_LEFT(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(createAdminLabel_NORTH(), BorderLayout.NORTH);
        panel.add(createAdminView_CENTER(), BorderLayout.CENTER);
        return panel;
    }

    public JLabel createAdminLabel_NORTH(){
        JLabel lblLogin = new JLabel("Admin Control", SwingConstants.CENTER);
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblLogin.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        return lblLogin;
    }

    public JPanel createAdminView_CENTER(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblOptions = new JLabel("Create Options:");
        lblOptions.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblOptions);

        rbEmployee = new JRadioButton("Employee");
        rbAdmin = new JRadioButton("Admin");
        rbEmployee.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rbAdmin.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rbEmployee.setAlignmentX(Component.LEFT_ALIGNMENT);
        rbAdmin.setAlignmentX(Component.LEFT_ALIGNMENT);
        rbAdmin.addActionListener(this);
        rbEmployee.addActionListener(this);

        ButtonGroup group = new ButtonGroup();
        group.add(rbEmployee);
        group.add(rbAdmin);

        panel.add(rbEmployee);
        panel.add(rbAdmin);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(createField("ID:", tfId = new JTextField()));
        panel.add(createField("Name:", tfName = new JTextField()));
        panel.add(createField("Address:", tfAddress = new JTextField()));
        panel.add(createField("Salary:", tfSalary = new JTextField()));
        panel.add(createField("Date of Hire:", tfDateOfHire = new JTextField()));
        panel.add(createField("Date of Birth:", tfDateOfBirth = new JTextField()));
        panel.add(createField("Department:", tfDepartment = new JTextField()));
        panel.add(createField("Role:", tfRole = new JTextField()));

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        btnCreate = new JButton("Create");
        btnCreate.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnCreate.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCreate.addActionListener(this);
        btnCreate.setActionCommand("Create");
        panel.add(btnCreate);

        return panel;
    }

    private JPanel createField(String labelText, JTextField field){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setPreferredSize(new Dimension(150, 30));

        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(300, 30));

        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args){
        new AdminView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("AddTask")){
            combo1.addItem(taTask.getText());
            taTask.setText("");
        }
        if(e.getActionCommand().equals("Create")){

        }
    }
}
