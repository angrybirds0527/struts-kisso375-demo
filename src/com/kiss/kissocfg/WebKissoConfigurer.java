package com.kiss.kissocfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.exception.KissoException;

public class WebKissoConfigurer
  extends SSOConfig
{
  protected static final Logger logger = Logger.getLogger("WebKissoConfigurer");
  public static final String CONFIG_LOCATION_PARAM = "kissoConfigLocation";
  private String ssoPropPath = "sso.properties";
  
  public WebKissoConfigurer() {}
  
  public WebKissoConfigurer(String ssoPropPath)
  {
    this.ssoPropPath = ssoPropPath;
  }
  
  public void initKisso(ServletContext servletContext)
  {
    String location = servletContext.getInitParameter("kissoConfigLocation");
    if (location != null)
    {
      if (location.indexOf("classpath") >= 0)
      {
        String[] cfg = location.split(":");
        if (cfg.length == 2) {
          initProperties(getInputStream(cfg[1]));
        }
      }
      else
      {
        File file = new File(location);
        if (file.isFile()) {
          try
          {
            initProperties(getInputStream(new FileInputStream(file)));
          }
          catch (FileNotFoundException e)
          {
            throw new KissoException(location, e);
          }
        } else {
          throw new KissoException(location);
        }
      }
    }
    else {
      servletContext.log("Initializing is not available kissoConfigLocation on the classpath");
    }
  }
  
  public void initKisso()
  {
    Properties prop = null;
    
    File file = new File(getSsoPropPath());
    if (file.isFile()) {
      try
      {
        prop = getInputStream(new FileInputStream(file));
      }
      catch (FileNotFoundException e)
      {
        throw new KissoException(getSsoPropPath(), e);
      }
    } else {
      prop = getInputStream(getSsoPropPath());
    }
    if (prop != null) {
      initProperties(prop);
    } else {
      logger.severe("Initializing is not available kissoConfigLocation on the classpath");
    }
  }
  
  
  public synchronized void initProperties(Properties props)
  {
    if (props != null) {
    	SSOConfig ssoconfig = new SSOConfig();
     	ssoconfig.setCookieName(props.getProperty("kisso.config.cookieName"));
     	ssoconfig.setSignkey(props.getProperty("kisso.config.signkey"));
     	int maxage = Integer.parseInt(props.getProperty("kisso.config.cookieMaxage"));
     	ssoconfig.setCookieMaxage(maxage);
    	SSOConfig.init(ssoconfig);
    	
    } else {
      throw new KissoException(" cannot load kisso config. ");
    }
  }
  
  public void shutdownKisso()
  {
    logger.info("Uninstalling Kisso ");
  }
  
  private Properties getInputStream(String cfg)
  {
    return getInputStream(WebKissoConfigurer.class.getClassLoader().getResourceAsStream(cfg));
  }
  
  private Properties getInputStream(InputStream in)
  {
    Properties p = null;
    try
    {
      p = new Properties();
      p.load(in);
    }
    catch (Exception e)
    {
      logger.severe(" kisso read config file error. \n" + e.toString());
    }
    return p;
  }
  
  public String getSsoPropPath()
  {
    return this.ssoPropPath;
  }
  
  public void setSsoPropPath(String ssoPropPath)
  {
    this.ssoPropPath = ssoPropPath;
  }
}

