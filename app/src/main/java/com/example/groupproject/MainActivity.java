package com.example.groupproject;

/*
!!!!
This is our old Sign in page before we upgraded to the SQLHelper & UserDatabase classes
No longer in use
See Registration2 Activity
!!!!
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends AppCompatActivity {

    //define variables

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    EditText firstName;
    EditText lastName;
    EditText college;
    EditText email;
    EditText password;
    EditText rePassword;
    EditText classyear;
    EditText mayor;
    EditText phone;
    RadioGroup question;
    RadioButton tutorbutton;
    RadioButton tuteebutton;
    RadioButton selectedRadioButton;
    Button register;
    Button signin;
    DB db;
    String radiovalue;
    Integer selectedId;

  /* public void onCheckChanged(RadioGroup group, int checkedId ) {
        tutorbutton = (RadioButton)findViewById(checkedId);
        tuteebutton = (RadioButton)findViewById(checkedId);
        if (tutorbutton != null) {
            radiovalue = "Tutor";
        }
        if (tuteebutton != null) {
            radiovalue = "Tutee";
        }
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        college = (EditText) findViewById(R.id.college);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.repassword);
        classyear = (EditText) findViewById(R.id.classyear);
        mayor = (EditText) findViewById(R.id.mayor);
        phone = (EditText) findViewById(R.id.phone);
        question = (RadioGroup) findViewById(R.id.RadioGroup1);
        tutorbutton = (RadioButton) findViewById(R.id.tutor);
        tuteebutton = (RadioButton) findViewById(R.id.tutee);
        register = (Button) findViewById(R.id.signup);
        signin = (Button) findViewById(R.id.signin);
        db = new DB(this);


        //listeners for buttons

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = firstName.getText().toString();
                String lname = lastName.getText().toString();
                String coll = college.getText().toString();
                String user = email.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();
                String year = classyear.getText().toString();
                String m = mayor.getText().toString();
                String phoneNum = phone.getText().toString();

                //get selected radio button from radioGroup
                selectedId = question.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    selectedRadioButton = (RadioButton) findViewById(selectedId);
                    radiovalue = selectedRadioButton.getText().toString();
                }


                //if any of the editText boxes empty will produce a toast message
                if (fname.equals("") || lname.equals("") || coll.equals("") || user.equals("") || phoneNum.equals("") || pass.equals("") || repass.equals("") || year.equals("") || m.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter information in all fields", Toast.LENGTH_SHORT).show();

                else {
                    if (pass.equals(repass)) {
                        Log.d(LOG_TAG, "Password matches");
                        Boolean checkemail = db.checkEmail(user);
                        Log.d(LOG_TAG, "Checkemail: " + checkemail);

                        //if user does not exist
                        if (!checkemail) {
                            Log.d(LOG_TAG, "Email doesn't exist, adding to DB");
                            //onCheckChanged(question, 1);
                            Log.d(LOG_TAG, "Radio value: " + radiovalue);
                            Boolean insert = db.insertData(fname, lname, coll, user, pass, year, m, phoneNum, radiovalue);
                            Log.d(LOG_TAG, "Inserted into DB?: " + insert);

                            if (insert) {
                                Toast.makeText(MainActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                // Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                // startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });

        signin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);

            }

        });
    }


}