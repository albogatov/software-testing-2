import logarithmic.Ln;
import logarithmic.Log;
import trigonometry.Cos;
import trigonometry.Sin;
import trigonometry.Tan;
import trigonometry.Cot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Function {
    Sin sin;

    Cos cos;

    Tan tan;

    Cot cot;
    Log log;
    Ln ln;

    public Function() {
        this.cos = new Cos();
        this.sin = new Sin();
        this.cot = new Cot();
        this.tan = new Tan();
        this.ln = new Ln();
        this.log = new Log(ln);
    }

    public Function(Sin sin, Cos cos, Tan tan, Cot cot, Ln ln, Log log) {
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.cot = cot;
        this.log = log;
        this.ln = ln;
    }

    public double systemSolve(double x, double eps) {
        if (x <= 0) {
            double ct = cot.cot(x, eps);
            double tn = tan.tan(x, eps);
            double cs = cos.cos(x, eps);
            double sn = sin.sin(x, eps);
            return (Math.pow((cot.cot(x, eps) + cot.cot(x, eps) - tan.tan(x, eps)), 2) * cot.cot(x, eps)) + ((Math.pow(cos.cos(x, eps), 3)/(sin.sin(x, eps)/tan.tan(x,eps))) + sin.sin(x,eps));
        }
            //return (Math.pow((cot.cot(x, eps) + cot.cot(x, eps) - tan.tan(x, eps)), 2) * cot.cot(x, eps)) + ((Math.pow(cos.cos(x, eps), 3)/(sin.sin(x, eps)/tan.tan(x,eps))) + sin.sin(x,eps));
        else
            return ((Math.pow(log.log(3, x, eps), 3)/ln.ln(x, eps))/log.log(2, x, eps) + log.log(2, x, eps) + (log.log(5, x, eps) + log.log(5, x, eps)));
    }
    public void writeResultToCsv (final String filename, final Function function, final double from, final double to, final double step, final double eps) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            Files.delete(path);
        }
        file.createNewFile();
        final PrintWriter printWriter = new PrintWriter(file);
        for (double current = from; current <= to; current += step) {
            printWriter.println(current + "," + function.systemSolve(current, eps));
        }
        printWriter.close();
    }
}
