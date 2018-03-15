package com.rubasace.bias.preset.manager.app.view;

import com.rubasace.bias.preset.manager.app.action.MainWindowClosedAction;
import eu.lestard.fluxfx.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ResourceBundle;

// @Component
public class MainWindowView extends ResourcesView {

	private final Stage stage;

	// @Autowired
	public MainWindowView(ResourceBundle resources, Stage stage, String cssResource, InputStream icon) {

		super(resources);

		this.stage = stage;

		// Set window title and icon

		stage.setTitle(this.translate("title"));
		stage.getIcons().add(new Image(icon));

		// Load and show main view

		Parent root = ViewLoader.load(MainView.class);

		Scene scene = new Scene(root, 600, 400);
		scene.getStylesheets().add(cssResource);

		stage.setScene(scene);
		stage.sizeToScene();

		// Listen to User actions

		stage.setOnCloseRequest(ev -> this.onCloseRequestHandler());

		// Listen to Store changes

	}

	private void onCloseRequestHandler() {
		this.publishAction(new MainWindowClosedAction());
	}

	public static void init(ResourceBundle resources, Stage stage, String cssResource, InputStream icon) {
		new MainWindowView(resources, stage, cssResource, icon);
	}

}
