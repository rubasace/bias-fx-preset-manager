package com.rubasace.bias.preset.manager.app.config;

import com.rubasace.bias.preset.manager.app.util.ResourceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
public class JavaFxConfiguration {

    private static final String ICON_RESOURCE_URI = "icon/music.png";
    private static final String CSS_RESOURCE_URI = "css/application.css";
    private static final String I18N_MESSAGES = "i18n/messages";

    private final ResourceManager resourceManager;

    public JavaFxConfiguration(final ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    // @Bean
    // public Stage primaryStage() {
    // 	// FIXME find a way to return the real JavaFX primary stage
    // 	return new Stage();
    // }

    @Bean
    public ResourceBundle resources() {
        return ResourceBundle.getBundle(I18N_MESSAGES, Locale.getDefault());
    }

    // TODO proveer también el icono como una Image de JavaFX?
    @Bean
    public InputStream icon() {
        // Devolvemos un nuevo InputStream cada vez que se nos pida inyectar el icono
        return resourceManager.getAsStream(ICON_RESOURCE_URI);
    }

    @Bean
    public String css() {
        return resourceManager.getAsString(CSS_RESOURCE_URI);
    }
}
