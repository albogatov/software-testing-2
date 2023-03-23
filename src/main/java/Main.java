import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Function function = new Function();
        function.writeResultToCsv("src/main/java/SystemOut.csv", function,-6.0, 10.0, 0.2, 0.1);
    }
}
