import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import pojo.Article;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public static List<Article> parseCsv(String pathToCsvFile) {
        List<Article> articleList = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(pathToCsvFile));

            CsvToBean<Article> csvToBean = new CsvToBeanBuilder<Article>(reader)
                    .withSeparator('|')
                    .withType(Article.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (Article article : csvToBean) {
                System.out.println("ID: " + article.getId());
                System.out.println("Product ID: " + article.getProductId());
                System.out.println("Name: " + article.getName());
                System.out.println("Description: " + article.getDescription());
                System.out.println("Price: " + article.getPrice());
                System.out.println("Stock Count: " + article.getStockCount());
                articleList.add(article);
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return articleList;
    }
}
