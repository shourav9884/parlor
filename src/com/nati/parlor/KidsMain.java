package com.nati.parlor;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;

public class KidsMain extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kidsfirstpage);
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
		if(item.getTitle().equals("Salon Search"))
		{
			Intent intent=new Intent(KidsMain.this,SearchParlor.class);
			startActivity(intent);
		}
		
		else if(item.getTitle().equals("Salon List"))
		{
			Intent intent=new Intent(KidsMain.this,SalonList.class);
			startActivity(intent);
		}
		else if(item.getTitle().equals("Home"))
		{
			Intent intent=new Intent(KidsMain.this,AdultLogged.class);
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
