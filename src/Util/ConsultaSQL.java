// Version 2005-06-04 13:30

package Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsultaSQL {


	protected Connection con;
	private ResultSet rs = null;
	private Statement stmt = null;

	public ConsultaSQL (Connection con) {
      this.con = con;
   }

	public ConsultaSQL (ConexionDirecta conexion) {
		this.con = conexion.getCon();
	}

	/**
	* Avanza al siguiente registro
	*/
   public boolean siguiente() throws Exception {
      return rs.next();
   }

	/**
	* Se posiciona en el ultimo registro
	*/
	public boolean ultimo() throws Exception {
		return rs.last();
	}
	
	/**
	* Se posiciona en el primer registro
	*/
	public boolean primero() throws Exception {
		return rs.first();
	}
	
	/**
	* Se mueve al registro anterior
	*/
	public boolean anterior() throws Exception {
		return rs.previous();
	}
	
	/**
	* Se mueve a un registro determinado
	*/
	public boolean ir(int registro) throws Exception {
		try { 
			return rs.absolute(registro); 
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	* Regresa el numero de registro actual
	*/
	public int actual() throws Exception {
		try {
			return rs.getRow();
		} catch(Exception e) {
			return 0;
		}
	}
	
	/**
	* Se posiciona antes del primer registro
	*/
	public void antesPrimero() throws Exception {
		try {
			rs.beforeFirst();
		} catch(Exception e) {}
	}

	public int cantidad() throws Exception {
		int nr=0;
		try {
			int ra = actual();
			if(ultimo()) nr = actual();
			ir(ra);
		} catch(Exception e) {}
		return nr;
	}
	
	/**
	* El contenido de una columna en forma de cadena
	*/
	
	public java.util.Date getDate(String inCol) throws Exception {
		return (rs.getObject(inCol)!=null?rs.getDate(inCol):null);
	}

	public java.util.Date getDate(int inCol) throws Exception {
 		return (rs.getObject(inCol)!=null?rs.getDate(inCol):null);
	}
	
	public String getQDate(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+Funciones.toUTF8(Funciones.fechatexto(rs.getDate(inCol))+"'" )
					: inCol+":''");
					
		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
   }

	public String getQDDate(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+Funciones.toUTF8(Funciones.fechatexto(rs.getDate(inCol))+"'," )
					: inCol+":'',");

		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
	}
	
   public String getString(String inCol) throws Exception {
      try {
			return (rs.getObject(inCol)!=null
					? Funciones.toUTF8( rs.getString(inCol).trim() )
					: "");
		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
   }
	
	public String getString(int inCol) throws Exception {
      return (rs.getObject(inCol)!=null
      				? "'"+Funciones.toUTF8( rs.getString(inCol)+"'" ) 
      				: "");
   }
	
	public String getQString(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+Funciones.toUTF8( rs.getString(inCol)+"'" )
					: inCol+":''");
					
		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
   }

	public String getQDString(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+Funciones.toUTF8( rs.getString(inCol)+"'," )
					: inCol+":'',");

		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
	}

	public String getQURLEncoder(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+Funciones.URLEncoder(rs.getString(inCol))+"'"
					: inCol+":''");

		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
	}

	public String getQDURLEncoder(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+Funciones.URLEncoder(rs.getString(inCol))+"',"
					: inCol+":'',");

		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
	}

   public String getQInt(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":"+Funciones.toUTF8( rs.getString(inCol)+"" )
					: inCol+":0");
					
		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
   }

	public String getQDInt(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":"+Funciones.toUTF8( rs.getString(inCol)+"," )
					: inCol+":0,");

		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
	}
   
   public boolean getBool(String inCol) throws Exception {
		try {
			return rs.getObject(inCol)!=null
					? rs.getString(inCol).equals("t") 
					: false;
					
		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
   }
   
   public String getQBool(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":"+rs.getBoolean(inCol)+""
					: inCol+":false");
					
		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
   }

	public String getQDBool(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":"+rs.getBoolean(inCol)+","
					: inCol+":false,");

		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
	}
   
   public String getQCheck(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+ (rs.getBoolean(inCol)?"on":"off") +"'" 
					: inCol+":''");
					
		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
   }

	public String getQDCheck(String inCol) throws Exception {
		try {
			return (rs.getObject(inCol)!=null
					? inCol+":'"+ (rs.getBoolean(inCol)?"on":"off") +"',"
					: inCol+":'',");

		} catch(Exception e) {
			System.out.println("Error al recuperar la columna: "+inCol);
			throw new Exception(e);
		}
	}
   
   public int getInt(String inCol) throws Exception {
   		try {
   			return (rs.getObject(inCol)!=null?rs.getInt(inCol):0);
   		} catch(Exception e) {
   			System.out.println("Nombre de columna no existe: "+inCol);
   			throw new Exception(e);
   			//return 0;
   		}
   		
   }
	
	public int getInt(int inCol) throws Exception {
      return (rs.getObject(inCol)!=null?rs.getInt(inCol):0);
   }
	
	public long getLong(String inCol) throws Exception {
      return (rs.getObject(inCol)!=null?rs.getLong(inCol):0);
   }
	
	public long getLong(int inCol) throws Exception {
      return (rs.getObject(inCol)!=null?rs.getLong(inCol):0);
   }
   
	public float getFloat(int inCol) throws Exception {
		float ret = (float)0.0;
		if(rs.getObject(inCol)!=null) ret=Float.parseFloat(rs.getString(inCol));
		return ret;
	}

	public float getFloat(String inCol) throws Exception {
		float ret = (float)0.0;
		if(rs.getObject(inCol)!=null) ret=Float.parseFloat(rs.getString(inCol));
		return ret;
	}
	
	public double getDouble(int inCol) throws Exception {
		double ret = (double)0.0;
		if(rs.getObject(inCol)!=null) ret=Double.parseDouble(rs.getString(inCol));
		return ret;
	}
	
	public double getDouble(String inCol) throws Exception {
		double ret = (double)0.0;
		if(rs.getObject(inCol)!=null) ret=Double.parseDouble(rs.getString(inCol));
		return ret;
	}
	
	public String getQDouble(String inCol) throws Exception {
		double ret = rs.getObject(inCol)!=null 
			? Double.parseDouble(rs.getString(inCol)) 
			: (double)0.0;
		return inCol + ":" + ret;
	}

	public String getQDDouble(String inCol) throws Exception {
		double ret = rs.getObject(inCol)!=null
				? Double.parseDouble(rs.getString(inCol))
				: (double)0.0;
		return inCol + ":" + ret+",";
	}
	
	public byte[] getBytes(int inCol) throws Exception {
		return rs.getBytes(inCol);
	}
	
	public byte[] getBytes(String inCol) throws Exception {
		return rs.getBytes(inCol);
	}
	
   public String getHTML(String inCol) throws Exception {
      return (rs.getObject(inCol)!=null?rs.getString(inCol).trim():"&nbsp;");
   }
   
   public boolean ejecuta(StringBuffer s) throws Exception {
   	return ejecuta(s.toString());
   }

	/** 
	* Ejecuta una consulta
	*/
   public boolean ejecuta(String sql ) throws Exception {
 		try {
 			if (rs!=null) { rs.close(); rs=null; }
			if (stmt!=null) {stmt.close(); stmt=null; }
			if(con==null) System.out.println("La conexion es nula");
     		stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
     		stmt.setFetchSize(25);
     		rs = stmt.executeQuery(sql);
     	} catch(Exception e) {
			try {
				cierra();
			} catch(Exception e2) {}
			System.out.println("FALLA AL CONSULTAR");
			System.out.println("Sentencia: "+sql);
			System.out.println("Error: "+e.getMessage());
			throw new Exception(e);
		}
    	return (rs!=null);
   }

	/**
	* Cierra la consulta en cuestion y libera los recursos
	*/
	public void cierra() throws Exception {
		try {
			if(stmt!=null) {
				stmt.close();
				stmt=null;
			}
			if(rs!=null) {
				rs.close();
				rs=null;
			}
		} catch(Exception e) {
			System.out.println("Error al cerrar la consulta");
			throw new Exception(e.getMessage());
		}
	}

}