import java.io.FileWriter;
import java.io.IOException;

public class File {
    public static final FileWriter writer;
    static {
        try {
            writer = new FileWriter("Alphabet.txt", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
