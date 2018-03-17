Scenario
========
Coolpay is a new company that allows to easily send money to friends through their API.
You work for Fakebook, a successful social network. You’ve been tasked to integrate Coolpay inside Fakebook. A/B tests show that users prefer to receive money than pokes!
You can find Coolpay documentation here: http://docs.coolpayapi.apiary.io/
You will write a small app that uses Coolplay API in a language of your choice. The app should be able do the following:

- Authenticate to Coolpay API
- Add recipients
- Send them money
- Check whether a payment was successful

How to run
==========
```shell
./gradlew build && java -jar build/libs/fakebook-1.0-SNAPSHOT.jar -Dkeystore=/path/to/keystore.jks -Dpassword=Password12 -Dalias=main
```

Browse to `http://localhost:8080`


Guide to make Payment
=====================
Sign up
-------
Click Signup -> Fill data -> Register 
Successful registration will take you to the homepage.

Login (Only follow if not registering)
--------------------------------------
Login: enter username & password. 
Successful sign in will take you to the homepage.

Add recipient
-------------
Add a payment recipient
Home page -> My Payments -> Add Recipient
 
 Add a Friend
 -------------
 Add your Social network friend as a payment recipient
 Home page -> My Payments -> Add Friend

Make a Payment
--------------
Pay a recipient
Home page -> My Payments -> Make a Payment

Check Payment Status
--------------------
Check a payment status
Home page -> My Payments -> Check Payment Payment




 
