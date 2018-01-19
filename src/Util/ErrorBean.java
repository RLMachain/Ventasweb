package Util;

public class ErrorBean {
	
	private boolean iserror=false;
	private int codigo=0;
	private String descripcion="Vacio";
	private String campo="";
	
	public ErrorBean() {
	}
	
	public ErrorBean(boolean iserror,int codigo,String descripcion) {
		setIserror(iserror);
		setCodigo(codigo);
		setDescripcion(descripcion);
	}
	
	public void setIserror(boolean iserror) {
		this.iserror=iserror;
	}
	
	public void setCodigo(int codigo) {
		this.codigo=codigo;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion=descripcion;
	}
	
	public void setCampo(String campo){
		this.campo = campo;
	}
	
	public boolean getIserror() { return iserror;
	}
	
	public int getCodigo() { return codigo;
	}
	
	public String getDescripcion() { return descripcion;
	}
	
	public String getCampo(){
		return campo;
	}

}