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
import java.util.Arrays;
import java.util.stream.IntStream;
import static java.util.stream.IntStream.range;

public class mmmmmm {
    
    public static void main(String[] args) {
        
    
        int[] numbers = {1,2,3,4,5,6,7,9,10,11,12,13};

        int sm = Arrays.stream(numbers).sum(); 
        int sum = 0;
        System.out.println(sm);
        
        for(int i = 1; i <= numbers.length;i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(numbers.length + 1 - (sm  -sum));
  }

    private static void sum(int range, int length) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    


}
   