package com.rubasace.bias.preset.manager.app.view;

import com.rubasace.bias.preset.manager.app.action.AddBankAction;
import com.rubasace.bias.preset.manager.app.action.BiasDirectorySelectedAction;
import com.rubasace.bias.preset.manager.app.action.ImportAction;
import com.rubasace.bias.preset.manager.app.action.PresetsDirectorySelectedAction;
import com.rubasace.bias.preset.manager.app.model.Bank;
import com.rubasace.bias.preset.manager.app.store.BankStore;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.fxmisc.easybind.EasyBind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class BankManagementView extends ResourcesView {

    private final BankStore store;

    private final DirectoryChooser biasDirectoryChooser;
    private final DirectoryChooser presetsDirectoryChooser;

    @FXML
    private TextField biasDirectoryTextField;

    @FXML
    private TextField presetsDirectoryTextField;

    @FXML
    private ListView<Bank> bankList;

    @FXML
    private TextField bankNameTextField;

    @FXML
    private Button importButton;

    @Autowired
    public BankManagementView(ResourceBundle resources, BankStore store) {

        super(resources);

        this.store = store;

        File userHomeDirectory = SystemUtils.getUserHome();

        this.biasDirectoryChooser = new DirectoryChooser();
        this.biasDirectoryChooser.setInitialDirectory(userHomeDirectory);
        this.biasDirectoryChooser.setTitle("%open.title");

        this.presetsDirectoryChooser = new DirectoryChooser();
        this.presetsDirectoryChooser.setInitialDirectory(userHomeDirectory);
        this.presetsDirectoryChooser.setTitle("%open.title");

    }

    @FXML
    public void initialize() {

        this.biasDirectoryTextField.textProperty().bind(EasyBind.map(this.store.biasDirectoryProperty(), this::fileToString));
        this.presetsDirectoryTextField.textProperty().bind(EasyBind.map(this.store.presetsDirectoryProperty(), this::fileToString));

        // this.listView.setEditable(true);
        // this.listView.getSelectionModel().setCellSelectionEnabled(true);
        this.bankList.setCellFactory((ListView<Bank> param) -> {
            ListCell<Bank> listCell = new ListCell<Bank>() {
                @Override
                protected void updateItem(Bank item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        this.setText(item.getName());
                    }
                }
            };
            // listCell.setEditable(true);
            return listCell;
        });

        this.bankList.setItems(this.store.getBanks());

        // TODO uncomment when fixed
        // this.bankNameTextField.disableProperty().bind(this.store.biasDirectoryProperty().isNull());

        // TODO revisit (.isNull())
        ReadOnlyObjectProperty<?>[] mandatoryProperties = {
                this.store.biasDirectoryProperty(),
                this.store.presetsDirectoryProperty(),
                this.bankList.getSelectionModel().selectedItemProperty()
        };

        this.importButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> Arrays.stream(mandatoryProperties)
                            .map(ReadOnlyObjectProperty::get)
                            .anyMatch(Objects::isNull),
                mandatoryProperties
        ));

    }

    private String fileToString(File file) {
        return Optional.ofNullable(file)
                       .map(File::getAbsolutePath)
                       // .map(s -> StringUtils.abbreviateMiddle(s, "...", 50))
                       .map(s -> StringUtils.abbreviate(s, "...", s.length(), 50))
                       .orElse("");
    }

    @FXML
    private void browseBiasDirectoryHandler() {

        // FIXME find a better way to get the window
        Window window = this.bankList.getScene().getWindow();

        File directory = this.biasDirectoryChooser.showDialog(window);

        this.publishAction(new BiasDirectorySelectedAction(directory));

        Optional.ofNullable(directory)
                .map(File::getParentFile)
                .ifPresent(this.biasDirectoryChooser::setInitialDirectory);

    }

    @FXML
    private void browsePresetDirectoryHandler() {

        // FIXME find a better way to get the window
        Window window = this.bankList.getScene().getWindow();

        File directory = this.presetsDirectoryChooser.showDialog(window);

        this.publishAction(new PresetsDirectorySelectedAction(directory));

        Optional.ofNullable(directory)
                .map(File::getParentFile)
                .ifPresent(this.presetsDirectoryChooser::setInitialDirectory);

    }

    @FXML
    private void addBankHandler() {

        String bankName = this.bankNameTextField.getText().trim();

        if (bankName.isEmpty()) {
            // Do nothing
            return;
        }

        this.publishAction(new AddBankAction(bankName));

        this.bankNameTextField.clear();

    }

    @FXML
    private void importHandler() {

        Bank bank = this.bankList.getSelectionModel().getSelectedItem();

        this.publishAction(new ImportAction(bank));

    }

    // private Callback<CellDataFeatures<ObservableList<ObjectProperty<?>>, String>, ObservableValue<String>> createCellValueFactory(int columnIndex, DataFormat<?> userFormat) {
    //
    // return cdf -> {
    //
    // ObjectProperty<?> observableValue = cdf.getValue().get(columnIndex);
    //
    // @SuppressWarnings("unchecked")
    // DataFormat<Object> casted = (DataFormat<Object>) userFormat;
    //
    // return Bindings.createStringBinding(() -> casted.toString(observableValue.getValue()), observableValue);
    //
    // };
    //
    // }
    //
    // private EventHandler<CellEditEvent<ObservableList<ObjectProperty<?>>, String>> createOnEditCommit(int columnIndex, DataFormat<?> userFormat) {
    // return event -> {
    //
    // ObjectProperty<?> objectProperty = event.getRowValue().get(columnIndex);
    //
    // @SuppressWarnings("unchecked")
    // ObjectProperty<Object> casted = (ObjectProperty<Object>) objectProperty;
    //
    // try {
    // casted.set(userFormat.fromString(event.getNewValue()));
    // } catch(ParseException ex) {
    // System.err.println("La que has liao, pollito...");
    // } catch(Exception ex) {
    // System.err.println("Error inesperado");
    // }
    //
    // };
    // }

}
