import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EditCard extends JPanel {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<Card> cardList;
    private JFrame frame;
    private BufferedWriter writer;
    private BufferedReader reader;
    private String[] strCards;
    private int cardCount = 0;

    public EditCard(){
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
        JButton loadButton = new JButton("Load Deck");

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
        this.add(loadButton,gbc);




        //add listeners to buttons
        nextButton.addActionListener(new EditCard.NextCardEventListener());
        saveButton.addActionListener(new EditCard.SaveCardEventListener());
        loadButton.addActionListener(new EditCard.LoadCardEventListener());

        //set menubar to frame



    }


    public class NextCardEventListener implements ActionListener {
        //TODO add empty card exception handling
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = new Card(question.getText(),answer.getText());
            cardList.set(cardCount,card);
            if(cardCount>=cardList.size()-1){
                cardCount =0;
                question.setText(cardList.get(cardCount).getQuestion());
                answer.setText(cardList.get(cardCount).getAnswer());
            }else{
                cardCount++;
                question.setText(cardList.get(cardCount).getQuestion());
                answer.setText(cardList.get(cardCount).getAnswer());
            }



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
    public class LoadCardEventListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String userhome = System.getProperty("user.home");
                JFileChooser fileLoad = new JFileChooser(userhome +"\\repos"+"\\FlashCards");
                fileLoad.showOpenDialog(frame);
                loadFile(fileLoad.getSelectedFile());
            }catch(NullPointerException ex){

            }

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
                writer.write(card.getQuestion()+"/");
                writer.write(card.getAnswer()+"\n");
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

    public void loadFile(File file){
        try {
            reader = new BufferedReader( new FileReader(file));
            String line = null;
            while((line = reader.readLine())!=null){
                makeCard(line);
            }
            question.setText(cardList.get(cardCount).getQuestion());
            answer.setText(cardList.get(cardCount).getAnswer());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeCard(String lineToParase){
        StringTokenizer parser = new StringTokenizer(lineToParase, "/");
        if(parser.hasMoreTokens()){
            Card card = new Card(parser.nextToken(),parser.nextToken());
            cardList.add(card);
        }
    }



    //card builder
    //card player
}
