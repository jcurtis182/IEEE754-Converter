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
    }
    
}
