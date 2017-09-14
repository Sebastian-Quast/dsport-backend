
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/Praxisprojekt/dSportBackend_NEU/dsport-backend/conf/routes
// @DATE:Wed Sep 13 14:00:18 CEST 2017

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
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:8
    UserController_0: controllers.UserController
  ) = this(errorHandler, UserController_0, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, UserController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """user""", """controllers.UserController.add"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:8
  private[this] lazy val controllers_UserController_add0_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("user")))
  )
  private[this] lazy val controllers_UserController_add0_invoker = createInvoker(
    UserController_0.add,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "add",
      Nil,
      "POST",
      this.prefix + """user""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:8
    case controllers_UserController_add0_route(params) =>
      call { 
        controllers_UserController_add0_invoker.call(UserController_0.add)
      }
  }
}
