package Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DAO extends Funciones {

	String oper="";
   	String table, sets="", where="",columns = "", values  = "";   
   	Connection con;
	private Statement stmt;
	
	public DAO(Connection con, String table) {
 		this.table = table;
		this.con = con;
	}

	public DAO(ConexionDirecta conexion, String table) {
		this.table = table;
		this.con = conexion.getCon();
	}

	public void setOperacion(String oper) {
		this.oper=oper;
	}
	
	public String getOperacion() {
		return this.oper;
	}
	
	public void col(String campo,String dato) {      
		String val = quote(dato.trim());
		
		if(this.oper.equals("upd")) {
			sets += (sets.equals("")?"":",");
			sets += campo+"="+ (!dato.equals("(nulo)")?val:"null");
		} else if(this.oper.equals("ins")) {
			columns += (columns.equals("")?campo:","+campo);
			values += (values.equals("")?val:","+val);
		}
   }

   public void col(String campo,int dato) {      
		String val = ""+dato;
		
		if(this.oper.equals("upd")) {
			sets += (sets.equals("")?"":",");
			sets += campo+"="+ (dato!=-999999?val:"null");
		} else if(this.oper.equals("ins")) {
			columns += (columns.equals("")?campo:","+campo);
			values += (values.equals("")?val:","+val);
		}
   }
   
	public void col(String campo,long dato) {      
		String val = ""+dato;
		
		if(this.oper.equals("upd")) {
			sets += (sets.equals("")?"":",");
			sets += campo+"="+val;
		} else if(this.oper.equals("ins")) {
			columns += (columns.equals("")?campo:","+campo);
			values += (values.equals("")?val:","+val);
		}
	}

	public void col(String campo,float dato) {      
		String val = ""+dato;
		
		if(this.oper.equals("upd")) {
			sets += (sets.equals("")?"":",");
			sets += campo+"="+val;
		} else if(this.oper.equals("ins")) {
			columns += (columns.equals("")?campo:","+campo);
			values += (values.equals("")?val:","+val);
		}
   }
	
   	public void col(String campo,double dato) {      
		String val = ""+dato;
		
		if(this.oper.equals("upd")) {
			sets += (sets.equals("")?"":",");
			sets += campo+"="+val;
		} else if(this.oper.equals("ins")) {
			columns += (columns.equals("")?campo:","+campo);
			values += (values.equals("")?val:","+val);
		}
   }
   
   public void col(String campo,boolean dato) {      
		String val = (dato?"true":"false");

		if(this.oper.equals("upd")) {
			sets += (sets.equals("")?"":",");
			sets += campo+"="+val;
		} else if(this.oper.equals("ins")) {
			columns += (columns.equals("")?campo:","+campo);
			values += (values.equals("")?val:","+val);
		}
   }
   
   public void key(String campo,String dato) {      
		if(!oper.equals("ins") && !dato.equals("")) {
			String val = quote(dato.trim());
			where += (where.equals("")?"":" AND ");
			where += campo+"="+val;
		}
   }

	public void key(String campo,int dato) {  
		if(!oper.equals("ins")) {
			String val = ""+dato;
			where += (where.equals("")?"":" AND ");
			where += campo+"="+val;
		}
   }

	public void key(String campo,float dato) {      
		if(!oper.equals("ins")) {
			String val = ""+dato;
			where += (where.equals("")?"":" AND ");
			where += campo+"="+val;
		}
   }
	
   	public void key(String campo,double dato) {      
		if(!oper.equals("ins")) {
			String val = ""+dato;
			where += (where.equals("")?"":" AND ");
			where += campo+"="+val;
		}
   }
   
   public void key(String campo,boolean dato) {      
		if(!oper.equals("ins")) {
			String val = (dato?"true":"false");
			where += (where.equals("")?"":" AND ");
			where += campo+"="+val;
		}
   }
   
   public void key(String campo,String operador,String dato) {      
		if(!oper.equals("ins")) {
			String val = quote(dato.trim());
			where += (where.equals("")?"":" AND ");
			where += campo+operador+val;
		}
   }

	public void key(String campo,String operador,int dato) {      
		if(!oper.equals("ins")) {
			String val = ""+dato;
			where += (where.equals("")?"":" AND ");
			where += campo+operador+val;
		}
   }

	public void key(String campo,String operador,float dato) {      
		if(!oper.equals("ins")) {
			String val = ""+dato;
			where += (where.equals("")?"":" AND ");
			where += campo+operador+val;
		}
   }
	
   	public void key(String campo,String operador,double dato) {      
		if(!oper.equals("ins")) {
			String val = ""+dato;
			where += (where.equals("")?"":" AND ");
			where += campo+operador+val;
		}	
   }
   
	/**
	* Regresa la sentencia SQL
	*/
	public String SQL() {
		String SQL="";
		if(this.oper.equals("upd")) {
			SQL = "UPDATE " + table + " SET " + sets +  " WHERE " + where;
		} else if(this.oper.equals("del")) {
			SQL = "DELETE FROM " + this.table + " \nWHERE " + this.where;
		} else if(this.oper.equals("ins")) {
			SQL = "INSERT INTO " + table + " (" + columns +  ") \nVALUES (" + values + ")";
		}
		return SQL;
	}
	
	/** 
	* Ejecuta la sentencia
	*/
	public int ejecuta() throws Exception {
		int autoIncKeyFromApi=0;
      try {
			stmt = con.createStatement();
		  System.out.println(SQL());
			stmt.executeUpdate(SQL(),Statement.RETURN_GENERATED_KEYS);
			ResultSet genkey = stmt.getGeneratedKeys();
			
			if (genkey.next()) {
				try {
    				autoIncKeyFromApi = genkey.getInt(1);
    			} catch(Exception e2) {}
			} else {
		   		autoIncKeyFromApi=0;
			}
			
			cierra();
			return autoIncKeyFromApi;
		} catch(Exception e) {
			System.out.println("FALLA AL EJECUTAR");
			System.out.println("Sentencia: "+SQL());
			System.out.println("Error: "+e.getMessage());
			throw new Exception(e);
		}
   }
	
	/**
	* Regresa el maximo valor de una columna numerica
	*/
	public int maximo(String col) throws Exception {
		int retValue = 0;
		String sql = "Select max("+col+") as maximo From "+table;
		ConsultaSQL c = new ConsultaSQL(con);
		if( c.ejecuta(sql) && c.siguiente() ) retValue = c.getInt(1);
		c.cierra();
		c=null;
		return retValue;
	}
	
	/** 
	* Regresa el siguiente valor de una columna numerica
	*/
	public int siguiente(String col) throws Exception {
		return maximo(col)+1;
	}
	
	/**
	* Libera los recursos
	*/
	public void cierra() throws Exception {
		if(stmt!=null) {
			stmt.close(); 
			stmt=null;
		}
	}
	
}

