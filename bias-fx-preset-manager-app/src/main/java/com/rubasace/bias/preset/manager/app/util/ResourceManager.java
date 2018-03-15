package com.rubasace.bias.preset.manager.app.util;

import java.io.InputStream;
import java.net.URL;

public class ResourceManager {

	public static URL get(String name) {
		return ResourceManager.class.getResource(name);
	}

	public static String getAsString(String name) {
		return ResourceManager.class.getResource(name).toExternalForm();
	}

	public static InputStream getAsStream(String name) {
		return ResourceManager.class.getResourceAsStream(name);
	}

}
