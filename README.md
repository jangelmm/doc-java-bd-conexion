## Clase `Conexion`: Descripción y Uso

### ¿Qué es la Clase `Conexion`?

La clase `Conexion` es una utilidad diseñada para simplificar la conexión a una base de datos MySQL en aplicaciones Java. Abstrae la complejidad de establecer una conexión, permitiendo a los desarrolladores integrar operaciones con bases de datos de manera sencilla. Con esta clase, los desarrolladores pueden centrarse en la lógica de negocio sin preocuparse por las tareas repetitivas involucradas en la configuración y gestión de conexiones a la base de datos.

### Propósito

El propósito principal de la clase `Conexion` es proporcionar un método reutilizable para establecer una conexión con una base de datos MySQL. Esta clase maneja el proceso de conexión utilizando la API JDBC (Java Database Connectivity) e incluye los parámetros de conexión como la URL de la base de datos, las credenciales de usuario y otras configuraciones necesarias.

### Código

```java
package ats.java.doc.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase de utilidad para establecer una conexión con una base de datos MySQL.
 * 
 * @author Jesús Ángel Martínez Mendoza <https://github.com/jangelmm>
 */
public class Conexion {
    // Variables de conexión
    public static final String URL = "jdbc:mysql://localhost:3306/nombre_tabla?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    public static final String usuario = "root";
    public static final String contrasena = "password";
    
    /**
     * Establece y retorna una conexión con la base de datos MySQL.
     * 
     * @return un objeto Connection si la conexión es exitosa; null en caso contrario.
     */
    public Connection getConnection() {
        Connection conexion = null;
        
        try {
            conexion = DriverManager.getConnection(URL, usuario, contrasena);
        } catch (Exception ex) {
            System.out.println("Error, " + ex);
        }
        
        return conexion;
    }
}
```

### Cómo Usar la Clase `Conexion`

La clase `Conexion` puede utilizarse para realizar diversas operaciones con bases de datos, como la inserción de datos en tablas y la recuperación de información. A continuación, se presentan ejemplos genéricos que demuestran cómo utilizar esta clase.

#### Ejemplo 1: Insertar Datos en la Base de Datos

Este ejemplo muestra cómo insertar un nuevo registro en la tabla `usuario` de la base de datos.

```java
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SQLUsuario {
    // Método para registrar un nuevo usuario en la base de datos
    public boolean registrar(Usuario usuario) {
        Conexion con = new Conexion();
        PreparedStatement ps = null;
        
        try {
            Connection conexion = con.getConnection();
            
            ps = conexion.prepareStatement("INSERT INTO usuario (nombre_usuario, contrasena, nombre_real, correo, id_tipo_usuario) "
                                            + "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getNombreReal());
            ps.setString(4, usuario.getCorreo());
            ps.setInt(5, usuario.getIdTipoUsuario());
            
            ps.executeUpdate();
            
            return true;
        } catch (Exception ex) {
            System.err.println("Error: " + ex);
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e);
            }
        }
    }
}
```

En este ejemplo:
- Se crea una instancia de la clase `Conexion`.
- Se utiliza el método `getConnection()` para establecer una conexión con la base de datos.
- Se prepara una sentencia SQL `INSERT` y se ejecuta para agregar un nuevo usuario a la tabla `usuario`.

#### Ejemplo 2: Recuperar Datos de la Base de Datos

Este ejemplo muestra cómo verificar si un usuario existe en la tabla `usuario`.

```java
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLUsuario {
    // Método para verificar si un usuario existe en la base de datos
    public boolean verificarUsuario(String nombreUsuario, String contrasena) {
        Conexion con = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conexion = con.getConnection();
            
            ps = conexion.prepareStatement("SELECT id, nombre_real, correo FROM usuario WHERE nombre_usuario=? AND contrasena=?");
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // Usuario encontrado, puedes retornar el objeto Usuario o un booleano
                return true;
            } else {
                // Usuario no encontrado
                return false;
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex);
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e);
            }
        }
    }
}
```

En este ejemplo:
- Se crea una instancia de la clase `Conexion`.
- Se utiliza el método `getConnection()` para establecer una conexión con la base de datos.
- Se prepara una sentencia SQL `SELECT` y se ejecuta para buscar un usuario en la tabla `usuario`.
- El método retorna `true` si el usuario existe, y `false` en caso contrario.

### Resumen

La clase `Conexion` es una forma simple y efectiva de gestionar conexiones a bases de datos en Java. Al usar esta clase, los desarrolladores pueden establecer conexiones fácilmente, realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) y manejar sus interacciones con la base de datos con un mínimo de código repetitivo. Los ejemplos proporcionados demuestran casos de uso básicos para la inserción y recuperación de datos, pero la clase puede adaptarse a una amplia gama de operaciones con bases de datos.
