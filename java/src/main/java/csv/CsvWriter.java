package csv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import pojo.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CsvWriter {
    public static byte[] writeProductListToCsv(List<Product> articleList) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);

        HeaderColumnNameAndOrderMappingStrategy<Product> mappingStrategy = new HeaderColumnNameAndOrderMappingStrategy<>(Product.class);

        StatefulBeanToCsv<Product> csvWriter = new StatefulBeanToCsvBuilder<Product>(streamWriter)
                .withMappingStrategy(mappingStrategy)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator('|')
                .withLineEnd("\n")
                .withOrderedResults(true)
                .build();

        try {
            csvWriter.write(articleList);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }

        try {
            streamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream.toByteArray();

    }
}
