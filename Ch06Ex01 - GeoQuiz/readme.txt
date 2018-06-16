Challenge: Reporting the Build Version

Add a TextView widget to the GeoQuiz layout that reports to the user what API level the device is
running. Figure 6.3 shows what the final result should look like.

You cannot set this TextView ’s text in the layout because you will not know the device’s build
version until runtime. Find the TextView method for setting text in the TextView reference page in
Android’s documentation. You are looking for a method that accepts a single argument – a string (or a
CharSequence ).
Use other XML attributes listed in the TextView reference to adjust the size or typeface of the text.
