
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/Praxisprojekt/dSportBackend_NEU/dsport-backend/conf/routes
// @DATE:Wed Sep 13 14:00:18 CEST 2017

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseUserController UserController = new controllers.ReverseUserController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseUserController UserController = new controllers.javascript.ReverseUserController(RoutesPrefix.byNamePrefix());
  }

}
