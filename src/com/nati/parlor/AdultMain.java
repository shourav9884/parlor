package com.nati.parlor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AdultMain extends Activity implements OnClickListener{
	Button loginBtn;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adult_first_page);
        
       loginBtn=(Button)findViewById(R.id.adultLoginBtn);
       
       
       loginBtn.setOnClickListener(this);
       
       
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.adultLoginBtn)
		{
			Intent intent= new Intent(AdultMain.this, AdultLogin.class);
			startActivity(intent);
		}
		
		
	}

}
