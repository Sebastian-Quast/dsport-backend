
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Florian/dsport/dsport-backend/conf/routes
// @DATE:Wed Sep 20 10:46:30 CEST 2017

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:8
package controllers.javascript {

  // @LINE:14
  class ReverseSessionController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def signup: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SessionController.signup",
      """
        function() {
        
          if (true) {
            return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "userAdd"})
          }
        
        }
      """
    )
  
    // @LINE:18
    def login: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SessionController.login",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "login"})
        }
      """
    )
  
  }

  // @LINE:8
  class ReverseUserController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def load: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.load",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "userLoad"})
        }
      """
    )
  
    // @LINE:12
    def deleteUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.deleteUser",
      """
        function() {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "userDelete"})
        }
      """
    )
  
    // @LINE:10
    def updateUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.updateUser",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "userUpdate"})
        }
      """
    )
  
  }

  // @LINE:20
  class ReverseTestController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:20
    def testVerify: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.TestController.testVerify",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "testVerify"})
        }
      """
    )
  
  }


}
