/*
Brandon Knieriem
Detects whether or not any two values in a text file sum to zero.

This is also my first time implementing unit tests in an IDE.
 */

package com.bknieriem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        // Is there an arg?
        if(args.length > 0 && args[0].length() > 0) {
            String fileName = args[0]; // Easier name to work with.

            // Format validation: Is this a text file? If so, process and print result.
            if(txtFileValidation(fileName)) {
                //-READ----------------------
                ArrayList<Integer> numbers = readFile(fileName);
                //-REPORT--------------------
                for (Integer i : numbers) {
                    System.out.println(i);
                }
                //-PROCESS-------------------
                Boolean result = false; // Negative instantiation because we may skip processing.
                if(numbers.size() > 0) { result = fileZeroSum(numbers); }
                //-------------------------
                System.out.println("Result: " + result);
            }
            else System.out.println("Error: Invalid file format.");
        }
        else System.out.println("Error: Missing input file.");
    }
    //------------------------------------------------------------------------------------------------------------------
    private static Boolean txtFileValidation(String fileName) {
        /*
        Format validation: Does it end with .txt? Regex: \S*.\.txt$ == Any number of non-whitespace, .txt, end of string.

        Compiler kept getting upset about Pattern.compile. No clue why; this seems to be the standard.
        Including as a proof of concept/to investigate later:

        Pattern p = new Pattern.compile();
        Matcher m = p.matcher(fileName);
        if (m.matches()) return true;
         */
        return Pattern.matches("\\S*.\\.txt$", fileName);
    }
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<Integer> readFile(String fileName) throws IOException {
        /*
        Does the file exist? If so, try to read.
        Can we read from it? If so, populate our list of numbers.
         */
        ArrayList<Integer> numbers = new ArrayList<>();

        // Does it exist? Try w/ resources (replaces Finally/BW close).
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // It exists! Read valid integer data, if there is any.
            try {
                Integer nextInt;
                String nextLine;
                for (;;) {
                    nextLine = br.readLine();                   // Fetch the next piece of data from our file.
                    if (nextLine == null) break;                // Break if we've run out of data.

                                                                // Attempt to translate/tally an integer value.
                    try {
                        nextInt = Integer.parseInt(nextLine);
                        numbers.add(nextInt);
                    }
                    catch (NumberFormatException e) { e.printStackTrace(); } // Skip/report invalid data.
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return numbers;
    }
    //------------------------------------------------------------------------------------------------------------------
    private static Boolean fileZeroSum(ArrayList<Integer> numbers) {
        /* Key: Value == Value: Index
        Record both because values can't have the same indices; they can't cancel themselves out.

        O(n), but potentially space complex.
         */
        Map<Integer, Integer> dict = new HashMap<>();
        Integer index = 0;
        Integer complimentNum;

        for (Integer currentNum : numbers) {
            complimentNum = -currentNum; // Register the hypothetical compliment. Does it exist in our dictionary?

            if(dict.containsKey(complimentNum)) {
                System.out.println("Match found: " + currentNum + " at [" + index + "]" + " and " + complimentNum + " at [" + dict.get(complimentNum) + "]");
                return true;
            }
            // After checking, add this number. This way, we won't accidentally overlap indices.
            dict.put(currentNum, index);
            ++index;
        }

        // No complimentary values? Then we fail to find a Zero Sum.
        return false;
    }
    //------------------------------------------------------------------------------------------------------------------
}