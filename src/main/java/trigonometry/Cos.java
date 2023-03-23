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

public class Cos {

    private final Sin sin;

    public Cos(Sin sin) {
        this.sin = sin;
    }

    public Cos() {
        this.sin = new Sin();
    }

    public double cos(double x, double eps) {
        double x_init = x;
        x %= Math.PI * 2;
        if (Double.POSITIVE_INFINITY == x || Double.NEGATIVE_INFINITY == x) {
            return Double.NaN;
        }
        if (x < -Math.PI) {
            while (x < -Math.PI) x += 2 * Math.PI;
        }
        if (x > Math.PI) {
            while (x > Math.PI) x -= 2 * Math.PI;
        }
        double result;
        if (x > Math.PI / 2 || x < -Math.PI / 2) {
            result = -1 * Math.sqrt(1 - sin.sin(x_init, eps) * sin.sin(x_init, eps));
        } else result = Math.sqrt(1 - sin.sin(x_init, eps) * sin.sin(x_init, eps));
        if (Math.abs(result) > 1) return Double.NaN;
        if (Math.abs(result) <= eps) return 0;
        return result;
    }

    public void writeResultToCsv (final String filename, final Cos function, final double from, final double to, final double step, final double eps) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            Files.delete(path);
        }
        file.createNewFile();
        final PrintWriter printWriter = new PrintWriter(file);
        for (double current = from; current <= to; current += step) {
            printWriter.println(current + "," + function.cos(current, eps));
        }
        printWriter.close();
    }
}