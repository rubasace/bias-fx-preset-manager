package com.rubasace.bias.preset.manager.app.store;

import com.rubasace.bias.preset.manager.app.action.AddBankAction;
import com.rubasace.bias.preset.manager.app.action.BiasDirectorySelectedAction;
import com.rubasace.bias.preset.manager.app.action.ImportAction;
import com.rubasace.bias.preset.manager.app.action.PresetsDirectorySelectedAction;
import com.rubasace.bias.preset.manager.app.model.Bank;
import com.rubasace.bias.preset.manager.core.model.BankCollection;
import com.rubasace.bias.preset.manager.core.service.BankCollectionService;
import com.rubasace.bias.preset.manager.core.service.PresetImporter;
import eu.lestard.fluxfx.Store;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class BankStore extends Store {

    private final ObservableList<Bank> banks = FXCollections.observableArrayList();
    private final ObjectProperty<File> biasDirectory = new SimpleObjectProperty<>();
    private final ObjectProperty<File> presetsDirectory = new SimpleObjectProperty<>();

    private final BankCollectionService bankCollectionService;
    private final PresetImporter presetImporter;

    public BankStore(final BankCollectionService bankCollectionService, final PresetImporter presetImporter) {
        this.bankCollectionService = bankCollectionService;
        this.presetImporter = presetImporter;
    }

    @PostConstruct
    private void subscribe() {
        this.subscribe(AddBankAction.class, this::addBank);
        this.subscribe(BiasDirectorySelectedAction.class, this::biasDirectorySelectedHandler);
        this.subscribe(PresetsDirectorySelectedAction.class, this::presetsDirectorySelectedHandler);
        this.subscribe(ImportAction.class, this::importHandler);
    }

    public File getBiasDirectory() {
        return this.biasDirectory.get();
    }

    public ReadOnlyObjectProperty<File> biasDirectoryProperty() {
        return this.biasDirectory;
    }

    public File getPresetsDirectory() {
        return this.presetsDirectory.get();
    }

    public ReadOnlyObjectProperty<File> presetsDirectoryProperty() {
        return this.presetsDirectory;
    }

    public ObservableList<Bank> getBanks() {
        return FXCollections.unmodifiableObservableList(this.banks);
    }

    private void biasDirectorySelectedHandler(final BiasDirectorySelectedAction action) {
        this.biasDirectory.set(getPresetsFile(action.getBiasDirectory()));
        this.loadBanks();
    }

    private void presetsDirectorySelectedHandler(final PresetsDirectorySelectedAction action) {
        this.presetsDirectory.set(action.getPresetsDirectory());
    }

    private void addBank(AddBankAction action) {

        File bankFile = new File(this.biasDirectory.get(), "bank.json");

        BankCollection bankCollection = this.bankCollectionService.add(action.getBankName(), bankFile);

        addBankCollection(bankCollection);

    }

    private void importHandler(final ImportAction action) {

        Bank bank = action.getBank();

        this.presetImporter.importAll(this.biasDirectory.get(), bank.getFolder(), this.presetsDirectory.get());

    }

    private void loadBanks() {

        File bankFile = new File(this.biasDirectory.get(), "bank.json");

        BankCollection bankCollection = this.bankCollectionService.read(bankFile);

        addBankCollection(bankCollection);

    }

    private void addBankCollection(BankCollection bankCollection) {

        Bank[] loadedBanks = bankCollection.getBanks().stream()
                                           .map(this::convert)
                                           .toArray(Bank[]::new);

        this.banks.setAll(loadedBanks);

    }

    // TODO do this in BankCollectionService
    private File getPresetsFile(File biasDirectory) {
        return new File(biasDirectory, "Presets");
    }

    private Bank convert(com.rubasace.bias.preset.manager.core.model.Bank bank) {
        Bank result = new Bank();
        BeanUtils.copyProperties(bank, result);
        return result;
    }

}
