
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/Praxisprojekt/dSportBackend_NEU/dsport-backend/conf/routes
// @DATE:Mon Sep 18 18:07:09 CEST 2017

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:8
package controllers {

  // @LINE:14
  class ReverseSessionController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def signup(): Call = {
    
      () match {
      
        // @LINE:14
        case ()  =>
          
          Call("POST", _prefix + { _defaultPrefix } + "userAdd")
      
      }
    
    }
  
    // @LINE:18
    def login(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "login")
    }
  
  }

  // @LINE:8
  class ReverseUserController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def load(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "userLoad")
    }
  
    // @LINE:12
    def deleteUser(): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "userDelete")
    }
  
    // @LINE:10
    def updateUser(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "userUpdate")
    }
  
  }

  // @LINE:20
  class ReverseTestController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:22
    def save(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "test")
    }
  
    // @LINE:20
    def testVerify(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "testVerify")
    }
  
  }


}
