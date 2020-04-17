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

public class LogoutNGTest {
    
    
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
    
    public LogoutNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of doGet method, of class Logout.
     */
    @Test
    public void testLogout1() throws Exception {
        fetch("/srv/signup?user=bob&pw=password&name=Robert");
        fetch( "/srv/login?user=bob&pw=password");
        String txt = fetch( "/srv/logout");
        System.out.print(txt);
        assertTrue( txt.contains("Logged out"));
    }
    
    @Test
    public void testLogout2() throws Exception { //no account or login
        String txt = fetch( "/srv/logout");
        System.out.print(txt);
        assertTrue( txt.contains("Logged out"));
    }
    
    @Test
    public void testLogout3() throws Exception { //No login
        fetch("/srv/signup?user=bob&pw=password&name=Robert");
        String txt = fetch( "/srv/logout");
        System.out.print(txt);
        assertTrue( txt.contains("Logged out"));
    }
    
    @Test
    public void testLogout4() throws Exception { //Bad login
        fetch( "/srv/login?user=bob&pw=password");
        String txt = fetch( "/srv/logout");
        System.out.print(txt);
        assertTrue( txt.contains("Logged out"));
    }
    
}
