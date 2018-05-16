import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder sb = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            sb.append(message.charAt(i));
        }
        //Remember StringBuilder
        //Counting for loops, maybe starting at different variable numbers
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for (int i = 0; i < klength; i++) {
            String s = sliceString(encrypted, i, klength);
            int k = cc.getKey(s);
            key[i] = k;
        }
        
        //WRITE YOUR CODE HERE - use caesarcracker
        //System.out.println("Key is " + Arrays.toString(key));
        return key;
        
    }

    public void breakVigenere () {
        //WRITE YOUR CODE HERE
        FileResource m = new FileResource();
        
        String message = m.asString();
        
        FileResource dictionary = new FileResource();
        HashSet<String> dict = readDictionary(dictionary);
        
        int keyLength = 4;
        char mostCommon = 'e';
        int[] key = tryKeyLength(message, keyLength, mostCommon);
        VigenereCipher vc = new VigenereCipher(key);
        //String decrypt = vc.decrypt(message);
        String decrypt = breakForLanguage(message,dict);
        System.out.println(message);
        System.out.println(decrypt);
        //.asString() can read a fileresource file
        // use tryKeyLength to break key, e is most common
        // use vigenereciper's decrypt method
    }
    
    public HashSet<String> readDictionary (FileResource fr) {
        HashSet<String> dict = new HashSet<String>();
        
        for (String line : fr.lines()) {
            String lcWord = line.toLowerCase();
            dict.add(lcWord);
        }
        
        return dict;
    }
    
    public int countWords(String message, HashSet<String> dictionary) {
        int countWords = 0;
        for (String word : message.split("\\W+")) {
            word = word.toLowerCase();
            if (dictionary.contains(word)) {
                countWords++;
            }
            
        }
        //System.out.println(countWords);
        return countWords;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        
        int[] wordcount = new int[100];
        char mostCommonCharIn = mostCommonCharIn(dictionary);
        
        for (int i = 1; i < 100; i++) {
           int[] keyAttempt = tryKeyLength(encrypted, i+1, mostCommonCharIn);
           VigenereCipher vc = new VigenereCipher(keyAttempt);
           String attempt = vc.decrypt(encrypted);
           wordcount[i] = countWords(attempt, dictionary);
        }
           
        int max = 0;
        int maxInd = 0;
        for (int j =1; j<100; j++) {    
            if (wordcount[j] > max) {
               max = wordcount[j];
               maxInd = j;
            }
        
        }
        
        
        int key = maxInd + 1;
        char mostCommon = mostCommonCharIn(dictionary);
        int[] rightKey = tryKeyLength(encrypted, key, mostCommon);
        int[] try38 = tryKeyLength(encrypted, 38, mostCommon);
        
        VigenereCipher vc = new VigenereCipher(rightKey);
        String decryption = vc.decrypt(encrypted);
        System.out.println(decryption);
        System.out.println("Key: " + Arrays.toString(rightKey));
        System.out.println("Key length: " + rightKey.length);
        System.out.println("Valid Words: " + wordcount[maxInd]);
        System.out.println("Valid Words: " + wordcount[37]);
        return decryption;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary) {
      
        HashMap<String,Integer> chars = new HashMap<String, Integer>();
        for (char c = 'a'; c <= 'z'; c++) {
          chars.put(String.valueOf(c),0);
        }
        for (String s : dictionary) {
            s = s.toLowerCase();
            String[] letters = s.split("");
                for (String letter : chars.keySet()) {
                    for (String str : letters) {
                       if (str.equals(letter)) {
                           chars.put(letter,chars.get(letter)+1);
                        }
                    }
                }
            
        }
        
        String mostCommonCharIn = "";
        int max = 0;
        
        for (Map.Entry<String,Integer> entry : chars.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostCommonCharIn = entry.getKey();
            }
        }
        return mostCommonCharIn.charAt(0);
      }
    
    public HashMap<String,String> breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {
         HashMap<String, String> decrpytedMessages = new HashMap<String, String>();
         String language = "";
         int wordcount = 0;
         for (String lang: languages.keySet()) {
             System.out.println("Currently breaking into "+lang);
             String decrypted_string = breakForLanguage(encrypted, languages.get(lang));
             //System.out.println(decrypted_string);
             int count = countWords(decrypted_string, languages.get(lang));
             if (wordcount < count) {
                 wordcount = count;
                 language = lang;
             }
             //System.out.println(count + " valid words\n");
             System.out.println();
             decrpytedMessages.put(lang, decrypted_string);
         }
         System.out.println("The language of this message is " + language);
         System.out.println(wordcount + " valid words\n");
         return decrpytedMessages;
        
    }
    
    public void breakVigenere3() {
    	FileResource fr = new FileResource();
        String message = fr.asString();
    	HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
    	DirectoryResource dr = new DirectoryResource();
    	for (File d: dr.selectedFiles()) {
    		FileResource fr2 = new FileResource(d.toString());
    		HashSet<String> result = new HashSet<String>();
            for (String line: fr2.lines()) {
                line = line.toLowerCase();
                result.add(line);
            }
            languages.put(d.getName(), result);
            //System.out.println("Finished reading "+f.getName());
    	}
    	HashMap<String, String> decrypted = breakForAllLanguages(message, languages);
        //System.out.println(decrypted.get("English"));
    }
    
}
