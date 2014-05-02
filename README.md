SuperListview
==============

***Pull requests are answered with all my love and a cookie.***


##Description

This library is making listview way more easy to use. No need to embed the listview in a framelayout to add the progressbar or the emptyview. **It's all right here**.

Features built in:
- ProgressBar while adapter hasn't been set
- EmptyView if adapter is empty
- SwipeRefreshLayout (Google's one)
- Open to suggestions (and Pull Request ofc) for others :)
- Infinite scrolling, when you reach the X last item, load more of them.

##Integration

Just add it to you dependencies

```
    compile 'com.quentindommerc.superlistview:library:0.1'

```
##Usage

-	Use directly SuperListview:

```xml
   <com.quentindommerc.superlistview.SuperListview
            xmlns:superlistview="http://schemas.android.com/apk/res-auto"
            android:id="@+id/list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            superlistview:clipToPadding="false"
            superlistview:divider="@android:color/transparent"
            superlistview:dividerHeight="10dp"
            superlistview:listViewPadding="10dp"
            superlistview:empty="@layout/emptyview"
            superlistview:scrollbarStyle="outsideOverlay" >
    </com.quentindommerc.superlistview.SuperListview>
```
####[Sample java][sample java]

##License

    Copyright (c) 2013 Etsy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[![Analytics](https://ga-beacon.appspot.com/UA-40136896-2/SuperListview/readme)](https://github.com/igrigorik/ga-beacon)
[sample java]:https://github.com/dommerq/SuperListview/blob/master/Sample/src/main/java/com/quentindommerc/superlistview/sample/MainActivity.java
