import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
@WebServlet(urlPatterns={"/signup"})
public class signup extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        int x = Main.users.size();
        Main.users.add(new User());
        Main.users.get(x).password = req.getParameter("pw");
        Main.users.get(x).user = req.getParameter("user");
        Main.users.get(x).name = req.getParameter("name");
        if( Main.users.get(x).name == null ){
            pw.printf("No name provided\n");
        } 
        else if( Main.users.get(x).password == null ){
            pw.printf("No password provided\n");
        }
        else if( Main.users.get(x).user == null ){
            pw.printf("No username provided\n");
        }else {
            var sess = req.getSession();
            //sess.setAttribute("name", Main.users.get(x).name );
            pw.printf("username is " + Main.users.get(x).user);
            pw.printf("\npassword is " + Main.users.get(x).password);
            pw.printf("\nyour name is " + Main.users.get(x).name);
        }
    }

}
