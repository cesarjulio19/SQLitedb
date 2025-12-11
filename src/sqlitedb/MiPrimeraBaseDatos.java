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
public class MiPrimeraBaseDatos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        insertar("DBD", 20.00, "Arc Raiders", 30.00);
    }
    
    private static final String URL = "jdbc:sqlite:C:\\Users\\Alumnos\\Desktop\\SQLB\\DB.Browser.for.SQLite-v3.13.1-win32\\juegos.db";
    
    public static void insertar(String nombre1, double precio1, String nombre2, double precio2) {
        
        Connection conn = null;
        
        try {
            
            // 1. Conectarse a la base de datos SQLite
            conn = DriverManager.getConnection(URL);
            
            // 2. Desactivar auto-commit para manejar la transacción manualmente
            conn.setAutoCommit(false);
            
            // SQL para insertar 
            String sqlInsertar1 = "INSERT INTO juegos (id,nombre,precio) VALUES (1,?,?)";
            String sqlInsertar2 = "INSERT INTO juegos (id,nombre,precio) VALUES (2,?,?)";
            
            // 3. Insertar la primera linea
            try (PreparedStatement psInsertar1 = conn.prepareStatement(sqlInsertar1)){
                psInsertar1.setString(1, nombre1);
                psInsertar1.setDouble(2, precio1);

                int filas1 = psInsertar1.executeUpdate();
                
                if (filas1 == 0) {
                    throw new SQLException("Fallo al insertar");
                }
            }
            
            // 4. Insertar la segunda linea
            try (PreparedStatement psInsertar2 = conn.prepareStatement(sqlInsertar2)){
                psInsertar2.setString(1, nombre2);
                psInsertar2.setDouble(2, precio2);

                int filas1 = psInsertar2.executeUpdate();
                
                if (filas1 == 0) {
                    throw new SQLException("Fallo al insertar");
                }
            }
            
            // 5. Si todo salió bien, confirmar la transacción
            conn.commit();
            System.out.println("Inserts realizadados correctamente en SQLite.");
            
        } catch (SQLException e) {
            
             System.out.println("Error durante los Inserts: " + e.getMessage());
             
              // 6. Si hubo un problema, deshacer todos los cambios
            if (conn != null) {
                
                try {
                    
                    conn.rollback();
                    System.out.println("Operación cancelada. La base de datos vuelve al estado anterior.");
                    
                } catch (SQLException ex) {
                    
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                    
                }
            
            }
        }finally{
            
            // 7. Cerrar la conexión y devolver auto-commit a true por seguridad
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
