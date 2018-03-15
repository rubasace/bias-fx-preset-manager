package com.rubasace.bias.preset.manager.app;

import com.rubasace.bias.preset.manager.app.action.ApplicationStartedAction;
import com.rubasace.bias.preset.manager.app.view.MainWindowView;
import com.rubasace.bias.preset.manager.core.config.ManagerConfiguration;
import eu.lestard.fluxfx.Dispatcher;
import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.io.InputStream;
import java.util.ResourceBundle;

@Import(ManagerConfiguration.class)
@SpringBootApplication
public class ManagerApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {

        this.springContext = SpringApplication.run(ManagerApplication.class);
    }

    @Override
    public void start(Stage stage) {

        Platform.setImplicitExit(false);
        this.springContext.getBeanFactory().registerResolvableDependency(Stage.class, stage);

        // Set dependency injector and resources for views
        ViewLoader.setDependencyInjector(this.springContext::getBean);
        ViewLoader.setResourceBundle(this.springContext.getBean(ResourceBundle.class));

        // Initialize MainWindowView
        MainWindowView.init(
                this.springContext.getBean(ResourceBundle.class),
                stage,
                this.springContext.getBean("css", String.class),
                this.springContext.getBean("icon", InputStream.class)
        );

        // Start application
        Dispatcher.getInstance().dispatch(new ApplicationStartedAction());

        stage.show();

        // stage.setScene(new Scene(this.rootNode));
        // stage.show();

    }

    @Override
    public void stop() throws Exception {
        this.springContext.close();
    }
}

