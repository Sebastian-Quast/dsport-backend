
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Florian/dsport/dsport-backend/conf/routes
// @DATE:Wed Sep 20 10:46:30 CEST 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
