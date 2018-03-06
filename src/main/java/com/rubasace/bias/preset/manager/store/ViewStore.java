package com.rubasace.bias.preset.manager.store;

import com.rubasace.bias.preset.manager.action.ApplicationStartedAction;
import com.rubasace.bias.preset.manager.action.ExitApplicationAction;
import com.rubasace.bias.preset.manager.action.MainWindowClosedAction;
import com.rubasace.bias.preset.manager.view.BankManagementView;
import eu.lestard.fluxfx.Store;
import eu.lestard.fluxfx.View;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.springframework.stereotype.Component;

@Component
public class ViewStore extends Store {

	// private final Stage primaryStage;
	private final ObjectProperty<Class<? extends View>> currentView;

	// @Autowired
	public ViewStore(/*Stage primaryStage*/) {

		// this.primaryStage = primaryStage;
		this.currentView = new ReadOnlyObjectWrapper<>();

		this.subscribe(ApplicationStartedAction.class, this::startApplicationAction);
		this.subscribe(ExitApplicationAction.class, this::exitApplicationAction);
		this.subscribe(MainWindowClosedAction.class, this::mainWindowClosedAction);

	}

	// Actions handlers

	private void startApplicationAction(ApplicationStartedAction action) {

		this.currentView.set(BankManagementView.class);

		// FIXME deberíamos hacer esto cambiando el estado de un boolean y que la vista lo muestre?
		// this.primaryStage.show();

	}

	private void exitApplicationAction(ExitApplicationAction action) {
		Platform.exit();
	}

	private void mainWindowClosedAction(MainWindowClosedAction action) {
		// TODO en un futuro, habría que comprobar si hay datos sin guardar para mostrar una alerta, etc.
		Platform.exit();
	}

	public Class<? extends View> getCurrentView() {
		return this.currentView.get();
	}

	public ReadOnlyObjectProperty<Class<? extends View>> currentViewProperty() {
		return this.currentView;
	}

}
