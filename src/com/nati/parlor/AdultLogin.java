package com.nati.parlor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdultLogin extends Activity implements OnClickListener{
	Button loginBtn;
	EditText username,password;
	String user,pass;
	int flag=0;
	final Context context = this;
	ProgressDialog pd;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(User.logFlag==0)
        {
	        setContentView(com.nati.parlor.R.layout.adult_login);

	        init();
        }
        else
        {
        	Intent intent=new Intent(AdultLogin.this,AdultLogged.class);
        	startActivity(intent);
        }
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
		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		return true;
	}

	private void init()
	{
		loginBtn=(Button)findViewById(com.nati.parlor.R.id.loginBtn);
		username=(EditText)findViewById(com.nati.parlor.R.id.userNameBox);
		password=(EditText)findViewById(com.nati.parlor.R.id.passBox);
		
		loginBtn.setOnClickListener(this);
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==com.nati.parlor.R.id.loginBtn)
		{
			AlertDialog.Builder alertBuilder= new AlertDialog.Builder(context);
			alertBuilder.setTitle("Message");
			GetFromTextBox();
			if(flag==0)
			{
				User.logFlag=1;
				alertBuilder.setMessage("Login Successful");
			}
			else if(flag==1)
			{
				alertBuilder.setMessage("Username or password field is Blank");
			}
			else
			{
				alertBuilder.setMessage("Wrong Username Password ");
			}
			alertBuilder.setCancelable(true);
			alertBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					if(flag==0)
					{
						Intent intent=new Intent(AdultLogin.this,AdultLogged.class);
						startActivity(intent);
					}
					else
					{
						dialog.cancel();
					}
				}
			  });
			alertBuilder.show();
		}
		
	}
	public void GetFromTextBox()
	{
		user=username.getText().toString();
		pass=password.getText().toString();
		if(user.equals("")||pass.equals(""))
		{
			flag=1;
		}
		else
		{
			flag=(GetFromDB(this.user, this.pass));
		}
	}
	
	public int GetFromDB(String user,String pass)
	{
		 InputStream content = null;
		 ArrayList<String> list=new ArrayList();
		 String url="http://10.0.2.2:81/salon/login.php?user="+user+"&pass="+pass;
		 ConnectivityManager mng=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo=mng.getActiveNetworkInfo();
			if(netInfo.isConnected())
			{
		 try {
			 pd=ProgressDialog.show(this, "loading", "loading....");
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpResponse response = httpclient.execute(new HttpGet(url));
			    content = response.getEntity().getContent();
			    list=readStream(content);
			    pd.hide();
			    System.out.println(list.get(0));
			    if(list.size()>0)
			    {
			    	if(list.get(0).toString().equals("true"))
			    	{
			    		return 0;
			    	}
			    	else
			    	{
			    		return 2;
			    	}
			    }
			  } catch (Exception e) {
			    Log.e("[GET REQUEST]", "Network exception");
			  }
			}
			else
			{
				AlertDialog.Builder alert=new AlertDialog.Builder(this);
				alert.setTitle("No Network");
				alert.setMessage("No Network was found");
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
			}
		 System.out.println("baal");
		 return 0;
		
	}
	private ArrayList<String> readStream(InputStream in) {
		  BufferedReader reader = null;
		  ArrayList<String> lines=new ArrayList<String>();
		  try {
		    reader = new BufferedReader(new InputStreamReader(in));
		    
		    String line="";
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		      lines.add(line);
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    if (reader != null) {
		      try {
		        reader.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		        }
		    }
		  }
		  return lines;
		} 
	

}
