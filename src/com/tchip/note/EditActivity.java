package com.tchip.note;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity{
	
	private EditText Title;		//便签标题
	private EditText Content;	//便签内容
	private int _noteId;		//便签ID
	private NoteDbHelper _db;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_edit);        
    	Title = (EditText)findViewById(R.id.editText1);
    	Content = (EditText)findViewById(R.id.editText2);	
		_db = new NoteDbHelper(this);		
		Intent intent = getIntent();
		_noteId = intent.getIntExtra(ListsActivity.EXTRA_NOTE_ID, -1);
		
		//编辑便签
		if (_noteId > 0) {
			Note note = _db.getNote(_noteId);
			Title.setText(note.getTitle());
			Content.setText(note.getContent());
			//设置显示创建时间格式
			TextView noteTime = (TextView)findViewById(R.id.textView4);				
			SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			java.util.Date dt = new Date(note.getCreated());  
			String sDateTime = sdf.format(dt);  			
			noteTime.setText(sDateTime);
		}      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lay_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        int id = item.getItemId();
        if (id == R.id.action_exit) {
        	//退出应用，Kill进程
        	Intent intent = new Intent(Intent.ACTION_MAIN);
    		intent.addCategory(Intent.CATEGORY_HOME);
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    		android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void EditCancel(View view1)
    {
    	//取消编辑Toast提示
    	Toast.makeText(this,"天启提示：放弃修改", Toast.LENGTH_SHORT).show();
    	finish();
    }
    public boolean EditSave(View view2)
    { 	
    	ToDatabase();	//插入数据库
    	Toast.makeText(this,"天启提示：便签保存成功", Toast.LENGTH_SHORT).show();
    	finish();
    	return true;
    }
    
    public void NoteRemove(View view3)
    {	
    	Dialog dialog=new AlertDialog.Builder(EditActivity.this)
    	.setTitle("删除对话框")
    	.setIcon(R.drawable.ic_launcher)
    	.setMessage("确认删除该便签吗？")
    	//相当于点击确认按钮
    	.setPositiveButton("确认", new DialogInterface.OnClickListener() {    		
    	public void onClick(DialogInterface dialog, int which) {
    		RealDelete();
    		
    	}
    	})
    	//相当于点击取消按钮
    	.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int which) {
    	// TODO Auto-generated method stub
    	}
    	})
    	.create();
    	dialog.show();
}
    
    public void RealDelete()
    {
    	//删除便签函数
    	_db.deleteNote(_noteId);
    	Toast.makeText(this,"天启提示：便签删除成功", Toast.LENGTH_SHORT).show();
    	finish();
    }
    
    public final int ToDatabase()
    {
    	//插入到数据库函数
    	String title = Title.getText().toString();
    	String content = Content.getText().toString();  	
    	int newNodeId = -1;  	
    	if (_noteId > 0) {
    		Note note = _db.getNote(_noteId);
    		note.setTitle(title);
    		note.setContent(content);
    		note.setModified(Long.valueOf(System.currentTimeMillis()));
    		_db.updateNote(note);
    	}
    	else {
    		Note newNote = new Note(title, content);
    		newNodeId = _db.addNote(newNote);
    	}
    	return newNodeId;
    }
}
