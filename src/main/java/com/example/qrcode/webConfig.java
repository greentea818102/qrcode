package com.example.qrcode;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

public class webConfig {
    @Bean
    public HttpMessageConverter< BufferedImage > createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter ();
    }
}
