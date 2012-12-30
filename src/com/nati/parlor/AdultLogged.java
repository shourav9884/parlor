package com.nati.parlor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class AdultLogged extends Activity implements OnClickListener {
	Button snapImage,salonList,searchSalon,gallery;
	ImageView imageView;
	String imageStr;
	ProgressDialog pd;
	
	Bitmap photo;
	private static final int CAMERA_REQUEST = 1888; 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adult_logged);
        init();
        
	}
	
	public void init()
	{
		snapImage=(Button)findViewById(R.id.snapImageBtn);
		salonList=(Button)findViewById(R.id.salonListBtn);
		searchSalon=(Button)findViewById(R.id.searchSalonBtn);
		imageView=(ImageView)findViewById(R.id.imageView1);
		gallery=(Button)findViewById(R.id.gallery_select);
		
		snapImage.setOnClickListener(this);
		salonList.setOnClickListener(this);
		searchSalon.setOnClickListener(this);
		gallery.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.snapImageBtn)
		{
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
            startActivityForResult(cameraIntent, CAMERA_REQUEST); 
		}
		else if(v.getId()==R.id.salonListBtn)
		{
			Intent intent=new Intent(AdultLogged.this,SalonList.class);
			startActivity(intent);
		}
		else if(v.getId()==R.id.searchSalonBtn)
		{
			Intent intent=new Intent(AdultLogged.this, SearchParlor.class);
			startActivity(intent);
		}
		else if(v.getId()==R.id.gallery_select)
		{
			Intent intent= new Intent(AdultLogged.this,KidsMain.class);
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
		if(item.getTitle().equals("Gallery"))
		{
			Intent intent=new Intent(AdultLogged.this,KidsMain.class);
			startActivity(intent);
		}
		else if(item.getTitle().equals("Salon Search"))
		{
			Intent intent=new Intent(AdultLogged.this,SearchParlor.class);
			startActivity(intent);
		}
		else if(item.getTitle().equals("Snap Image"))
		{
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
            startActivityForResult(cameraIntent, CAMERA_REQUEST); 
		}
		else if(item.getTitle().equals("Salon List"))
		{
			Intent intent=new Intent(AdultLogged.this,SalonList.class);
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
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            SaveToSdcard(photo);
           
        }  
    } 
	public void SaveToSdcard(Bitmap photo)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		this.photo=photo;
		photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
		      

		byte[] ba = bytes.toByteArray();

		String ba1 = Base64.encodeBytes(ba);
		imageStr=ba1;
		System.out.println(imageStr);

		pd=ProgressDialog.show(AdultLogged.this, "", "Uploading to Database....");
		RequestThread reqThread=new RequestThread();
		reqThread.start();
		/*ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("image", ba1));
		nameValuePairs.add(new BasicNameValuePair("name", "Nati"));
		ConnectivityManager mng=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo=mng.getActiveNetworkInfo();
		if(netInfo!=null)
		{
			try {

				HttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new

						HttpPost("http://10.0.2.2:81/uploadimageAB/base.php"); 

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);

				HttpEntity entity = response.getEntity();

				//is = (InputStream) entity.getContent();
				//is = (InputStream) entity.getContent();

			} catch (Exception e) {

				Log.e("log_tag", "Error in http connection " + e.toString());

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
		}*/
		 
	
		
	}
	
	class RequestThread extends Thread
	{

		@Override
		public void run() {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("image", imageStr));
			nameValuePairs.add(new BasicNameValuePair("name", "Nati"));
			
			DefaultHttpClient clien=new DefaultHttpClient();
			HttpPost post=new HttpPost("http://10.0.2.2:81/uploadimageAB/base.php");
			try {
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = clien.execute(post);
				HttpEntity entity = response.getEntity();
				
				handler.sendEmptyMessage(1);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				AlertDialog.Builder alertbuild=new AlertDialog.Builder(AdultLogged.this);
				alertbuild.setTitle("Title");
				alertbuild.setMessage("Errorr to hoichee");
				alertbuild.show();
			}
			else
			{
				 imageView.setImageBitmap(photo);
			}
			
		}
    	
    };
}
