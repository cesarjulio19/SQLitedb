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

/**
 *
 * @author Alumnos
 */
public class ConsultarJuegos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        consultar();
    }
    
    private static final String URL = "jdbc:sqlite:C:\\Users\\Alumnos\\Desktop\\SQLB\\DB.Browser.for.SQLite-v3.13.1-win32\\juegos.db";
    
    public static void consultar() {
        
        Connection conn = null;
        
        try {
            
            // 1. Conectarse a la base de datos SQLite
            conn = DriverManager.getConnection(URL);
            
            
            // SQL para consultar 
            String sqlInsertar1 = "SELECT * FROM juegos WHERE precio > 30";
            
            // 3. consultar las lineas
            try (PreparedStatement psInsertar1 = conn.prepareStatement(sqlInsertar1)){
                
                ResultSet rs = psInsertar1.executeQuery();
                 
                while (rs.next()) {
                    int id = rs.getInt("id");          
                    String nombre = rs.getString("nombre"); // columna "nombre"
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
