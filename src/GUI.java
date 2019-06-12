import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GUI {
    //frame
    //panels
    //filemenu
    //buttons to create a deck or load a deck
    //handling to go change frame to new

    JFrame frame;
    JPanel panel;
    JTabbedPane tabbedPane;

    public void init() {

        frame = new JFrame("Flash Card Builder");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new TabbedPane(), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        System.out.println("Started");

    }

    public class TabbedPane extends JPanel {
        public TabbedPane() {
            super(new GridLayout(1, 1));

            JTabbedPane tabbedPane = new JTabbedPane();
            ImageIcon icon = createImageIcon("images/image.jpg");

            CardBuilder createPanel = new CardBuilder();
            tabbedPane.addTab("Create", icon, createPanel,
                    "Create a new deck of flash cards");
            tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

            EditCard editPanel = new EditCard();
            JComponent panel2 = makeTextPanel("Panel #2");
            tabbedPane.addTab("Edit", icon, editPanel,
                    "Edit an existing deck of cards");
            tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

            PlayCard playPanel = new PlayCard();
            JComponent panel3 = makeTextPanel("Panel #3");
            tabbedPane.addTab("Play", icon, playPanel,
                    "Play with a deck of cards");
            tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
            playPanel.setPreferredSize(new Dimension(500, 500));



            //Add the tabbed pane to this panel.
            add(tabbedPane);

            //The following line enables to use scrolling tabs.
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        }






        protected JComponent makeTextPanel(String text) {
            JPanel panel = new JPanel(false);
            JLabel filler = new JLabel(text);
            JButton button = new JButton("test");
            panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(filler,gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(button,gbc);

            return panel;
        }

        protected ImageIcon createImageIcon(String path) {
            java.net.URL imgURL = GUI.class.getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        }

    }
}
