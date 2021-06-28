package com.project.mynoteapp;

import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddNewRecord extends AppCompatActivity {
	private EditText txtnama, txtjbt, txttelefon, txtemel;
	private Button btnsave;
	private DBHelper mHelper;
	private SQLiteDatabase dataBase;
	private String id,name,dept;//to hold the data strings

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_record);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);
		mHelper=new DBHelper(this);
		dataBase = mHelper.getWritableDatabase();
		
		//initiate all textbox container to hold the data for the staff
		txtnama=(EditText)findViewById(R.id.txtnama);
		txtjbt=(EditText)findViewById(R.id.txtjbt);
		
		btnsave=(Button)findViewById(R.id.btnsavenew);
		btnsave.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				//capture amendment
				name=txtnama.getText().toString();
				dept=txtjbt.getText().toString();
				if(name.equals("")||dept.equals("")) {
					Toast.makeText(AddNewRecord.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
				}
				else
					{
					saveData();
				}

			}
		});//end btnsave setOnCLickCListener
	}


	//save new record
	private void saveData(){
		ContentValues values=new ContentValues();
		
		values.put(DBHelper.NAMA,name);
		values.put(DBHelper.JBT,dept);
		dataBase.insert(DBHelper.TABLE_NAME, null, values);
		dataBase.close();
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch (id) {
			case R.id.Appinfo :
				new AlertDialog.Builder(AddNewRecord.this)
						.setIcon(android.R.drawable.ic_menu_info_details)
						.setTitle("Designed and Developed By :")
						.setMessage("Rayson L Dsouza\n"+"Santhosh G Patkar")
						.setNegativeButton("Close", null).show();

		}
		return true;
	}

}

