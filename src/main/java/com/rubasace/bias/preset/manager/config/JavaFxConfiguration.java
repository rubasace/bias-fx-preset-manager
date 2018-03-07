package com.rubasace.bias.preset.manager.config;

import com.rubasace.bias.preset.manager.ManagerApplication;
import com.rubasace.bias.preset.manager.util.ResourceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
public class JavaFxConfiguration {

    private static final String ICON_RESOURCE_URI = "icon/music.png";
    private static final String CSS_RESOURCE_URI = "application.css";

    // @Bean
    // public Stage primaryStage() {
    // 	// FIXME find a way to return the real JavaFX primary stage
    // 	return new Stage();
    // }

    @Bean
    public ResourceBundle resources() {
        return ResourceBundle.getBundle(ManagerApplication.class.getPackage().getName() + ".i18n.messages", Locale.getDefault());
    }

    // TODO proveer también el icono como una Image de JavaFX?
    @Bean
    public InputStream icon() {
        // Devolvemos un nuevo InputStream cada vez que se nos pida inyectar el icono
        return ResourceManager.getAsStream(ICON_RESOURCE_URI);
    }

    @Bean(name = "css")
    public String cssResource() {
        return ResourceManager.getAsString(CSS_RESOURCE_URI);
    }
}
