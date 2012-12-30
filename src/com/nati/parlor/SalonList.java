package com.nati.parlor;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SalonList extends Activity {
	
	
	

	ArrayList<String> list=new ArrayList<String>();
	ArrayList<Salon> salonList=new ArrayList<Salon>();
	String s="";
	ProgressDialog pd;
	Context context;
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
			setContentView(R.layout.salon_list);
			lv=(ListView)findViewById(R.id.list1);
			context=this;

			pd=ProgressDialog.show(SalonList.this, "title", "Loading....");
	        
	        RequestThread reqThread=new RequestThread();
	        reqThread.start();
			
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
			Intent intent=new Intent(SalonList.this,KidsMain.class);
			startActivity(intent);
		}
		else if(item.getTitle().equals("Salon Search"))
		{
			Intent intent=new Intent(SalonList.this,SearchParlor.class);
			startActivity(intent);
		}
		
		else if(item.getTitle().equals("Home"))
		{
			Intent intent=new Intent(SalonList.this,AdultLogged.class);
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
	
	class RequestThread extends Thread
	{

		@Override
		public void run() {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("image", ""));
			nameValuePairs.add(new BasicNameValuePair("name", "Nati"));
			
			DefaultHttpClient clien=new DefaultHttpClient();
			HttpGet getReq=new HttpGet("http://10.0.2.2:81/salon/show_list.php");
			HttpResponse response;
			try {
				response = clien.execute(getReq);
			
			int status=response.getStatusLine().getStatusCode();
			if(status==HttpStatus.SC_OK)
			{
				//get
				
				HttpEntity entity=response.getEntity();
				String jsonStr=EntityUtils.toString(entity);
				System.out.println(jsonStr);
				//parse json
				try {
					JSONArray jsonArray=new JSONArray(jsonStr);
					for(int i=0;i<jsonArray.length();i++)
					{
						JSONObject obj=jsonArray.getJSONObject(i);
						int userId=Integer.parseInt((String)obj.get("owner_id"));
						String name=(String) obj.get("store_name");
						String phone=obj.getString("store_phone_no");
						String postcode=obj.getString("postal_code_no");
						
						Salon salon=new Salon(userId, name, postcode, phone);
						System.out.println(salon.getMobileNum()+" "+salon.getPostcode()+" "+salon.getSalonName());
						salonList.add(salon);
						
						//User user=new User(userId, email, lat, lon);
						//users.add(user);  
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				handler.sendEmptyMessage(1);
			}
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			

			
		}
		
	}
	
	Handler handler=new Handler()
    {

		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			if(msg.what==0)
			{
				AlertDialog.Builder alertbuild=new AlertDialog.Builder(SalonList.this);
				alertbuild.setTitle("Title");
				alertbuild.setMessage("Errorr to hoichee");
				alertbuild.show();
			}
			else
			{
				 //imageView.setImageBitmap(photo);
				//System.out.println(salonList.size());
				for(int i=0;i<salonList.size();i++)
				{
					s=salonList.get(i).getSalonName()+"\n"+salonList.get(i).getMobileNum()+"\n"+salonList.get(i).getPostcode();
					list.add(s);
					
					
				}
			
				
				
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.text_view,list);
				lv.setAdapter(adapter);
				
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
						// TODO Auto-generated method stub
						Toast.makeText(context, ""+arg2, Toast.LENGTH_SHORT).show();
						
						Salon salons=salonList.get(arg2);
						Intent intent=new Intent(SalonList.this,Booking.class);
						Bundle b=new Bundle();
						b.putParcelable("salon", salons);
						intent.putExtras(b);
						startActivity(intent);
					}
				});

			}

		}
    	
    };
	

	
		
		
		
	

}
