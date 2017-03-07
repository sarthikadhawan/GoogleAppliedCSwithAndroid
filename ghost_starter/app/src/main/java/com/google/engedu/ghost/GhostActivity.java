/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.BufferUnderflowException;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private SimpleDictionary simpleDictionary;
    private FastDictionary fdictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    //EditText ed;
    TextView text;
    TextView label;
    Button challenge;
    Button reset;
    Button prefixx;
    Button suffixx;
    String prefix="unus";
    int chk=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();

        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        try {
            InputStream inputStream = assetManager.open("words.txt");
            simpleDictionary = new SimpleDictionary(new InputStreamReader(inputStream));
            fdictionary = new FastDictionary(new InputStreamReader(inputStream));
        } catch (IOException e) {

        }
        text = (TextView) findViewById(R.id.ghostText);
        label = (TextView) findViewById(R.id.gameStatus);

        challenge=(Button)findViewById(R.id.challenge);
        reset=(Button)findViewById(R.id.reset);
        reset=(Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                onStart(v);
            }
        });
        challenge.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                challenge(v);
            }
        });





    onStart(null);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        String str1 = null;
        String str2 = null;

        super.onSaveInstanceState(savedInstanceState);

        str1 = (String) text.getText();
        str2 = (String) label.getText();
        savedInstanceState.putString("text", str1);
        savedInstanceState.putString("label", str2);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        String str1 = null;
        String str2 = null;

        super.onRestoreInstanceState(savedInstanceState);

        str1 = savedInstanceState.getString("text");
        str2 = savedInstanceState.getString("label");
        text.setText(str1);
        label.setText(str2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {

        chk=0;
        challenge.setEnabled(true);
        userTurn = random.nextBoolean();

        int ans = random.nextInt(simpleDictionary.words.size());
        String y=simpleDictionary.words.get(ans);
        while(y.length() < 4 && simpleDictionary.isWord(y))
        {
             ans = random.nextInt(simpleDictionary.words.size());
             y=simpleDictionary.words.get(ans);
        }

                text.setText(y.substring(0, 4));
                prefix = y.substring(0, 4);
                if (userTurn) {
                    label.setText(USER_TURN);

                } else {
                    label.setText(COMPUTER_TURN);
                    computerTurn();
                }


        return true;

    }




    private void computerTurn()
    {
        int chh=0;
        int ch=check();
        if(ch==0)
        {
            prefix = text.getText().toString().trim().toLowerCase();

            String w = simpleDictionary.getAnyWordStartingWith(prefix);
            //text.setText(w);
            if (w != null)
            {
                if (prefix.length()  <= w.length())
                {
                    text.setText(w.substring(0, prefix.length() + 1));
                    prefix = w.substring(0, prefix.length() + 1);
                }

            }
            else
            {
                 chh=check();

            }

            // Do computer turn stuff then make it the user's turn again
            if(chh==0)
            {
            userTurn = true;
            label.setText(USER_TURN);
            }

        }
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/

        char c=(char)(keyCode+68);
        if(c>=97&&c<=122&&chk==0)
        {
            prefix=prefix+c;
            text.setText(prefix);
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        else
            Toast.makeText(getApplicationContext(), "Wrong Input", Toast.LENGTH_LONG).show();
        return super.onKeyUp(keyCode, event);
    }
    public int check()
    {

        String prefix=text.getText().toString().trim().toLowerCase();
        String w=simpleDictionary.getAnyWordStartingWith(prefix);
        if(simpleDictionary.isWord(prefix.toString())&&prefix.length()>=4)
        {Toast.makeText(getApplicationContext(), "Computer Won!!!", Toast.LENGTH_LONG).show(); return 1;}
        else if(w==null)
        { Toast.makeText(getApplicationContext(), "Computer Won!!!", Toast.LENGTH_LONG).show();return 1;}

return 0;
    }
    public void challenge(View view)
    {

        String prefix=text.getText().toString().trim().toLowerCase();

        if(simpleDictionary.isWord(prefix.toString())&&prefix.length()>=4)

            Toast.makeText(getApplicationContext(), "You Won!!!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Computer Won!!!", Toast.LENGTH_LONG).show();

        challenge.setEnabled(false);
        chk=1;

        //onStart(null);


    }

}
