import java.util
import bot.Bot
import com.typesafe.config.ConfigFactory

object Main {
  def main(args: Array[String]) {
    try {
      val conf = ConfigFactory.load()
      val token: String = conf.getString("bot-app.token")
      val users: util.List[Integer] = conf.getIntList("bot-app.users")
      val imageSource: String = conf.getString("bot-app.image-source")
      val bot = new Bot(token, users, imageSource)
      bot.run()

      println("Press [ENTER] to shutdown the bot, it may take a few seconds...")

      scala.io.StdIn.readLine()
      bot.shutdown()
    }
    catch {
      case _: Throwable => println("Is your 'settings.conf' file there?")
    }
  }
}