/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sqlitedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Alumnos
 */
public class BuscarJuegoSeguro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String nombre;
        
        System.err.println("Introduce un nombre de juego: ");
        
        nombre = s.next();
        
        buscarSeguro(nombre);
        
        s.close();
        
    }
    
    private static final String URL = "jdbc:sqlite:C:\\Users\\Alumnos\\Desktop\\SQLB\\DB.Browser.for.SQLite-v3.13.1-win32\\juegos.db";
    
    public static void buscarSeguro(String nombre) {
        
        Connection conn = null;
        
        try {
            
            // 1. Conectarse a la base de datos SQLite
            conn = DriverManager.getConnection(URL);
            
            
            // SQL para consultar 
            String sqlInsertar1 = "SELECT * FROM juegos WHERE nombre = ?";
            
            // 3. consultar la linea
            try (PreparedStatement psInsertar1 = conn.prepareStatement(sqlInsertar1)){
                psInsertar1.setString(1, nombre);
                ResultSet rs = psInsertar1.executeQuery();
                 
                while (rs.next()) {
                    int id = rs.getInt("id");          
                    double precio = rs.getDouble("precio"); // columna "precio"

                    System.out.println("ID: " + id +
                        ", Nombre: " + nombre +
                        ", Precio: " + precio);
                }
            
            }
            
        } catch (SQLException e ) {
            
            System.out.println("Error durante la consulta: " + e.getMessage());
        }finally{
            
            // 7. Cerrar la conexión y devolver auto-commit a true por seguridad
            if (conn != null) {
                
                try {
                    
                    conn.close();
                    
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                    
                }
            
            }
        
        }
    
    }
    
}
