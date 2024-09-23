/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.spring;

import java.awt.Dimension;
import java.awt.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(screenShareHandler(), "/screen-share").setAllowedOrigins("*");
    }

    @Bean
    public ScreenShareHandler screenShareHandler() {
        // Supply custom Point and Dimension for the capture area
        Point customCapturePoint = new Point(100, 100);  // Example: Capture starts at (100, 100)
        Dimension customCaptureSize = new Dimension(1024, 768);  // Example: 1024x768 capture size

        // Create and return ScreenShareHandler bean with supplied Point and Dimension
        return new ScreenShareHandler(customCapturePoint, customCaptureSize);
    }
}