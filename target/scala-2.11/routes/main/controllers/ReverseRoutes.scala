
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/Praxisprojekt/dSportBackend_NEU/dsport-backend/conf/routes
// @DATE:Wed Sep 13 14:00:18 CEST 2017

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:8
package controllers {

  // @LINE:8
  class ReverseUserController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def add(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "user")
    }
  
  }


}
