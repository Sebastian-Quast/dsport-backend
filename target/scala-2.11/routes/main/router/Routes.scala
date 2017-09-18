
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/Praxisprojekt/dSportBackend_NEU/dsport-backend/conf/routes
// @DATE:Mon Sep 18 14:12:13 CEST 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:8
  UserController_0: controllers.UserController,
  // @LINE:14
  SessionController_1: controllers.SessionController,
  // @LINE:20
  TestController_2: controllers.TestController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:8
    UserController_0: controllers.UserController,
    // @LINE:14
    SessionController_1: controllers.SessionController,
    // @LINE:20
    TestController_2: controllers.TestController
  ) = this(errorHandler, UserController_0, SessionController_1, TestController_2, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, UserController_0, SessionController_1, TestController_2, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """userLoad""", """controllers.UserController.load"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """userUpdate""", """controllers.UserController.updateUser()"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """userDelete""", """controllers.UserController.deleteUser()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """userAdd""", """controllers.SessionController.signup"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """signup""", """controllers.SessionController.signup"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.SessionController.login"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """testVerify""", """controllers.TestController.testVerify"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:8
  private[this] lazy val controllers_UserController_load0_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("userLoad")))
  )
  private[this] lazy val controllers_UserController_load0_invoker = createInvoker(
    UserController_0.load,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "load",
      Nil,
      "POST",
      this.prefix + """userLoad""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_UserController_updateUser1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("userUpdate")))
  )
  private[this] lazy val controllers_UserController_updateUser1_invoker = createInvoker(
    UserController_0.updateUser(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "updateUser",
      Nil,
      "POST",
      this.prefix + """userUpdate""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_UserController_deleteUser2_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("userDelete")))
  )
  private[this] lazy val controllers_UserController_deleteUser2_invoker = createInvoker(
    UserController_0.deleteUser(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "deleteUser",
      Nil,
      "DELETE",
      this.prefix + """userDelete""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_SessionController_signup3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("userAdd")))
  )
  private[this] lazy val controllers_SessionController_signup3_invoker = createInvoker(
    SessionController_1.signup,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SessionController",
      "signup",
      Nil,
      "POST",
      this.prefix + """userAdd""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_SessionController_signup4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("signup")))
  )
  private[this] lazy val controllers_SessionController_signup4_invoker = createInvoker(
    SessionController_1.signup,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SessionController",
      "signup",
      Nil,
      "POST",
      this.prefix + """signup""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_SessionController_login5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_SessionController_login5_invoker = createInvoker(
    SessionController_1.login,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SessionController",
      "login",
      Nil,
      "POST",
      this.prefix + """login""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_TestController_testVerify6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("testVerify")))
  )
  private[this] lazy val controllers_TestController_testVerify6_invoker = createInvoker(
    TestController_2.testVerify,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TestController",
      "testVerify",
      Nil,
      "POST",
      this.prefix + """testVerify""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:8
    case controllers_UserController_load0_route(params) =>
      call { 
        controllers_UserController_load0_invoker.call(UserController_0.load)
      }
  
    // @LINE:10
    case controllers_UserController_updateUser1_route(params) =>
      call { 
        controllers_UserController_updateUser1_invoker.call(UserController_0.updateUser())
      }
  
    // @LINE:12
    case controllers_UserController_deleteUser2_route(params) =>
      call { 
        controllers_UserController_deleteUser2_invoker.call(UserController_0.deleteUser())
      }
  
    // @LINE:14
    case controllers_SessionController_signup3_route(params) =>
      call { 
        controllers_SessionController_signup3_invoker.call(SessionController_1.signup)
      }
  
    // @LINE:16
    case controllers_SessionController_signup4_route(params) =>
      call { 
        controllers_SessionController_signup4_invoker.call(SessionController_1.signup)
      }
  
    // @LINE:18
    case controllers_SessionController_login5_route(params) =>
      call { 
        controllers_SessionController_login5_invoker.call(SessionController_1.login)
      }
  
    // @LINE:20
    case controllers_TestController_testVerify6_route(params) =>
      call { 
        controllers_TestController_testVerify6_invoker.call(TestController_2.testVerify)
      }
  }
}
