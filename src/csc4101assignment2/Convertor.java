package csc4101assignment2;

public class Convertor {
    
    public static void IEEE754(String input){
        double inputNumber = Double.parseDouble(input); //convert input string to double
        int wholeNumber = (int) inputNumber; //separate whole and decimal values
        double decimalNumber = inputNumber - wholeNumber;
        boolean isNegative = false; //for checking sign
        boolean isLessThanOne = false;
        
        StringBuilder format23 = new StringBuilder();               
        StringBuilder final32 = new StringBuilder();
        StringBuilder format52 = new StringBuilder();
        StringBuilder final64 = new StringBuilder();
        
        if (inputNumber > 0 && inputNumber < 1) //check for positive decimals < 1
            isLessThanOne = true;
        else if (inputNumber < 0){ //check sign, note and flip if negative
            isNegative = true;
            wholeNumber = Math.abs(wholeNumber); 
            decimalNumber = Math.abs(decimalNumber);
        }
        
        
        final32.append(convertToBinary(wholeNumber, 8)).append(".").append(convertToBinary(decimalNumber, 23)); //convert wholeNumber then append decimal point and converted decimal numbers             
        final64.append(convertToBinary(wholeNumber, 11)).append(".").append(convertToBinary(decimalNumber, 52));
        
        if (isNegative){ //add sign bit
            format23.append("1 ");                                                           
            format52.append("1 ");
        }
        else {
            format23.append("0 ");
            format52.append("0 ");
        }
        
        format23.append(shiftExponent(final32, 8, isLessThanOne)); //shift exp, calc converted wholeNumber, append to final
        format52.append(shiftExponent(final64, 11, isLessThanOne));
                
        System.out.println(format23);                                           
        System.out.println(format52);
    }
    
    public static StringBuilder convertToBinary(int input, int bits){ //converts wholeNumbers  
        StringBuilder binaryString = new StringBuilder();
        for(int i = 0; i < bits; i++){                                   
            if (((int) input % 2) == 0) //if wholeNumber/2 has remainder, add '1', else add '0', then loop using dividend as new wholeNumber
                binaryString.insert(0, "0");
            else 
                binaryString.insert(0, "1");
            input = input/2;
        }
        return binaryString;                                       
    }
    
    
    public static StringBuilder convertToBinary(double input, int bits){ //converts decimal numbers
        StringBuilder binaryString = new StringBuilder();                          
        double wholeNumber;
        for (int i = 0; i < bits; i++){                                                                                 
            if (((int) (input * 2)) == 0){ //if decimalNumber * 2 has wholeNumber, add '1', else add '0'; loop with (product - wholeNumber) as new decimalNumber
                binaryString.append("0");
            }
            else {
                binaryString.append("1");
            }
            wholeNumber = (int) (input*2);
            input = (input*2) - wholeNumber;                                       
        }
        return binaryString;                                                       
    }
    
    public static String shiftExponent(StringBuilder binaryString, int bits, boolean isLessThanOne){                        
        int decimalPosition = binaryString.indexOf("."); //find decimal point position
        int firstOne = 0, shiftVar = 0;
        int exponentUnadjusted, exponentAdjusted;
        for (int i=0; i < binaryString.length(); i++) { //find first '1' in string
            if (binaryString.charAt(i) == '1'){
                firstOne = i;
                break;
            }
        }
        
        if (!isLessThanOne){ //finding exponent (total spaces moved by decimal point)
            exponentUnadjusted = decimalPosition - firstOne - 1; 
            shiftVar = 1;                                                       
        }                                                                       
        else  
            exponentUnadjusted = decimalPosition - firstOne; //special case for when inputNumber < 0
        exponentAdjusted = (int)(exponentUnadjusted + (Math.pow(2,(bits-1))) - 1); //convert exponent using excess/bias notation
                
        binaryString.deleteCharAt(decimalPosition); //replace decimal point
        binaryString.insert(firstOne + shiftVar, ".");                             

        String shiftedString = Convertor.convertToBinary(exponentAdjusted, bits).toString(); //convert result of adjustedExponent to binary
        String shiftedDecimals = binaryString.substring(binaryString.indexOf(".")+1); //isolate and save decimals from combined string
        
        if (!isLessThanOne) shiftedDecimals = shiftedDecimals.substring(0, shiftedDecimals.length()-exponentUnadjusted); //delete excess bits if inputNumber is < 0
        else for (int i = 0; i > exponentUnadjusted; i--) //fill lost bits w/ '0' if inputNumber < 0
                shiftedDecimals += "0";                                                        
        return shiftedString + " " + shiftedDecimals;                           
    }  
}