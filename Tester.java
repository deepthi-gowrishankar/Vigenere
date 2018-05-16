import edu.duke.*;
import java.util.*;
import java.io.*;
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tester {
    
    public void testCaesarCipher() {
        int key = 3;
        CaesarCipher cc = new CaesarCipher(key);
        FileResource f = new FileResource();
        String message = f.asString();
        String encrypted = cc.encrypt(message);
        String decrypted = cc.decrypt(encrypted);
        System.out.println(message);
        System.out.println(encrypted);
        System.out.println(decrypted);
        
        
    }
    
    public void testCaesarCracker() {
        
        CaesarCracker cc = new CaesarCracker();
        FileResource f = new FileResource();
        String message = f.asString();
        String decrypted = cc.decrypt(message);
        System.out.println(message);
        System.out.println(decrypted);
        
        
    }
    
    public void testVigenereCipher() {
        int[] key ={17,14,12,4};
        VigenereCipher vc = new VigenereCipher(key);
        FileResource f = new FileResource();
        String message = f.asString();
        String encrypted = vc.encrypt(message);
        String decrypted = vc.decrypt(encrypted);
        System.out.println(message);
        System.out.println(encrypted);
        System.out.println(decrypted);
        
        
    }
    


}
