package com.rubasace.bias.preset.manager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bank {

  @JsonProperty("bank_folder")
  private String folder;
  @JsonProperty("bank_name")
  private String name;
  @JsonProperty("display_order")
  private int order;

  public String getFolder() {
    return folder;
  }

  public void setFolder(final String folder) {
    this.folder = folder;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(final int order) {
    this.order = order;
  }
}
