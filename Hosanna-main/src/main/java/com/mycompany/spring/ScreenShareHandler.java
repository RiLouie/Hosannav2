/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.spring;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ScreenShareHandler extends BinaryWebSocketHandler {
    private volatile boolean running = true;
    private Rectangle windowBounds;
    
    private Point capturePoint;
    private Dimension captureSize;

    // Default constructor (no arguments)
    public ScreenShareHandler(Point capturePoint, Dimension captureSize) {
        this.capturePoint = capturePoint;
        this.captureSize = captureSize;
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
            Rectangle screenRect = new Rectangle(capturePoint, captureSize);

            while (running) {
                //BufferedImage windowCapture = robot.createScreenCapture(windowBounds);
                BufferedImage windowCapture = robot.createScreenCapture(screenRect);
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
        }
    }

    
     // Getter and Setter for capture point (top-left corner)
    public Point getCapturePoint() {
        return capturePoint;
    }

    public void setCapturePoint(Point capturePoint) {
        this.capturePoint = capturePoint;
    }

    // Getter and Setter for capture size (width and height)
    public Dimension getCaptureSize() {
        return captureSize;
    }

    public void setCaptureSize(Dimension captureSize) {
        this.captureSize = captureSize;
    }

}
