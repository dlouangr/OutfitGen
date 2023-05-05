import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Stream;

public class Panel3 extends JPanel {

    private DefaultListModel<String> ruleListModel;
    private JList<String> ruleList;
    private JComboBox<String> clothing1ComboBox;
    private JComboBox<String> clothing2ComboBox;
    JRadioButton pairRadio;
    JRadioButton noPairRadio;
    private JButton btnDelete;
    private JButton btnRefresh;

    public Panel3() {
        setLayout(new BorderLayout());

        /**
         * Create a panel to hold the input components
         */
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));

        /**
         * Create components
         */
        JLabel spacingLabel = new JLabel();
        JLabel text2 = new JLabel("with?");
        text2.setHorizontalAlignment(JLabel.CENTER);
        JButton btnAdd = new JButton("Add Rule");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        /**
         * Create a Pair or Don't Pair check box and surround them in a panel
         */
        JPanel pairPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pairRadio = new JRadioButton("Pair");
        noPairRadio = new JRadioButton("Don't Pair");

        // Set to "Don't Pair" by default
        noPairRadio.setSelected(true);

        pairPanel.add(noPairRadio);
        pairPanel.add(pairRadio);

        /**
         * Clothing 1 combo box
         */
        // Create a new string with every clothes option
        String[] clothing = Stream.of(
        Clothing.getCategoryTops(),
        Clothing.getCategoryBottoms(),
        Clothing.getCategoryShoes())
        .flatMap(Stream::of)
        .toArray(String[]::new);
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>(clothing);
        clothing1ComboBox = new JComboBox<>(comboModel);

        /**
         * Clothing 2 combo box
         * Reuses the list from the first box
         */
        DefaultComboBoxModel<String> comboModel2 = new DefaultComboBoxModel<String>(clothing);
        clothing2ComboBox = new JComboBox<>(comboModel2);

        /**
         * Create the listeners
         */
        btnAdd.addActionListener(new AddButtonListener());
        btnDelete.addActionListener(new DeleteButtonListener());
        btnRefresh.addActionListener(new RefreshButtonListener());

        CheckBoxListener checkBoxListener = new CheckBoxListener();
        pairRadio.addActionListener(checkBoxListener);
        noPairRadio.addActionListener(checkBoxListener);

        /**
         * Create a scroll pane for the outfit list
         */
        JScrollPane scrollPane = new JScrollPane();

        /**
         * Create a list model for the rules
         */
        ruleListModel = new DefaultListModel<String>();
        ruleList = new JList<String>(ruleListModel);
        scrollPane.setViewportView(ruleList);
        
        /**
         * Add the rules to the list model
         */
        Rule[] rules = SharedRules.rulebook.getRules();
        for (Rule rule : rules) {
            ruleListModel.addElement(rule.toString());
        }
        

        /**
         * Create the button panel
         */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnDelete);

        /**
         * Add components to the main panel
         */
        mainPanel.add(pairPanel);
        mainPanel.add(clothing1ComboBox);
        mainPanel.add(text2);
        mainPanel.add(clothing2ComboBox);
        mainPanel.add(spacingLabel);
        mainPanel.add(btnAdd);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        /**
         * Add the mainPanel to the frame
         */
        add(mainPanel, BorderLayout.NORTH);
    }

    public class AddButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            // Create new clothing pieces from the selected types
            Clothing clothing1 = new Clothing("", clothing1ComboBox.getSelectedItem().toString());
            Clothing clothing2 = new Clothing("", clothing2ComboBox.getSelectedItem().toString());
    
            // Determine which checkbox is selected and sets the boolean pair accordingly
            Boolean pair = null;
            if (pairRadio.isSelected()) {
                pair = true;
            } else if (noPairRadio.isSelected()) {
                pair = false;
            }

            if (pair != null) {
                // Checks the rule to see if the two clothing pieces are the same type or category
                if (clothing1.getType().equals(clothing2.getType())) {
                    JOptionPane.showMessageDialog(null,
                                        "Cannot make a rule with two clothing articles of the same type!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Add a new rule with those pieces and the boolean pair
                    Rule rule = new Rule(clothing1, clothing2, pair);
                    SharedRules.rulebook.addRule(rule);
            
                    // Refresh the list of rules to show the newly added rule
                    ruleListModel.clear();
                    Rule[] rules = SharedRules.rulebook.getRules();
                    for (Rule r : rules) {
                        ruleListModel.addElement(r.toString());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                                        "Please select either Pair or Don't Pair!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    public class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = ruleList.getSelectedIndex();
            if (selectedIndex != -1) {
                Rule selectedRule = SharedRules.rulebook.getRules()[selectedIndex];
                SharedRules.rulebook.removeRule(selectedRule);
                ruleListModel.remove(selectedIndex);
            }
        }
    }
    

    public class RefreshButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ruleListModel.clear();
            Rule[] rules = SharedRules.rulebook.getRules();
            for (Rule rule : rules) {
                ruleListModel.addElement(rule.toString());
            }
        }
    }

    public class CheckBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pairRadio) {
                noPairRadio.setSelected(false);
            } else if (e.getSource() == noPairRadio) {
                pairRadio.setSelected(false);
            }
        }
    }
}
