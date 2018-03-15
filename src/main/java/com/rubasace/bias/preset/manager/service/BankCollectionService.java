package com.rubasace.bias.preset.manager.service;

import com.rubasace.bias.preset.manager.factories.BankFactory;
import com.rubasace.bias.preset.manager.model.Bank;
import com.rubasace.bias.preset.manager.model.BankCollection;
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

    public BankCollection add(final String bankName, final File file) {
        BankCollection bankCollection = read(file);
        Bank bank = bankFactory.create(bankName, bankCollection);
        bankCollection.getBanks().add(bank);
        fileMapper.write(file, bankCollection);
        return bankCollection;
    }

    public BankCollection read(final File file) {
        return fileMapper.read(file, BankCollection.class);
    }
}
