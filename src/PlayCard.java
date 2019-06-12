import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PlayCard extends JPanel {
    private JTextArea cardArea;
    private ArrayList<Card> cardList;
    private JFrame frame;
    private BufferedReader reader;
    private String[] strCards;
    private int cardCount = 0;
    private boolean flipped = false;
    private JLabel qLabel;

    public PlayCard(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        cardArea = new JTextArea(6,20);
        cardArea.setLineWrap(true);
        cardArea.setWrapStyleWord(true);
        cardArea.setFont(bigFont);

        JScrollPane qScroller = new JScrollPane(cardArea);
            qScroller.setVerticalScrollBarPolicy(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            qScroller.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton flipButton = new JButton("Flip Card");
        JButton loadButton = new JButton("Load Deck");

        //create next button
        JButton nextButton = new JButton("Next Card");
        cardList = new ArrayList();

        qLabel = new JLabel("Question:");



        //add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(qLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(qScroller, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        this.add(flipButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        this.add(nextButton,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(loadButton,gbc);




        //add listeners to buttons
            nextButton.addActionListener(new PlayCard.NextCardEventListener());
            loadButton.addActionListener(new PlayCard.LoadCardEventListener());
            flipButton.addActionListener(new PlayCard.FlipCardEventListener());


        //set menubar to frame
    }

    public class FlipCardEventListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            flipped = !flipped;
            if(flipped){
                cardArea.setText(cardList.get(cardCount).getAnswer());
                qLabel.setText("Answer: ");

            }else{
                cardArea.setText(cardList.get(cardCount).getQuestion());
                qLabel.setText("Question: ");
            }
        }
    }



    public class NextCardEventListener implements ActionListener {
        //TODO add empty card exception handling
        @Override
        public void actionPerformed(ActionEvent e) {
            flipped = false;
            qLabel.setText("Question: ");
            if(cardCount>=cardList.size()-1){
                cardCount =0;
                cardArea.setText(cardList.get(cardCount).getQuestion());

            }else{
                cardCount++;
                cardArea.setText(cardList.get(cardCount).getQuestion());

            }



        }
    }

    public class LoadCardEventListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileLoad = new JFileChooser();
            fileLoad.showOpenDialog(frame);
            loadFile(fileLoad.getSelectedFile());
        }
    }


    public void loadFile(File file){
        try {
            reader = new BufferedReader( new FileReader(file));
            String line = null;
            while((line = reader.readLine())!=null){
                makeCard(line);
            }
            cardArea.setText(cardList.get(cardCount).getQuestion());

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
