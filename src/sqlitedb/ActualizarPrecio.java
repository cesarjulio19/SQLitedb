/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sqlitedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Alumnos
 */
public class ActualizarPrecio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        buscarSeguro("Minecraft", 19.99);
    }
    
    private static final String URL = "jdbc:sqlite:C:\\Users\\Alumnos\\Desktop\\SQLB\\DB.Browser.for.SQLite-v3.13.1-win32\\juegos.db";
    
    public static void buscarSeguro(String nombre, double precio) {
        
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(false);
            
            String sqlsetPrecio = "UPDATE juegos SET precio = ? WHERE nombre = ?";
            
            try (PreparedStatement psSetPrecio = conn.prepareStatement(sqlsetPrecio)){
                
                psSetPrecio.setDouble(1,precio);
                psSetPrecio.setString(2, nombre);
                
                int filas1 = psSetPrecio.executeUpdate();
                
                if (filas1 == 0) {
                    throw new SQLException("No se encontro el nombre del juego");
                }
            
            }
            
            conn.commit();
            System.out.println("cambio realizado correctamente en SQLite.");
        } catch (SQLException e) {
            
            System.out.println("Error durante la accion: " + e.getMessage());
            
            
            if (conn != null) {
                
                try {
                    
                    conn.rollback();
                    System.out.println("Operación cancelada. La base de datos vuelve al estado anterior.");
                    
                } catch (SQLException ex) {
                    
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                    
                }
            
            }
            
            
        }finally{
            
            if (conn != null) {
                
                try {
                    
                    conn.setAutoCommit(true);
                    conn.close();
                    
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                    
                }
            
            }
        
        }
    
    
    }
    
}
