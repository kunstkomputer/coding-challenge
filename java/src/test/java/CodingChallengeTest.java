import org.junit.Test;
import pojo.Article;
import pojo.Product;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CodingChallengeTest {

    @Test
    public void buildServerUrl() throws MalformedURLException, URISyntaxException {
        URI sampleUrl = URI.create("http://localhost:8080/articles/50");

        URL basePath = new URL("http://localhost:8080");
        String resource = "articles";
        Integer lines = 50;

        assertEquals(sampleUrl, CodingChallenge.buildServerUrl(basePath, resource, lines));

        URL basePathWithTrailingSlash = new URL("http://localhost:8080/");
        assertEquals(sampleUrl, CodingChallenge.buildServerUrl(basePathWithTrailingSlash, resource, lines));

    }

    @Test
    public void onlyArticlesWithStockGreaterZeroAreAddedToProductsList() {
        Article sample = new Article("A-1", "P-1", "OBLAEDD", "Gfaokn Ttefoa pfrnZ",
                58.77f, 38);
        Article sample_two = new Article("A-2", "P-2", "OBLAEDD", "Gfaokn Ttefoa pfrnZ",
                58.77f, 0);

        ArrayList<Article> artList = new ArrayList<>();
        artList.add(sample);
        artList.add(sample_two);

        Product dut = new Product("P-1","OBLAEDD", "Gfaokn Ttefoa pfrnZ", 58.77f, 38);
        List<Product> pdl = CodingChallenge.condenseArticleListToProductList(artList);

        assertEquals(dut, pdl.get(0));
        assertEquals(1, pdl.size());
    }
}
