package com.hackinroms.jaymoh.healthyrecipe;

/**
 * Created by Jaymoh on 1/10/2016.
 */

import android.content.Context;
import java.util.ArrayList;
import android.database.Cursor;
import android.content.ContentValues;
import android.util.Log;

public class Recipe
{
    public final String KEY_TABLE_NAME = "recipe";
    private Db db;
    public String name;
    public String category;
    public String ingredients;
    public String instructions;
    public int _id;

    /*FIELD KEYS*/
    public String KEY_NAME = "name";
    public String KEY_CATEGORY = "category";
    public String KEY_INGREDIENTS = "ingredients";
    public String KEY_INSTRUCTIONS = "instructions";
    public String KEY__ID = "_id";

    public Recipe ()
    {

    }

    public Recipe (Context context)
    {
        db = new Db(context);
    }

    public void init(android.database.sqlite.SQLiteDatabase db)
    {
        String sql;
        db.execSQL("DROP TABLE IF EXISTS recipe");
        sql =
                "CREATE TABLE recipe"+
                        "("+
                        "name text,"+
                        "category text,"+
                        "ingredients text,"+
                        "instructions text,"+
                        "_id integer primary key autoincrement"+
                        ")";
        db.execSQL(sql);
    }

    public Recipe get(int id)
    {
        Recipe recipe;
        db.open();
        Cursor cursor = db.fetch(KEY_TABLE_NAME, id);
        if(cursor==null || cursor.getCount()<1)
            return null;
        cursor.moveToFirst();
	/*Get column IDs*/
        int col_name =  cursor.getColumnIndex(KEY_NAME);
        int col_category =  cursor.getColumnIndex(KEY_CATEGORY);
        int col_ingredients =  cursor.getColumnIndex(KEY_INGREDIENTS);
        int col_instructions =  cursor.getColumnIndex(KEY_INSTRUCTIONS);
        int col__id =  cursor.getColumnIndex(KEY__ID);
        do
        {
            recipe = new Recipe();
            recipe.name = cursor.getString(col_name);
            recipe.category = cursor.getString(col_category);
            recipe.ingredients = cursor.getString(col_ingredients);
            recipe.instructions = cursor.getString(col_instructions);
            recipe._id = Integer.parseInt(cursor.getString(col__id));
        }while (cursor.moveToNext());
        db.close();
        return recipe;

    }

    public ArrayList getAll()
    {
        ArrayList<Recipe> list = new ArrayList<Recipe>();
        db.open();
        Cursor cursor = db.fetchAll(KEY_TABLE_NAME);
        if(cursor==null || cursor.getCount()<1)
            return null;
        cursor.moveToFirst();

        /*Get column IDs*/
        int col_name =  cursor.getColumnIndex(KEY_NAME);
        int col_category =  cursor.getColumnIndex(KEY_CATEGORY);
        int col_ingredients =  cursor.getColumnIndex(KEY_INGREDIENTS);
        int col_instructions =  cursor.getColumnIndex(KEY_INSTRUCTIONS);
        int col__id =  cursor.getColumnIndex(KEY__ID);
        do
        {
            Recipe recipe = new Recipe();

            recipe.name = cursor.getString(col_name);
            recipe.category = cursor.getString(col_category);
            recipe.ingredients = cursor.getString(col_ingredients);
            recipe.instructions = cursor.getString(col_instructions);
            recipe._id = cursor.getInt(col__id);
            Log.e("Test:RECIPE - id",  cursor.getInt(col__id)+"");
            list.add(recipe);
        }while (cursor.moveToNext());
        db.close();
        return list;

    }

    public long insert()
    {
        ContentValues cv = new  ContentValues();
        db.open();
        cv.put(KEY_NAME,name);
        cv.put(KEY_CATEGORY,category);
        cv.put(KEY_INGREDIENTS,ingredients);
        cv.put(KEY_INSTRUCTIONS,instructions);
        long id = db.insert(KEY_TABLE_NAME, cv);
        db.close();
        return id;

    }

    public boolean delete(int id)
    {
        db.open();
        boolean done = db.delete(KEY_TABLE_NAME, id);
        db.close();
        return done;

    }

    public boolean delete()
    {
        db.open();
        boolean done = db.delete(KEY_TABLE_NAME, _id);
        db.close();
        return done;

    }


    public ArrayList<Recipe> getCategory(String cat) {
        ArrayList<Recipe> old = getAll();
        if(old==null)
            return null;
        ArrayList<Recipe> list = new ArrayList<Recipe>();
        for(Recipe r:old)
        {
            if(r.category.equals(cat))
            {
                list.add(r);
            }
        }
        return list;
    }
}
