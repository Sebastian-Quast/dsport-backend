
// @GENERATOR:play-routes-compiler
// @SOURCE:D:/Praxisprojekt/dSportBackend_NEU/dsport-backend/conf/routes
// @DATE:Wed Sep 13 14:00:18 CEST 2017


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
