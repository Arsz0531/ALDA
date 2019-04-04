import java.util.*;

/**
 * Arpad Szell
 * Andreas Ährlund-Richter
 */

public class HuffmanCompressor {

    PriorityQueue<HuffNode> nodeQueue = new PriorityQueue<HuffNode>();
    HashMap<Character, Integer> charFreq = new HashMap<>();
    HashMap<Character, String> charCodes = new HashMap<>();
    HashMap<String,Character> charCodeReverse = new HashMap<>();
    HuffNode root = new HuffNode(0);

    public static void main(String[] args){
        HuffmanCompressor hf = new HuffmanCompressor();
        String compressed = hf.compress(hf.getInput());
        String decompressed = hf.decompressedTree(compressed);
        System.out.println(decompressed);
    }
    public String getInput(){
        return "AAAAABBBBAAAAL";
    }

    private class HuffNode implements Comparable{
        Integer frequency;
        Character character;
        HuffNode leftChild;
        HuffNode rightChild;

        HuffNode(int frequency){
            this.frequency = frequency;
        }

        HuffNode(char character, int frequency){
            this.character = character;
            this.frequency = frequency;
        }
        @Override
        public int compareTo(Object other){
            HuffNode otherNode;
            if(other instanceof HuffNode){
                otherNode = (HuffNode)other;
                return this.frequency - otherNode.frequency;
            }
            return 0;
        }

        private String writeTree(){
            String returnString = "";

            if(leftChild != null){
                returnString += leftChild.writeTree() + "\n";
            }

            returnString += "\n" + frequency + character;

            if(rightChild !=null){
                returnString += rightChild.writeTree();
            }

            return returnString;

        }

    }

    public String decompressedTree(String compressed){
        String decompressed = "";
        HuffNode currentNode = root;

        for(int i = 0; i < compressed.length(); i++){
            char currenctChar = compressed.charAt(i);
            if(currenctChar == '0')
                currentNode = currentNode.leftChild;

            if(currenctChar == '1')
                currentNode = currentNode.rightChild;

            if(currentNode.character != null){
                decompressed += currentNode.character;
                currentNode = root;
            }
        }
        return decompressed;
    }

    public String decomPress(String compressed){

        for(String currentCode : charCodeReverse.keySet()){
            compressed = compressed.replace(""+currentCode,""+charCodeReverse.get(currentCode));
        }
        System.out.println(compressed);
        return compressed;
    }

    public String compress(String input){
        buildFreqMap(input);
        buildNodeQueue();
        root = buildTree();
        System.out.println(root.writeTree());
        assignCharCodes(root,"");
        String compressed = makeCompressedData(input);
        System.out.println(charCodes);
        System.out.println(compressed);

        return compressed;
    }

    private String makeCompressedData(String input){
        String temp = input;
        ArrayList<Character> allCharacter = new ArrayList<>();
        allCharacter.addAll(charFreq.keySet());

        for(Character currentChar : allCharacter){
            temp = temp.replace(""+currentChar,(charCodes.get(currentChar).toString()));
        }

    return temp;
    }

    private void assignCharCodes(HuffNode node, String path) {
        String currentPath = path;
        if (node.character == null) {
            if (node.leftChild != null)
                assignCharCodes(node.leftChild, currentPath + "0");

            if (node.rightChild != null)
                assignCharCodes(node.rightChild, currentPath + "1");

        }else{
            charCodes.put(node.character,currentPath);
            charCodeReverse.put(currentPath, node.character);
        }
    }

    private HuffNode buildTree(){
        HuffNode root = new HuffNode(0);
        HuffNode firstNode;
        HuffNode secondNode;

        while(nodeQueue.size() > 1){
            firstNode = nodeQueue.poll();
            secondNode = nodeQueue.poll();
            HuffNode currentNode = new HuffNode(firstNode.frequency + secondNode.frequency);
            currentNode.leftChild = firstNode;
            currentNode.rightChild = secondNode;
            nodeQueue.add(currentNode);
            if(!(nodeQueue.size() > 1)){
                root = new HuffNode(currentNode.frequency);
                root.leftChild = currentNode.leftChild;
                root.rightChild = currentNode.rightChild;
            }
        }
        return root;
    }

    private void buildFreqMap(String input){
        char[] characters = new char[input.length()];
        input.getChars(0,input.length(),characters,0);
        for(char currentchar : characters){
            Integer currentFreq;
            currentFreq = (charFreq.get(currentchar));
            if(currentFreq == null){
                charFreq.put(currentchar,1);
            }else{
                charFreq.put(currentchar,charFreq.get(currentchar)+1);
            }
        }
    }

    private void buildNodeQueue(){
        Iterator<Map.Entry<Character, Integer>> freqIterator = charFreq.entrySet().iterator();
        while(freqIterator.hasNext()){
            Map.Entry<Character, Integer> entry = freqIterator.next();
            nodeQueue.add(new HuffNode(entry.getKey(),entry.getValue()));
        }
    }

}
