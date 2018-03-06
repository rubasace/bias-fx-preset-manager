package com.rubasace.bias.preset.manager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Preset {

	@JsonProperty("is_favorite")
	private boolean favorite;
	@JsonProperty("preset_name")
	private String name;
	@JsonProperty("preset_uuid")
	private String uuid;
	@JsonProperty("display_order")
	private int order;

	public int getOrder() {
		return this.order;
	}

	public void setOrder(final int order) {
		this.order = order;
	}

	public boolean isFavorite() {
		return this.favorite;
	}

	public void setFavorite(final boolean favorite) {
		this.favorite = favorite;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

}
