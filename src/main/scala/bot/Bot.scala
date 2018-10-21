package bot

import java.util

import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{ChatActions, Polling, TelegramBot}
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.methods.SendPhoto
import com.bot4s.telegram.models.{InputFile, Message, User}

import scala.concurrent.Future

class Bot(val token: String, val allowedUsersIds: util.List[Integer], imageSource: String) extends TelegramBot
  with Polling
  with Commands
  with ChatActions {

  val client = new ScalajHttpClient(token)

  onCommand("hello") { implicit msg: Message =>
    checkAuth(msg, (user: User) => {
      for {
        r <- Future {
          scalaj.http.Http(s"http://${imageSource}").asBytes
        }
        if r.isSuccess
        bytes = r.body
      } {
        reply("Uploading picture...")
        uploadingPhoto
        val framePng = InputFile("frame.png", bytes)
        request(SendPhoto(msg.source, framePng)).onComplete((completed) => reply("Here you are!"))
      }
    })
  }

  onCommand("start") { implicit msg: Message =>
    msg.from match {
      case Some(x: User) => {
        reply("Greetings!").onComplete((c) => {
          reply(s"Your user id is: ${x.id}").onComplete((c) => {
            reply("Send it to bot administrator in order to authenticate.")
          })
        })
      }
      case None => sendUserNotRecognisedMessage(msg)
    }
  }

  private def sendImageSourceNotDefinedMessage(msg: Message): Future[Message] = {
    reply("Image source is not defined!")(msg)
  }

  private def sendAuthFailedMessage(msg: Message): Future[Message] = {
    reply("Auth failed!")(msg)
  }

  private def sendUserNotRecognisedMessage(msg: Message): Future[Message] = {
    reply("Are you alive, buddy?")(msg)
  }

  private def checkAuth(msg: Message, callback: (User) => Any): Any = {
    if (imageSource == null || imageSource.length == 0) {
      sendImageSourceNotDefinedMessage(msg)
      return
    }
    val user: Option[User] = msg.from
    user match {
      case Some(x: User) => {
        if (allowedUsersIds.contains(x.id)) {
          callback(x)
        }
        else {
          sendAuthFailedMessage(msg)
        }
      }
      case None => sendUserNotRecognisedMessage(msg)
    }
  }
}
