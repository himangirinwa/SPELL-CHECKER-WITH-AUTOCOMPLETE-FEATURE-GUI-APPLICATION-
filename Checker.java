import java.io.File;
//for reading dictionary.txt
import java.util.*;
//for using ArrayList


class Checker {

    Scanner sc;
    private TrieNode root;
    private ArrayList<String> suggestions;
    
    Checker(){
        //root of trie
        root = new TrieNode();
        //to store autocomplete suggestion
        suggestions = new ArrayList<String>();
    }

    //getter method to return suggestions for autocomplete
    public ArrayList<String> getList() {
        return suggestions;
    }
    


    //Node of trie
    class TrieNode {
        TrieNode[] node;
        boolean isEnd;
        //is true, if the character marks the end of any word

        TrieNode() {
            node = new TrieNode[256];
            
            // Iterate over all possible characters of the string
            for(int i=0; i<256; i++){
                node[i] = null;
            }

            boolean isEnd = false;
        }
    }


    //Inserting a string into the trie
    void Insert(String s){
        TrieNode temp = root;
        
        //traversing string
        for(int i=0; i<s.length(); i++){
            if(temp.node[s.charAt(i)] == null){
                temp.node[s.charAt(i)] = new TrieNode();
            }

            temp = temp.node[s.charAt(i)];
        }

        //marking last letter of the word as end of the word
        temp.isEnd = true;
    }


    boolean isPresent(String s){
        //to check whether a word is present in a trie
        // this returns true if string represents a complete word present in the dictionary
        //And, returns false, if the string represents a prefix (it might or might not be valid prefix)
        TrieNode temp = root;

        for(int i=0; i<s.length(); i++){
            if(temp.node[s.charAt(i)] == null){
                // System.out.println("Word Not Found!");
                //If word not found
                return false;
            }

            temp = temp.node[s.charAt(i)];
        }

        //if complete word is present
        if(temp.isEnd == true){
            return true;
        }
        //word isn't complete, it is a prefix
        //suggesting complete words for the prefix
        else{
            //calling suggester function to suggest complete word
            suggester(s, temp);
            return false;
        }
    }

    void suggester(String s, TrieNode temp){
       
        //If character is the end of a word, then adding the final string to the suggestion list
        if(temp.isEnd == true){
            suggestions.add(s);
        }

        for(int i=0; i<256; i++){
            
            if(temp.node[i] != null){
                s += (char) i;
                suggester(s, temp.node[i]);
            }
        }
    }


    boolean search(String s){
        
        //reading dictionary
        try{
            File myObj = new File("dictionary.txt");
            sc = new Scanner(myObj);
        }
        catch(Exception e){
            System.out.println(e);
        }

        //buinding trie using dictionary.txt
        while(sc.hasNext()){
            this.Insert(sc.nextLine());
            //inserts the word into the trie
        }

        //checks if the string is present, if the string is prefix then returns false
        boolean ans  = this.isPresent(s);
        sc.close();

        return ans;        
    }
}