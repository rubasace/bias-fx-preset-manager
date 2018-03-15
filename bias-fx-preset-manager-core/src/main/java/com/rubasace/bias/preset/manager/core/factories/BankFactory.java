package com.rubasace.bias.preset.manager.core.factories;

import com.rubasace.bias.preset.manager.core.model.Bank;
import com.rubasace.bias.preset.manager.core.model.BankCollection;
import org.springframework.stereotype.Component;

@Component
public class BankFactory {

    public Bank create(final String name, final BankCollection bankCollection) {
        Bank bank = new Bank();
        bank.setFolder(name);
        bank.setName(name);
        bank.setOrder(bankCollection.getBanks().size());
        return bank;
    }
}
