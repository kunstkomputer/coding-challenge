import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import pojo.CustomMappingStrategy;
import pojo.Product;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvWriter {
    static void writeProductListToCsv(String path, List<Product> articleList){
        try {
            // create a write
            Writer writer = Files.newBufferedWriter(Paths.get(path));

            // header record

            CustomMappingStrategy<Product> mappingStrategy = new CustomMappingStrategy<>();
            mappingStrategy.setType(Product.class);

            // create a csv writer
            StatefulBeanToCsv<Product> csvWriter = new StatefulBeanToCsvBuilder<Product>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withQuotechar('_')
                    .withSeparator('|')
                    .withLineEnd("\n")
                    .withOrderedResults(true)
                    .build();

            // write header record
            //csvWriter.writeNext(headerRecord);

            // write data records
            csvWriter.write(articleList);

            writer.close();

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException ex) {
            ex.printStackTrace();
        }
    }
}
