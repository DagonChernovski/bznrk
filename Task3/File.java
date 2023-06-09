import java.io.FileWriter;
import java.io.IOException;

public class File {
    public static final FileWriter writer, aWriter;
    static {
        try {
            writer=new FileWriter("Threader.txt",false);
            aWriter = new FileWriter("Alphabet.txt", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
