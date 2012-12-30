package com.nati.parlor;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
    
public class Booking extends Activity {
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TextView tv1,tv2,tv3;
		Button but;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salon_details);
		 
		tv1=(TextView)findViewById(R.id.parlorName);
		tv2=(TextView)findViewById(R.id.parlorMobile);
		tv3=(TextView)findViewById(R.id.parlorPost);
		but=(Button)findViewById(R.id.bookingBut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert=new AlertDialog.Builder(context);
				alert.setTitle("");
				alert.setMessage("Booking is Under Construction");
				alert.show();
				
				
			}
		});
		
		Intent i = getIntent();  
		Bundle b=i.getExtras();
		Salon ob=b.getParcelable("salon");
		
		tv1.append(" "+ob.getSalonName());
		tv2.append(" "+ob.getMobileNum());
		tv3.append(" "+ob.getPostcode());
		System.out.println("baall: "+ob.getSalon_id()+" "+ob.getMobileNum());
		
		
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
			Intent intent=new Intent(Booking.this,KidsMain.class);
			startActivity(intent);
		}
		else if(item.getTitle().equals("Salon Search"))
		{
			Intent intent=new Intent(Booking.this,SearchParlor.class);
			startActivity(intent);
		}
		
		else if(item.getTitle().equals("Home"))
		{
			Intent intent=new Intent(Booking.this,AdultLogged.class);
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
