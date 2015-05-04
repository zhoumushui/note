package com.tchip.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {
	private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_main);  
        imageView =(ImageView)findViewById(R.id.imageView1);
        imageView.setAlpha(50);	 //Alpha 0-255
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lay_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_about) {
        	Intent intent = new Intent(this, AboutActivity.class);
        	startActivity(intent);    	
            return true;
        }else if(id == R.id.action_exit) {
        	Intent intent = new Intent(Intent.ACTION_MAIN);
    		intent.addCategory(Intent.CATEGORY_HOME);
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    		android.os.Process.killProcess(android.os.Process.myPid());	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Chakan(View view1)
    {
    	Intent intent1 = new Intent(this,ListsActivity.class);
    	startActivity(intent1);	
    }
    public void Xinjian(View view2)
    {
    	Intent intent2 = new Intent(this,NewActivity.class);
    	startActivity(intent2);
    }
}
