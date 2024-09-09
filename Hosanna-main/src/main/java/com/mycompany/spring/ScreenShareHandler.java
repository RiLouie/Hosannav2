/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.spring;

import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenShareHandler extends BinaryWebSocketHandler {
    private volatile boolean running = true;
    private Rectangle windowBounds;
    
    public ScreenShareHandler(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
    }

    ScreenShareHandler() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        new Thread(() -> captureAndSendScreen(session)).start();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        running = false;
    }

    private void captureAndSendScreen(WebSocketSession session) {
        try {
            Robot robot = new Robot();
//            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            while (running) {
                BufferedImage windowCapture = robot.createScreenCapture(windowBounds);
                byte[] imageInByte;
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(windowCapture, "jpg", baos);
                    baos.flush();
                    imageInByte = baos.toByteArray();
                }

                if (session.isOpen()) {
                    session.sendMessage(new BinaryMessage(imageInByte));
                }

                Thread.sleep(100); // Adjust the frame rate as needed
            }
        } catch (AWTException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
