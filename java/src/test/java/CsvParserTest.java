import pojo.Article;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvParserTest {

    @Test
    public void parseCsv() throws IOException {
        Path resourceDirectory = Paths.get("src", "test", "resources", "sample.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        List<Article> artList = CsvParser.parseCsv(absolutePath);
        Article sample = new Article("A-cVBTQHVF", "P-cVBTQHVF", "OBLAEDD", "Gfaokn Ttefoa pfrnZ",
                58.77f, 38);
        assertEquals(sample, artList.get(0));
    }
}