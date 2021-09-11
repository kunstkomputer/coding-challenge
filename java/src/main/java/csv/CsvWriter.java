package csv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import csv.HeaderColumnNameAndOrderMappingStrategy;
import pojo.Product;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvWriter {
    public static void writeProductListToCsv(String path, List<Product> articleList) {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(path));

            HeaderColumnNameAndOrderMappingStrategy<Product> mappingStrategy = new HeaderColumnNameAndOrderMappingStrategy<>(Product.class);

            StatefulBeanToCsv<Product> csvWriter = new StatefulBeanToCsvBuilder<Product>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator('|')
                    .withLineEnd("\n")
                    .withOrderedResults(true)
                    .build();

            csvWriter.write(articleList);

            writer.close();

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException  ex) {
            ex.printStackTrace();
        }
    }
}
