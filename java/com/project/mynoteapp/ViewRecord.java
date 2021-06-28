package com.project.mynoteapp;

import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewRecord extends AppCompatActivity {
	TextView notehed,notepara;
	private DBHelper mHelper;
	private SQLiteDatabase dataBase;
	String id,name,desc;//to hold the data strings

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_record);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);
		//capture sent parameter from previous screen
		id=getIntent().getExtras().getString("stafid");
		
		//initiate all textbox container to hold the data for the staff
		notepara=findViewById(R.id.notepara);
		//end btnemail setOnCLickCListener
	}
	
	@Override
	protected void onResume() {
		//refresh data for screen is invoked/displayed
		displayData();
		super.onResume();
	}
	
	//display single record of data from stafid
	private void displayData() {
		mHelper=new DBHelper(this);
		dataBase = mHelper.getWritableDatabase();
		//the SQL command to fetched all records from the table
		String sql="SELECT * FROM "
				+ DBHelper.TABLE_NAME +" WHERE id='"+id+"';";
		Cursor mCursor = dataBase.rawQuery(sql, null);
		
		//fetch the record
		if (mCursor.moveToFirst()) {
			desc = mCursor.getString(mCursor.getColumnIndex(DBHelper.JBT));
			notepara.setText(desc);
				
		}
		else{
			//do something here if no record fetched from database
			notehed.setText(sql);
		}
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
				new AlertDialog.Builder(ViewRecord.this)
						.setIcon(android.R.drawable.ic_menu_info_details)
						.setTitle("Designed and Developed By :")
						.setMessage("Rayson L Dsouza\n"+"Santhosh G Patkar")
						.setNegativeButton("Close", null).show();

		}
		return true;
	}//end displayData

}
