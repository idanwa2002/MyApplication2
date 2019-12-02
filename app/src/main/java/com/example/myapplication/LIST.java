package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.myapplication.FBref.myRef;

public class LIST extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText tvup;
    ListView lv;
    ArrayList<String> stringList= new ArrayList<String>();
    ArrayAdapter<String> adp;
    String nd;

    // FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("message");
    //  ValueEventListener strListener;

    AlertDialog.Builder ad;
    LinearLayout dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tvup=(EditText)findViewById(R.id.etnewdata);
        lv=(ListView) findViewById(R.id.listVieID);


        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,stringList);
        lv.setAdapter(adp);
    }
    public void updateData(View view) {
        nd=tvup.getText().toString();
        // rtdb step 4:
        myRef.child(nd).setValue(nd);
        Toast.makeText(this, "Writing succeeded", Toast.LENGTH_SHORT).show();
        tvup.setText("");
        ValueEventListener mrListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                stringList.clear();
                for (DataSnapshot data : ds.getChildren()){
                    String tmp=data.getValue(String.class);
                    stringList.add(tmp);
                }
                adp = new ArrayAdapter<String>(LIST.this,R.layout.support_simple_spinner_dropdown_item, stringList);
                lv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(mrListener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialogx, null);
        ad = new AlertDialog.Builder(this);
        ad.setCancelable(false);
        ad.setTitle("Confirm deleting value from Firebase");
        ad.setView(dialog);
        ad.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String str= stringList.get(position);
                myRef.child(str).removeValue();
                Toast.makeText(LIST.this, "Deleting succeeded", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        ad.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog adb = ad.create();
        adb.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String st=item.getTitle().toString();
        if(st.equals("register")){
            Intent a=new Intent(this, MainActivity.class);
            startActivity(a);
        }
        if (st.equals("gallery")){
            Intent a=new Intent(this, GALLERY.class);
            startActivity(a);
        }
        return super.onOptionsItemSelected(item);
    }


}
