package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OtherActivity extends AppCompatActivity {
    EditText editText;
    private ArrayList<Map<String, String>> mPeopleList;
    private int permissionCode=1;
    private SimpleAdapter mAdapter;
    private AutoCompleteTextView mTxtPhoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        mTxtPhoneNo = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},permissionCode);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            mPeopleList = new ArrayList<Map<String, String>>();
            PopulatePeopleList();


            mAdapter = new SimpleAdapter(this, mPeopleList, R.layout.custview, new String[]{"Name", "Phone", "Type"}, new int[]{R.id.ccontName, R.id.ccontNo, R.id.ccontType});

            mTxtPhoneNo.setAdapter(mAdapter);
            mTxtPhoneNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Map<String, String> map = (Map<String, String>) adapterView.getItemAtPosition(i);
                    String key = "Phone";
                    String value = (String) map.get(key);
                    mTxtPhoneNo.setText(value);
                }

            });
        }

    }

    public void PopulatePeopleList()
    {

        mPeopleList.clear();

        Cursor people = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (people.moveToNext())
        {

            String contactName = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));

            String contactId = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts._ID));
            String hasPhone = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if ((Integer.parseInt(hasPhone) > 0))
            {

                // You know have the number so now query it like this
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                        null, null);
                while (phones.moveToNext()) {

                    //store numbers and display a dialog letting the user select which.
                    String phoneNumber = phones.getString(
                            phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String ar[]=phoneNumber.split(" ");
                    phoneNumber="";
                    for(int i=0;i<ar.length;i++){
                        phoneNumber+=ar[i];
                    }
                    if((""+phoneNumber.charAt(0)).equals("+")){
                        phoneNumber=phoneNumber.substring(3,phoneNumber.length());
                    }
                    String numberType = phones.getString(phones.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.TYPE));

                    Map<String, String> NamePhoneType = new HashMap<String, String>();

                    NamePhoneType.put("Name", contactName);
                    NamePhoneType.put("Phone", phoneNumber);

                    if(numberType.equals("0"))
                        NamePhoneType.put("Type", "Work");
                    else
                    if(numberType.equals("1"))
                        NamePhoneType.put("Type", "Home");
                    else if(numberType.equals("2"))
                        NamePhoneType.put("Type",  "Mobile");
                    else
                        NamePhoneType.put("Type", "Other");

                    //Then add this map to the list.
                    mPeopleList.add(NamePhoneType);
                }
                phones.close();
            }
        }
        people.close();

        startManagingCursor(people);
    }
    public void Perform(View v){
        if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},permissionCode);
        }
        editText=(EditText)findViewById(R.id.editText);
        String psk=editText.getText().toString();
        String phone=mTxtPhoneNo.getText().toString();
        boolean b=true;
        if(psk.equals("")){
            editText.setError("This Field can't be empty");
            b=false;
        }
        if(b&&phone.equals("")){
            mTxtPhoneNo.setError("This Field can't be empty");
            b=false;
        }
        if(b&&psk.length()!=4){
            editText.setError("Key should be 4 digit");
            b=false;
        }
        if(b) {
            String message="AmP";
            String type=getIntent().getExtras().getString("name");
            message=message+" "+psk+" "+type;
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phone,null,message,null,null);
                Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Permission Denied For SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
