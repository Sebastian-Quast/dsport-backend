
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Florian/dsport/dsport-backend/conf/routes
// @DATE:Wed Sep 20 10:46:30 CEST 2017

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseSessionController SessionController = new controllers.ReverseSessionController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseUserController UserController = new controllers.ReverseUserController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseTestController TestController = new controllers.ReverseTestController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseSessionController SessionController = new controllers.javascript.ReverseSessionController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseUserController UserController = new controllers.javascript.ReverseUserController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseTestController TestController = new controllers.javascript.ReverseTestController(RoutesPrefix.byNamePrefix());
  }

}
