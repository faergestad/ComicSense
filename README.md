# xkcdComics
App for viewing xkcd comics

![1](https://user-images.githubusercontent.com/20108194/48985340-aed39600-f106-11e8-929d-0c8f82f8ad6e.jpg)
![2](https://user-images.githubusercontent.com/20108194/48985341-aed39600-f106-11e8-80ac-8f88af2252bc.jpg)  
The app launches "Newest", and show the latest comic. Browse contains a recyclerview populated by the response from a JSONrequest using volley. Images are begin set with Glide.  
![3](https://user-images.githubusercontent.com/20108194/48985342-aed39600-f106-11e8-81c9-279138c6c106.jpg)
![4](https://user-images.githubusercontent.com/20108194/48985343-aed39600-f106-11e8-834f-fda81ad1793a.jpg)  
Sliding your finger from right to left will reveal options to either explain the comic, or share it. The explanation is the result of webscraping with jsoup. It's currently not very advanced and by no means perfect, since it only fetches the text from paragraphs on "https://www.explainxkcd.com", but it does the trick for now.  
![5](https://user-images.githubusercontent.com/20108194/48985344-aed39600-f106-11e8-805f-b0937cf6693a.jpg)
![6](https://user-images.githubusercontent.com/20108194/48985345-af6c2c80-f106-11e8-99d7-4ba2c26174f9.jpg)  
I chose to do a search through the list of comics instead of using the result from "https://relevantxkcd.appspot.com/", simply because time ran away from me.  
![7](https://user-images.githubusercontent.com/20108194/48985346-af6c2c80-f106-11e8-984c-925b64d0b0a0.jpg)
![8](https://user-images.githubusercontent.com/20108194/48985347-af6c2c80-f106-11e8-85f5-87439470861e.jpg)  
Sliding your finger from left to right will reveal an option to save the comic to the apps sqliteDB, and add it to favorites to make it available offline. I got as far as being able to save each comic but didn't quite have time to saving the image itself.
