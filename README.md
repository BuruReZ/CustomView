
CustomView
==========

Library contained some usefull (at least for me :P) for manage time in this manner: hh:mm:ss
=======
# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
In this library are contained some usefull (at least for me :P) custom compouds for android.

* Version 0.01
In this first version are contained two very basic custom compounds:
- CustomTimeEditView: it's possible to use this when you would set one time in this manner hh:mm:ss
- CustomTimeTextView: it's possible to use this when you would view one time in this manner hh:mm:ss

### How do I get set up? ###

Download the source, import it into eclipse and mark this as library if it isn't already. after that open properties of the project that you would use this library and go under the android voice and add this library.

To use this in xml you must declare:

<com.bururez.customview.CustomTimeEditView />
or
<com.bururez.customview.CustomTimeTextView />

Attributes available now for xml for CustomTimeEditView are:
edt_width, edt_color.

Attributes available now for xml for CustomTimeTextView are:
txt_width, txt_color.

In order to use this selector in xml you must set in the main layout this selector:
xmlns:custom="http://schemas.android.com/apk/res-auto"

Some information about selectors:
width: take width and is divided by 3 to resize the three EditText or TextView in three equal part
color: color text for three EditText or TextView

One time it is set in xml you can retrieve in usual manner by

CustomTimeTextView customTimeTextView = (CustomTimeTextView) findViewById(R.id.custom_time_edit_view);

The usefull methods are:

getTimeInSeconds();
getTimeInMinutes();
setTimeInSeconds(long time);
setTimeInMinutes(double time);



### Contribution guidelines ###

made by bururez.
