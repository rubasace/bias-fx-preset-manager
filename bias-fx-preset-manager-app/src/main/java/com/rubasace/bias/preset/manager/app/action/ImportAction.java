package com.rubasace.bias.preset.manager.app.action;

import com.rubasace.bias.preset.manager.app.model.Bank;
import eu.lestard.fluxfx.Action;

public class ImportAction implements Action {

    private final Bank bank;

    public ImportAction(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return this.bank;
    }
}
