/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rok
 */
import java.util.ArrayList;

public class mmmmmm {
    
    public static void main(String[] args) {

        String morseCode = "-..- -.-.- -.-   -.-.   -.-.   -.-";
        System.out.println(morseCode.replaceFirst("-", "leofe"));
        
         String translation = "";
         translation.matches("[a-zA-Z]+");
      String[] words = sentence.split(" ");
      
      for(String word : words) {
            char firstLetter = word.charAt(0);
        word = word.replaceFirst(firstLetter , "") + "ay " +
                firstLetter;
        
        translation += word;
      }
      
      return translation;

    }
}
