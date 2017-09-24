package com.example.android.uwevents;

import java.util.ArrayList;

/**
 * Created by Danny on 9/23/2017.
 */

// This class populates the side drawer with icons and titles.

public class SideMenuDetails extends EventActivity {
   public ArrayList<DrawerItem> getDrawerItems(){
       ArrayList<DrawerItem> mDrawerItemList = new ArrayList<>();
       DrawerItem item = new DrawerItem();
       item.setIcon(R.drawable.ic_menu_slideshow);
       item.setTitle("Filters");
       mDrawerItemList.add(item);
       DrawerItem item2 = new DrawerItem();
       item2.setIcon(R.drawable.ic_menu_manage);
       item2.setTitle("Settings");
       mDrawerItemList.add(item2);

       return mDrawerItemList;
   }
}
