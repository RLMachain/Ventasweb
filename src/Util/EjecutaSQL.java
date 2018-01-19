// Version 2005-06-04 13:30

package Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class EjecutaSQL {

   protected Connection con;	
   
   public EjecutaSQL (Connection con) {
	this.con = con;
   }

   public int ejecuta(String sql) throws Exception {
      Statement stmt = con.createStatement();
      int retValue = 0;
      try {
      	retValue = stmt.executeUpdate(sql);
      } catch(Exception e) {
      	System.out.println(e.getMessage());
      	System.out.println(sql);
      	throw new Exception(e);
      }
      return retValue;
      
   }
   
   public int ejecuta(StringBuffer sql) throws Exception {
      Statement stmt = con.createStatement();
      int retValue = 0;
      try {
      	retValue = stmt.executeUpdate(sql.toString());
      } catch(Exception e) {
      	System.out.println(e.getMessage());
      	System.out.println(sql);
      	throw new Exception(e);
      }
      return retValue;
      
   }

   public String estructura(String t) throws Exception {
		String sql = "select * from " + t + " where false";
		StringBuffer tabla = new StringBuffer("");
        Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
        int ncol = rsmd.getColumnCount();
        
        tabla.append("<table border=1>");
        tabla.append("<tr><td colspan=4>");
        tabla.append("<h2 align=center>"+t+"</h2>");
        tabla.append("<tr>");
        tabla.append("<th>Columna</th>");
        tabla.append("<th>Tipo</th>");
        tabla.append("<th>Longitud</th>");
        tabla.append("<th>Nulos?</th>");
        
        for (int i=1;i<ncol+1;i++) { 
           tabla.append("<tr>");
           tabla.append("<td>" + rsmd.getColumnName(i));
           tabla.append("<td>" + rsmd.getColumnTypeName(i));
           tabla.append("<td>" + rsmd.getColumnDisplaySize(i));
           tabla.append("<td>" + rsmd.isNullable(i));
        }
        tabla.append("</table>");
        rs.close();
		stmt.close();     
        return tabla.toString();
   }
}







