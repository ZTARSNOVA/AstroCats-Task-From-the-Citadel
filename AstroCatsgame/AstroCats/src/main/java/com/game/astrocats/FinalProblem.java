package com.game.astrocats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FinalProblem extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;
    
    private ProblemHandler problemHandler;
    private JTextArea codeEditor;
    private JLabel resultLabel;
    private JButton runButton;
    private boolean isCorrectSolution = false;
    

    public interface ProblemHandler {
        void onProblemCompleted();
    }

    public FinalProblem(ProblemHandler handler) {
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
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
   
        JLabel titleLabel = new JLabel("Problem 3: Assimilator Helm – Consciousness Teleport");
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 28));
        titleLabel.setForeground(new Color(173, 255, 47));
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
      
        JPanel contextPanel = createContextPanel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        mainPanel.add(contextPanel, gbc);
      
        JPanel editorPanel = createEditorPanel();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
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
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Courier New", Font.BOLD, 16),
            new Color(173, 255, 47)
        ));
        
        String htmlContent = "<html><body style='font-family: Arial; font-size: 16px; color: white; margin: 0; padding: 0;'>"
            + "<b>Context:</b> After failing to save their planet and unable to complete the orbital jump,<br/>"
            + "the cats activate the Assimilator Helm as a last resort to teleport their<br/>"
            + "consciousness to another universe—one where humanity thrives. The helm<br/>"
            + "encodes their essence into numbers representing human letters (A=1, B=2, ..., Z=26).<br/>"
            + "Create a program that converts these numbers into text readable by humans.<br/><br/>"
            
            + "<b>Example 1:</b><br/>"
            + "Input: [3, 1, 20]<br/>"
            + "Output: \"CAT\"<br/><br/>"
            
            + "<b>Example 2:</b><br/>"
            + "Input: [20, 5, 19, 20]<br/>"
            + "Output: \"TEST\"<br/><br/>"
            ;
        

        JLabel contextLabel = new JLabel(htmlContent);
        contextLabel.setBackground(new Color(72, 61, 139));
        contextLabel.setOpaque(true);
        contextLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
   
        JScrollPane scrollPane = new JScrollPane(contextLabel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        scrollPane.getVerticalScrollBar().setBackground(new Color(30, 30, 30));
        
        contextPanel.add(scrollPane, BorderLayout.CENTER);
        return contextPanel;
    }
    
    private JPanel createEditorPanel() {
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.setBackground(new Color(20, 20, 20));
        editorPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(new Color(50, 100, 150), 1),
            "Code Editor (Python)",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 16),
            new Color(100, 200, 255)
        ));
        
       
        codeEditor = new JTextArea();
        codeEditor.setFont(new Font("Consolas", Font.PLAIN, 16));
        codeEditor.setForeground(Color.WHITE);
        codeEditor.setBackground(new Color(30, 30, 30));
        codeEditor.setCaretColor(Color.GREEN);
        codeEditor.setTabSize(4);
        codeEditor.setLineWrap(true);
        codeEditor.setWrapStyleWord(true);
     
        String initialCode = 
            "def teleport_consciousness(codes):\n" +
            "    # Code here\n" +
            "    return ''\n" +
            "\n" ;
        
        codeEditor.setText(initialCode);
        
     
        JScrollPane editorScrollPane = new JScrollPane(codeEditor);
        editorScrollPane.setBorder(
            new CompoundBorder(
                new LineBorder(new Color(50, 50, 50), 1),
                new EmptyBorder(5, 5, 5, 5)
            )
        );
        
    
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(20, 20, 20));
      
        runButton = new JButton("RUN");
        runButton.setFont(new Font("Courier New", Font.BOLD, 16));
        runButton.setBackground(new Color(173, 216, 230));
        runButton.setForeground(Color.BLACK);
        runButton.setFocusPainted(false);
        runButton.setPreferredSize(new Dimension(150, 40));
        runButton.addActionListener(new RunButtonListener());
        
        buttonPanel.add(runButton, BorderLayout.EAST);
        
    
        resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBackground(new Color(30, 30, 30));
        resultLabel.setOpaque(true);
        resultLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        
        editorPanel.add(editorScrollPane, BorderLayout.CENTER);
        editorPanel.add(buttonPanel, BorderLayout.SOUTH);
        editorPanel.add(resultLabel, BorderLayout.NORTH);
        
        return editorPanel;
    }
    
    private class RunButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            executeCode();
        }
    }
    
    private void executeCode() {
  
        String code = codeEditor.getText();
        
    
        boolean hasFunction = code.contains("def teleport_consciousness(codes):");
        boolean hasCorrectLogic = code.contains("chr(num + 64)") || 
                                 code.contains("chr(num + ord('A') - 1)");
        
        if (hasFunction && hasCorrectLogic) {
            isCorrectSolution = true;
            resultLabel.setForeground(new Color(0, 200, 100));
            resultLabel.setText("¡Éxito! El código se ejecutó correctamente.");
        } else {
            isCorrectSolution = false;
            resultLabel.setForeground(new Color(200, 50, 50));
            resultLabel.setText("Error: El código no produce los resultados esperados.");
        }
        
        if (isCorrectSolution && problemHandler != null) {
            resultLabel.setText(resultLabel.getText() + " ¡Felicidades! Has restaurado la individualidad de los gatos.");


            SwingUtilities.invokeLater(() -> {
                AnimatedGifPanel.showForDuration("/images/ending.gif", 10000, () -> {
                    
                    System.exit(0);
                    SwingUtilities.invokeLater(() -> {
                        problemHandler.onProblemCompleted();
                    });
                });
            });
        }

    }
}