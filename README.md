FlashCards
==========

A simple flash cards app to be used for the GDG Stillwater Android Codelabs.

This tutorial will be assume that you have installed

1. Java SDK >= 1.7
2. AndroiD Studio beta >= 0.8.2
3. Android Built Tools 19.1.0
4. It will also asume that you have a working android device to debug or an emulator. I personally recommend [genymotion](http://www.genymotion.com/) emulator.

Recommended Website to get dependencies: [Gradle Please](http://gradleplease.appspot.com/)

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


###### Adding Dependencies
Android studio uses the [Gradle](http://www.gradle.org/) with the [Android plugin](http://tools.android.com/tech-docs/new-build-system/user-guide)to manaje it's dependencies and build system.
Each of the modules has it's own build.gradle file to manaje specific dependencies necessary for each module.

With the newest version of Android Studio sometimes Gradle makes the mistake of setting the app to target SDK 20 which is current the Android Wear SDK so let's change it a little bit.

In the build.gradle file inside the module :app
app/build.gradle
```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 19 // The SDK that we want to compile for
    buildToolsVersion "19.1.0" // The build tools that should be installed in the SDK.

    defaultConfig {
        applicationId "gabilheri.com.flashcards" // This should your application ID do not copy mine.
        minSdkVersion 15 // Here is where we change the  minimum SDK
        targetSdkVersion 19 // The target SDK version. SDK 19 = 4.4 Kit kat
        versionCode 1 // The version Code is used by the Play Store. Every time an update is pushed to the play store the version code should be updated as well.
        versionName "1.0" // Visible version name for the users on the Play Store
    }
    buildTypes {
        release {
            runProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar']) // Will compile all the Libraries inside the 'libs' folder
    compile 'com.android.support:support-v4:19.1.0' // We will be using the support library for the Drawer layout
    compile 'com.github.gabrielemariotti.cards:library:1.6.0' // Very good library to use cards 
}

````
