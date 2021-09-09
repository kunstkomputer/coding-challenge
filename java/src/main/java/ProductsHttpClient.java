import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProductsHttpClient {
    public static void httpArticlesGetRequest(URI uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(uri)
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("httpGetRequest: " + responseBody);
        System.out.println("httpGetRequest status code: " + responseStatusCode);
    }

    public static void httpProductsPutRequest(URI uri, String csvContent) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "text/csv")
                .PUT(HttpRequest.BodyPublishers.ofString(csvContent))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("httpPostRequest : " + responseBody);
    }
}
