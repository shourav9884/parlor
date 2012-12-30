package com.nati.parlor;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class SearchParlor extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);
		
		Resources ressources = getResources(); 
		TabHost tabHost=getTabHost(); 
 
		// Android tab
		Intent intentAndroid = new Intent().setClass(this, SearchOne.class);
		TabSpec tabSpecAndroid = tabHost
		  .newTabSpec("Android")
		  .setIndicator("Search One")
		  
		  .setContent(intentAndroid);
		Intent intentTwo= new Intent().setClass(this, SearchTwo.class);
		TabSpec tabSpecSecond=tabHost
				.newTabSpec("Two")
				.setIndicator("Search Two")
				.setContent(intentTwo);
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecSecond);
		
		tabHost.setCurrentTab(0);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(com.nati.parlor.R.menu.main_activity, menu);
	    getOverflowMenu();
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Gallery"))
		{
			Intent intent=new Intent(SearchParlor.this,KidsMain.class);
			startActivity(intent);
		}
		
		else if(item.getTitle().equals("Salon List"))
		{
			Intent intent=new Intent(SearchParlor.this,SalonList.class);
			startActivity(intent);
		}
		else if(item.getTitle().equals("Home"))
		{
			Intent intent=new Intent(SearchParlor.this,AdultLogged.class);
			startActivity(intent);
		}
		return true;
	}
	private void getOverflowMenu() {

	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
