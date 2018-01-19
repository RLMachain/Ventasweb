package server;

import DAO.DAO_Movil;
import Util.ConexionDirecta;
import Util.Peticion;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/server/movil/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doWork(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doWork(request,response);
    }

    protected void doWork(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Peticion peticion = new Peticion(request);
        String user = peticion.get("user");

        ConexionDirecta con=new ConexionDirecta();
        DAO_Movil dao_movil;
        JSONObject json=new JSONObject();
        try {
            con.abre();
            dao_movil=new DAO_Movil(con);
            json=dao_movil.iniciarsesion(user);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                con.cierra();
                con=null;
            } catch(Exception e2) {}
        }

        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }
}
