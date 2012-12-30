package com.nati.parlor;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class SearchTwo extends Activity {

	ArrayList<Salon>salonList=new ArrayList<Salon>();
	ArrayList<String> list=new ArrayList<String>();
	String first,second;
	ProgressDialog pd;
	Context context;
	EditText et1, et2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_second);
		et1=(EditText)findViewById(R.id.secondSearchEdit1);
		et2=(EditText)findViewById(R.id.secondSearchEdit2);
		final Button but=(Button)findViewById(R.id.searchBtn2);
		
		context=this;
		but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
						// TODO Auto-generated method stub
						String temp1=et1.getText().toString();
						String temp2=et2.getText().toString();
						
						if(temp1.equals("")||temp2.equals(""))
						{
							AlertDialog.Builder alert= new AlertDialog.Builder(context);
							alert.setTitle("Error");
							alert.setMessage("Post code can not be empty");
						}
						else
						{		    	 
							first=temp1;
							second=temp2;
							pd=ProgressDialog.show(SearchTwo.this, "title", "Loading....");
							RequestThread thread=new RequestThread();
							thread.start();
							

							
							 
						
						}
						
						

				
				
			}

			
		
		});



	}


	class RequestThread extends Thread
	{

		@Override
		public void run() {
			

			DefaultHttpClient clien=new DefaultHttpClient();
			HttpGet getReq=new HttpGet("http://10.0.2.2:81/salon/show_list.php?first="+first+"&second="+second);
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
				AlertDialog.Builder alertbuild=new AlertDialog.Builder(SearchTwo.this);
				alertbuild.setTitle("Title");
				alertbuild.setMessage("Errorr to hoichee");
				alertbuild.show();
			}
			else
			{
				//imageView.setImageBitmap(photo);
				//System.out.println(salonList.size());
				if(salonList.size()>0)
				{
				for(int i=0;i<salonList.size();i++)
				{
					String s;
					s=salonList.get(i).getSalonName()+"\n"+salonList.get(i).getMobileNum()+"\n"+salonList.get(i).getPostcode();
					list.add(s);


				}
				}
				else
				{
					String s="No Such Salon found";
					list.add(s);
				}



				ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.text_view,list);
				ListView lView = new ListView(context); 
		        
		        lView.setAdapter(adapter);
		       lView.setFocusableInTouchMode(true);     

		       

				lView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
						// TODO Auto-generated method stub
						Toast.makeText(context, ""+arg2, Toast.LENGTH_SHORT).show();
					}
				});
				
				 setContentView(lView);

			}

		}

	};



}

