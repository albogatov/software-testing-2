package logarithmic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import trigonometry.Tan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Log {

    private final Ln ln;

    public Log(Ln ln) {
        this.ln = ln;
    }

    public Log() {
        this.ln = new Ln();
    }

    public double log(double a, double b, double esp) {
        return ln.ln(b, esp) / ln.ln(a, esp);
    }

    public void writeResultToCsv (final String filename, final Log function, final double from, final double to, final double step, final double eps, final double a) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            Files.delete(path);
        }
        file.createNewFile();
        final PrintWriter printWriter = new PrintWriter(file);
        for (double current = from; current <= to; current += step) {
            printWriter.println(current + "," + function.log(a, current, eps));
        }
        printWriter.close();
    }
}