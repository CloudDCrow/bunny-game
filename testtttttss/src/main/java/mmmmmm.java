/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rok
 */
import static java.lang.Integer.sum;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;
import static java.util.stream.IntStream.range;

public class mmmmmm {
    
    public static void main(String[] args) {
        
        String a = "aefsdfsefsaefasaf";
        String[] arr = a.split("");
         String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
      
      for(int i = 0; i < alphabet.length(); i++) {
        if(a.contains(alphabet.charAt(i) + "")) {
            builder.append(alphabet.charAt(i));
        }
      }
      
        System.out.println(builder);
   }
}
   