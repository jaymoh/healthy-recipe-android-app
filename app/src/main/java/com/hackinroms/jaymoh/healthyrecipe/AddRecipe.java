package com.hackinroms.jaymoh.healthyrecipe;

/**
 * Created by Jaymoh on 1/10/2016.
 */
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddRecipe extends ActionBarActivity {

    private EditText etName;
    private EditText etIns;
    private EditText etIng;
    private Spinner spinCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        init();
    }

    private void init() {
        etName = (EditText)findViewById(R.id.etName);
        etIns = (EditText)findViewById(R.id.etInstructions);
        etIng = (EditText)findViewById(R.id.etIngredients);
        spinCat = (Spinner)findViewById(R.id.spinnerCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void save(View v)
    {
        EditText [] ets = {etName,etIng,etIns};
        for(EditText et:ets)
        {
            if(et.getText().length()<1)
            {
                Toast.makeText(v.getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Recipe r = new Recipe(this);
        r.category = (String)spinCat.getSelectedItem();
        r.name = String.valueOf(etName.getText());
        r.instructions = String.valueOf(etIns.getText());
        r.ingredients = String.valueOf(etIng.getText());
        int id = (int)(long)r.insert();
        if(id!=-1)
        {
            Toast.makeText(v.getContext(), "Recipe saved", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,DisplayActivity.class);
            i.setAction(r.category);
            i.putExtra("ACTIVE", id);
            Log.e("Test AddRecipe id :",id+" ");
            Recipe re = new Recipe(this);
            re = re.get(id);
            Log.e("TEST KEY_NAME ",re.KEY_NAME);
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(v.getContext(), "Recipe not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
