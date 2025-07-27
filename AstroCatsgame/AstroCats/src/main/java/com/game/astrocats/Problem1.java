package com.game.astrocats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Problem1 extends JPanel {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;

    private ProblemHandler problemHandler;
    private JTextArea codeEditor;
    private JLabel resultLabel;

    public interface ProblemHandler {
        void onProblemCompleted();
    }

    public Problem1(ProblemHandler handler) {
        this.problemHandler = handler;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        createContent();
    }

    private void createContent() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        JLabel titleLabel = new JLabel("Problem 1: Code Rescue");
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 28));
        titleLabel.setForeground(new Color(173, 255, 47));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel contextPanel = createContextPanel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        mainPanel.add(contextPanel, gbc);

        JPanel editorPanel = createEditorPanel();
        gbc.gridx = 1;
        mainPanel.add(editorPanel, gbc);

        add(titleLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createContextPanel() {
        JPanel contextPanel = new JPanel(new BorderLayout());
        contextPanel.setBackground(new Color(106, 90, 205));
        contextPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(new Color(173, 255, 47), 1),
            "Problem Context",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Courier New", Font.BOLD, 16),
            new Color(173, 255, 47)
        ));

        String html = "<html><body style='font-size: 16px; color: white;'>"
            + "<b>Context:</b> The rebel cats found critical code fragments to hack the assimilation system,<br/>"
            + "but they're reversed! Each word in the code was encrypted backward to hide its purpose.<br/>"
            + "Your mission is to restore the original code by reversing each word individually.<br/><br/>"
            + "<b>Example 1:</b><br/>Input: \"tac eht yaw\"<br/>Output: \"cat the way\"<br/><br/>"
            + "<b>Example 2:</b><br/>Input: \"gnitset si siht\"<br/>Output: \"testing is this\"<br/>"
            + "</body></html>";

        JLabel label = new JLabel(html);
        label.setOpaque(true);
        label.setBackground(new Color(72, 61, 139));
        label.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(label);
        scroll.setBorder(null);
        contextPanel.add(scroll, BorderLayout.CENTER);
        return contextPanel;
    }

    private JPanel createEditorPanel() {
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.setBackground(new Color(20, 20, 20));
        editorPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(new Color(50, 100, 150), 1),
            "Code Editor (Python)",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 16),
            new Color(100, 200, 255)
        ));

        codeEditor = new JTextArea("def rescue_code(s):\n    # Code here\n    return ''\n");
        codeEditor.setFont(new Font("Consolas", Font.PLAIN, 16));
        codeEditor.setForeground(Color.WHITE);
        codeEditor.setBackground(new Color(30, 30, 30));
        codeEditor.setCaretColor(Color.GREEN);
        codeEditor.setLineWrap(true);
        codeEditor.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(codeEditor);
        scroll.setBorder(new CompoundBorder(
            new LineBorder(new Color(50, 50, 50), 1),
            new EmptyBorder(5, 5, 5, 5)
        ));

        JButton runButton = new JButton("RUN");
        runButton.addActionListener(new RunButtonListener());
        runButton.setBackground(new Color(173, 216, 230));
        runButton.setFont(new Font("Courier New", Font.BOLD, 16));

        resultLabel = new JLabel(" ");
        resultLabel.setOpaque(true);
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBackground(new Color(30, 30, 30));
        resultLabel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(new Color(20, 20, 20));
        buttons.add(runButton, BorderLayout.EAST);

        editorPanel.add(resultLabel, BorderLayout.NORTH);
        editorPanel.add(scroll, BorderLayout.CENTER);
        editorPanel.add(buttons, BorderLayout.SOUTH);
        return editorPanel;
    }

    private class RunButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String code = codeEditor.getText();
            boolean hasFunction = code.contains("def rescue_code(s):");
            boolean hasCorrectLogic = code.contains("[::-1]") && code.contains("join");

            if (hasFunction && hasCorrectLogic) {
                resultLabel.setForeground(new Color(0, 200, 100));
                resultLabel.setText("✅ Success! Words are correctly reversed.");
                if (problemHandler != null) {
                    problemHandler.onProblemCompleted();
                }
            } else {
                resultLabel.setForeground(new Color(200, 50, 50));
                resultLabel.setText("❌ Incorrect solution. Try reversing each word.");
            }
        }
    }
}
