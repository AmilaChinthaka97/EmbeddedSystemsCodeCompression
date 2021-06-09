import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String compression = "1110100, 1111010, 1111011, 1110110, 1111100, 1110001, 1110010, 1110111, 1110100, 1110001, 1111101, 1110100, 1110010, 1110110, 1110100, 1110000, 1110111, 1110001, 001101, 1111000, 1111110, 1111111, 1110000, 1111000, 1110110, 1111001, 1110110, 1110000, 1110010, 001110, 11000011111000100, 1110100, 1110001, 1110101, 001111, 1110101, 1110111, 1111000, 1110000, 1110010, 1111001, 1111000, 1110110, 1110111, 1110100, 1110001, 1110111, 1110001, 1110100, 1110110, 1111001, 1111000, 0101101110010110, 1110111, 1110010, 1110000, 001110, 1110100, 1110000, 1110100, 1110010, 1111001, 1110001, 1110000, 0101000011010100, 1111000, 1110110, 1110100, 1110010, 1110110, 1110001, 1110100, 1110000, 1111001, 00011001000111110110101011110011111, 0100000010101000, 1110100, 1110111, 11001000110100110, 1110011, 001111, 1110011, 001010, 0100101110010011";
        //        String compression = "1110100, 1111010, 1111011, 1110110, 1111100, 1110001, 1110010, 1110111, 1110100, 1110001, 1111101, 1110100, 1110010, 1110110, 1110100, 1110000, 1110111, 1110001, 001101, 1111000, 1111110, 1111111, 1110000, 1111000, 1110110, 1111001, 1110110, 1110000, 1110010, 001110, 11000011111000100, 1110100, 1110001, 1110101, 001111, 1110101, 1110111, 1111000, 1110000, 1110010, 1111001, 1111000, 1110110, 1110111, 1110100, 1110001, 1110111, 1110001, 1110100, 1110110, 1111001, 1111000, 0101101110010110, 1110111, 1110010, 1110000, 001110, 1110100, 1110000, 1110100, 1110010, 1111001, 1110001, 1110000, 0101000011010100, 1111000, 1110110, 1110100, 1110010, 1110110, 1110001, 1110100, 1110000, 1111001, 00011001000111110110101011110011111, 0100000010101000, 1110100, 1110111, 11001000110100110, 1110011, 001111, 1110011, 001010, 0100101110010011";
        List<String> instructions = Arrays.asList(compression.split(","));

        for (String instruction:instructions) {
            System.out.println(instruction);

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
