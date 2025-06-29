/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.entity.DisplayDimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author admin
 */
public class DisplayService {
    
    
    public List<DisplayDimension>  getDisplayList (){
    
        DefaultListModel dm =  new DefaultListModel();
        List<DisplayDimension> dmList = new ArrayList<DisplayDimension>();
        DisplayDimension displayDimentsion = null;
        // Get the number of displays
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        int numberOfDisplays = screens.length;

        StringBuilder sb = new StringBuilder();
        sb.append("Number of Displays: ").append(numberOfDisplays).append("\n\n");
        
        
        
        // Iterate through each screen and display its details
        for (int i = 0; i < screens.length; i++) {
            displayDimentsion = new DisplayDimension();
            GraphicsDevice screen = screens[i];
            DisplayMode displayMode = screen.getDisplayMode();
            int width = displayMode.getWidth();
            int height = displayMode.getHeight();
            int refreshRate = displayMode.getRefreshRate();
            int bitDepth = displayMode.getBitDepth();

            // Get the screen bounds (position and size)
            GraphicsConfiguration gc = screen.getDefaultConfiguration();
            Rectangle bounds = gc.getBounds();
            int xPos = bounds.x;
            int yPos = bounds.y;
            
            
            displayDimentsion.setxPos(bounds.x);
            displayDimentsion.setyPos(bounds.y);
            displayDimentsion.setWidth(width);
            displayDimentsion.setHeight(height);

            // Append the details to the text area
            sb.append("Display ").append(i + 1).append(":\n")
            .append("  Resolution: ").append(width).append(" x ").append(height).append("\n")
            .append("  Position: (").append(xPos).append(", ").append(yPos).append(")\n")
            .append("  Refresh Rate: ").append(refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN ? "Unknown" : refreshRate + " Hz").append("\n")
            .append("  Bit Depth: ").append(bitDepth).append(" bits\n\n");

            String displayNum = "Display("+ xPos +","+yPos+"),("+width+"," + height + ")";
            dm.addElement(displayNum);
            dmList.add(displayDimentsion);
        }
        
        return dmList;
    }
    
}
