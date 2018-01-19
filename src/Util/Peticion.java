// Java Document
package Util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class Peticion {
	HttpServletRequest request;
	public Peticion(HttpServletRequest req) {
		this.request = req;
	}

	public String getURLDecoder(String p) { return Funciones.URLDecoder(get(p)); }

	public String get(String p) {
		return (request.getParameter(p)!=null?request.getParameter(p):"");
	}

	public String upper(String p) {
		return get(p).toUpperCase();
	}

	public String lower(String p) {
		return get(p).toLowerCase();
	}
	
	public boolean getBoolean(String p) {
		return get(p).equals("true");
	}
	
	public boolean getCheck(String p) {
		return get(p).equals("on");
	}
	
	public Date getDate(String p) {
		return request.getParameter(p) !=null 
				? Funciones.textoFecha( get(p) )
				: null;
	}
	public String unescape(String p) {
		return Funciones.unescape(get(p));
	}
	
	public String getEncode(String p) {
		return java.net.URLEncoder.encode(get(p));
	}
	public String getDecode(String p) {
		return java.net.URLDecoder.decode(get(p));
	}
	
	public int getInt(String p) {
		int valor = 0;
		try {
			valor = Integer.parseInt(get(p));
		} catch(Exception e) {}	
		return valor;
	}
	public float getFloat(String p) {
		float valor = (float)0.0;
		try {
			valor = Float.parseFloat(get(p));
		} catch(Exception e) {}	
		return valor;
	}
	public double getDouble(String p) {
		double valor = (double)0.0;
		try {
			valor = Double.parseDouble(get(p));
		} catch(Exception e) {}	
		return valor;
	}

}