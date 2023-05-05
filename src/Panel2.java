import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Panel2 extends JPanel {

    private JComboBox<String> typeComboBox;
    private JComboBox<String> topsComboBox;
    private JComboBox<String> bottomsComboBox;
    private JComboBox<String> shoesComboBox;
    private JTextArea outputArea;
    private JButton btnAdd;
    private JButton btnRead;
    private JButton btnDelete;
    private JButton btnClear;
    private JLabel spacingLabel;
    CardLayout cardLayout = new CardLayout();
    JPanel categoryPanel = new JPanel(cardLayout);

    public Panel2() {
        setLayout(new BorderLayout());

        // Create a panel to hold the input components
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        // Category text field
        JLabel categoryLabel = new JLabel("  Category:");

        String[] tops = Clothing.getCategoryTops();
        String[] bottoms = Clothing.getCategoryBottoms();
        String[] shoes = Clothing.getCategoryShoes();

        DefaultComboBoxModel<String> comboModel2 = new DefaultComboBoxModel<String>(tops);
        DefaultComboBoxModel<String> comboModel3 = new DefaultComboBoxModel<String>(bottoms);
        DefaultComboBoxModel<String> comboModel4 = new DefaultComboBoxModel<String>(shoes);
        
        topsComboBox = new JComboBox<>(comboModel2);
        bottomsComboBox = new JComboBox<>(comboModel3);
        shoesComboBox = new JComboBox<>(comboModel4);

        // Add each combobox to the card layout
        categoryPanel.add(topsComboBox, "Top");
        categoryPanel.add(bottomsComboBox, "Bottom");
        categoryPanel.add(shoesComboBox, "Shoes");

        // Clothing type combo box
        JLabel typeLabel = new JLabel("  Clothing type:");
        String[] types = {"Top", "Bottom", "Shoes"};
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(types);
        typeComboBox = new JComboBox<>(comboModel);

        CategoryComboBoxListener categoryComboBoxListener = new CategoryComboBoxListener();
        typeComboBox.addActionListener(categoryComboBoxListener);

        // Add button and listeners
        btnAdd = new JButton("Add");
        btnAdd.addActionListener(new AddClothesButtonListener());

        // Add input components to panel
        inputPanel.add(typeLabel);
        inputPanel.add(typeComboBox);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryPanel);
        
        spacingLabel = new JLabel();
        inputPanel.add(spacingLabel);
        inputPanel.add(btnAdd);

        // Create a panel to hold the buttons
        JPanel btnPanel = new JPanel();

        // Add the three buttons
        btnRead = new JButton("Print Wardrobe");
        btnRead.addActionListener(new ReadButtonListener());
        btnPanel.add(btnRead);

        btnDelete = new JButton("Delete All");
        btnDelete.addActionListener(new DeleteButtonListener());
        btnPanel.add(btnDelete);

        btnClear = new JButton("Clear Screen");
        btnClear.addActionListener(new ClearButtonListener());
        btnPanel.add(btnClear);

        /**
         * Set the size of the buttons equal to each other
         */
        btnDelete.setPreferredSize(btnRead.getPreferredSize());
        btnClear.setPreferredSize(btnRead.getPreferredSize());

        // Output text area
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        // Create a panel to hold the input panel and button panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Add components to frame
        add(mainPanel, BorderLayout.NORTH);
        add(outputScrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        /**
         * Print on first load
         */
        outputArea.setText("Nothing in your wardrobe yet. Add something!\n");
    }

    /**
     * This changes the combobox shown depending on the clothing type selected
     */
    public class CategoryComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String type = (String) typeComboBox.getSelectedItem();
    
            if (type.equals("Top")) {
                cardLayout.show(categoryPanel, "Top");
            } else if (type.equals("Bottom")) {
                cardLayout.show(categoryPanel, "Bottom");
            } else {
                cardLayout.show(categoryPanel, "Shoes");
            }
        }
    }

    /**
     * This is for adding the clothing selected by the user.
     * It pulls from the combobox determined by the clothing type selected
     */
    private class AddClothesButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String type = (String) typeComboBox.getSelectedItem();
            String category;

            if (type == "Top") {
                category = (String) topsComboBox.getSelectedItem();
            } else if (type == "Bottom") {
                category = (String) bottomsComboBox.getSelectedItem();
            } else {
                category = (String) shoesComboBox.getSelectedItem();
            }

            Clothing item = new Clothing(type, category);
            outputArea.append("Added " + item.getCategory() + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());

            // Add the new clothing item to the wardrobe
            SharedWardrobe.wardrobe.addClothing(item);
        }
    }

    /**
     * This simply calls the clear method to delete the contents of the wardrobe.
     */
    private class DeleteButtonListener implements ActionListener {    
        public void actionPerformed(ActionEvent event) {
            SharedWardrobe.wardrobe.clear();
            outputArea.setText("Wardrobe Deleted." + "\n");
            outputArea.append("------------------------" + "\n");
        }
    }

    /**
     * This is a screen clear call that I found that I wanted.
     */
    private class ClearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            outputArea.setText("Cleared!" + "\n");
        }
    }

    /**
     * This prints the contents of the wardrobe to the output area.
     */
    private class ReadButtonListener implements ActionListener {    
        public void actionPerformed(ActionEvent event) {
            printWardrobe();
        }
    }

    /**
     * Read and print the wardrobe
     */
    public void printWardrobe() {
            outputArea.append("------------------------" + "\n");
            outputArea.append("Tops:\n" + SharedWardrobe.wardrobe.getTops() + "\n" +
                              "Bottoms:\n" + SharedWardrobe.wardrobe.getBottoms() + "\n" +
                              "Shoes:\n" + SharedWardrobe.wardrobe.getShoes() + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}
