package com.hackinroms.jaymoh.healthyrecipe;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andexert.library.RippleView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RippleView rippleView = (RippleView) findViewById(R.id.rectangle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addItem) {
            Intent intent = new Intent(this,AddRecipe.class);
            startActivity(intent);
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void categorySelected(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.btnFruits:
            case R.id.ibFruits:
            {
                //Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(this,DisplayActivity.class);
                intent.setAction("Fruits");
                startActivity(intent);
                break;
            }
            case R.id.btnVegetables:
            case R.id.ibVegetables:
            {
                Intent intent =new Intent(this,DisplayActivity.class);
                intent.setAction("Vegetables");
                startActivity(intent);
                break;
            }
            case R.id.ibBread:
            case R.id.btnBread:
            {
                Intent intent =new Intent(this,DisplayActivity.class);
                intent.setAction("Bread");
                startActivity(intent);
                break;
            }
            case R.id.btnSoup:
            case R.id.ibSoup:
            {
                Intent intent =new Intent(this,DisplayActivity.class);
                intent.setAction("Soup");
                startActivity(intent);
                break;
            }
        }
    }
}
