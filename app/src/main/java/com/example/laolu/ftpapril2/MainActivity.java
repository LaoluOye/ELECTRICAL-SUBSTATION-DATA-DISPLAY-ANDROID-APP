
package com.example.laolu.ftpapril2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;

import java.util.ArrayList;


public class MainActivity extends Activity {
    //SET STATIC VARIABLES
    //STATIC  DETAILS SO COULD BE REPLACED BY CONFIGURATION FILE
    private static String SERVER = "ftp.drivehq.com";
    private static String LOGIN = "Olaoluu";
    private static String PASSWORD = "yesdem2def";
    private static String SUBSTATION1FILENAME = "/wwwhome/Uploadedfile.csv";
    private static String SUBSTATION2FILENAME = "/wwwhome/value.csv";

    //SET UI OBJECTS
    // MAIN APPLICATION INTERFACE CONTROLS
    Button firstsubstationButton;
    Button secondsubstationButton;
    EditText edittextStatus;
    GridView gridView;
    ArrayAdapter<String> adapter;

    ArrayList<String> result_arr = new ArrayList<String>();
    //OVERRIDE ON CREATE TO DISPLAY THE USER INTERFACE
    //THIS IS WHERE WE DO ALL THE OPERATIONS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        gridView.setNumColumns(3);
        edittextStatus = (EditText) findViewById(R.id.edittextstatus);



        setFirstsubstationButtonOnClick();
        setSecondsubstationButtonOnClick();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();//toast for grid items(pop up)
            }
        });
    }

    public void setFirstsubstationButtonOnClick(){
        //This will link the view button resource with my Button so I may manipulate it
        firstsubstationButton = (Button) findViewById(R.id.sub1button);
        //I create a button to execute the Onclick event to execute the ftp for substation 1
        firstsubstationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting status before connection
                edittextStatus.setText("Now Displaying UPPER BROOK STREET Electrical Parameters");
                //runs the activity task ftpactions in the background
                //        edittextData.setText("");
                ftpactions task = new ftpactions();
                task.execute(new String[]{SERVER, LOGIN, PASSWORD, SUBSTATION1FILENAME});
            }
        });
    }
    //set button sub2
    public void setSecondsubstationButtonOnClick(){
        secondsubstationButton = (Button) findViewById(R.id.sub2button);
        secondsubstationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextStatus.setText("Now Displaying BRIDGEFORD STREET Electrical Parameters");

                ftpactions task = new ftpactions();
                task.execute(new String[] {SERVER,LOGIN,PASSWORD,SUBSTATION2FILENAME});
            }
        });

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //download substation1 data
    private class ftpactions extends AsyncTask<String, Void, ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(String[] arguments){

            FTPClient ftpclient = new FTPClient();

                try{
                    ReadFromFTP ftpreader = new ReadFromFTP (arguments[0],arguments[1],arguments[2] );
                    String fileContent= ftpreader.ReadFileFromFTP(arguments[3]);

                    CSVManager csvmanager = new CSVManager();
                    result_arr =csvmanager.GetArrayListFromCSV(fileContent);



                }
                catch (Exception e){
                    //response= "Error connecting";
                }

                return result_arr;
            }
        @Override
        protected void onPostExecute(ArrayList<String> result){
            super.onPostExecute(result);
            //ARRAY ADAPTER
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, result);// apter tells gridview how to display data- simple list for sstrings
            gridView.setAdapter(adapter);

        }


    }

}

