import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/logout"})
public class Logout extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.getSession().removeAttribute("name");
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        pw.printf("Logged out");
    }

}