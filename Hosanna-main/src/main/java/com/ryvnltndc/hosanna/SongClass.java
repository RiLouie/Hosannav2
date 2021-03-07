/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryvnltndc.hosanna;

import com.ryvnltndc.Controller.DbConnection;
import com.ryvnltndc.model.Song;
import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author acer
 */
public class SongClass {
    
    
    public ArrayList<Song>  loadSongs(){
        
        ArrayList<Song> strList = new ArrayList();
            String sql = "SELECT * FROM employees";  
          
        try {  
            Connection conn = DbConnection.DBConn();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                Song song =  new Song();
                song.setSongID(Integer.parseInt(rs.getString("SongID")));
                song.setSongTitle(rs.getString("SongTitle"));
                strList.add(song);
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
    return strList;
    }
    
}
