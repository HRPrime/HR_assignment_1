import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/who"})
public class Who extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/html");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var name = sess.getAttribute("name");
        pw.printf("<!DOCTYPE HTML>\n");
        pw.printf("<HTML><head><meta charset=\"utf-8\"></head>");
        pw.printf("<body>");
        if( name == null ){
            pw.printf("Don't know who you are<br>");
            pw.printf("http://localhost:2020/srv/login <br>");
            pw.printf("http://localhost:2020/srv/signup");
        } else {
            pw.printf("You are "+name+"<br>");
            pw.printf("http://localhost:2020/srv/logout");
        }
        pw.printf("</body></html>");
    }

}
