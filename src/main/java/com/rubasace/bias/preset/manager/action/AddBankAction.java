package com.rubasace.bias.preset.manager.action;

import eu.lestard.fluxfx.Action;

public class AddBankAction implements Action {

	private final String bankName;

	public AddBankAction(String bankName) {
		this.bankName = bankName;
	}

	public String getBankName() {
		return this.bankName;
	}

}
