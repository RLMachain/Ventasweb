package Util;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class ConexionPool {
	
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
			
			//InitialContext cxt = new InitialContext();
			InitialContext cxt = new InitialContext();
      		//Context env  = (Context)cxt.lookup("java:/comp/env");
      		//DataSource ds = (DataSource)env.lookup("jdbc/postgres");
      		DataSource ds = (DataSource)cxt.lookup( "java:/comp/env/jdbc/escolardb" );
      		con = ds.getConnection();     		
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
	public void auto() throws Exception {
		if(con!=null){
			con.setAutoCommit(true);
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
	public void rollback() {
		try {
			if(con!=null) con.rollback();
		} catch(Exception e) {
			System.out.println("ERROR: "+e.getMessage());
		}
	}

}