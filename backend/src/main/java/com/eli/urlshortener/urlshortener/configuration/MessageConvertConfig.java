package com.eli.urlshortener.urlshortener.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

/**
 * This class configures the HttpMessageConverter for the urlshortener application
 * @author Ilay Zvi
 */
@Configuration
public class MessageConvertConfig {
    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter(){
        return new BufferedImageHttpMessageConverter();
    }
}
