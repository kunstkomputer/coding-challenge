package csv;

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
                articleList.add(article);
            }
            isr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return articleList;
    }
}
