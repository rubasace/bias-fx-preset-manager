package com.rubasace.bias.preset.manager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object o) {
		return reflectionEquals(this, o);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
	}

}
