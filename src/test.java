import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        String decompress = "1110000, 1110010, 1110011, 1111000, 1110110, 1110100, 1111001, 1110001, 1111010, 1111011, 1111100, 1111101, 1111110, 1111111, 00011110001111000001010000110101001, 00011100011101101110100111001011101, 00010111000111101001110000111100100, 00001100100011111011010101111001111, 00010100000010101000111010011101111, 00010010001101001101110011001111111, 1110101, 1110000, 001111, 1110000, 001111, 1110000, 001111, 1110000, 001000, 1110111, 001000, 011111010111, 100011110000, 011111000111, 1110010, 001111, 011010100010, 011110010010, 1110010, 101010010010, 100000010010, 1110011, 001000, 0100001111100011, 00011000111111011110100111101101101, 0100001011010011, 1110011, 001000, 1110101, 001000, 0101110010110101, 0101110011100101, 0101110011010101, 0101110010010101, 00000110010100100101110010011000111, 11011011111110101, 101111000101, 1110101, 001000, 1110110, 1110100, 1110110, 1110100, 1110110, 101011100100, 00011011111000111111010000001110000, 1110100, 0101011010110110, 1110100, 1110001, 001111, 1110001, 001001, 0100111111100001, 100010100001, 011010100001, 101011100001, 1110001, 001110";
        String compress = "1110000, 1110010, 1110011, 1111000, 1110110, 1110100, 1111001, 1110001, 1111010, 1111011, 1111100, 1111101, 1111110, 1111111, 00011110001111000001010000110101001, 00011100011101101110100111001011101, 00010111000111101001110000111100100, 00001100100011111011010101111001111, 00010100000010101000111010011101111, 00010010001101001101110011001111111, 1110101, 1110000, 001111, 1110000, 001111, 1110000, 001111, 1110000, 001000, 1110111, 001000, 011111010111, 100011110000, 011111000111, 1110010, 001111, 011010100010, 011110010010, 1110010, 101010010010, 100000010010, 1110011, 001000, 0100001111100011, 00011000111111011110100111101101101, 0100001011010011, 1110011, 001000, 1110101, 001000, 0101110010110101, 0101110011100101, 0101110011010101, 0101110010010101, 00000110010100100101110010011000111, 11011011111110101, 101111000101, 1110101, 001000, 1110110, 1110100, 1110110, 1110100, 1110110, 101011100100, 00011011111000111111010000001110000, 1110100, 0101011010110110, 1110100, 1110001, 001111, 1110001, 001001, 0100111111100001, 100010100001, 011010100001, 101011100001, 1110001, 001110";
        String toPrint = compress;
        for (String inst:toPrint.split(",")) {
            System.out.println(inst);
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
