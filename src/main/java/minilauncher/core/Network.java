package minilauncher.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;

/**
 * UNUSED
 */
public class Network extends App {
    public static void downloadFile(URL url, String fileName) throws IOException {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }
	public static void findVersions(Runnable callback) {
		new Thread(() -> {
			Log.debug("Fetching release list from GitHub..."); // Fetch the latest version from GitHub
			try {
                HttpURLConnection con = (HttpURLConnection) new URL("https://api.github.com/repos/chrisj42/minicraft-plus-revived/releases").openConnection();
				con.setRequestMethod("GET");
                if (con.getResponseCode() != 200) {
					Log.debug("Version request returned status code " + con.getResponseCode() + ": " + con.getResponseMessage());
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    String inputLine;
                    String content = "";
                    while ((inputLine = in.readLine()) != null) {
                        content += inputLine;
                    }
                    in.close();
                    Log.debug("Response body: " + content.toString());
				} else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    String content = "";
                    while ((inputLine = in.readLine()) != null) {
                        content += inputLine;
                    }
                    in.close();
                    new JSONArray(content);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			callback.run(); // finished.
		}).start();
	}
}
