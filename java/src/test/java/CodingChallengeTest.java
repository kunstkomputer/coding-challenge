import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
}