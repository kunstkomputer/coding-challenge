import csv.CsvWriter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pojo.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvWriterTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void writeProductListToCsv() throws IOException {
        List<Product> prodList = new ArrayList<>();
        prodList.add(new Product("P-SA6YNXRd", "KCBOXLLTPM FFSQ", "xs lzul n lmpPPf mjd", 29.85f, 27));
        prodList.add(new Product("P-A6YNXRdD", "CVKCBOX", "", 29.85f, 27));
        prodList.add(new Product("P-SYNXRdDY", "KCBOXLLTPM FFSQ", "xs lzul n lmpPPf mjd", 29.85f, 27));
        prodList.add(new Product("P-6YNXRdDY", "KCBOXLLTPM FFSQ", "xs lzul n lmpPPf mjd", 29.85f, 134));
        prodList.add(new Product("P-XRdDYcOL", "FFSQ AOXKS", "zulHklmpP", 17.09f, 48));


        final File tempFile = tempFolder.newFile();
        byte[] csvContent = CsvWriter.writeProductListToCsv(prodList);
        Files.write(Paths.get(tempFile.getAbsolutePath()), csvContent);

        String premadeCsvTestFileContent = new String(getClass().getClassLoader().getResourceAsStream("sampleProductList.csv").readAllBytes());

        final String generatedCsvTestFileContent = Files.readString(Path.of(tempFile.getAbsolutePath()));
        assertEquals(premadeCsvTestFileContent, generatedCsvTestFileContent);
    }

    @Test
    public void floatsAreRenderedAsTwoDigitsAfterDecimalPoint() throws IOException {
        List<Product> prodList = new ArrayList<>();

        Product bothPriceDigitsZero = new Product("P-SA6YNXRd", "KCBOXLLTPM FFSQ", "xs lzul n lmpPPf mjd", 29.00f, 27);
        Product lastPriceDigitsZero = new Product("P-A6YNXRdD", "CVKCBOX", "", 29.50f, 27);

        prodList.add(bothPriceDigitsZero);
        prodList.add(lastPriceDigitsZero);

        final File tempFile = tempFolder.newFile();
        byte[] csvContent = CsvWriter.writeProductListToCsv(prodList);
        Files.write(Paths.get(tempFile.getAbsolutePath()), csvContent);

        String premadeCsvTestFileContent = new String(getClass().getClassLoader().getResourceAsStream("sampleProductListWithFloatFormat.csv").readAllBytes());

        final String generatedCsvTestFileContent = Files.readString(Path.of(tempFile.getAbsolutePath()));
        assertEquals(premadeCsvTestFileContent, generatedCsvTestFileContent);
    }

}
