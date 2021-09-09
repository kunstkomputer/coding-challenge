import pojo.Article;
import pojo.Product;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CodingChallenge {
    public static void main(String[] args) {


        try {
            URL serverUrl = new URL("http://localhost:8080?a=??");
            Integer numArticlesToFetch = 50;

            // fetch articles from webserver and stream to parser
            URI articlesUri = buildServerUrl(serverUrl, "articles", numArticlesToFetch);
            InputStream is = ProductsHttpClient.httpArticlesGetRequest(articlesUri);

            List<Article> arl = CsvParser.parseCsvAsStream(is);

            URI productsUri = buildServerUrl(serverUrl, "products", 0);
            ProductsHttpClient.httpProductsPutRequest(productsUri, "produktId|name|beschreibung|preis|summeBestand\n");
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
        ArrayList<Product> productArrayList = new ArrayList<>();
        for (Article art : articleList) {
            if ( art.stockCount > 0 ){
                Product prd = new Product(art.productId,art.name,art.description,art.price,art.stockCount);
                productArrayList.add(prd);
            }

        }
        return productArrayList;
    }
}

