package com.project.mynoteapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateRecord extends Activity {
	private EditText txtnama, txtjbt;
	private Button btnsave;
	private DBHelper mHelper;
	private SQLiteDatabase dataBase;
	private String id,name,dept;//to hold the data strings

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_record);
		mHelper=new DBHelper(this);
		dataBase = mHelper.getWritableDatabase();
		id=getIntent().getExtras().getString("stafid");
		
		//initiate all textbox container to hold the data for the staff
		txtnama=(EditText)findViewById(R.id.txtnama);
		txtjbt=(EditText)findViewById(R.id.txtjbt);
		displayData();
		
		btnsave=(Button)findViewById(R.id.btnsave);
		btnsave.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				//capture amendment
				name=txtnama.getText().toString();
				dept=txtjbt.getText().toString();
				if(name.equals("")||dept.equals("")) {
					Toast.makeText(UpdateRecord.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
				}
				else {
					saveData();
				}
			}
		});//end btnsave setOnCLickCListener
	}
	
	//display single record of data from stafid
	private void displayData() {
		
		//the SQL command to fetched all records from the table
		String sql="SELECT * FROM "
				+ DBHelper.TABLE_NAME +" WHERE id='"+id+"';";
		Cursor mCursor = dataBase.rawQuery(sql, null);
		
		//fetch the record
		if (mCursor.moveToFirst()) {
			name=mCursor.getString(mCursor.getColumnIndex(DBHelper.NAMA));
			txtnama.setText(name);
			txtjbt.setText(mCursor.getString(mCursor.getColumnIndex(DBHelper.JBT)));
			Toast.makeText(UpdateRecord.this, "Success", Toast.LENGTH_SHORT).show();
		}
		else{
			//do something here if no record fetched from database
			txtnama.setText(sql);
		}
	}//end displayData
	
	
	//save updated data
	private void saveData(){
		//dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(DBHelper.NAMA,name);
		values.put(DBHelper.JBT,dept);
		dataBase.update(DBHelper.TABLE_NAME, values, DBHelper.STAFID+"="+id, null);
		dataBase.close();
		finish();
	}

}
