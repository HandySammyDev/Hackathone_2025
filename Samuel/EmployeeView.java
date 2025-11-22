package Gui;

import javax.swing.*;
import java.awt.*;

public class EmployeeView extends JFrame {
    JFrame frame = new JFrame();

    public EmployeeView(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(700,800);

        frame.add(createGUI());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblHeader = new JLabel("Employee:");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblHeader);
        panel.add(Box.createVerticalStrut(20));

        JLabel lblTaskProgress = new JLabel("Task: Progress");
        lblTaskProgress.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblTaskProgress.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTaskProgress);
        panel.add(Box.createVerticalStrut(5));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(50); // TODO: CHANGE THIS
        progressBar.setStringPainted(true);
        progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(progressBar);
        panel.add(Box.createVerticalStrut(20));

        JLabel lblTasks = new JLabel("Tasks:");
        lblTasks.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblTasks.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTasks);
        panel.add(Box.createVerticalStrut(5));

        String[] options = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(comboBox);
        panel.add(Box.createVerticalStrut(10));

        JPanel checklistPanel = new JPanel();
        checklistPanel.setLayout(new BoxLayout(checklistPanel, BoxLayout.Y_AXIS));
        checklistPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        checklistPanel.setBorder(BorderFactory.createTitledBorder("Task Status"));
        checklistPanel.setVisible(false); // hidden initially
        panel.add(checklistPanel);
        panel.add(Box.createVerticalStrut(20));

        comboBox.addActionListener(e -> {
            checklistPanel.removeAll();

            String selectedTask = (String) comboBox.getSelectedItem();

            JCheckBox cbNotDone = new JCheckBox(selectedTask + " - Not Done");
            JCheckBox cbKindaDone = new JCheckBox(selectedTask + " - Kinda Done");
            JCheckBox cbDone = new JCheckBox(selectedTask + " - Done");

            checklistPanel.add(cbNotDone);
            checklistPanel.add(cbKindaDone);
            checklistPanel.add(cbDone);

            checklistPanel.setVisible(true);
            checklistPanel.revalidate();
            checklistPanel.repaint();
        });

        JButton btnGroup = new JButton("Group");
        btnGroup.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnGroup.setMaximumSize(new Dimension(150, 35));
        panel.add(btnGroup);

        return panel;
    }

    public static void main(String[] args){
        new EmployeeView();
    }
}
