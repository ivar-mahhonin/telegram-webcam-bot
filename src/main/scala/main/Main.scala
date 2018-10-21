import java.util
import wvlet.log.LogSupport
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import bot.Bot
import com.typesafe.config.ConfigFactory

object Main extends LogSupport {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    try {
      val conf = ConfigFactory.load()

      val token: String = conf.getString("bot-app.token")
      val users: util.List[Integer] = conf.getIntList("bot-app.users")
      val imageSource: String = conf.getString("bot-app.image-source")

      val bot = new Bot(token, users, imageSource)
      bot.run()

      info(s"Bot is running.")

      system.registerOnTermination(() => {
        println("Shutting down bot.")
        bot.shutdown()
      })
    }
    catch {
      case e: Throwable => {
        error("Is your settings file defined?")
        error(e.toString)
        system.terminate()
      }
    }
  }
}