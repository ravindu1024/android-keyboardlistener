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





