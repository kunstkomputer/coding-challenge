import csv.CsvParser;
import org.junit.Test;
import pojo.Article;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvParserTest {

    @Test
    public void parseCsvAsStream() throws IOException {
        InputStream str = getClass().getClassLoader().getResourceAsStream("sampleArticleList.csv");

        List<Article> artList = CsvParser.parseCsvAsStream(str);
        Article sample = new Article("A-cVBTQHVF", "P-cVBTQHVF", "OBLAEDD", "Gfaokn Ttefoa pfrnZ",
                58.77f, 38);
        assertEquals(sample, artList.get(0));
    }
}
