package com.rubasace.bias.preset.manager.app.view;

import eu.lestard.fluxfx.View;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public abstract class ResourcesView implements View {

	private final ResourceBundle resources;

	public ResourcesView(ResourceBundle resources) {
		this.resources = resources;
	}

	protected ResourceBundle getResources() {
		return this.resources;
	}

	private boolean canTranslate(String key) {
		return key != null && this.resources != null && this.resources.containsKey(key);
	}

	protected String translate(String key) {

		if(!this.canTranslate(key)) {
			return key;
		}

		return this.resources.getString(key);

	}

	protected String translate(String key, Object... args) {

		if(!this.canTranslate(key)) {
			return key;
		}

		return MessageFormat.format(this.resources.getString(key), args);

	}

	protected String format(String message, Object... args) {
		return MessageFormat.format(message, args);
	}

}
