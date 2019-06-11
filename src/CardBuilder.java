import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CardBuilder extends JPanel {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<Card> cardList;
    private JFrame frame;
    private BufferedWriter writer;
    public CardBuilder(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


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

        JButton saveButton = new JButton("Save Deck");
        JButton newButton = new JButton("New Deck");

        //add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(qLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(qScroller, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(aLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(aScroller, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        this.add(nextButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(saveButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(newButton,gbc);




        //add listeners to buttons
        nextButton.addActionListener(new CardBuilder.NextCardEventListener());
        saveButton.addActionListener(new CardBuilder.SaveCardEventListener());
        newButton.addActionListener(new CardBuilder.NewMenuListener());

        //set menubar to frame



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

    public class SaveCardEventListener implements ActionListener {
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



