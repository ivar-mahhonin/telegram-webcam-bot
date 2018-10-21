# telegram-webcam-bot
Small telegram webcam bot written in Scala to send me latest pictures of my chinchilla :)

#

## Run application:
1. Clone repository with: `git clone https://github.com/ivarmah/telegram-webcam-bot.git`
2. Go to the directory.
3. Define your bot settings at `src/main/resources/settings.conf.`
4. Create docker image: `docker build -t telegram_bot:v1 .`
5. Run docker container: `docker run telegram_bot:v1`

Provide your own settings.conf file at `src/main/resources/settings.conf`.

Structure should be following:

```
bot-app {
    token="TELEGRAM API TOKEN"                  # https://core.telegram.org/bots/api#authorizing-your-bot
    users=[11111111111, 22222222222]            # list of telegram users ids, that will have access to data
    image-source="192.168.1.109:8080/photo.jpg" # url to fetch latest web camera frame          
}

```

##Web camera:
You can use your old android phone as a web camera. 
For that you can use an app called [IP Webcam](https://play.google.com/store/apps/details?id=com.pas.webcam&hl=en_US).

1. Install app and open it.
2. Start the server inside the app (option at the bottom of the list).
3. The app will give you `IPv4` link like that: `http://192.168.1.101:8080`. 
Use it inside `src/main/resources/settings.conf` as a `image-source` parameter. 
Don't forget to add `/photo.jpg` at the end of the link.

