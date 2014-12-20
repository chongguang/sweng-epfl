package com.example.swengtutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * This is the class where we implement how to display
 * the user's message.
 * @author lcg31439
 *
 */
public class ShowMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_message);

	    // get the Intent that started this Activity
	    Intent startingIntent = getIntent();

	    // get the value of the user string
	    String userText = startingIntent.getStringExtra(MainActivity.class.getName());

	    // get the TextView on which we are going to show the string, and update
	    // its contents
	    TextView textView = (TextView) findViewById(R.id.displayed_text);
	    textView.setText(userText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
