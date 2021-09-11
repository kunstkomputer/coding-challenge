import csv.CsvParser;
import csv.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Article;
import pojo.Product;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CodingChallenge {
    private static final Logger logger = LoggerFactory.getLogger(CodingChallenge.class);

    public static void main(String[] args) {


        try {
            Integer numArticlesToFetch = 50;
            final URL serverUrl = new URL("http://localhost:8080");

            // fetch articles from webserver and stream to parser
            URI articlesUri = buildServerUrl(serverUrl, "articles", numArticlesToFetch);
            logger.info("fetching " + numArticlesToFetch + "articles.");
            logger.info("Remote Server: " + articlesUri);
            InputStream is = ProductsHttpClient.httpArticlesGetRequest(articlesUri);

            List<Article> arl = CsvParser.parseCsvAsStream(is);

            List<Product> prl = condenseArticleListToProductList(arl);

            byte[] csvPayload = CsvWriter.writeProductListToCsv(prl);

            URI productsUri = buildServerUrl(serverUrl, "products", numArticlesToFetch);
            logger.info("Uploading product to remote Server: " + productsUri);

            ProductsHttpClient.httpProductsPutRequest(productsUri, csvPayload);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static URI buildServerUrl(URL baseUrl, String resource, Integer lines) throws URISyntaxException {
        URI uri = baseUrl.toURI();
        String baseUrlString = uri.getPath();
        // trim trailing slashes
        if (baseUrlString.endsWith("/")) baseUrlString = baseUrlString.substring(0, baseUrlString.length() - 1);

        String newPath = baseUrlString + '/' + resource + '/' + lines;
        return uri.resolve(newPath);
    }

    public static List<Product> condenseArticleListToProductList(List<Article> articleList) {
        List<Product> productList = new ArrayList<>();
        List<Article> articlesInStock = articleList
                .stream()
                .filter(Article::isInStock)
                .collect(Collectors.toList());


        Map<String, List<Article>> mppy = articlesInStock.stream()
                .collect(groupingBy(Article::getProductId));

        for (List<Article> productGroup : mppy.values()) {
            Integer sumStockCount = productGroup.stream().mapToInt(Article::getStockCount).sum();
            Article cheapestArticle = productGroup.stream().min(
                    (first, second) -> Float.compare(first.getPrice(), second.getPrice())).get();

            Product product = new Product(cheapestArticle);
            product.setSumStockCount(sumStockCount);
            productList.add(product);
        }
        productList.sort(Comparator.comparing(Product::getSortKey));
        return productList;
    }
}

