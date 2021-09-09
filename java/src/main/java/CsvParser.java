import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import pojo.Article;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public static List<Article> parseCsvAsStream(InputStream is) {
        List<Article> articleList = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);

            CsvToBean<Article> csvToBean = new CsvToBeanBuilder<Article>(isr)
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
            isr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return articleList;
    }
}
