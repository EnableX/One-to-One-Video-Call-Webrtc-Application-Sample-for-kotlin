package com.enablex.demoenablex.web_communication

object WebConstants {

    /* To try the app with Enablex hosted service you need to set the kTry = true */
    val kTry = true

    /*Your webservice host URL, Keet the defined host when kTry = true */
    val kBaseURL = "https://demo.enablex.io/"

    /*The following information required, Only when kTry = true, When you hosted your own webservice remove these fileds*/

    /*Use enablec portal to create your app and get these following credentials*/
    val kAppId = "669e2fc4e1c1f3d9170aac39"
    val kAppkey = "a8ujedeWumyga7uvygu9adudeBaZe6eQyXyW"

    val getRoomId = "createRoom/"
    val getRoomIdCode = 1
    val validateRoomId = "getRoom/"
    val validateRoomIdCode = 2
    val getTokenURL = "createToken/"
    val getTokenURLCode = 3
}
