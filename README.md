# android-keyboardlistener

An Android Keyboard visibility listener based on the information from the following page:

http://stackoverflow.com/questions/2150078/how-to-check-visibility-of-software-keyboard-in-android

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



