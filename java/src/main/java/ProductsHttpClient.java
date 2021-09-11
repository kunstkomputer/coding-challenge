import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ProductsHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(ProductsHttpClient.class);

    public static InputStream httpArticlesGetRequest(URI uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(uri)
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        logger.info("httpGetRequest status code: " + response.statusCode());
        return response.body();
    }

    public static void httpProductsPutRequest(URI uri, byte[] csvContent) throws IOException, InterruptedException {
        logger.trace("Csv File Content: \n" + new String(csvContent, StandardCharsets.UTF_8));
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "text/csv")
                .PUT(HttpRequest.BodyPublishers.ofByteArray(csvContent))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        logger.info("httpGetRequest status code: " + response.statusCode());
        logger.info("httpPostRequest : " + response.body());
    }
}
