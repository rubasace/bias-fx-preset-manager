package com.rubasace.bias.preset.manager.core.service;

import com.rubasace.bias.preset.manager.core.factories.BankFactory;
import com.rubasace.bias.preset.manager.core.model.Bank;
import com.rubasace.bias.preset.manager.core.model.BankCollection;
import com.rubasace.bias.preset.manager.core.util.FileMapper;
import com.rubasace.bias.preset.manager.core.writers.PresetCollectionWriter;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class BankCollectionService {

    private final BankFactory bankFactory;
    private final FileMapper fileMapper;
    private final PresetCollectionWriter presetCollectionWriter;

    public BankCollectionService(final BankFactory bankFactory, final FileMapper fileMapper, final PresetCollectionWriter presetCollectionWriter) {
        this.bankFactory = bankFactory;
        this.fileMapper = fileMapper;
        this.presetCollectionWriter = presetCollectionWriter;
    }

    public BankCollection add(final String bankName, final File bankFile) {

        BankCollection bankCollection = read(bankFile);
        Bank bank = this.bankFactory.create(bankName, bankCollection);
        bankCollection.getBanks().add(bank);
        this.fileMapper.write(bankFile, bankCollection);

        this.presetCollectionWriter.create(bankFile.getParentFile(), bank.getFolder());

        return bankCollection;
    }

    public BankCollection read(final File bankFile) {
        return this.fileMapper.read(bankFile, BankCollection.class);
    }
}
