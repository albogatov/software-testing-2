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

public class Ln {
    public double ln(double x, double eps) {
        if (Double.isNaN(x) || x < (double) 0) {
            return Double.NaN;
        } else if (x == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        } else if (x == 0.0) {
            return Double.NEGATIVE_INFINITY;
        }

        double constant = ((x - 1) * (x - 1)) / ((x + 1) * (x + 1));

        double sum = 0;
        double currentValue = (x - 1) / (x + 1);
        int step = 1;
        while (Math.abs(currentValue) > eps / 2) {
            sum += currentValue;
            currentValue = (2 * step - 1) * currentValue * constant / (2 * step + 1);
            step++;
        }
        sum *= 2;
        return sum;
    }

    public void writeResultToCsv (final String filename, final Ln function, final double from, final double to, final double step, final double eps) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            Files.delete(path);
        }
        file.createNewFile();
        final PrintWriter printWriter = new PrintWriter(file);
        for (double current = from; current <= to; current += step) {
            printWriter.println(current + "," + function.ln(current, eps));
        }
        printWriter.close();
    }
}