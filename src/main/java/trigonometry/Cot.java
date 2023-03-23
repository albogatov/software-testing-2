package trigonometry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public class Cot {

    private final Sin sin;
    private final Cos cos;

    public Cot() {
        this.sin = new Sin();
        this.cos = new Cos();
    }

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public double cot(double x, double eps) {


        double resultSin = sin.sin(x, eps);
        double resultCos = cos.cos(x, eps);

        if (resultSin == 0.0 && resultCos > 0) {
            return POSITIVE_INFINITY;
        } else if (resultSin == 0.0 && resultCos < 0) {
            return NEGATIVE_INFINITY;
        }

        return resultCos / resultSin;
    }

    public void writeResultToCsv (final String filename, final Cot function, final double from, final double to, final double step, final double eps) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            Files.delete(path);
        }
        file.createNewFile();
        final PrintWriter printWriter = new PrintWriter(file);
        for (double current = from; current <= to; current += step) {
            printWriter.println(current + "," + function.cot(current, eps));
        }
        printWriter.close();
    }
}