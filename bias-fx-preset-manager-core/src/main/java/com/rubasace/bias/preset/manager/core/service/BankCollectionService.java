package com.rubasace.bias.preset.manager.core.service;

import com.rubasace.bias.preset.manager.core.factories.BankFactory;
import com.rubasace.bias.preset.manager.core.model.Bank;
import com.rubasace.bias.preset.manager.core.model.BankCollection;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class BankCollectionService {

    private final BankFactory bankFactory;
    private final FileMapper fileMapper;

    public BankCollectionService(final BankFactory bankFactory, final FileMapper fileMapper) {
        this.bankFactory = bankFactory;
        this.fileMapper = fileMapper;
    }

    public BankCollection add(final String bankName, final File bankFile) {
        BankCollection bankCollection = read(bankFile);
        Bank bank = this.bankFactory.create(bankName, bankCollection);
        bankCollection.getBanks().add(bank);
        this.fileMapper.write(bankFile, bankCollection);
        return bankCollection;
    }

    public BankCollection read(final File bankFile) {
        return this.fileMapper.read(bankFile, BankCollection.class);
    }
}
