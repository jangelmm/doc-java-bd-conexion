package ats.java.doc.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Jesús Ángel Martínez Mendoza <https://github.com/jangelmm>
 */
public class Conexion {
    //Variables de conexión                                       /******
    public static final String URL = "jdbc:mysql://localhost:3306/nombre_tabla?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    public static final String usuario = "root";
    public static final String contrasena = "password";
    
    //Métodos de conexión
    public Connection getConnection(){
        Connection conexion = null;
        
        try{
            conexion = DriverManager.getConnection(URL, usuario, contrasena);
        }catch(Exception ex){
            System.out.println("Error, " + ex);
        }
        
        return conexion;
    }
}
