package com.nati.parlor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ParlorActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	Button kidBtn,adultBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        kidBtn=(Button)findViewById(R.id.button1);
        adultBtn=(Button)findViewById(R.id.button2);
        kidBtn.setOnClickListener(this);
        adultBtn.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button1)
		{
			Intent intent=new Intent(ParlorActivity.this,KidsMain.class);
			startActivity(intent);
		}
		else if(v.getId()==R.id.button2)
		{
			Intent intent = new Intent(ParlorActivity.this,AdultMain.class);
			startActivity(intent);
		}
		
	}
}