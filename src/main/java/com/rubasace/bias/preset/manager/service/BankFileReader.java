package com.rubasace.bias.preset.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubasace.bias.preset.manager.factories.BankFactory;
import com.rubasace.bias.preset.manager.model.Bank;
import com.rubasace.bias.preset.manager.model.BankCollection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BankFileReader extends AbstractFileReader<BankCollection> {

  private final BankFactory bankFactory;

  public BankFileReader(final ObjectMapper objectMapper, final BankFactory bankFactory) {
    super(objectMapper);
    this.bankFactory = bankFactory;
  }

  @Override
  protected Class<BankCollection> getCollectionClass() {
    return BankCollection.class;
  }

  public BankCollection add(final String bankName, final File file) {
    try {
      BankCollection bankCollection = read(file);
      Bank bank = bankFactory.create(bankName, bankCollection);
      bankCollection.getBanks().add(bank);
      objectMapper.writeValue(file, bankCollection);
      return bankCollection;
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
