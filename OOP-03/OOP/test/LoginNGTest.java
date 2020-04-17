/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import java.net.URL;

/**
 *
 * @author hridgeway
 */
public class LoginNGTest {
    
    
    @BeforeClass
    public static void startJetty() throws Exception {
        String[] args = new String[]{
            "jetty.home=C:\\2104\\netbeans workspace\\jetty-distribution-9.4.25.v20191220",
            "STOP.PORT=2021", "STOP.KEY=AutomaticTofu"
        };
        var LG = new StdErrLog();
        LG.setLevel(AbstractLogger.LEVEL_OFF);
        Log.setLog(LG);
        org.eclipse.jetty.start.Main.main(args);
    }
    
    @AfterClass
    public static void stopJetty() throws Exception {
        String[] args = new String[]{ "jetty.home=C:\\2104\\netbeans workspace\\jetty-distribution-9.4.25.v20191220",
            "STOP.PORT=2021", "STOP.KEY=AutomaticTofu",
            "--stop"
        };
        org.eclipse.jetty.start.Main.main(args);
    }
    
    
    String fetch(String... allurls) throws Exception{
    String str=null;
    byte[] returnedData=new byte[]{0};  //dummy
    for(String oneurl: allurls ){
        var url = new URL("http://localhost:2020"+oneurl);
        var conn = url.openConnection();
        conn.connect();
        var istr = conn.getInputStream();
        returnedData = istr.readAllBytes();
    }
    return new String(returnedData,0,returnedData.length);
}
    
    public LoginNGTest() {
    }
    
    @Test
    public void testLogin1() throws Exception{
        String txt = fetch( "/srv/login?user=bob");
        System.out.print(txt);
        assertTrue( txt.contains("No password provided"));
    }
    
    @Test
    public void testLogin2() throws Exception{
        String txt = fetch( "/srv/login?pw=bobspassword");
        System.out.print(txt);
        assertTrue( txt.contains("No username provided"));
    }
    
    @Test
    public void testLogin3() throws Exception{
        String txt = fetch( "/srv/login?user=bob&pw=password");
        System.out.print(txt);
        assertTrue( txt.contains("nonexistant user"));
    }
    
    @Test
    public void testLogin4() throws Exception{
        fetch("/srv/signup?user=bob&pw=password&name=Robert");
        String txt = fetch( "/srv/login?user=bob&pw=password");
        System.out.print(txt);
        assertTrue( txt.contains("Logged in as bob"));
    }
}
