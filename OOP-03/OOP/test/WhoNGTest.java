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
import java.net.CookieManager;
import java.net.CookieHandler;

/**
 *
 * @author hridgeway
 */

public class WhoNGTest {
    
    
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
    
    static CookieManager cookieManager = new CookieManager();
    @BeforeClass
    public static void setupSession()
    {
        CookieHandler.setDefault(cookieManager);
    }
    
    
    @AfterClass
    public static void stopJetty() throws Exception {
        String[] args = new String[]{ "jetty.home=C:\\2104\\netbeans workspace\\jetty-distribution-9.4.25.v20191220",
            "STOP.PORT=2021", "STOP.KEY=AutomaticTofu",
            "--stop"
        };
        org.eclipse.jetty.start.Main.main(args);
    }
    
    @BeforeMethod
        public void clearCookies()
        {
            cookieManager.getCookieStore().removeAll();
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
    
    
    @Test
    public void whoTest1() throws Exception
    {
        String txt = fetch("/srv/who");
        System.out.print(txt);
        assertTrue( txt.contains("Don't know who you are"));
    }
    
    @Test
    public void whoTest2() throws Exception
    {
        fetch("/srv/signup?user=bob&pw=password&name=Robert");
        fetch("/srv/login?user=bob&pw=password");
        String txt = fetch("/srv/who");
        System.out.print(txt);
        assertTrue( txt.contains("You are bob"));
    }
    
    @Test
    public void whoTest3() throws Exception
    {
        fetch("/srv/signup?user=bob&pw=password&name=Robert");
        String txt = fetch("/srv/who");
        System.out.print(txt);
        assertTrue( txt.contains("Don't know who you are"));
    }
    
    @Test
    public void whoTest4() throws Exception //fail login
    {
        fetch("/srv/signup?user=bob&pw=password&name=Robert");
        fetch("/srv/login?user=bob");
        String txt = fetch("/srv/who");
        System.out.print(txt);
        assertTrue( txt.contains("Don't know who you are"));
    }
    
    @Test
    public void whoTest5() throws Exception //fail login
    {
        fetch("/srv/signup?user=bob&pw=password&name=Robert");
        fetch("/srv/login?user=bob&pw=password");
        fetch("/srv/logout");
        String txt = fetch("/srv/who");
        System.out.print(txt);
        assertTrue( txt.contains("Don't know who you are"));
    }
    
}
