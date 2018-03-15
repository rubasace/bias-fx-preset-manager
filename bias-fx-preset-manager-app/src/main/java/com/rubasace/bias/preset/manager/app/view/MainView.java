package com.rubasace.bias.preset.manager.app.view;

import com.rubasace.bias.preset.manager.app.action.ExitApplicationAction;
import com.rubasace.bias.preset.manager.app.store.BankStore;
import com.rubasace.bias.preset.manager.app.store.ViewStore;
import eu.lestard.fluxfx.View;
import eu.lestard.fluxfx.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class MainView extends ResourcesView {

	private final ViewStore viewStore;

	@FXML
	private GridPane viewContainer;

	@FXML
	private MenuItem closeMenuItem;

	@Autowired
	public MainView(ResourceBundle resources, ViewStore viewStore, BankStore bankStore) {
		super(resources);
		this.viewStore = viewStore;
	}

	@FXML
	public void initialize() {

		this.loadView(this.viewStore.getCurrentView());
		this.viewStore.currentViewProperty().addListener((obs, ov, nv) -> this.loadView(nv));

	}

	private void loadView(Class<? extends View> viewClass) {

		this.viewContainer.getChildren().clear();

		Optional.ofNullable(viewClass).ifPresent(v -> {
			Parent root = ViewLoader.load(v);
			this.viewContainer.getChildren().add(root);
		});

	}

	@FXML
	private void exitMenuItemHandler() {
		this.publishAction(new ExitApplicationAction());
	}

}
