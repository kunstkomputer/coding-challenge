import csv.CsvWriter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pojo.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CsvWriterTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void writeProductListToCsv() throws IOException {
        List<Product> prodList = new ArrayList<>();
        prodList.add(new Product("P-SA6YNXRd","KCBOXLLTPM FFSQ","xs lzul n lmpPPf mjd",29.85f,27));
        prodList.add(new Product("P-A6YNXRdD","CVKCBOX","",29.85f,27));
        prodList.add(new Product("P-SYNXRdDY","KCBOXLLTPM FFSQ","xs lzul n lmpPPf mjd",29.85f,27));
        prodList.add(new Product("P-6YNXRdDY","KCBOXLLTPM FFSQ","xs lzul n lmpPPf mjd",29.85f,134));
        prodList.add(new Product("P-XRdDYcOL","FFSQ AOXKS","zulHklmpP",17.09f,48));


        final File tempFile = tempFolder.newFile();
        CsvWriter.writeProductListToCsv(tempFile.getAbsolutePath() ,prodList);

        String premadeCsvTestFileContent = new String(getClass().getClassLoader().getResourceAsStream("sampleProductList.csv").readAllBytes());

        final String generatedCsvTestFileContent = Files.readString(Path.of(tempFile.getAbsolutePath()));
        assertEquals(premadeCsvTestFileContent,generatedCsvTestFileContent);
    }
}