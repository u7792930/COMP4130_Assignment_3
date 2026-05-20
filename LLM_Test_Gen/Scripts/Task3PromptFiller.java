import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;


 //Reads task2_output.csv (one row per generation unit) and fills the prompt template for each row by replacing placeholders with the
 //corresponding CSV column values.
 //writes task3_output.csv with all existing columns preserved plus FilledPrompt

public class Task3PromptFiller {

    private static final String INPUT_CSV    = "LLM_Test_Gen/Data/task2_output.csv";
    private static final String OUTPUT_CSV   = "LLM_Test_Gen/Data/task3_output.csv";
    private static final String TEMPLATE_FILE = "LLM_Test_Gen/Scripts/prompt_template.txt";

    //column indices in task2_output.csv
    private static final int IDX_FQN               = 0;
    private static final int IDX_METHOD_NAME        = 1;
    private static final int IDX_METHOD_SIGNATURE   = 2;
    private static final int IDX_RETURN_TYPE        = 3;
    private static final int IDX_MODIFIERS          = 4;
    private static final int IDX_PARAMETERS         = 5;
    private static final int IDX_THROWN_EXCEPTIONS  = 6;
    private static final int IDX_METHOD_INVOCATIONS = 7;
    private static final int IDX_COMMENTS           = 8;
    private static final int IDX_SOURCE_CODE        = 9;
    private static final int IDX_GENERATION_TARGET  = 10;

    public static void main(String[] args) {
        try {
            new File("LLM_Test_Gen/Data").mkdirs();

            // Load the prompt template
            String template = Files.readString(Paths.get(TEMPLATE_FILE));

            CSVReader reader = new CSVReader(new FileReader(INPUT_CSV));
            java.util.List<String[]> allRows = reader.readAll();
            reader.close();

            if (allRows.isEmpty()) {
                System.out.println("Input CSV is empty: " + INPUT_CSV);
                return;
            }

            CSVWriter writer = new CSVWriter(new FileWriter(OUTPUT_CSV));

            // add filledprompt to the header
            String[] originalHeader = allRows.get(0);
            String[] newHeader = appendColumn(originalHeader, "FilledPrompt");
            writer.writeNext(newHeader);

            int filled = 0;

            // Fill prompt for each generation unit
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);

                if (row.length < 11) {
                    row = padRow(row, 11);
                }

                String filledPrompt = fillTemplate(template, row);
                String[] newRow = appendColumn(row, filledPrompt);
                writer.writeNext(newRow);
                filled++;

                System.out.println("Filled prompt for: "
                        + row[IDX_METHOD_NAME]
                        + " ["
                        + partitionLabel(row[IDX_GENERATION_TARGET])
                        + "]");
            }

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Placeholder replacement
    private static String fillTemplate(String template, String[] row) {
        return template
                .replace("#{FQN}#",               safe(row, IDX_FQN))
                .replace("#{MethodSignature}#",    safe(row, IDX_METHOD_SIGNATURE))
                .replace("#{ReturnType}#",         safe(row, IDX_RETURN_TYPE))
                .replace("#{Modifiers}#",          safe(row, IDX_MODIFIERS))
                .replace("#{Parameters}#",         safe(row, IDX_PARAMETERS))
                .replace("#{ThrownExceptions}#",   safe(row, IDX_THROWN_EXCEPTIONS))
                .replace("#{MethodInvocations}#",  safe(row, IDX_METHOD_INVOCATIONS))
                .replace("#{Comments}#",           safe(row, IDX_COMMENTS))
                .replace("#{SourceCode}#",         safe(row, IDX_SOURCE_CODE))
                .replace("#{GenerationTarget}#",   safe(row, IDX_GENERATION_TARGET));
    }

    // helpers

    // Returns the cell value or empty string if the index is out of bounds
    private static String safe(String[] row, int index) {
        return (index < row.length && row[index] != null) ? row[index] : "";
    }

    // Returns a new array with value appended as the last element
    private static String[] appendColumn(String[] row, String value) {
        String[] newRow = new String[row.length + 1];
        System.arraycopy(row, 0, newRow, 0, row.length);
        newRow[row.length] = value;
        return newRow;
    }

    //pad  row with empty strings up to minLength
    private static String[] padRow(String[] row, int minLength) {
        if (row.length >= minLength) return row;
        String[] padded = new String[minLength];
        System.arraycopy(row, 0, padded, 0, row.length);
        for (int i = row.length; i < minLength; i++) padded[i] = "";
        return padded;
    }

    // Extracts the short partition label for console logging.
    private static String partitionLabel(String target) {
        if (target == null || target.isBlank()) return "UNKNOWN";
        int colon = target.indexOf(':');
        return colon > 0 ? target.substring(0, colon) : target.substring(0, Math.min(15, target.length()));
    }
}
