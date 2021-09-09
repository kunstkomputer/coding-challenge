import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CodingChallenge {
    public static void main(String[] args) {


        try {
            Integer numArticlesToFetch = 50;
            URI serverUrl = buildServerUrl(new URL("http://localhost:8080?a=??"), "articles", numArticlesToFetch);
            ProductsHttpClient.httpArticlesGetRequest(serverUrl);
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
}

