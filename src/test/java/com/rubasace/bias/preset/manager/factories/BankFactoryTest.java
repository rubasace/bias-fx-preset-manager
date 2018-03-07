package com.rubasace.bias.preset.manager.factories;

import com.rubasace.bias.preset.manager.model.Bank;
import com.rubasace.bias.preset.manager.model.BankCollection;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BankFactoryTest {

  private final BankFactory bankFactory = new BankFactory();

  @Test
  public void shouldCreate() {

    String name = "test bank";
    BankCollection bankCollection = new BankCollection();
    bankCollection.setBanks(Arrays.asList(new Bank(), new Bank()));

    Bank bank = bankFactory.create(name, bankCollection);

    assertThat(bank.getName(), is(name));
    assertThat(bank.getFolder(), is(name));
    assertThat(bank.getOrder(), is(bankCollection.getBanks().size()));
  }
}
