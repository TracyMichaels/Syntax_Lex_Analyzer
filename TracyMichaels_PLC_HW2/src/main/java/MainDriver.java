import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Tracy
 */
public class MainDriver {
    public static void main(String[] args) throws IOException {
        // file to be analyzed
        String inputFile;
        String fileContents;
        StringBuilder fileContentsBuilder;
        Scanner sc = new Scanner(System.in);

        // get input, either commandline args or user input
        // precondition: must be file path
        if (args.length > 0) {

            inputFile = args[0];
        } else {
            System.out.println("Enter file path:");
            inputFile = sc.nextLine();
        }

        // convert file contents to string object
        try (BufferedReader buff = new BufferedReader(new FileReader(inputFile))) {
            fileContentsBuilder = new StringBuilder();
            String nextLine = buff.readLine();
            while (nextLine != null) {
                fileContentsBuilder.append(nextLine);
                nextLine = buff.readLine();
            }
            fileContents = fileContentsBuilder.toString();
        }

        Tokenizer tk = new Tokenizer(fileContents);
        tk.tokenize();
        SyntaxAnalyzer sa = new SyntaxAnalyzer(tk.getTokens());
        sa.analyze();
    }
}
