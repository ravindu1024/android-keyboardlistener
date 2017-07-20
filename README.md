# Android Keyboard Listener
![alt tag](https://img.shields.io/badge/Android%20Arsenal-android--keyboardlistener-green.svg?style=true)


An Android Keyboard visibility listener based on the information from the following page:

# Installation:
Simply download and copy this file to your project: https://github.com/ravindu1024/android-keyboardlistener/blob/master/keyboard-listener/src/main/java/com/rw/keyboardlistener/KeyboardUtils.java

OR

Add this to your main gradle file:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
And add this to your app gradle file:
```gradle
	dependencies {
	        compile 'com.github.ravindu1024:android-keyboardlistener:1.0.0'
	}
```

Thanks to the contributers on this page:
http://stackoverflow.com/questions/2150078/how-to-check-visibility-of-software-keyboard-in-android

# Usage:

Simply include this in your application and use as follows:

    KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener()
        {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                Log.d("keyboard", "keyboard visible: "+isVisible);
            }
    });

The default height difference is set to 200dp but this may(probably not) need to be changed to adapt it for some screen sizes and densities. It is tested on 720x1280, 1080x1920 and 1440x2560 displays.  



