package com.rubasace.bias.preset.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubasace.bias.preset.manager.factories.BankFactory;
import com.rubasace.bias.preset.manager.model.Bank;
import com.rubasace.bias.preset.manager.model.BankCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankCollectionServiceTest {

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private BankFactory bankFactory;

  @InjectMocks
  private BankCollectionService bankCollectionService;

  @Test
  public void shouldAdd() throws IOException {

    File file = mock(File.class);
    String name = "test name";

    BankCollection originalBankCollection = new BankCollection();
    Bank bank1 = mock(Bank.class);
    Bank bank2 = mock(Bank.class);
    List<Bank> originalBanks = new ArrayList<>();
    originalBanks.add(bank1);
    originalBanks.add(bank2);
    originalBankCollection.setBanks(originalBanks);
    when(objectMapper.readValue(file, BankCollection.class)).thenReturn(originalBankCollection);

    Bank newBank = mock(Bank.class);
    when(bankFactory.create(name, originalBankCollection)).thenReturn(newBank);

    BankCollection expectedBankCollection = new BankCollection();
    expectedBankCollection.setBanks(Arrays.asList(bank1, bank2, newBank));

    BankCollection readBankCollection = bankCollectionService.add(name, file);

    assertThat(readBankCollection, is(expectedBankCollection));
    verify(objectMapper).writeValue(file, expectedBankCollection);
  }

  @Test(expected = RuntimeException.class)
  public void addShouldThrowRuntimeWhenObjectmapperFailsToRead() throws IOException {

    File file = mock(File.class);
    String name = "test name";

    BankCollection originalBankCollection = new BankCollection();
    Bank bank1 = mock(Bank.class);
    Bank bank2 = mock(Bank.class);
    originalBankCollection.setBanks(Arrays.asList(bank1, bank2));
    when(objectMapper.readValue(file, BankCollection.class)).thenThrow(new IOException());

    Bank newBank = mock(Bank.class);
    when(bankFactory.create(name, originalBankCollection)).thenReturn(newBank);

    BankCollection expectedBankCollection = new BankCollection();
    expectedBankCollection.setBanks(Arrays.asList(bank1, bank2, newBank));

    bankCollectionService.add(name, file);
  }

  @Test(expected = RuntimeException.class)
  public void addShouldThrowRuntimeWhenObjectmapperFailsToWrite() throws IOException {

    File file = mock(File.class);
    String name = "test name";

    BankCollection originalBankCollection = new BankCollection();
    Bank bank1 = mock(Bank.class);
    Bank bank2 = mock(Bank.class);
    originalBankCollection.setBanks(Arrays.asList(bank1, bank2));
    when(objectMapper.readValue(file, BankCollection.class)).thenReturn(originalBankCollection);

    Bank newBank = mock(Bank.class);
    when(bankFactory.create(name, originalBankCollection)).thenReturn(newBank);

    BankCollection expectedBankCollection = new BankCollection();
    expectedBankCollection.setBanks(Arrays.asList(bank1, bank2, newBank));
    doThrow(new IOException()).when(objectMapper).writeValue(file, expectedBankCollection);

    bankCollectionService.add(name, file);
  }
}
