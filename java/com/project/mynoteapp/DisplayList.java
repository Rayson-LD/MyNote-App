package com.project.mynoteapp;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DisplayList extends AppCompatActivity {
	
	private String id;//to hold selected stafid 
	private DBHelper mHelper;
	private SQLiteDatabase dataBase;

	//variables to hold staff records
	private ArrayList<String> stafid = new ArrayList<String>();
	private ArrayList<String> nama = new ArrayList<String>();
	private ArrayList<String> jbt = new ArrayList<String>();

	private ListView userList;
	private AlertDialog.Builder build;
	private EditText searchbar;
	private FloatingActionButton add;
	private DisplayAdapter disapt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_list);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);
		userList = (ListView) findViewById(R.id.List);
		userList.setTextFilterEnabled(true);
		searchbar = findViewById(R.id.search);
		mHelper = new DBHelper(this);
		userList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent i = new Intent(getApplicationContext(),
						ViewRecord.class);
				i.putExtra("stafid", stafid.get(arg2));
				startActivity(i);
			}
		});


		//long-press to update data
		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				
				//invoking AlertDialog box
				build = new AlertDialog.Builder(DisplayList.this);
				build.setTitle("Update/Delete staff " + nama.get(arg2));
				build.setMessage("Do you want to update/delete the record?(Hit back to cancel)");
				
				//user select UPDATE
				build.setNegativeButton("UPDATE",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							//Update record selected
							Intent i = new Intent(getApplicationContext(),
									UpdateRecord.class);
							i.putExtra("stafid", stafid.get(arg2));
							startActivity(i);
							dialog.cancel();
						}
					});//end UPDATE
				//user select DELETE
				build.setPositiveButton("DELETE",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,int which) {
								dataBase.delete(
										DBHelper.TABLE_NAME,
										DBHelper.STAFID + "="
												+ stafid.get(arg2), null);
								Toast.makeText(
										getApplicationContext(),
										nama.get(arg2)+
										" is deleted.", Toast.LENGTH_SHORT).show();
								displayData();
								dialog.cancel();
							}
						});//end DELETE
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}
		});//end setOnItemLongClickListener
		
		
	}// end onCreate method
	@Override
	protected void onResume() {
		//refresh data for screen is invoked/displayed
		displayData();
		super.onResume();
	}

	/**
	 * displays data from SQLite
	 */
	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		//the SQL command to fetched all records from the table
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DBHelper.TABLE_NAME, null);

		//reset variables
		stafid.clear();
		nama.clear();
		jbt.clear();
		
		//fetch each record
		if (mCursor.moveToFirst()) {
			do {
				//get data from field 
				stafid.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.STAFID)));
				nama.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.NAMA)));
				jbt.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.JBT)));
				
			} while (mCursor.moveToNext());
			//do above till data exhausted 
		}
		
		//display to screen
		disapt = new DisplayAdapter(DisplayList.this, nama);
		userList.setAdapter(disapt);
		searchbar.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				disapt.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		mCursor.close();

	}
	;//end displayData
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.Appinfo) {
			new androidx.appcompat.app.AlertDialog.Builder(DisplayList.this)
					.setIcon(android.R.drawable.ic_menu_info_details)
					.setTitle("Designed and Developed By :")
					.setMessage("Rayson L Dsouza\n" + "Santhosh G Patkar")
					.setNegativeButton("Close", null).show();
		}
			if (item.getItemId() == R.id.m_add_record) {
				Intent i = new Intent(getApplicationContext(),
						AddNewRecord.class);
				startActivity(i);
			}

        return true;
    }
    
}//end DisplayList class
