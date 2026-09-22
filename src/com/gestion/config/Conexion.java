package com.gestion.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase encargada de gestionar la conexion a la Base de Datos mediante JDBC.
 */
public class Conexion {

    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String url = "jdbc:sqlserver://localhost;databaseName=gestion_productos;integratedSecurity=true;trustServerCertificate=true;";
    private static String user = "";
    private static String password = "";

    static {
        cargarConfiguracion();
    }

    private static void cargarConfiguracion() {
        Properties prop = new Properties();
        File archivoProp = new File("db.properties");
        if (archivoProp.exists()) {
            try (InputStream is = new FileInputStream(archivoProp)) {
                prop.load(is);
                if (prop.getProperty("db.driver") != null) {
                    driver = prop.getProperty("db.driver").trim();
                }
                if (prop.getProperty("db.url") != null) {
                    url = prop.getProperty("db.url").trim();
                }
                if (prop.getProperty("db.user") != null) {
                    user = prop.getProperty("db.user").trim();
                }
                if (prop.getProperty("db.password") != null) {
                    password = prop.getProperty("db.password").trim();
                }
            } catch (Exception e) {
                System.err.println("No se pudo cargar db.properties, usando valores por defecto. Error: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene una conexion activa a la base de datos.
     * @return Connection
     * @throws SQLException si falla la conexion
     */
    public static Connection getConexion() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC no encontrado: " + driver + ". Asegurese de incluir el archivo JAR en el classpath.", e);
        }

        if (user != null && !user.isEmpty()) {
            return DriverManager.getConnection(url, user, password);
        } else {
            return DriverManager.getConnection(url);
        }
    }

    /**
     * Prueba rapida de conexion desde la consola.
     */
    public static void main(String[] args) {
        try (Connection conn = getConexion()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexion exitosa a la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }
}
