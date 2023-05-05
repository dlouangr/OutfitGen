import java.awt.*;
import javax.swing.*;
import java.net.URL;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;

public class Main extends JFrame {

    private JPanel headerPanel;
    private CardLayout cardLayout;
    private JPanel panel1, panel2, panel3, panel4;

    public Main() {
        setTitle("OutfitGen");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the icon image from the resources folder
        URL iconUrl = getClass().getResource("resources/outfit.png");
        ImageIcon icon = new ImageIcon(iconUrl);
        
        // Set the icon for the JFrame
        setIconImage(icon.getImage());

        // Create the menu bar and menus
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu appearanceMenu = new JMenu("Appearance");
        JMenu infoMenu = new JMenu("Info");

        // Create dropdown for info
        JMenuItem btnAbout = new JMenuItem("About");
        infoMenu.add(btnAbout);

        // Create the radio buttons for light and dark mode
        JRadioButtonMenuItem btnLight = new JRadioButtonMenuItem("Light");
        JRadioButtonMenuItem btnDark = new JRadioButtonMenuItem("Dark");
        JRadioButtonMenuItem btnPurple = new JRadioButtonMenuItem("Purple");
        JRadioButtonMenuItem btnClassic = new JRadioButtonMenuItem("Classic");

        // Add the radio buttons to a ButtonGroup so they are mutually exclusive
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(btnLight);
        modeGroup.add(btnDark);
        modeGroup.add(btnPurple);
        modeGroup.add(btnClassic);

        // Set the purple mode button as selected by default
        btnPurple.setSelected(true);

        // Add the radio buttons to the appearance menu
        appearanceMenu.add(btnLight);
        appearanceMenu.add(btnDark);
        appearanceMenu.add(btnPurple);
        appearanceMenu.add(btnClassic);

        // Create the menu items for file menu
        JMenuItem loadMenuItem = new JMenuItem("Load Wardrobe from File");
        JMenuItem saveMenuItem = new JMenuItem("Save Wardrobe to File");
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Add the menu items to the file menu
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(restartMenuItem);
        fileMenu.add(exitMenuItem);

        // Add the menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(appearanceMenu);
        menuBar.add(infoMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        cardLayout = new CardLayout();

        JLabel titleLabel = new JLabel("OutfitGen - Your Wardrobe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 4));
        JButton btn1 = new JButton("Generate Outfits!");
        JButton btn2 = new JButton("Your Wardrobe");
        JButton btn3 = new JButton("Add/Edit Rules");
        JButton btn4 = new JButton("Saved Outfits");
        btnPanel.add(btn1);
        btnPanel.add(btn2);
        btnPanel.add(btn3);
        btnPanel.add(btn4);
        headerPanel.add(btnPanel, BorderLayout.SOUTH);

        panel1 = new Panel1();
        panel2 = new Panel2();
        panel3 = new Panel3();
        panel4 = new Panel4();

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(panel1, "Panel 1");
        cardPanel.add(panel2, "Panel 2");
        cardPanel.add(panel3, "Panel 3");
        cardPanel.add(panel4, "Panel 4");

        add(headerPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        /**
         * Add listeners to all buttons
         */
        restartMenuItem.addActionListener(e -> {
            dispose();
            new Main();
        });

        saveMenuItem.addActionListener(e -> {
            boolean result = SharedWardrobe.wardrobe.writeToFile();
            if (result == true) {
                JOptionPane.showMessageDialog(null, "Saved to File!");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to save to selected file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadMenuItem.addActionListener(e -> {
            boolean result = SharedWardrobe.wardrobe.loadFromFile();
            if (result == true) {
                JOptionPane.showMessageDialog(null, "Wardrobe Loaded from File!");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to load selected file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAbout.addActionListener(e -> {
            JTextArea info = new JTextArea("OutfitGen - Created by Dominique Louangrath for ITCS 3112\n\n" +
                                           "Working prototype for the program I've been designing over the course of the semester\n" +
                                           "UI provided by FlatLaf: https://github.com/JFormDesigner/FlatLaf");
            info.setEditable(false);
            info.setBackground(UIManager.getColor("OptionPane.background"));
        
            JOptionPane.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);
        });        

        btnLight.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnDark.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnPurple.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnClassic.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        exitMenuItem.addActionListener(e -> System.exit(0));

        btn1.addActionListener(e -> {
            cardLayout.show(cardPanel, "Panel 1");
            titleLabel.setText("Generate Outfits!");
        });
        btn2.addActionListener(e -> {
            cardLayout.show(cardPanel, "Panel 2");
            titleLabel.setText("Your Wardrobe");
        });
        btn3.addActionListener(e -> {
            cardLayout.show(cardPanel, "Panel 3");
            titleLabel.setText("Add/Edit Rules");
        });
        btn4.addActionListener(e -> {
            cardLayout.show(cardPanel, "Panel 4");
            titleLabel.setText("Saved Outfits");
        });

        // Finally, show the panel.
        cardLayout.show(cardPanel, "Panel 2");
        setVisible(true);
    }
}
