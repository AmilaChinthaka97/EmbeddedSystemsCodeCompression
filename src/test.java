import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        for (String s: args) {
            System.out.println(s);
        }
    }

    /**
     * private static String twoBitMismatchesAnywhereCompression(String instruction, List<String> dictionary){
     *
     *         String compressedInstruction = Constants.CANNOT_COMPRESS.getText();
     *
     *         for (String dictionaryEntry: dictionary) {
     *
     *             String xorResult = xorStrings(instruction, dictionaryEntry);
     *
     *             if (xorResult.chars().filter(ch -> ch == '1').count() == 2) {
     *
     *                 int matchCount = 0;
     *                 String match1 = null;
     *                 String match2 = null;
     *                 for (int i = 0; i < xorResult.length() - 1; i++) {
     *                     if ("1".equals(xorResult.substring(i,i+1))){
     *                         matchCount++;
     *
     *                         if (matchCount == 1){
     *                             match1 = Integer.toBinaryString(i);
     *                             i+=1;
     *                         }else if (matchCount == 2){
     *
     *                             match2 = Integer.toBinaryString(i);
     *
     *                             String dictionaryEntryIndex = Integer.toBinaryString(dictionary.indexOf(dictionaryEntry));
     *                             if (match1.length() != 5) {
     *                                 match1 = String.join("", Collections.nCopies(5 - match1.length(), "0")) + match1;
     *                             }
     *                             if (match2.length() != 5) {
     *                                 match2 = String.join("", Collections.nCopies(5 - match2.length(), "0")) + match2;
     *                             }
     *                             if (dictionaryEntryIndex.length() != 4) {
     *                                 dictionaryEntryIndex = String.join("", Collections.nCopies(4 - dictionaryEntryIndex.length(), "0")) + dictionaryEntryIndex;
     *                             }
     *                             compressedInstruction = Constants.COMPRESSION_SCHEME110.getText() + match1 + match2 + dictionaryEntryIndex;
     *
     *                             break;
     *                         }
     *                     }
     *                 }
     *             }
     *         }
     *         return compressedInstruction;
     *     }
     */
}
