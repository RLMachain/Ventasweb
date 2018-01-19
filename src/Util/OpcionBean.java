package Util;

public class OpcionBean {
	
	private String clave="";
	private String opcion="";
	
	public OpcionBean(String clave,String opcion) {
		setClave(clave);
		setOpcion(opcion);
	}
	
	public OpcionBean(int clave,String opcion) {
		setClave(clave+"");
		setOpcion(opcion);
	}
	
	public OpcionBean(int clave,int opcion) {
		setClave(clave+"");
		setOpcion(opcion+"");
	}
	
	public void setClave(String clave) { this.clave=clave; }
	public void setOpcion(String opcion) { this.opcion=opcion; }
	public String getClave() { return clave; }
	public String getOpcion() { return opcion; }
}