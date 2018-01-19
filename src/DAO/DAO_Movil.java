package DAO;

import Util.ConexionDirecta;
import Util.ConsultaSQL;
import org.json.JSONObject;

public class DAO_Movil {
    ConexionDirecta con;

    public DAO_Movil(ConexionDirecta cnx){
        con=cnx;
    }

    public JSONObject iniciarsesion(String user) throws Exception{
        JSONObject json=new JSONObject();
        ConsultaSQL data=new ConsultaSQL(con);
        String s="select* from usuarios where loginuser='mashain'";

        if (data.ejecuta(s) && data.primero()){
                json.put("loginuser",data.getString("loginuser"));
        }
        data.cierra();
        data=null;
        return json;
    }
}
