import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


 //Task 2 input space partition fuzzing strategy.
 //read task1_output.csv and expand each row into 3 or 4 generation units by assigning one partition label

public class Task2Strategy {

    private static final String INPUT_CSV  = "LLM_Test_Gen/Data/task1_output.csv";
    private static final String OUTPUT_CSV = "LLM_Test_Gen/Data/task2_output.csv";

    //Partition descriptions injected into the prompt

    private static final String NORMAL_FLOW =
            "NORMAL_FLOW: Generate tests using typical valid inputs that exercise " +
                    "the method's primary success path. Verify correct return values and " +
                    "expected side-effects. Avoid null, empty, and extreme boundary inputs.";

    private static final String NULL_EMPTY =
            "NULL_EMPTY: Generate tests using null arguments, empty strings (\"\"), " +
                    "empty arrays (new byte[0]), and empty collections. Verify the method " +
                    "handles these gracefully — returns null/empty, throws a documented " +
                    "exception, or behaves as stated in its Javadoc.";

    private static final String BOUNDARY_VALUES =
            "BOUNDARY_VALUES: Generate tests at boundary values: Integer.MIN_VALUE, " +
                    "Integer.MAX_VALUE, Long.MAX_VALUE, 0, -1, single-character strings, " +
                    "single-element collections, and minimum/maximum length arrays. Target " +
                    "the edge transitions in the method body that are most likely to cause " +
                    "unexpected behaviour.";

    private static final String EXCEPTION_PATH =
            "EXCEPTION_PATH: Generate tests with inputs specifically designed to " +
                    "trigger every exception listed in ThrownExceptions and every explicit " +
                    "throw statement visible in SourceCode. Use a try-catch block with a " +
                    "fail() call after the statement under test to assert the exception is thrown.";


    private static final int IDX_THROWN     = 6;
    private static final int IDX_SOURCECODE = 9;

    public static void main(String[] args) {
        try {
            new File("LLM_Test_Gen/Data").mkdirs();

            CSVReader reader = new CSVReader(new FileReader(INPUT_CSV));
            List<String[]> allRows = reader.readAll();
            reader.close();

            if (allRows.isEmpty()) {
                System.out.println("Input CSV is empty: " + INPUT_CSV);
                return;
            }

            CSVWriter writer = new CSVWriter(new FileWriter(OUTPUT_CSV));

            // Write header (original columns + GenerationTarget)
            String[] originalHeader = allRows.get(0);
            String[] newHeader = appendColumn(originalHeader, "GenerationTarget");
            writer.writeNext(newHeader);

            int unitCount = 0;

            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);

                // pad short rows to expected length
                if (row.length < 10) {
                    row = padRow(row, 10);
                }

                String thrownExceptions = row[IDX_THROWN].trim();
                String sourceCode       = row[IDX_SOURCECODE].trim();

                boolean hasExceptions = !thrownExceptions.isEmpty()
                        || sourceCode.contains("throw ");

                // writes the 3 base partitions
                writer.writeNext(appendColumn(row, NORMAL_FLOW));
                writer.writeNext(appendColumn(row, NULL_EMPTY));
                writer.writeNext(appendColumn(row, BOUNDARY_VALUES));
                unitCount += 3;

                // Only write EXCEPTION_PATH if the method can throw
                if (hasExceptions) {
                    writer.writeNext(appendColumn(row, EXCEPTION_PATH));
                    unitCount++;
                }
            }

            writer.close();

            int methodCount = allRows.size() - 1;
            System.out.println("Strategy complete.");
            System.out.println("  Methods processed : " + methodCount);
            System.out.println("  Generation units  : " + unitCount);
            System.out.println("  Output CSV        : " + OUTPUT_CSV);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // helper

    // returns a new array with value appended as the last element
    private static String[] appendColumn(String[] row, String value) {
        String[] newRow = new String[row.length + 1];
        System.arraycopy(row, 0, newRow, 0, row.length);
        newRow[row.length] = value;
        return newRow;
    }

    // Pad a row with empty strings up to minLength
    private static String[] padRow(String[] row, int minLength) {
        if (row.length >= minLength) return row;
        String[] padded = new String[minLength];
        System.arraycopy(row, 0, padded, 0, row.length);
        for (int i = row.length; i < minLength; i++) padded[i] = "";
        return padded;
    }
}
