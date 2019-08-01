package com.kiss.kissocfg;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class KissoConfigListener
  implements ServletContextListener
{
  protected static final Logger logger = Logger.getLogger("KissoConfigListener");
  
  public void contextInitialized(ServletContextEvent sce)
  {
    new WebKissoConfigurer().initKisso(sce.getServletContext());
  }
  
  public void contextDestroyed(ServletContextEvent sce)
  {
    logger.info("Uninstalling Kisso ");
  }
}
