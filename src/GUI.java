import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GUI {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<Card> cardList;
    private JFrame frame;
    private BufferedWriter writer;
    public void init(){
        // build gui
        frame = new JFrame("Quiz Card Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // title bar
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        //create and add vertical scrolls to text area
        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //create next button
        JButton nextButton = new JButton("Next Card");
        cardList = new ArrayList();

        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");

        //add components to panel
        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);

        //create menubar add jmenu file and item new
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        //create menu items
        JMenuItem newMenuItem = new JMenuItem("New Deck");
        JMenuItem saveMenuItem = new JMenuItem("Save Deck");
        JMenuItem loadMenuItem = new JMenuItem("Load Deck");

        //add menu items to menu
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);

        //add file menu to menubar
        menuBar.add(fileMenu);
        //add listeners to buttons
        nextButton.addActionListener(new NextCardEventListener());
        saveMenuItem.addActionListener(new SaveCardEventListener());
        newMenuItem.addActionListener(new NewMenuListener());

        //set menubar to frame
        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500,600);
        frame.setVisible(true);
    }


    public class NextCardEventListener implements ActionListener{
    //TODO add empty card exception handling
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = new Card(question.getText(),answer.getText());
            cardList.add(card);
            clearCards();
        }
    }

    public class SaveCardEventListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = new Card(question.getText(),answer.getText());
            JFileChooser fileSave = new JFileChooser();
            fileSave.showOpenDialog(frame);
            saveFile(fileSave.getSelectedFile());

        }
    }
    //Creates new card deck and clears the card
    public class NewMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            cardList.clear();
            clearCards();
        }
    }
    public void clearCards(){
        question.setText("");
        answer.setText("");
    }

    //Saves the file to a file
    public void saveFile(File file){
        try {
             writer = new BufferedWriter(new FileWriter(file));
            for(Card card:cardList){
                writer.write(card.getQuestion()+"*");
                writer.write(card.getAnswer()+"$");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    //card builder
    //card player
}
