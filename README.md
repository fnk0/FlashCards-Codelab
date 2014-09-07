FlashCards
==========

A simple flash cards app to be used for the GDG Stillwater Android Codelabs.

This tutorial will be assume that you have installed

1. Java SDK >= 1.7
2. AndroiD Studio beta >= 0.8.2
3. Android Built Tools 19.1.0

It will also asume that you have a working android device to debug or an emulator. 
I personally recommend [genymotion](http://www.genymotion.com/) emulator. 


###### Creating a New Project
Open Android Studio and select New Project

![New project screenshot](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.52.49.png)
Type in the name of the project and your default Package name as a reverse URL.

![Selecting minimun SDK](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.52.58.png)
For this project we will use Ice Cream Sandwich (API 15) As our Default SDK.
We only select the Phone and Tablet module as for now those are the only one we use. In a later version of this series of tutorial we will be covering other modules such as the App Engine module for online storage and the Android Wear for integration with Android Wear Watches.  
With the SDK 15 we will cover about 86% of all Android Devices.
``` Tip: To see the coverage of an particular SDK version click on "help me choose" ```

![Blanck activity selection](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.53.10.png)
Select blank activty as we want to start this project clean without too much boylerplate.

![Main Activity name](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.53.21.png)
Name the activity, give it a title and select Finish.

![Project created](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.55.30.png)
If everything went well the project will now be created and you should be seeing something like this. 
