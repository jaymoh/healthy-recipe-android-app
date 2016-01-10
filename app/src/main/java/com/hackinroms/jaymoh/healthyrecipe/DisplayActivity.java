package com.hackinroms.jaymoh.healthyrecipe;

/**
 * Created by Jaymoh on 1/10/2016.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class DisplayActivity extends ActionBarActivity {

    private TextView tvCat;
    private TextView tv1;
    private TextView tv3;
    private TextView tv2;
    private TextView tv4;
    private TextView tvEmpty;
    private int active;
    private int _id;
    private int size;
    ArrayList<Recipe> list;
    private ImageButton prev;
    private ImageButton next;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        init();
    }

    private void init() {
        prev = (ImageButton)findViewById(R.id.prev);
        next = (ImageButton)findViewById(R.id.next);
        tvCat = (TextView)findViewById(R.id.tvCat);
        tvEmpty = (TextView)findViewById(R.id.empty);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        tv4 = (TextView)findViewById(R.id.tv4);
        Intent intent =  getIntent();
        category =intent.getAction();
        active = 0;
        _id = intent.getIntExtra("ACTIVE",-1);
        tvCat.setText("Category : "+category);
        if(_id==-1) {
            Recipe r = new Recipe(this);
            list = r.getCategory(category);
            _id=0;
        }
        else{
            list = getCategory(category, _id);
        }
        size = list==null? 0 :list.size();
        display();

    }

    private void display() {
        if(size<1)
        {
            tvEmpty.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            return;
        }
        Recipe recipe = list.get(active);
        tv2.setText(recipe.ingredients);
        tv4.setText(recipe.instructions);
        if(active==0)
            prev.setVisibility(View.GONE);
        else
            prev.setVisibility(View.VISIBLE);
        if(active==size-1)
            next.setVisibility(View.GONE);
        else
            next.setVisibility(View.VISIBLE);
    }

    public void navigate(View v)
    {
        if(v.getId()==R.id.prev)
            active--;
        else
            active++;
        display();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
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
        else if(id ==R.id.deleteItem)
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete(active);
                }
            });
            adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            adb.setMessage("Are you sure you want to delete this recipe?");
            adb.setTitle("Delete recipe");
            adb.create().show();

        }

        return super.onOptionsItemSelected(item);
    }



    public ArrayList<Recipe> getCategory(String cat, int id) {
        Log.e("DA","TEST id of inserted : "+id);
        ArrayList<Recipe> old = new Recipe(this).getAll();
        ArrayList<Recipe> list = new ArrayList<Recipe>();
        int i = 0;
        for(Recipe r:old)
        {
            if(r.category.equals(cat))
            {
                list.add(r);
                if(_id==-1) {
                    if (id == r._id)
                    {
                        active = i;
                        _id = -2;
                    }
                    i++;
                }
            }
        }
        return list;
    }
    private void delete(int index) {
        int id= list.get(index)._id;
        Recipe r = new Recipe(this);
        if(r.delete(id))
        {
            Log.e("DAS","DELETION SUCCESSFUL");
        }
        else
            Log.e("DAS","DELETION UNSUCCESSFUL");
        active = 0;
        list = r.getCategory(category);
        active=0;
        display();
    }
}
