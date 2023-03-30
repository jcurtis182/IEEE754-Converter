//Joseph Curtis
//891970816

package csc4101assignment2;

import java.util.Scanner;


public class CSC4101Assignment2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Input: ");
        String input = scan.nextLine();
        scan.close();
        Convertor.IEEE754(input);

//        Convertor.IEEE754("19.59375");            //identical
//        Convertor.IEEE754("-73.40");              //32bit: last bit incorrect
//        Convertor.IEEE754("16.64");               //identical 
//        Convertor.IEEE754("-265.4671");           //32bit: 2nd to last bit incorrect
//        Convertor.IEEE754("0.085");               
           
    }
    
}
