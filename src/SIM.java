import java.io.*;
import java.util.*;

/**
 * @author Amila Abeysundara
 * @since 05-06-2021
 */
public class SIM {

    public static void main(String[] args){
        try {
            for (String input: args) {
                if ("1".equals(input)) {
                    compression();
                }else if ("2".equals(input)){
                    decompression();
                }
            }
        }catch (FileNotFoundException ex){
            System.out.println(ex);
        }
    }

    private static void decompression() throws FileNotFoundException {

        Map<String, Object> dictionaryAndCompressedInstructions = extractDictionaryAndCompressedInstructions();


        List<String> compressedInstructions = retrieveCompressedInstructions((StringBuilder) dictionaryAndCompressedInstructions.get(Constants.COMPRESSED_PROGRAM.getText()));
        List<String> dictionary = (List<String>) dictionaryAndCompressedInstructions.get(Constants.DICTIONARY.getText());

        List<String> decompressedInstructions = new ArrayList<>();

        for (String compressedInstruction:compressedInstructions) {

            String header = compressedInstruction.substring(0,3);

            if (Constants.COMPRESSION_SCHEME000.getText().equals(header)){
                decompressedInstructions.add(originalBinaryDecompression(compressedInstruction));

            }else if (Constants.COMPRESSION_SCHEME001.getText().equals(header)){
                decompressedInstructions.addAll(runningLengthDecompression(compressedInstruction,decompressedInstructions.get(decompressedInstructions.size()-1)));

            }else if (Constants.COMPRESSION_SCHEME010.getText().equals(header)){
                decompressedInstructions.add(bitMaskBasedDecompression(compressedInstruction, dictionary));

            }else if (Constants.COMPRESSION_SCHEME011.getText().equals(header)){
                decompressedInstructions.add(oneBitMismatchDecompression(compressedInstruction, dictionary));

            }else if (Constants.COMPRESSION_SCHEME100.getText().equals(header)){
                decompressedInstructions.add(twoBitConsecutiveMismatchDecompression(compressedInstruction, dictionary));

            }else if (Constants.COMPRESSION_SCHEME101.getText().equals(header)){
                decompressedInstructions.add(fourBitConsecutiveMismatchDecompression(compressedInstruction, dictionary));

            }else if (Constants.COMPRESSION_SCHEME110.getText().equals(header)){
                decompressedInstructions.add(twoBitMismatchesAnywhereDecompression(compressedInstruction, dictionary));

            }else if (Constants.COMPRESSION_SCHEME111.getText().equals(header)){
                decompressedInstructions.add(directMatchingDecompress(compressedInstruction, dictionary));
            }
        }
        writeDecompressedCodeToTxt(decompressedInstructions);
    }


    /**
     * <h1>This method extracts the dictionary and compressed instructions from compressed.txt</h1>
     * @return dictionaryAndCompressedInstructions
     * @throws FileNotFoundException
     */
    private static Map<String, Object> extractDictionaryAndCompressedInstructions() throws FileNotFoundException {

        Map<String, Object> dictionaryAndCompressedInstructions = new HashMap<>();

        File compressedTxt = new File(Constants.COMPRESSED.getText());
        Scanner compressedTxtScanner = new Scanner(compressedTxt);

        StringBuilder compressedCode = new StringBuilder();
        List<String> dictionary = new ArrayList<>();
        boolean isDictionary = false;

        while (compressedTxtScanner.hasNext()){
            String currentLine = compressedTxtScanner.next();

            if (!isDictionary && !Constants.SEPARATOR.getText().equals(currentLine)){
                compressedCode.append(currentLine);
            }else if (Constants.SEPARATOR.getText().equals(currentLine)){
                isDictionary = true;
                continue;
            }else if (isDictionary){
                dictionary.add(currentLine);
            }
        }
        dictionaryAndCompressedInstructions.put(Constants.DICTIONARY.getText(), dictionary);
        dictionaryAndCompressedInstructions.put(Constants.COMPRESSED_PROGRAM.getText(), compressedCode);

        return dictionaryAndCompressedInstructions;
    }

    /**
     * <h1>This method retrieves the compressed instructions</h1>
     * @param compressedCodeBuilder
     * @return compressedInstructionsList
     */
    private static List<String> retrieveCompressedInstructions(StringBuilder compressedCodeBuilder){
        List<String> compressedInstructions = new ArrayList<>();

        for (int i =0; i < compressedCodeBuilder.length(); i++){
            String header = compressedCodeBuilder.substring(i,i+3);

            if (header.equals(Constants.COMPRESSION_SCHEME000.getText())){
                try {
                    compressedInstructions.add(compressedCodeBuilder.substring(i, i + 35));
                    i += 34;
                }catch (StringIndexOutOfBoundsException ex){
                    break;
                }
            }else if (header.equals(Constants.COMPRESSION_SCHEME001.getText())){
                compressedInstructions.add(compressedCodeBuilder.substring(i,i+6));
                i+=5;
            }else if (header.equals(Constants.COMPRESSION_SCHEME010.getText())){
                compressedInstructions.add(compressedCodeBuilder.substring(i,i+16));
                i+=15;
            }else if (header.equals(Constants.COMPRESSION_SCHEME011.getText())){
                compressedInstructions.add(compressedCodeBuilder.substring(i,i+12));
                i+=11;
            }else if (header.equals(Constants.COMPRESSION_SCHEME100.getText())){
                compressedInstructions.add(compressedCodeBuilder.substring(i,i+12));
                i+=11;
            }else if (header.equals(Constants.COMPRESSION_SCHEME101.getText())){
                compressedInstructions.add(compressedCodeBuilder.substring(i,i+12));
                i+=11;
            }else if (header.equals(Constants.COMPRESSION_SCHEME110.getText())){
                compressedInstructions.add(compressedCodeBuilder.substring(i,i+17));
                i+=16;
            }else if (header.equals(Constants.COMPRESSION_SCHEME111.getText())){
                compressedInstructions.add(compressedCodeBuilder.substring(i,i+7));
                i+=6;
            }
        }
        return compressedInstructions;
    }



    /**
     * <h1>Decompression Schemes</h1>
     */

    /**
     * <h1>This method decompresses according to original binary decompression</h1>
     * @param instruction
     * @return decompressedInstruction
     */
    private static String originalBinaryDecompression(String instruction){
        return instruction.substring(3);
    }


    /**
     * <h1>This method decompresses according to running length decompression</h1>
     * @param currentCompressedInstruction
     * @param previousDecompressedInstruction
     * @return decompressedInstructionsList
     */
    private static List<String> runningLengthDecompression(String currentCompressedInstruction, String previousDecompressedInstruction){
        List<String> decompressedInstruction = new ArrayList<>();

        for (int i=0; i < Integer.parseInt(currentCompressedInstruction.substring(3),2) + 1; i++){
            decompressedInstruction.add(previousDecompressedInstruction);
        }

        return decompressedInstruction;
    }


    /**
     * <h1>This method decompresses according to bit mask based decompression</h1>
     * @param instruction
     * @param dictionary
     * @return decompressedInstruction
     */
    private static String bitMaskBasedDecompression(String instruction, List<String> dictionary){

        String startingLocation = instruction.substring(3,8);
        String bitMask = instruction.substring(8,12);
        String dictionaryEntryIndex = instruction.substring(12);

        String xorResult = String.join("",Collections.nCopies(Integer.parseInt(startingLocation,2),"0")) + bitMask;
        xorResult = xorResult + String.join("",Collections.nCopies(32-xorResult.length(),"0"));

        return xorStrings(xorResult, dictionary.get(Integer.parseInt(dictionaryEntryIndex,2)));
    }


    /**
     * <h1>This method decompresses according to one bit mismatch decompression</h1>
     * @param instruction
     * @param dictionary
     * @return decompressedInstruction
     */
    private static String oneBitMismatchDecompression(String instruction, List<String> dictionary){
        String dictionaryEntryIndex = instruction.substring(9);
        String dictionaryEntry = dictionary.get(Integer.parseInt(dictionaryEntryIndex,2));
        String mismatchLocation = instruction.substring(3,8);
        int from = Integer.parseInt(mismatchLocation,2);
        int to = from + 1;

        return dictionaryEntry.substring(0,from) + flipBits(dictionaryEntry.substring(from,to)) + dictionaryEntry.substring(to);
    }


    /**
     * <h1>This method decompresses according to two bit consecutive mismatch decompression</h1>
     * @param instruction
     * @param dictionary
     * @return decompressedInstruction
     */
    private static String twoBitConsecutiveMismatchDecompression(String instruction, List<String> dictionary){
        String dictionaryEntryIndex = instruction.substring(9);
        String dictionaryEntry = dictionary.get(Integer.parseInt(dictionaryEntryIndex,2));
        String mismatchLocation = instruction.substring(3,8);
        int from = Integer.parseInt(mismatchLocation,2);
        int to = from + 2;

        return dictionaryEntry.substring(0,from) + flipBits(dictionaryEntry.substring(from,to)) + dictionaryEntry.substring(to);
    }

    /**
     * <h1>This method decompresses according to four bit consecutive mismatch decompression</h1>
     * @param instruction
     * @param dictionary
     * @return decompressedInstruction
     */
    private static String fourBitConsecutiveMismatchDecompression(String instruction, List<String> dictionary){
        String dictionaryEntryIndex = instruction.substring(9);
        String dictionaryEntry = dictionary.get(Integer.parseInt(dictionaryEntryIndex,2));
        String mismatchLocation = instruction.substring(3,8);
        int from = Integer.parseInt(mismatchLocation,2);
        int to = from + 4;

        return dictionaryEntry.substring(0,from) + flipBits(dictionaryEntry.substring(from,to)) + dictionaryEntry.substring(to);
    }


    /**
     * <h1>This method decompresses according to two bit mismatches anywhere decompression</h1>
     * @param instruction
     * @param dictionary
     * @return decompressedInstruction
     */
    private static String twoBitMismatchesAnywhereDecompression(String instruction, List<String> dictionary){
        String dictionaryEntryIndex = instruction.substring(13);
        String dictionaryEntry = dictionary.get(Integer.parseInt(dictionaryEntryIndex,2));

        String mismatchLocation1 = instruction.substring(3,8);
        String mismatchLocation2 = instruction.substring(8,13);
        int from1 = Integer.parseInt(mismatchLocation1,2);
        int to1 = from1 + 1;
        int from2 = Integer.parseInt(mismatchLocation2,2);
        int to2 = from2 + 1;

        String answer = dictionaryEntry.substring(0,from1);
        answer += flipBits(dictionaryEntry.substring(from1,to1));
        answer += dictionaryEntry.substring(to1,from2);
        answer += flipBits(dictionaryEntry.substring(from2,to2));
        answer += dictionaryEntry.substring(to2);
        return answer;
    }


    /**
     * <h1>This method decompresses according to direct matching decompression</h1>
     * @param instruction
     * @param dictionary
     * @return decompressedInstruction
     */
    private static String directMatchingDecompress(String instruction, List<String> dictionary){
        String ditionaryEntryIndex = instruction.substring(3);

        return dictionary.get(Integer.parseInt(ditionaryEntryIndex,2));
    }


    /**
     * <h1>This method flips bits given</h1>
     * @param stringToFlip
     * @return flipped
     */
    private static String flipBits(String stringToFlip){

        String flipped = xorStrings(String.join("",Collections.nCopies(stringToFlip.length(),"1")), stringToFlip);

        return flipped;
    }


    /**
     * <h1>This method writes decompressed code to dout.txt</h1>
     * @param decompressedInstructions
     */
    private static void writeDecompressedCodeToTxt(List<String> decompressedInstructions){

        try {
            FileWriter decompressedTxt = new FileWriter(Constants.DECOMPRESSED_OUTPUT.getText());
            BufferedWriter decompressed = new BufferedWriter(decompressedTxt);

            for (int i=0; i<decompressedInstructions.size(); i++) {
                String instruction = decompressedInstructions.get(i);
                decompressed.write(instruction);
                decompressed.newLine();
            }
            decompressed.close();
            decompressedTxt.close();
        }catch (IOException ex){
            System.out.println(ex);
        }
    }


    /**
     * <h1>This method compresses the original code</h1>
     * @throws FileNotFoundException
     */
    private static void compression() throws FileNotFoundException {
        Map<String, List<String >> dictionaryAndOriginalProgram = createDictionary();

        List<String> originalProgram = dictionaryAndOriginalProgram.get(Constants.ORIGINAL_PROGRAM.getText());
        List<String> dictionary = dictionaryAndOriginalProgram.get(Constants.DICTIONARY.getText());

        List<String> compressedInstructions = new ArrayList<>();

        boolean isPreviousInstructionRLE = false;

        for (int i = 0; i < originalProgram.size(); i++){

            String instruction = originalProgram.get(i);

            List<String> compressedCurrentInstruction = new ArrayList<>();

            compressedCurrentInstruction.add(originalBinaryCompression(instruction));

            if (i != 0 && !isPreviousInstructionRLE){
                compressedCurrentInstruction.add(runningLengthCompression(i, originalProgram, isPreviousInstructionRLE));
            }else{
                compressedCurrentInstruction.add(Constants.CANNOT_COMPRESS.getText());
            }
            compressedCurrentInstruction.add(bitMaskBasedCompression(instruction, dictionary));
            compressedCurrentInstruction.add(oneBitMismatchCompression(instruction, dictionary));
            compressedCurrentInstruction.add(twoBitConsecutiveMismatchCompression(instruction, dictionary));
            compressedCurrentInstruction.add(fourBitConsecutiveMismatchCompression(instruction, dictionary));
            compressedCurrentInstruction.add(twoBitMismatchesAnywhereCompression(instruction, dictionary));
            compressedCurrentInstruction.add(directMatchingCompression(instruction, dictionary));

            String optimalCompression = chooseOptimalCompression(compressedCurrentInstruction);
            compressedInstructions.add(optimalCompression);

            if (optimalCompression.equals(compressedCurrentInstruction.get(1))){
                isPreviousInstructionRLE = true;
                i += Integer.parseInt(optimalCompression.substring(3),2);
            }else {
                isPreviousInstructionRLE = false;
            }
        }
        writeCompressedCodeToTxt(compressedInstructions, dictionary);
    }


    /**
     * <h1>Eight Compression Schemes</h1>
     */

    /**
     *<h1>This method compresses instruction according to the original binary scheme</h1>
     * @param instruction
     * @return compressedInstruction
     */
    private static String originalBinaryCompression(String instruction){
        return Constants.COMPRESSION_SCHEME000.getText() + instruction;
    }


    /**
     * <h1>This method compresses instruction according to the running length scheme</h1>
     * @param i
     * @param originalProgram
     * @return compressedInstruction
     */
    private static String runningLengthCompression(int i, List<String> originalProgram, boolean isPreviousInstructionRLE){

        String instruction = originalProgram.get(i);
        String compressedInstruction = null;

        if (instruction.equals(originalProgram.get(i-1))){
            int runLength = 1;

            int j = i + 1;

            while (true){
                if (instruction.equals(originalProgram.get(j)) && runLength < 8 && !isPreviousInstructionRLE ){
                    runLength+=1;
                    j++;
                }else {
                    break;
                }
            }

            String binaryRunLength = Integer.toBinaryString(runLength-1);

            if (binaryRunLength.length() != 3){
                binaryRunLength = String.join("", Collections.nCopies(3 - binaryRunLength.length(),"0")) + binaryRunLength;
            }

            compressedInstruction = Constants.COMPRESSION_SCHEME001.getText() + binaryRunLength;


        }else {
            compressedInstruction = Constants.CANNOT_COMPRESS.getText();
        }

        return compressedInstruction;
    }


    /**
     * <h1>This method compresses instruction according to the bitmask compression scheme</h1>
     * @param instruction
     * @param dictionary
     * @return compressedInstruction
     */
    private static String bitMaskBasedCompression(String instruction, List<String> dictionary){

        String compressedInstruction = Constants.CANNOT_COMPRESS.getText();

        for (String dictionaryEntry: dictionary) {

            String xorResult = xorStrings(instruction, dictionaryEntry);
            String bitMask = null;
            String startingLocation = null;

            for (int j = 0; j < xorResult.length() - 3; j++){

                if (('1' == xorResult.charAt(j)) && xorResult.substring(j+5).chars().filter(ch -> ch == '1').count() == 0){
                    bitMask = xorResult.substring(j,j+4);
                    startingLocation = Integer.toBinaryString(j);
                    break;

                }else if ('0' == xorResult.charAt(j)){
                    continue;

                }else {
                    break;
                }
            }
            if (bitMask != null && startingLocation != null) {
                String dictionaryEntryIndex = Integer.toBinaryString(dictionary.indexOf(dictionaryEntry));

                if (startingLocation.length() != 5) {
                    startingLocation = String.join("", Collections.nCopies(5 - startingLocation.length(), "0")) + startingLocation;
                }
                if (dictionaryEntryIndex.length() != 4) {
                    dictionaryEntryIndex = String.join("", Collections.nCopies(4 - dictionaryEntryIndex.length(), "0")) + dictionaryEntryIndex;
                }

                compressedInstruction = Constants.COMPRESSION_SCHEME010.getText() + startingLocation + bitMask + dictionaryEntryIndex;
                break;
            }


        }
        return compressedInstruction;
    }


    /**
     * <h1>This method compresses instruction according to the one bit mismatch compression scheme</h1>
     * @param instruction
     * @param dictionary
     * @return compressedInstruction
     */
    private static String oneBitMismatchCompression(String instruction, List<String> dictionary){

        String compressedInstruction = Constants.CANNOT_COMPRESS.getText();

        for (String dictionaryEntry: dictionary) {

            String xorResult = xorStrings(instruction, dictionaryEntry);

            if (xorResult.chars().filter(ch -> ch == '1').count() == 1){
                String mismatchLocation = Integer.toBinaryString(xorResult.indexOf('1'));
                String dictionaryEntryIndex = Integer.toBinaryString(dictionary.indexOf(dictionaryEntry));

                if (mismatchLocation.length() != 5){
                    mismatchLocation = String.join("", Collections.nCopies(5 - mismatchLocation.length(),"0")) + mismatchLocation;
                }
                if (dictionaryEntryIndex.length() != 4){
                    dictionaryEntryIndex = String.join("", Collections.nCopies(4 - dictionaryEntryIndex.length(),"0"))+ dictionaryEntryIndex;
                }

                compressedInstruction = Constants.COMPRESSION_SCHEME011.getText() + mismatchLocation + dictionaryEntryIndex;
                break;
            }
        }
        return compressedInstruction;
    }


    /**
     * <h1>This method compresses instruction according to the two bit consecutive mismatch compression scheme</h1>
     * @param instruction
     * @param dictionary
     * @return compressedInstruction
     */
    private static String twoBitConsecutiveMismatchCompression(String instruction, List<String> dictionary){

        String compressedInstruction = Constants.CANNOT_COMPRESS.getText();

        for (String dictionaryEntry: dictionary) {

            String xorResult = xorStrings(instruction,dictionaryEntry);

            if (xorResult.contains("11") && xorResult.chars().filter(ch -> ch == '1').count() == 2){

                String startingLocation = Integer.toBinaryString(xorResult.indexOf("1"));
                String dictionaryEntryIndex = Integer.toBinaryString(dictionary.indexOf(dictionaryEntry));

                if (startingLocation.length() != 5) {
                    startingLocation = String.join("", Collections.nCopies(5 - startingLocation.length(), "0")) + startingLocation;
                }
                if (dictionaryEntryIndex.length() != 4) {
                    dictionaryEntryIndex = String.join("", Collections.nCopies(4 - dictionaryEntryIndex.length(), "0")) + dictionaryEntryIndex;
                }

                compressedInstruction = Constants.COMPRESSION_SCHEME100.getText() + startingLocation + dictionaryEntryIndex;
                break;
            }
        }
        return compressedInstruction;
    }


    /**
     * <h1>This method compresses instruction according to the four bit consecutive mismatch compression scheme</h1>
     * @param instruction
     * @param dictionary
     * @return compressedInstruction
     */
    private static String fourBitConsecutiveMismatchCompression(String instruction, List<String> dictionary){

        String compressedInstruction = Constants.CANNOT_COMPRESS.getText();

        for (String dictionaryEntry: dictionary) {

            String xorResult = xorStrings(instruction,dictionaryEntry);

            if (xorResult.contains("1111") && xorResult.chars().filter(ch -> ch == '1').count() == 4){

                String startingLocation = Integer.toBinaryString(xorResult.indexOf("1"));
                String dictionaryEntryIndex = Integer.toBinaryString(dictionary.indexOf(dictionaryEntry));

                if (startingLocation.length() != 5) {
                    startingLocation = String.join("", Collections.nCopies(5 - startingLocation.length(), "0")) + startingLocation;
                }
                if (dictionaryEntryIndex.length() != 4) {
                    dictionaryEntryIndex = String.join("", Collections.nCopies(4 - dictionaryEntryIndex.length(), "0")) + dictionaryEntryIndex;
                }

                compressedInstruction = Constants.COMPRESSION_SCHEME101.getText() + startingLocation + dictionaryEntryIndex;
                break;
            }
        }
        return compressedInstruction;
    }


    /**
     * <h1>This method compresses instruction according to the two mismatches anywhere compression scheme</h1>
     * @param instruction
     * @param dictionary
     * @return compressedInstruction
     */
    private static String twoBitMismatchesAnywhereCompression(String instruction, List<String> dictionary){

        String compressedInstruction = Constants.CANNOT_COMPRESS.getText();

        for (String dictionaryEntry: dictionary) {

            String xorResult = xorStrings(instruction, dictionaryEntry);

            if (xorResult.chars().filter(ch -> ch == '1').count() == 2){
                String dictionaryEntryIndex = Integer.toBinaryString(dictionary.indexOf(dictionaryEntry));
                String mismatchLocation1 = Integer.toBinaryString(xorResult.indexOf("1"));
                String mismatchLocation2 = Integer.toBinaryString(xorResult.lastIndexOf("1"));

                if (mismatchLocation1.length() != 5) {
                    mismatchLocation1 = String.join("", Collections.nCopies(5 - mismatchLocation1.length(), "0")) + mismatchLocation1;
                }
                if (mismatchLocation2.length() != 5) {
                    mismatchLocation2 = String.join("", Collections.nCopies(5 - mismatchLocation2.length(), "0")) + mismatchLocation2;
                }
                if (dictionaryEntryIndex.length() != 4) {
                    dictionaryEntryIndex = String.join("", Collections.nCopies(4 - dictionaryEntryIndex.length(), "0")) + dictionaryEntryIndex;
                }

                compressedInstruction = Constants.COMPRESSION_SCHEME110.getText() + mismatchLocation1 + mismatchLocation2 + dictionaryEntryIndex;
                break;
            }
        }
        return compressedInstruction;
    }


    /**
     * <h1>This method compresses instruction according to the direct matching compression scheme</h1>
     * @param instruction
     * @param dictionary
     * @return compressedInstruction
     */
    private static String directMatchingCompression(String instruction, List<String> dictionary){

        String compressedInstruction = Constants.CANNOT_COMPRESS.getText();

        String dictionaryEntryIndex;
        for (String dictionaryEntry: dictionary) {
            if (instruction.equals(dictionaryEntry)){
                dictionaryEntryIndex = Integer.toBinaryString(dictionary.indexOf(dictionaryEntry));

                if (dictionaryEntryIndex.length() != 4) {
                    dictionaryEntryIndex = String.join("", Collections.nCopies(4 - dictionaryEntryIndex.length(), "0")) + dictionaryEntryIndex;
                }
                compressedInstruction = Constants.COMPRESSION_SCHEME111.getText() + dictionaryEntryIndex;
                break;
            }
        }
        return compressedInstruction;
    }


    /**
     * <h1>This method returns the XOR function of two same length binary numbers</h1>
     * @param string1
     * @param string2
     * @return string1 xor string2
     */
    private static String xorStrings(String string1, String string2){

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < string1.length(); i++){
            if (string1.charAt(i) == string2.charAt(i)){
                output.append("0");
            }else {
                output.append("1");
            }
        }
        return output.toString();
    }


    /**
     * <h1>This method chooses the optimal compression method</h1>
     * @param compressedCurrentInstruction
     * @return optimalCompressionMethod
     */
    private static String chooseOptimalCompression(List<String> compressedCurrentInstruction){

        String optimalCompression = compressedCurrentInstruction.get(0);

        for (String compressedMethod: compressedCurrentInstruction) {
            if ( !Constants.CANNOT_COMPRESS.getText().equals(compressedMethod) && (compressedMethod.length() < optimalCompression.length()) ){
                optimalCompression = compressedMethod;
            }
        }
        return optimalCompression;
    }

    /**
     * <h1>This method writes the compressed code in to a .txt file</h1>
     * @param compressedInstructions
     */
    private static void writeCompressedCodeToTxt(List<String> compressedInstructions, List<String> dictionary){

        StringBuilder outputSequence = new StringBuilder();

        for (String compressedInstruction: compressedInstructions) {
            outputSequence.append(compressedInstruction);
        }

        if (outputSequence.length()%32 != 0){
            outputSequence = outputSequence.append(String.join("", Collections.nCopies(32 - outputSequence.length()%32, "0")));
        }

        try {
            FileWriter compressedFileWriter = new FileWriter(Constants.COMPRESSED_OUTPUT.getText());
            BufferedWriter compressedFile = new BufferedWriter(compressedFileWriter);

            for (int i = 0; i < outputSequence.length(); i+=32){
                compressedFile.write(outputSequence.substring(i,i+32));
                compressedFile.newLine();
            }

            compressedFile.write(Constants.SEPARATOR.getText());
            compressedFile.newLine();

            for (String dictionaryEntry:dictionary) {
                compressedFile.write(dictionaryEntry);
                compressedFile.newLine();
            }
            compressedFile.close();
            compressedFileWriter.close();
        }catch (IOException ex){
            System.out.println(ex);
        }
    }


    /**
     * <h1>This method creates the dictionary</h1>
     * @return dictionaryAndOriginalProgram
     * @throws FileNotFoundException
     */
    private static Map<String, List<String>> createDictionary() throws FileNotFoundException {

        File original = new File(Constants.ORIGINAL.getText());
        Scanner originalCode = new Scanner(original);

        ArrayList<String> instructionSet = new ArrayList<>();
        Map<String, Integer> frequencyMap = new HashMap<>();
        List<String> originalProgram = new ArrayList<>();

        while (originalCode.hasNext()){
            String instruction = originalCode.next();

            originalProgram.add(instruction);

            if (!frequencyMap.containsKey(instruction)){
                frequencyMap.put(instruction,1);
            }else {
                Integer previousFrequency = frequencyMap.get(instruction);
                frequencyMap.replace(instruction, previousFrequency + 1);
            }

            if (!instructionSet.contains(instruction)){
                instructionSet.add(instruction);
            }
        }

        List<String> dictionary = sortDictionaryByValues(frequencyMap, instructionSet);

        Map<String, List<String >> dictionaryAndOriginalProgram = new HashMap<>();

        dictionaryAndOriginalProgram.put(Constants.DICTIONARY.getText(), dictionary);
        dictionaryAndOriginalProgram.put(Constants.ORIGINAL_PROGRAM.getText(), originalProgram);

        return dictionaryAndOriginalProgram;
    }


    /**
     * <h1>This method returns the sorted dictionary</h1>
     * @param frequencyMap
     * @param instructionSet
     * @return sortedDictionary
     */
    private static List<String> sortDictionaryByValues(Map<String, Integer> frequencyMap, ArrayList<String> instructionSet) {
        LinkedHashMap<String, Integer> dictionaryWithTies = sortMapByValuesReverse(frequencyMap);

        ArrayList<String> instructionListWithTies = new ArrayList<>();
        ArrayList<Integer> frequencyListWithTies = new ArrayList<>();

        for (String instruction : dictionaryWithTies.keySet()) {
            instructionListWithTies.add(instruction);
            frequencyListWithTies.add(frequencyMap.get(instruction));
        }

        ArrayList<String> dictionary = new ArrayList<>();

        for (int i = 0; i < instructionListWithTies.size(); i++) {
            int lastIndex = frequencyListWithTies.lastIndexOf(frequencyListWithTies.get(i));
            int ties = lastIndex - i + 1;

            if (ties > 1) {
                List<String > tiedInstructions = instructionListWithTies.subList(i,i+ties);
                Map<String, Integer> tieMapWithInstructionSet = new HashMap<>();
                for (String tiedInstruction: tiedInstructions){
                    tieMapWithInstructionSet.put(tiedInstruction, instructionSet.indexOf(tiedInstruction));
                }
                LinkedHashMap<String, Integer> sortedTiedInstructions = sortMapByValues(tieMapWithInstructionSet);
                dictionary.addAll(sortedTiedInstructions.keySet());
            }else {
                dictionary.add(instructionListWithTies.get(i));
            }

            i += lastIndex -i;

            if (lastIndex == frequencyListWithTies.size() - 1){
                break;
            }
        }

        return dictionary.subList(0,16);
    }


    /**
     * <h1>This method sorts a Map according to it's values in descending order</h1>
     * @param frequencyMap
     * @return sortedMap
     */
    private static LinkedHashMap<String, Integer> sortMapByValuesReverse(Map<String, Integer> frequencyMap) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        frequencyMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x ->
                map.put(x.getKey(), x.getValue()));
        return map;
    }


    /**
     * <h1>This method sorts a Map according to it's values in ascending order</h1>
     * @param frequencyMap
     * @return sortedMap
     */
    private static LinkedHashMap<String, Integer> sortMapByValues(Map<String, Integer> frequencyMap) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        frequencyMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x ->
                map.put(x.getKey(), x.getValue()));
        return map;
    }




    /**
     *<h1>Constant Enum</h1>
     */
    private enum Constants{
        ORIGINAL("original.txt"),
        COMPRESSED("compressed.txt"),
        COMPRESSED_OUTPUT("cout.txt"),
        DECOMPRESSED_OUTPUT("dout.txt"),

        SEPARATOR("xxxx"),

        DICTIONARY("Dictionary"),
        ORIGINAL_PROGRAM("Original Program"),
        COMPRESSED_PROGRAM("Compressed Program"),

        COMPRESSION_SCHEME000("000"),
        COMPRESSION_SCHEME001("001"),
        COMPRESSION_SCHEME010("010"),
        COMPRESSION_SCHEME011("011"),
        COMPRESSION_SCHEME100("100"),
        COMPRESSION_SCHEME101("101"),
        COMPRESSION_SCHEME110("110"),
        COMPRESSION_SCHEME111("111"),


        CANNOT_COMPRESS("Cannot Compress");

        private String text;

        Constants(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }
}

