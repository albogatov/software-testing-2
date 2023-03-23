import logarithmic.Ln;
import logarithmic.Log;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import trigonometry.Cos;
import trigonometry.Cot;
import trigonometry.Sin;
import trigonometry.Tan;

import java.io.*;

class SystemSolveTest {

    static double functionEps = 0.3;
    double eps = 0.6;
    static Cos cosMock;
    static Sin sinMock;

    static Tan tanMock;

    static Cot cotMock;
    static Ln lnMock;
    static Log logMock;

    static Reader tanIn;

    static Reader cotIn;
    static Reader cosIn;
    static Reader sinIn;
    static Reader lnIn;
    static Reader log2In;

    static Reader log3In;

    static Reader log5In;

    static FileWriter file;


    @BeforeAll
    static void init() {
        cosMock = Mockito.mock(Cos.class);
        sinMock = Mockito.mock(Sin.class);
        tanMock = Mockito.mock(Tan.class);
        cotMock = Mockito.mock(Cot.class);
        lnMock = Mockito.mock(Ln.class);
        logMock = Mockito.mock(Log.class);
        try {
            cosIn = new FileReader("src/main/resources/csvInput/CosIn.csv");
            sinIn = new FileReader("src/main/resources/csvInput/SinIn.csv");
            tanIn = new FileReader("src/main/resources/csvInput/TanIn.csv");
            cotIn = new FileReader("src/main/resources/csvInput/CotIn.csv");
            lnIn = new FileReader("src/main/resources/csvInput/LnIn.csv");
            log2In = new FileReader("src/main/resources/csvInput/Log2In.csv");
            log3In = new FileReader("src/main/resources/csvInput/Log3In.csv");
            log5In = new FileReader("src/main/resources/csvInput/Log5In.csv");

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(cosIn);
            for (CSVRecord record : records) {
                Mockito.when(cosMock.cos(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(sinIn);
            for (CSVRecord record : records) {
                Mockito.when(sinMock.sin(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(tanIn);
            for (CSVRecord record : records) {
                Mockito.when(tanMock.tan(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(cotIn);
            for (CSVRecord record : records) {
                Mockito.when(cotMock.cot(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(lnIn);
            for (CSVRecord record : records) {
                Mockito.when(lnMock.ln(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log2In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(2, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log3In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(3, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log5In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(5, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
            }
        } catch (IOException ex) {
            System.err.println("Rest in pieces");
        }

    }

    @BeforeEach
    void initFile() throws IOException {
        file = new FileWriter("src/main/java/SystemOut.csv");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testSystemOnlyMocks(double value, double expected) {
        Function function = new Function(sinMock, cosMock, tanMock, cotMock, lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testSin(double value, double expected) {
        Function function = new Function(new Sin(), cosMock, tanMock, cotMock, lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithCos(double value, double expected) {
        Function function = new Function(sinMock, new Cos(sinMock), tanMock, cotMock, lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithSinCos(double value, double expected) {
        Function function = new Function(new Sin(), new Cos(new Sin()), tanMock, cotMock, lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithTan(double value, double expected) {
        Function function = new Function(sinMock, cosMock, new Tan(sinMock, cosMock), cotMock, lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithCot(double value, double expected) {
        Function function = new Function(sinMock, cosMock, tanMock, new Cot(sinMock, cosMock), lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithSinCosTan(double value, double expected) {
        Function function = new Function(new Sin(), new Cos(new Sin()), new Tan(new Sin(), new Cos()), cotMock, lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithSinCosCot(double value, double expected) {
        Function function = new Function(new Sin(), new Cos(new Sin()), tanMock, new Cot(new Sin(), new Cos()), lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithTanCot(double value, double expected) {
        Function function = new Function(new Sin(), new Cos(), new Tan(), new Cot(), lnMock, logMock);
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithLog(double value, double expected) {
        Function function = new Function(sinMock, cosMock, tanMock, cotMock, lnMock, new Log(lnMock));
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithLn(double value, double expected) {
        Function function = new Function(sinMock, cosMock, tanMock, cotMock, new Ln(), new Log());
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps * 20);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
    void testWithAll(double value, double expected) {
        Function function = new Function();
        Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps * 20);
    }

//    @ParameterizedTest
//    @CsvFileSource(resources = "/csvInput/SystemIn.csv")
//    void testWithWriter(double value, double expected) throws IOException {
//        Function function = new Function();
//            function.writeResultToCsv("/Users/abogatov/IdeaProjects/tpo2/src/main/resources/csvOutput/SystemOut.csv", function,-6.0, 10.0, 0.2, 0.1);
//            Assertions.assertEquals(expected, function.systemSolve(value, functionEps), eps * 20);
//    }
}