package Util;

//import javax.sql.*;
//import javax.naming.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDirecta {

    private Connection con;

    /**
     *Regresa la conexion
     */
    public Connection getCon() {
        return con;
    }

    /**
     *Abre la conexion
     */
    public void abre() throws Exception {
        con = null;
        try {
            Class.forName("org.postgresql.Driver");
            //String URL = "jdbc:postgresql://localhost:5432/escolardb";
            String URL = "jdbc:postgresql://localhost:5432/cventas";
            String USUARIO = "postgres";
            String CLAVE = "postgres";
            con = DriverManager.getConnection(URL,USUARIO,CLAVE);
        } catch ( Exception e) {
            System.out.println("Error al abrir la conexion:" + e);
            throw new Exception(e);
        }
    }

    /**
     *Cierra la conexion
     */
    public void cierra() throws Exception {
        try {
            con.close();
        } catch ( Exception e) {
            System.out.println("Error al cerrar la conexion:" + e);
            throw new Exception(e);
        } finally {
            con=null;
        }
    }

    /**
     *Activa/Desactiva el modo de transacciones
     */
    public void begin() throws Exception {
        if(con!=null) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        }
    }

    /**
     *Asienta una transaccion
     */
    public void commit() throws Exception {
        if(con!=null) con.commit();
    }

    /**
     *Aborta una transaccion
     */
    public void rollback() throws Exception {
        if(con!=null) con.rollback();
    }

}