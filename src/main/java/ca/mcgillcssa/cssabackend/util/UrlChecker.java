package ca.mcgillcssa.cssabackend.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlChecker {
    public static boolean isValidUrl(String imgUrl) throws IOException {
        URL url = new URL(imgUrl);
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("HEAD");
        return huc.getResponseCode() == 200;
    }
}
