SuperListview
==============

***Pull requests are answered with all my love and a cookie.***


##Description

This library is making listview way more easy to use. No need to embed the listview in a framelayout to add the progressbar or the emptyview. **It's all right here**.

Features built in:
- ProgressBar while adapter hasn't been set
- EmptyView if adapter is empty
- SwipeRefreshLayout (Google's one)
- Infinite scrolling, when you reach the X last item, load more of them.
- Swipe To Dismiss for the SuperListView (doesn't make sense for a gridview) (Thanks [Roman Nurik][roman-swipe-to-dismiss])
- GridView with SuperGridView
- Open to suggestions (and Pull Request ofc) for others :)

####[Sample apk][apk] (pull to add item to the adapter)


##Integration

Just add it to you dependencies

For release version:
```groovy
compile 'com.quentindommerc.superlistview:library:1.5.2'
```

For snapshot version:
```groovy
'https://oss.sonatype.org/content/repositories/snapshots/'
```
```groovy
compile 'com.quentindommerc.superlistview:library:1.5.1-SNAPSHOT'
```
##Usage

-	Use directly SuperListview:

```xml
<com.quentindommerc.superlistview.SuperListview
        xmlns:superlistview="http://schemas.android.com/apk/res-auto"
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        superlistview:superlv__listClipToPadding="false"
        superlistview:superlv__listDivider="@android:color/transparent"
        superlistview:superlv__listDividerHeight="10dp"
        superlistview:superlv__listPadding="10dp"
        superlistview:superlv__empty="@layout/emptyview"
        superlistview:superlv__scrollbarStyle="outsideOverlay" >
</com.quentindommerc.superlistview.SuperListview>
```

### For Gridview
```xml
<com.quentindommerc.superlistview.SuperGridview
        xmlns:superlistview="http://schemas.android.com/apk/res-auto"
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        superlistview:superlv__listClipToPadding="false"
        superlistview:superlv__listDivider="@android:color/transparent"
        superlistview:superlv__listDividerHeight="10dp"
        superlistview:superlv__listPadding="10dp"
        superlistview:superlv__empty="@layout/emptyview"
        superlistview:supergv__columns="2"
        superlistview:supergv__verticalSpacing="10dp"
        superlistview:supergv__horizontalSpacing="10dp"
        superlistview:superlv__scrollbarStyle="outsideOverlay" >
</com.quentindommerc.superlistview.SuperGridview>
```

-   Current Attributes supported:
```xml
<attr name="superlv__listSelector" format="reference"/>
<attr name="superlv__empty" format="reference"/>
<attr name="superlv__moreProgress" format="reference"/>
<attr name="superlv__progress" format="reference"/>
<attr name="superlv__listDivider" format="reference"/>
<attr name="superlv__listDividerHeight" format="dimension"/>
<attr name="superlv__listClipToPadding" format="boolean"/>
<attr name="superlv__listPadding" format="dimension"/>
<attr name="superlv__listPaddingTop" format="dimension"/>
<attr name="superlv__listPaddingBottom" format="dimension"/>
<attr name="superlv__listPaddingLeft" format="dimension"/>
<attr name="superlv__listPaddingRight" format="dimension"/>
<attr name="superlv__scrollbarStyle">
    <flag name="insideOverlay" value="0x0"/>
    <flag name="insideInset" value="0x01000000"/>
    <flag name="outsideOverlay" value="0x02000000"/>
    <flag name="outsideInset" value="0x03000000"/>
</attr>

<!-- Layout to build a superList. Default values are  @layout/view_progress_listview for a list and
@layout/view_progress_gridview for a grid-->
<attr name="superlv_mainLayoutID" format="reference"/>

```

##SuperListView Java Usage

```java
list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener {
  @Override
  public void onRefresh() {
      // Do your refresh
  });

// when there is only 10 items to see in the list, this is triggered
list.setupMoreListener(new OnMoreListener() {
  @Override
  public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
    // Fetch more from Api or DB
  }}, 10);


// the 2nd parameters is true if you want SuperListView to automatically
// delete the item from the listview or false if you don't
list.setupSwipeToDismiss(new SwipeDismissListViewTouchListener.DismissCallbacks() {
  @Override
  public boolean canDismiss(int position) {
    return true
  }

  @Override
  public void onDismiss(ListView listView, int[] reverseSortedPositions) {
    // Do your stuff like call an Api or update your db
  }}, true);

```


####[Sample java][sample java]

## Contributors

* Arasthel [Github](https://github.com/Arasthel)
* Gabriele Mariotti [Github](https://github.com/gabrielemariotti), [Google+](https://plus.google.com/+GabrieleMariotti/posts)
* Kochkin Artem [Github](https://github.com/kolipass)


##License

    Copyright (c) 2014 Quentin Dommerc

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    Come on, don't tell me you read that.

[![Analytics](https://ga-beacon.appspot.com/UA-40136896-2/SuperListview/readme)](https://github.com/igrigorik/ga-beacon)
[sample java]:https://github.com/dommerq/SuperListview/blob/dev/Sample/src/main/java/com/quentindommerc/superlistview/sample/ListSample.java
[apk]:https://github.com/dommerq/SuperListview/blob/dev/sample.apk
[roman-swipe-to-dismiss]:https://github.com/romannurik/Android-SwipeToDismiss
