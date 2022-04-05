package minilauncher.core;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.saveload.Version;

/**
 * UNUSED
 */
public class VersionInfo {
	
	public final Version version;
	public final String releaseUrl;
	public final String releaseName;
	public final String releaseJarUrl;
	
	public VersionInfo(JSONObject releaseInfo) {
		String versionTag = releaseInfo.getString("tag_name").substring(1); // Cut off the "v" at the beginning
		version = new Version(versionTag);
		
		releaseUrl = releaseInfo.getString("html_url");
		
		releaseName = releaseInfo.getString("name");

		String jar = "";
		JSONArray assets = releaseInfo.getJSONArray("assets");
		for (int i = 0; i<assets.length(); i++) {
			JSONObject asset = assets.getJSONObject(i);
			if (asset.getString("name").endsWith(".jar")) {
				jar = asset.getString("browser_download_url");
				break;
			}
		}
		releaseJarUrl = jar;
	}
	
	public VersionInfo(Version version, String releaseUrl, String releaseName, String releaseJarUrl) {
		this.version = version;
		this.releaseUrl = releaseUrl;
		this.releaseName = releaseName;
		this.releaseJarUrl = releaseJarUrl;
	}
	
}
