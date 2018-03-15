package com.rubasace.bias.preset.manager.core.service;

import com.rubasace.bias.preset.manager.core.factories.BankFactory;
import com.rubasace.bias.preset.manager.core.model.Bank;
import com.rubasace.bias.preset.manager.core.model.BankCollection;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankCollectionServiceTest {

    @Mock
    private FileMapper fileMapper;

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
        when(fileMapper.read(file, BankCollection.class)).thenReturn(originalBankCollection);

        Bank newBank = mock(Bank.class);
        when(bankFactory.create(name, originalBankCollection)).thenReturn(newBank);

        BankCollection expectedBankCollection = new BankCollection();
        expectedBankCollection.setBanks(Arrays.asList(bank1, bank2, newBank));

        BankCollection readBankCollection = bankCollectionService.add(name, file);

        assertThat(readBankCollection, is(expectedBankCollection));
        verify(fileMapper).write(file, expectedBankCollection);
    }
}
