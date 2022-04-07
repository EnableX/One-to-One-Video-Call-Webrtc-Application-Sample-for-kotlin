# 1-to-1 RTC: A Sample Android App with EnableX Android Toolkit

This is a Sample Android App that demonstrates the use of EnableX platform Server APIs (https://www.enablex.io/developer/video-api/server-api) and Android Toolkit (https://www.enablex.io/developer/video-api/client-api/android-toolkit/) to build 1-to-1 RTC (Real Time Communication) Application.  It allows developers to ramp up on app development by hosting on their own devices. 

This App creates a virtual Room on the fly  hosted on the Enablex platform using REST calls and uses the Room credentials (i.e. Room Id) to connect to the virtual Room as a mobile client.  The same Room credentials can be shared with others to join the same virtual Room to carry out an RTC session. 

> EnableX Developer Center: https://developer.enablex.io/


## 1. How to get started

### 1.1 Prerequisites

#### 1.1.1 App Id and App Key 

* Register with EnableX [https://portal.enablex.io/cpaas/trial-sign-up/] 
* Create your Application
* Get your App ID and App Key delivered to your email

#### 1.1.2 Sample Android Client 

* Clone or download this Repository [https://github.com/EnableX/One-to-One-Video-Call-Webrtc-Application-Sample-for-kotlin.git] 


#### 1.1.3 Test Application Server

You need to set up an Application Server to provision Web Service API for your Android Application to enable Video Session. 

To help you to try our Android Application quickly, without having to set up Applciation Server, this Application is shipped pre-configured to work in a "try" mode with EnableX hosted Application Server i.e. https://demo.enablex.io. 

Our Application Server restricts a single Session duations to 10 minutes, and allows 1 moderator and not more than 3 participants in a Session.

Once you tried EnableX Android Sample Application, you may need to set up your own  Application Server and verify your Application to work with your Application Server.  Refer to point 2 for more details on this.



#### 1.1.4 Configure Android Client 

* Open the App
* Go to WebConstants and change the following:
``` 
 /* To try the App with Enablex Hosted Service you need to set the kTry = true When you setup your own Application Service, set kTry = false */
     
     val   kTry = true;
     
 /* Your Web Service Host URL. Keet the defined host when kTry = true */
 
     val kBaseURL = "https://demo.enablex.io/"
     
 /* Your Application Credential required to try with EnableX Hosted Service
     When you setup your own Application Service, remove these */
     
     val kAppId = "App_ID"
     val kAppkey = "App_Key"
     
 ```
 
 Note: The distributable comes with demo username and password for the Service. 

### 1.2 Test

#### 1.2.1 Open the App

* Open the App in your Device. You get a form to enter Credentials i.e. Name & Room Id.
* You need to create a Room by clicking the "Create Room" button.
* Once the Room Id is created, you can use it and share with others to connect to the Virtual Room to carry out an RTC Session either as a Moderator or a Participant (Choose applicable Role in the Form).

Note: Only one user with Moderator Role allowed to connect to a Virtual Room while trying with EnableX Hosted Service. Your Own Application Server may allow upto 5 Moderators.
  
 Note:- In case of emulator/simulator your local stream will not create. It will create only on real device.
  
## 2 Set up Your Own Application Server

You may need to set up your own Application Server after you tried the Sample Application with EnableX hosted Server. We have differnt variants of Appliciation Server Sample Code. Pick one in your preferred language and follow instructions given in respective README.md file.

* NodeJS: [https://github.com/EnableX/Video-Conferencing-Open-Source-Web-Application-Sample.git]
* PHP: [https://github.com/EnableX/Group-Video-Call-Conferencing-Sample-Application-in-PHP]

Note the following:
* You need to use App ID and App Key to run this Service.
* Your Android Client End Point needs to connect to this Service to create Virtual Room and Create Token to join the session.
* Application Server is created using EnableX Server API, a Rest API Service helps in provisioning, session access and post-session reporting.  

To know more about Server API, go to:
https://www.enablex.io/developer/video-api/server-api


## 3 Android Toolkit

This Sample Applcation uses EnableX Android Toolkit to communicate with EnableX Servers to initiate and manage Real Time Communications. Please update your Application with latest version of EnableX Android Toolkit as and when a new release is available.

* Documentation: https://www.enablex.io/developer/video-api/client-api/android-toolkit/
* Download Toolkit: https://www.enablex.io/developer/video-api/client-api/android-toolkit/


## 4 Application Walk-through

### 4.1 Create Token

We create a Token for a Room Id to get connected to EnableX Platform to connect to the Virtual Room to carry out a RTC Session.

To create Token, we make use of Server API. Refer following documentation:
https://www.enablex.io/developer/video-api/server-api/rooms-route/#create-token


### 4.2 Connect to a Room, Initiate & Publish Stream

We use the Token to get connected to the Virtual Room. Once connected, we intiate local stream and publish into the room. Refer following documentation for this process:
https://www.enablex.io/developer/video-api/client-api/android-toolkit/room-connection/#connect-room



### 4.3 Play Stream

We play the Stream into EnxPlayerView Object.
``` 
private var enxPlayerView: EnxPlayerView? = null
enxPlayerView = EnxPlayerView(this, EnxPlayerView.ScalingType.SCALE_ASPECT_BALANCED, true)
    
// Attach & render Stream to Player 
localStream!!.attachRenderer(enxPlayerView)

// Add Player to View
yourView?.addView(enxPlayerView)
  ```
More on Player: https://www.enablex.io/developer/video-api/client-api/android-toolkit/play-stream/

### 4.4 Handle Server Events

EnableX Platform will emit back many events related to the ongoing RTC Session as and when they occur implicitly or explicitly as a result of user interaction. We use Call Back Methods to handle all such events.

``` 
/* Example of Call Back Methods */

/* Call Back Method: onRoomConnected 
Handles successful connection to the Virtual Room */ 

override fun onRoomConnected(enxRoom: EnxRoom?, jsonObject: JSONObject) {
    /* You may initiate and publish stream */
}


/* Call Back Method: onRoomError
 Error handler when room connection fails */
 
override fun onRoomError(jsonObject: JSONObject) {
} 

 
/* Call Back Method: onStreamAdded
 To handle any new stream added to the Virtual Room */
 
override fun onStreamAdded(enxStream: EnxStream?) {
    /* Subscribe Remote Stream */
} 


/* Call Back Method: onActiveTalkerList
 To handle any time Active Talker list is updated */
  
 override fun onActiveTalkerList(jsonObject: JSONObject) {
    /* Handle Stream Players */
}
```

## 5 Trial

EnableX provides hosted Demo Application Server of different use-case for you to try out.

Try a quick Video Call: https://demo.enablex.io/
Sign up for a free trial https://portal.enablex.io/cpaas/trial-sign-up/

