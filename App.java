import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

//GUI USING JAVA SWING
class App {
    public static void main(String[] args){
        
        JFrame f = new JFrame("SPELL CHECKER");

        //Displaying logo
        ImageIcon image = new ImageIcon("index.png");
        JLabel imageLabel = new JLabel(image); 
        imageLabel.setBounds(33, 25, 800, 192);
        imageLabel.setVisible(true);
        f.add(imageLabel);


        JLabel inWord = new JLabel("ENTER A WORD : ");
        inWord.setFont(new Font("Verdana", Font.BOLD, 24));
        inWord.setBounds(125, 235, 300, 50);
        inWord.setVisible(true);
        f.add(inWord);

        //textfield to take input from the user
        JTextField input = new JTextField();
        input.setFont(new Font("Verdana", Font.PLAIN, 24));
        input.setBounds(380, 240, 375, 42);
        input.setVisible(true);
        f.add(input);

        JLabel suggestion = new JLabel("SUGGESTIONS : ");
        suggestion.setFont(new Font("Verdana", Font.PLAIN, 22));
        suggestion.setBounds(51, 320, 200, 50);
        suggestion.setVisible(true);
        f.add(suggestion);

        //results and suggestion will be displayed in this panel
        JPanel panel = new JPanel();
        panel.setBounds(51, 371, 775, 322);
        
        //search image(this image will show at the start before user searches for any string)
        ImageIcon searchImage = new ImageIcon("solution.png");
        JLabel searchImgL = new JLabel(searchImage); 
        searchImgL.setSize(620, 170);
        searchImgL.setVisible(true);
        panel.add(searchImgL);
        panel.setBorder(BorderFactory.createEtchedBorder());
        //provides border to our panel
        panel.setVisible(true);
        f.add(panel);


        //attaching action listener to textField to get the text searched by user
        //it listens to the event when the ENTER key is hit
        input.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                //getting text in the textField
                String s = input.getText();

                //searching for the string
                Checker g = new Checker();
                //searching to lowercase because the .txt file contains all the words in small case
                //and as words are stored with respect to assci value in trie, then capital and smallcase may differ
                boolean res = g.search(s.toLowerCase());

                //if the string represents the correct spelling of the word and is present in dictionary
                if(res){
                    searchImgL.setVisible(false);

                    //acknowleding the correct answer
                    suggestion.setText("RESULT : ");
                    ImageIcon correctImage = new ImageIcon("correct.png");
                    JLabel correct = new JLabel(correctImage); 
                    correct.setVisible(true);
                    panel.add(correct);
                }
                //if string represents a pre word or an invalid word
                else {
                    searchImgL.setVisible(false);
                    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 39, 52));
                    
                    //getting the suggestions list 
                    ArrayList<String> list = g.getList();

                    //if list has some contains in it ans symbolises a pre-word
                    //then suggesting autocomplete
                    if(list.size() > 0){
                        //panel will show at max 9 suggestions
                        int length = (list.size())>=9?(9):(list.size());

                        for(int i=0; i<length; i++){
                            JButton b = new JButton(list.get(i).toUpperCase());
                            b.setFont(new Font("Verdana", Font.PLAIN, 22));
                            b.setBackground(Color.green);
                            b.setVisible(true);
                            panel.add(b);
                        }
                    }
                    //if the word is invalid
                    else{
                        searchImgL.setVisible(false);
                        ImageIcon image4 = new ImageIcon("notFound.png");
                        JLabel notFound = new JLabel(image4); 
                        notFound.setVisible(true);
                        panel.add(notFound);
                    }
                }

            }
        });

        f.setSize(910, 780);
        f.setLayout(null); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);   
    }

}