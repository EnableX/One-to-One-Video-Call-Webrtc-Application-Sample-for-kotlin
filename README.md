# 1-to-1 RTC: A Sample Android App with EnableX Android Toolkit

This is a Sample Android App demonstrates the use of EnableX (https://www.enablex.io) platform Server APIs and Android Toolkit to build 1-to-1 RTC (Real Time Communication) Application.  It allows developers to ramp up on app development by hosting on their own devices. 

This App creates a virtual Room on the fly  hosted on the Enablex platform using REST calls and uses the Room credentials (i.e. Room Id) to connect to the virtual Room as a mobile client.  The same Room credentials can be shared with others to join the same virtual Room to carry out a RTC session. 

> EnableX Developer Center: https://developer.enablex.io/


## 1. How to get started

### 1.1 Pre-Requisites

#### 1.1.1 App Id and App Key 

* Register with EnableX [https://www.enablex.io] 
* Create your Application
* Get your App ID and App Key delivered to your Email


#### 1.1.2 Sample Android Client 

* Clone or download this Repository [https://github.com/EnableX/One-to-One-Video-Call-Webrtc-Application-Sample-for-kotlin.git] 


#### 1.1.3 Sample App Server 

* Clone or download this Repository [https://github.com/EnableX/One-to-One-Video-Chat-Sample-Web-Application.git ] & follow the steps further 
* You need to use App ID and App Key to run this Service. 
* Your Android Client End Point needs to connect to this Service to create Virtual Room.
* Follow README file of this Repository to setup the Service.


#### 1.1.4 Configure Android Client 

* Open the App
* Go to WebConstants and change the following:
``` 
 String userName = "USERNAME"  /* HTTP Basic Auth Username of App Server */
 String password = "PASSWORD"  /* HTTP Basic Auth Password of App Server */
 String kBaseURL = "FQDN"      /* FQDN of of App Server */
 ```
 
 Note: The distributable comes with demo username and password for the Service. 

### 1.2 Test

#### 1.2.1 Open the App

* Open the App in your Device. You get a form to enter Credentials i.e. Name & Room Id.
* You need to create a Room by clicking the "Create Room" button.
* Once the Room Id is created, you can use it and share with others to connect to the Virtual Room to carry out a RTC Session.
  
## 2 Server API

EnableX Server API is a Rest API service meant to be called from Partners' Application Server to provision video enabled 
meeting rooms. API Access is given to each Application through the assigned App ID and App Key. So, the App ID and App Key 
are to be used as Username and Password respectively to pass as HTTP Basic Authentication header to access Server API.
 
For this application, the following Server API calls are used: 
* https://api.enablex.io/v1/rooms - To create new room
* https://api.enablex.io/v1/rooms/:roomId - To get information of a given Room
* https://api.enablex.io/v1/rooms/:roomId/tokens - To create Token for a given Room to get into a RTC Session

To know more about Server API, go to:
https://developer.enablex.io/api/server-api/


## 3 Android Toolkit

Android App to use Android Toolkit to communicate with EnableX Servers to initiate and manage Real Time Communications.  

* Documentation: https://developer.enablex.io/api/client-api/android-toolkit/
* Download: https://developer.enablex.io/wp-content/uploads/EnxRtcAndroid-release_0.9.2.aar


## 4 Application Walk-through

### 4.1 Create Token

We create a Token for a Room Id to get connected to EnableX Platform to connect to the Virtual Room to carry out a RTC Session.

To create Token, we make use of Server API. Refer following documentation:
https://developer.enablex.io/api/server-api/api-routes/rooms-route/#create-token


### 4.2 Connect to a Room, Initiate & Publish Stream

We use the Token to get connected to the Virtual Room. Once connected, we intiate local stream and publish into the room. Refer following documentation for this process:
https://developer.enablex.io/api/client-api/android-toolkit/enxrtc/



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
More on Player: https://developer.enablex.io/api/client-api/android-toolkit/enxplayerview/

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
