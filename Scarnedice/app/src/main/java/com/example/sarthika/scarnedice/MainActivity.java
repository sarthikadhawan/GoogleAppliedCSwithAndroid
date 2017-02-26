package com.example.sarthika.scarnedice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int yourscore=0,mainyourscore=0,maincompscore=0,compscore=0;
    Random rand;
    ImageView btn ;


    Button roll;
    Button hold;
    Button reset;
    TextView yourscoret;
    TextView yourscorett;
    TextView compscorett;
    TextView compscoret;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll= (Button) findViewById(R.id.roll);
        hold= (Button) findViewById(R.id.hold);
        reset= (Button) findViewById(R.id.reset);
        btn = (ImageView) findViewById(R.id.img);
        compscoret=(TextView) findViewById(R.id.compscore);
        yourscoret=(TextView) findViewById(R.id.yourscore);
        compscorett=(TextView) findViewById(R.id.currcomp);
        yourscorett=(TextView) findViewById(R.id.currscore);

        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                reset_func(v);
            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                hold(v);
            }
        });

        roll.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                roll(v);
            }
        });

    }
public void img(int n)
{

    if(n==1)
        btn.setImageResource(R.drawable.dice1);
    else if(n==2)
        btn.setImageResource(R.drawable.dice2);
    else if(n==3)
        btn.setImageResource(R.drawable.dice3);
    else if(n==4)
        btn.setImageResource(R.drawable.dice4);
    else if(n==5)
        btn.setImageResource(R.drawable.dice5);
    else if(n==6)
        btn.setImageResource(R.drawable.dice6);


}
    public void roll(View view)
    {

        rand=new Random();
        int ans=rand.nextInt(6)+1;
        img(ans);
        int i;

        if(ans==1)
        {

            if(mainyourscore!=0)
            {
                yourscorett.setText("Your current score:");
                yourscorett.setText(yourscorett.getText()+Integer.toString(yourscore-mainyourscore));
                yourscore=mainyourscore;
                hold(view);


            }
            else
            {
                yourscore=0;
                hold2(view);

            }
        }
        else if(ans>=2||ans<=6)
        {
            yourscore+=ans;
            yourscorett.setText("Your current score:");
            yourscorett.setText(yourscorett.getText()+Integer.toString(yourscore-mainyourscore));
        }

        yourscoret.setText("Your score:");
        yourscoret.setText(yourscoret.getText()+Integer.toString(yourscore));

       // yourscoret.setText(Integer.toString(yourscore));
        if(yourscore>=100)
        { Toast.makeText(getApplicationContext(), "YOU WON!!!", Toast.LENGTH_LONG).show();
            reset_func(view);}
        //rollcomp();
    }
    public void hold2(View view)
    {
        int xx=rollcomp();
        if(xx>=100)
        {Toast.makeText(getApplicationContext(), "YOU LOST!!!", Toast.LENGTH_LONG).show();
            reset_func(view);}
    }
    public void hold(View view)
    {

        mainyourscore=yourscore;


        int xx=rollcomp();
        if(xx>=100)
        {Toast.makeText(getApplicationContext(), "YOU LOST!!!", Toast.LENGTH_LONG).show();
            reset_func(view);}

    }
    public void reset_func(View view)
    {

        yourscore=0;mainyourscore=0;
        compscore=0;maincompscore=0;
        yourscoret.setText("Your score:0");
        compscoret.setText("Computer's score:0");
        yourscorett.setText("Your current score:0");
        compscorett.setText("Computer's current score:0");
    }
    int  rollcomp()
    {

compscore=0;
        roll.setEnabled(false);
        hold.setEnabled(false);


        rand=new Random();

        int ans=rand.nextInt(6)+1;


        img(ans);


while(compscore<20)
{
    if (ans == 1 )
    {


          compscore=0;break;
    }
    else if (ans >= 2 || ans <= 6)
    {
        compscore += ans;
    }

    compscorett.setText("Computer's current score:");
    compscorett.setText(compscorett.getText()+Integer.toString(compscore));
    //compscorett.setText(Integer.toString(compscore));
    ans=rand.nextInt(6)+1;
    img(ans);






}
        if(compscore>=20)
        {
            maincompscore+=compscore;
            compscore=0;
        }

        compscoret.setText("Computer's score:");
        compscoret.setText(compscoret.getText()+Integer.toString(maincompscore));

        compscorett.setText("Computer's current score:");
        compscorett.setText(compscorett.getText()+Integer.toString(compscore));

        //compscorett.setText(Integer.toString(compscore));
        //compscoret.setText(Integer.toString(maincompscore));
        roll.setEnabled(true);
        hold.setEnabled(true);
        return maincompscore;
    }
}
