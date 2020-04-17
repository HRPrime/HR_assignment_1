import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/who2"})
public class Who2 extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
        req.getRequestDispatcher("/namex.jsp").forward(req,resp);
    }
}
