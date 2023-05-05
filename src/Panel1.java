import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class Panel1 extends JPanel {
    
    private JTextArea outputArea;
    JButton btnGenerate = new JButton("Generate Outfit");
    JButton btnSave = new JButton("Save Outfit");
    SharedOutfits sharedOutfits = new SharedOutfits();
    SharedRules sharedRules = new SharedRules();
    Outfit result = new Outfit();

    public Panel1() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        // Create GUI components
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel();
        outputArea = new JTextArea(10, 30);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputArea.setEditable(false);
        
        // Add action listeners
        btnGenerate.addActionListener(new GenerateButtonListener());
        btnSave.addActionListener(new SaveButtonListener());
        
        // Set buttons equal size to each other
        btnSave.setPreferredSize(btnGenerate.getPreferredSize());
        btnGenerate.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        
        // Add components to panels
        btnPanel.add(btnGenerate);
        btnPanel.add(btnSave);
        mainPanel.add(btnPanel, BorderLayout.NORTH);
        mainPanel.add(outputScrollPane, BorderLayout.CENTER);
        
        // Add panels to main panel
        add(mainPanel);
    }

    public class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            result = SharedWardrobe.wardrobe.generateOutfit(SharedRules.rulebook);
            if (result == null) {
                outputArea.append("The wardrobe is either empty, or your rules do not allow for " + 
                                  "an outfit to be made from your current wardrobe.\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            } else {
                outputArea.append("Your Outfit:" + "\n");
                outputArea.append(result.toString() + "\n");
                outputArea.append("------------------------" + "\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            }
        }
    }
    
    public class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (result != null) {
                SharedOutfits.addOutfit(result);
                outputArea.append("Outfit Saved!" + "\n");
                outputArea.append("------------------------" + "\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            } else {
                outputArea.append("No outfit to save!" + "\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            }
        }
    }
}
