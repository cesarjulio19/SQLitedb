/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sqlitedb;
import java.sql.*;


/**
 *
 * @author Alumnos
 */
public class SQLitedb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Transferir 100.0 de la cuenta 1 a la cuenta 2
        transferir(1, 2, 100.0);
    }
    
    private static final String URL = "jdbc:sqlite:C:\\Users\\Alumnos\\Desktop\\SQLB\\DB.Browser.for.SQLite-v3.13.1-win32\\DBEJEMPLO.db";
    
    public static void transferir(int idOrigen, int idDestino, double monto) {
        
        Connection conn = null;
        
        try {
            // 1. Conectarse a la base de datos SQLite
            conn = DriverManager.getConnection(URL);
            
            // 2. Desactivar auto-commit para manejar la transacción manualmente
            conn.setAutoCommit(false);
            
            // SQL para restar y sumar saldo
            String sqlRestar = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ?";
            String sqlSumar = "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?";
            
            // 3. Restar saldo de la cuenta origen
            try (PreparedStatement psRestar = conn.prepareStatement(sqlRestar)){
                psRestar.setDouble(1, monto);
                psRestar.setInt(2, idOrigen);

                int filas1 = psRestar.executeUpdate();
                
                if (filas1 == 0) {
                    throw new SQLException("No se encontró la cuenta origen");
                }
            }
            
            // 4. Sumar saldo a la cuenta destino
            try (PreparedStatement psSumar = conn.prepareStatement(sqlSumar)) {
                
                psSumar.setDouble(1, monto);
                psSumar.setInt(2, idDestino);

                int filas2 = psSumar.executeUpdate();

                if (filas2 == 0) {
                    
                    throw new SQLException("No se encontró la cuenta destino");
                    
                }
            
            }
            
            // 5. Si todo salió bien, confirmar la transacción
            conn.commit();
            System.out.println("Transferencia realizada correctamente en SQLite.");
            
        } catch (SQLException e) {
            
            System.out.println("Error durante la transferencia: " + e.getMessage());
            
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
