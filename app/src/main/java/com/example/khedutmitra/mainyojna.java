package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class mainyojna extends AppCompatActivity {
    RecyclerView rcv;
    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainyojna);
        rcv = (RecyclerView) findViewById(R.id.recview);
        //  rcv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new myadapter(dataqueue(),getApplicationContext());
        rcv.setAdapter(adapter);

        //LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //rcv.setLayoutManager(layoutManager);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        rcv.setLayoutManager(gridLayoutManager);
    }

    public ArrayList<Model> dataqueue()
    {
        ArrayList<Model> holder=new ArrayList<>();

        Model ob1=new Model();
        ob1.setHeader("પ્રધાનમંત્રી કૃષિ સિંચાઈ યોજના | PMKSY");
        ob1.setDate("22 September 2022");
        ob1.setDesc("Active");
        ob1.setLink("https://pmksy.gov.in/");
        ob1.setImgname(R.drawable.modi);
        holder.add(ob1);

        Model ob2=new Model();
        ob2.setHeader("પ્રધાનમંત્રી કિસાન સન્માન નિધિ | PMKSN");
        ob2.setDate("28 August 2022");
        ob2.setDesc("Active");
        ob2.setLink("https://pmkisan.gov.in/");
        ob2.setImgname(R.drawable.modi1);
        holder.add(ob2);


        Model ob3=new Model();
        ob3.setHeader("પીએમ કિસાન માન ધન યોજના | PMKMDY");
        ob3.setDate("09 September 2022");
        ob3.setDesc("Active");
        ob3.setLink("https://maandhan.in/");

        ob3.setImgname(R.drawable.modi2);
        holder.add(ob3);

        Model ob4=new Model();
        ob4.setHeader("એગ્રીકલ્ચર ઈન્ફ્રાસ્ટ્રક્ચર ફંડ | AIF");
        ob4.setDate("19 July 2022");
        ob4.setDesc("Active");
        ob4.setLink("https://agriinfra.dac.gov.in/");

        ob4.setImgname(R.drawable.modi3);
        holder.add(ob4);


        Model ob5=new Model();
        ob5.setHeader("ખેડૂતો માટે ક્રેડિટ સુવિધા | CFF");
        ob5.setDate("25 August 2022");
        ob5.setDesc("Active");
        ob5.setLink("https://www.nabard.org/");

        ob5.setImgname(R.drawable.modi4);
        holder.add(ob5);



        Model ob6=new Model();
        ob6.setHeader("પાક વીમા યોજનાઓ | CIS");
        ob6.setDate("18 July 2022");
        ob6.setDesc("Active");
        ob6.setLink("https://pmfby.gov.in/");

        ob6.setImgname(R.drawable.modi5);
        holder.add(ob6);







        return holder;
    }
}