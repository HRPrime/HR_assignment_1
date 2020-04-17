import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/login"})
public class Login extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var passwordAtt = req.getParameter("pw");
        var nameAtt = req.getParameter("user");
        if( nameAtt == null ){
            pw.printf("No username provided");
        } 
        else if( passwordAtt == null ){
            pw.printf("No password provided");
        }
    
        else 
        {
            int nameCorrect = 0;
            for(int x=0;x<Main.users.size();x++)
            {
                if (nameAtt.equals(Main.users.get(x).user))
                {   nameCorrect = 1;
                    if (passwordAtt.equals(Main.users.get(x).password))
                    {   
                        
                        var sess = req.getSession();
                        sess.setAttribute("name", nameAtt );
                        pw.printf("Logged in as "+nameAtt);
                        pw.printf("\nhello "+Main.users.get(x).name);
                        pw.printf("\nhttp://localhost:2020/srv/logout");
                    }
                    else
                    {
                        pw.printf("\nincorrect password");
                    }
                }
                
            }
            if (nameCorrect == 0)
            {pw.printf("\nnonexistant user");}
           
        }
    }

}
