package com.rubasace.bias.preset.manager.store;

import com.rubasace.bias.preset.manager.action.AddBankAction;
import com.rubasace.bias.preset.manager.model.javafx.Bank;
import eu.lestard.fluxfx.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class BankStore extends Store {

	private final ObservableList<Bank> banks = FXCollections.observableArrayList();

	// @Autowired
	public BankStore() {
		this.subscribe(AddBankAction.class, this::addBank);
	}

	public ObservableList<Bank> getBanks() {
		return FXCollections.unmodifiableObservableList(this.banks);
	}

	private void addBank(AddBankAction action) {

		Bank bank = new Bank();
		bank.setName(action.getBankName());

		this.banks.add(bank);

	}

}
