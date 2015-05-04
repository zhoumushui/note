package com.tchip.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends Activity{
	
	private EditText Title;		//便签标题
	private EditText Content;	//便签内容
	private int _noteId;		//便签ID
	private NoteDbHelper _db;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_new); 
    	Title = (EditText)findViewById(R.id.editText1);
    	Content = (EditText)findViewById(R.id.editText2);
		_db = new NoteDbHelper(this);	
		Intent intent = getIntent();
		_noteId = intent.getIntExtra(ListsActivity.EXTRA_NOTE_ID, -1);	
		if (_noteId > 0) {
			Note note = _db.getNote(_noteId);
			Title.setText(note.getTitle());
			Content.setText(note.getContent());
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lay_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
        	Intent intent = new Intent(Intent.ACTION_MAIN);
    		intent.addCategory(Intent.CATEGORY_HOME);
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    		android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }else if(id == R.id.action_about)
        {
        	Intent intent = new Intent(this, AboutActivity.class);
        	startActivity(intent);
        	return true;
        }
        return super.onOptionsItemSelected(item);
        
    }
    public void EditCancel(View view1)
    {
    	Toast.makeText(this,"天启提示：放弃新建便签", Toast.LENGTH_SHORT).show();
    	finish();
    }
    public void EditSave(View view2)
    {   	
    	String titleVoid = Title.getText().toString();
    	String contentVoid = Content.getText().toString();
    	if(titleVoid.equals("")||contentVoid.equals(""))
    	{
    		Toast.makeText(this,"天启提示：标题或内容为空", Toast.LENGTH_SHORT).show();
    	}else
    	{	
        	ToDatabase();	//插入数据库
        	Toast.makeText(this,"天启提示：便签保存成功", Toast.LENGTH_SHORT).show();
        	finish();
    	}
    }
    
    public final int ToDatabase()
    {
    	String title = Title.getText().toString();
    	String content = Content.getText().toString();  	
    	int newNoteId = -1;
    	if (_noteId > 0) {
    		Note note = _db.getNote(_noteId);
    		note.setTitle(title);
    		note.setContent(content);
    		note.setModified(Long.valueOf(System.currentTimeMillis()));
    		_db.updateNote(note);
    	}
    	else {
    		Note newNote = new Note(title, content);
    		newNoteId = _db.addNote(newNote);
    	}
    	return newNoteId;
    }
}
