package com.rubasace.bias.preset.manager.app.view;

import com.rubasace.bias.preset.manager.app.action.AddBankAction;
import com.rubasace.bias.preset.manager.app.model.Bank;
import com.rubasace.bias.preset.manager.app.store.BankStore;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
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
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class BankManagementView extends ResourcesView {

	private final BankStore store;

	private final DirectoryChooser biasDirectoryChooser;
	private final DirectoryChooser presetsDirectoryChooser;

	private final ObjectProperty<File> biasDirectory;
	private final ObjectProperty<File> presetsDirectory;

	@FXML
	private TextField biasDirectoryTextField;

	@FXML
	private TextField presetsDirectoryTextField;

	@FXML
	private ListView<Bank> bankList;

	@FXML
	private TextField bankNameTextField;

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

		this.biasDirectory = new SimpleObjectProperty<>();
		this.presetsDirectory = new SimpleObjectProperty<>();

	}

	@FXML
	public void initialize() {

		this.biasDirectoryTextField.textProperty().bind(EasyBind.map(this.biasDirectory, this::fileToString));
		this.presetsDirectoryTextField.textProperty().bind(EasyBind.map(this.presetsDirectory, this::fileToString));

		// this.listView.setEditable(true);
		// this.listView.getSelectionModel().setCellSelectionEnabled(true);
		this.bankList.setCellFactory((ListView<Bank> param) -> {
			ListCell<Bank> listCell = new ListCell<Bank>() {
				@Override
				protected void updateItem(Bank item, boolean empty) {
					super.updateItem(item, empty);
					if(item != null) {
						this.setText(item.getName());
					}
				}
			};
			listCell.setEditable(true);
			return listCell;
		});

		this.bankList.setItems(this.store.getBanks());

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
		this.browseDirectory(this.biasDirectoryChooser, this.biasDirectory);
	}

	@FXML
	private void browsePresetDirectoryHandler() {
		this.browseDirectory(this.presetsDirectoryChooser, this.presetsDirectory);
	}

	private void browseDirectory(DirectoryChooser directoryChooser, ObjectProperty<File> fileProperty) {

		// FIXME find a better way to get the window
		Window window = this.bankList.getScene().getWindow();

		File directory = directoryChooser.showDialog(window);

		fileProperty.set(directory);

		Optional.ofNullable(directory)
				.map(File::getParentFile)
				.ifPresent(directoryChooser::setInitialDirectory);

	}

	@FXML
	private void addBankHandler() {

		String bankName = this.bankNameTextField.getText().trim();

		if(bankName.isEmpty()) {
			// Do nothing
			return;
		}

		this.publishAction(new AddBankAction(bankName));

		this.bankNameTextField.clear();

	}

	@FXML
	private void importHandler() {
		System.out.println("Pending...");
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