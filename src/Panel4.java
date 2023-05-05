import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class Panel4 extends JPanel {
    private DefaultListModel<Outfit> outfitListModel;
    private JList<Outfit> outfitList;
    private JButton btnDelete;
    private JButton btnRefresh;

    public Panel4() {
        setLayout(new BorderLayout());

        /**
         * Create the outfit list model and list
         */
        outfitListModel = new DefaultListModel<>();
        outfitList = new JList<>(outfitListModel);

        /**
         * Add the outfits from SharedOutfits to the list model
         */
        List<Outfit> outfits = SharedOutfits.getOutfits();
        for (Outfit outfit : outfits) {
            outfitListModel.addElement(outfit);
        }

        /**
         *  Create the buttons and listeners
         */
        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new DeleteButtonListener());

        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new RefreshButtonListener());

        /**
         * Create a scroll pane for the outfit list and add it to this panel
         */
        JScrollPane scrollPane = new JScrollPane(outfitList);
        add(scrollPane, BorderLayout.CENTER);

        /**
         * Create the button panel
         */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        /**
         * Add a component listener to this panel to listen to when it's shown or hidden
         */
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // When the panel is shown, refresh the outfit list
                refreshOutfitList();
            }
        });
    }

    public void refreshOutfitList() {
        outfitListModel.clear();
        List<Outfit> outfits = SharedOutfits.getOutfits();
        for (Outfit outfit : outfits) {
            outfitListModel.addElement(outfit);
        }
    }

    public class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = outfitList.getSelectedIndex();
            if (selectedIndex != -1) {
                outfitListModel.remove(selectedIndex);
                SharedOutfits.getOutfits().remove(selectedIndex);
            }
        }
    }

    public class RefreshButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshOutfitList();
        }
    }
}
