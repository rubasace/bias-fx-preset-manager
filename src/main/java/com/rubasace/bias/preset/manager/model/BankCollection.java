package com.rubasace.bias.preset.manager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BankCollection {

  @JsonProperty("LiveBanks")
  private List<Bank> banks;

  public List<Bank> getBanks() {
    return banks;
  }

  public void setBanks(final List<Bank> banks) {
    this.banks = banks;
  }
}
