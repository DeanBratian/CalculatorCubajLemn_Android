package com.Cubaj.CubajLemnRotund;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Locale;

/*
Naming Convention:

Funtion: FunctieBuna()
LocalVar: localNumeVar_tip
GlobalVar: globalNumeVar_tip
Const: const_NumeConst_tip

 */

public class InserareDateActivity extends AppCompatActivity{

    public static final String constLabelBucata_String = "bucata";
    public static final String constKeyToSaveInSP_String = "CubajLemn";

    // buton schimbare activity
    private Button globalBTVizualizare_Button;

    // calculare volum
    private EditText globalETDiametru_EditText;
    private EditText globalETLungime_EdiText;
    private TextView globalTVRezultatVolum_TextView;
    private Button globalBTAdauga_Button;
    private Spinner globalSPSpecie_Spinner;
    private Spinner globalSPContainer_Spinner;
    private Spinner globalSPCount_Spinner;
    private androidx.appcompat.widget.Toolbar globalTBToolbar_TB;


    //valori de trimis in activity de vizualizare
    private String globalLungimedeTrimis_String;
    private String globalDiametrudeTrimis_String;
    private String globalSpeciedeTrimis_String;
    private String globalContainerdeTrimis_String;
    private String globalVolumdeTrimis_String;
    private String globalIddeTrimis_String;

    //initial doar 7 specii introduse, index incepe de la 0
    static private int globalSizeofSpeciiSpinner_Int = 6;

    //initial doar 6 valor introduse, index incepe de la 0
    static private int globalSizeofSpinnerCount_Int = 5;

    //AL-uri spinnere si AL pentru bucati introduse
    private ArrayList<String> globalarrayListSpinnerSpecie_AL = new ArrayList<>();
    private ArrayList<String> globalarrayListSpinnerContainer_AL = new ArrayList<>();
    private ArrayList<String> globalarrayListSpinnerCount_AL = new ArrayList<>();

    //arraylist bucati
    private ArrayList<Bucata> globalarrayEntryBucati_AL = new ArrayList<>();

    //AA pentru spinnere
    private ArrayAdapter<String> globaladapterSPSpecie_AA;
    private ArrayAdapter<String> globaladapterSPContainer_AA;
    private ArrayAdapter<String> globaladapterSPCount_AA;

    // number of entries in arraylist Bucata[]
    static private int globalSizeArrayEntryBucati_Int = 0;
    // add bucata 1 at the beggining
    static private int globalIdBucata_Int = 1;

    // linking toolbar layout to Activity inserare date
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inserare_layout, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        //sterge din SP si scrie din nou
        deletefromSharedPref();
        saveDatainSharedPref();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserare_date);

        //populare Spinnere
        globalarrayListSpinnerSpecie_AL.add("Brad");
        globalarrayListSpinnerSpecie_AL.add("Fag");
        globalarrayListSpinnerSpecie_AL.add("Paltin");
        globalarrayListSpinnerSpecie_AL.add("Plop");
        globalarrayListSpinnerSpecie_AL.add("Stejar");
        globalarrayListSpinnerSpecie_AL.add("Molid");
        globalarrayListSpinnerSpecie_AL.add("Pin");

        globalarrayListSpinnerContainer_AL.add("Fata");
        globalarrayListSpinnerContainer_AL.add("Spate");

        globalarrayListSpinnerCount_AL.add("X1");
        globalarrayListSpinnerCount_AL.add("X2");
        globalarrayListSpinnerCount_AL.add("X3");
        globalarrayListSpinnerCount_AL.add("X4");
        globalarrayListSpinnerCount_AL.add("X5");
        globalarrayListSpinnerCount_AL.add("X10");


        //controale
        globalETDiametru_EditText= findViewById(R.id.editTextDiametru);
        globalETLungime_EdiText = findViewById(R.id.editTextLungime);
        globalTVRezultatVolum_TextView = findViewById(R.id.textViewVolumBucata);

        //butoane
        globalBTVizualizare_Button = findViewById(R.id.buttonVizualizare);
        globalBTAdauga_Button = findViewById(R.id.buttonAdauga);

        //spinnere
        globalSPContainer_Spinner = findViewById(R.id.spinnercontainer);
        globalSPSpecie_Spinner = findViewById(R.id.spinnerspecie);
        globalSPCount_Spinner = findViewById(R.id.spinnercount);

        //toolbar
        globalTBToolbar_TB = findViewById(R.id.Toolbar);
        setSupportActionBar(globalTBToolbar_TB);


        //populare spinner specie
        globaladapterSPSpecie_AA = new ArrayAdapter<>(this,  R.layout.layout_item_spinner, globalarrayListSpinnerSpecie_AL);
        globaladapterSPSpecie_AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        globalSPSpecie_Spinner.setAdapter(globaladapterSPSpecie_AA);
        globalSPSpecie_Spinner.setPrompt("Alege specia");
        globalSPSpecie_Spinner.setSelection(0);

        //populare spiner container
        globaladapterSPContainer_AA = new ArrayAdapter<>(this, R.layout.layout_item_spinner, globalarrayListSpinnerContainer_AL);
        globaladapterSPContainer_AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        globalSPContainer_Spinner.setAdapter(globaladapterSPContainer_AA);
        globalSPContainer_Spinner.setPrompt("Alege loc incarcare");
        globalSPContainer_Spinner.setSelection(0);

        //populare spinner count
        globaladapterSPCount_AA = new ArrayAdapter<>(this, R.layout.layout_item_spinner, globalarrayListSpinnerCount_AL);
        globaladapterSPCount_AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        globalSPCount_Spinner.setAdapter(globaladapterSPCount_AA);
        globalSPCount_Spinner.setPrompt("Alege nr. bucati");
        globalSPCount_Spinner.setSelection(0);


        // adauga listener
        //globalSPSpecie_Spinner.setOnItemSelectedListener(globalListenerSPSPecie_LST);
        //globalSPCount_Spinner.setOnItemSelectedListener(globalListenerSPCount_LST);

        //adauga watchers
        globalETDiametru_EditText.addTextChangedListener(globalTWWatcherDiametru_TW);
        globalETLungime_EdiText.addTextChangedListener(globalTWWatcherLungime_TW);

        globalETLungime_EdiText.addTextChangedListener(globalTWValoriValide_TW);
        globalETDiametru_EditText.addTextChangedListener(globalTWValoriValide_TW);

        loadDatafromSharedPref();

        //handle butoane
        HandlebutonAdauga();
        HandlebutonVizualizare();

    }

    //watcher folosit ca sa dezacivez butonul de adaugare daca volumul este 0 sau nu sunt date introduse
    private TextWatcher globalTWValoriValide_TW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String localValLungime_String = globalETLungime_EdiText.getText().toString().trim();
            String localValDiametru_String = globalETDiametru_EditText.getText().toString().trim();

            String localVolumbucata_String = CalculateVolume();
            double localValoareVolum_Double = Double.parseDouble(localVolumbucata_String);


            if( (localValLungime_String.isEmpty() && localValDiametru_String.isEmpty())  ||  (localValDiametru_String.equals("0") && localValLungime_String.equals("0")) )
            {
                globalBTAdauga_Button.setBackgroundColor(Color.parseColor("#FF5C615C"));
                globalBTAdauga_Button.setEnabled(false);
            }

            else if ( localValLungime_String.isEmpty() || localValLungime_String.equals("0"))
            {
                globalBTAdauga_Button.setBackgroundColor(Color.parseColor("#FF5C615C"));
                globalBTAdauga_Button.setEnabled(false);
            }

            else if ( localValDiametru_String.isEmpty() || localValDiametru_String.equals("0"))
            {
                globalBTAdauga_Button.setBackgroundColor(Color.parseColor("#FF5C615C"));
                globalBTAdauga_Button.setEnabled(false);
            }
            else if ( localValoareVolum_Double !=0)
            {
                globalBTAdauga_Button.setBackgroundColor(Color.parseColor("#00C853"));
                globalBTAdauga_Button.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // watcher folosit sa calculez volumul cand se introduce o valoare in Diametru
    private TextWatcher globalTWWatcherDiametru_TW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            globalTVRezultatVolum_TextView.setText(getString(R.string.textvolumbuc)+ " " + CalculateVolume() + getString(R.string.textmc));

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // watcher folosit sa calculez volumul cand se introduce o valoare in Lungime
    private TextWatcher globalTWWatcherLungime_TW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            globalTVRezultatVolum_TextView.setText(getString(R.string.textvolumbuc)+ " " + CalculateVolume() + getString(R.string.textmc));

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    // schimba activiy catre pagina de vizualizare
    private void HandlebutonVizualizare()
    {
        globalBTVizualizare_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntentTransfer_Intent = new Intent(InserareDateActivity.this, VizualizareDateActivity.class);

                if( (globalarrayEntryBucati_AL !=null && globalarrayEntryBucati_AL.size() != 0) )
                {
                    localIntentTransfer_Intent.putParcelableArrayListExtra(constLabelBucata_String, globalarrayEntryBucati_AL);

                    startActivityForResult(localIntentTransfer_Intent,1);

                }
                else
                {

                    Toast localToastNimicDeAfisat_Toast = Toast.makeText(getApplicationContext(), "Lista nu contine nimic!",
                            Toast.LENGTH_SHORT);

                    localToastNimicDeAfisat_Toast.setGravity(Gravity.TOP , 0, 240);
                    localToastNimicDeAfisat_Toast.show();

                }

            }
        });
    }


    //handling pentru butonul de adaugare
    private void HandlebutonAdauga()
    {

        globalBTAdauga_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //ia date de pe controale
                globalLungimedeTrimis_String = globalETLungime_EdiText.getText().toString().trim();
                globalDiametrudeTrimis_String = globalETDiametru_EditText.getText().toString().trim();
                globalSpeciedeTrimis_String = globalSPSpecie_Spinner.getSelectedItem().toString().trim();
                globalContainerdeTrimis_String = globalSPContainer_Spinner.getSelectedItem().toString().trim();
                globalVolumdeTrimis_String = CalculateVolume();
                globalIddeTrimis_String = Integer.toString(globalIdBucata_Int);

                String localCountBucati_String = globalSPCount_Spinner.getSelectedItem().toString().trim();

                int localnumarCountBucati_int = Integer.parseInt(localCountBucati_String.substring(1));

                Integer local_loopCounter_Int = 0;

                if(localnumarCountBucati_int>1)
                {

                    for(int localcounter_int=0;localcounter_int<localnumarCountBucati_int;localcounter_int++)
                    {
                        Integer local_IdBucataInt = globalIdBucata_Int;

                        Bucata localBucataIntrodusa_Buc = new Bucata(globalLungimedeTrimis_String,globalDiametrudeTrimis_String,globalSpeciedeTrimis_String,globalContainerdeTrimis_String,globalVolumdeTrimis_String,local_IdBucataInt.toString());
                        //stocare in array
                        globalarrayEntryBucati_AL.add(0,localBucataIntrodusa_Buc);

                        //increment size of entries
                        globalSizeArrayEntryBucati_Int += 1;
                        globalIdBucata_Int = globalSizeArrayEntryBucati_Int + 1;

                        local_loopCounter_Int +=1;
                    }

                    //afisare toast
                    Toast localToastCounterBucati_Toast = Toast.makeText(getApplicationContext(),  local_loopCounter_Int.toString() +  " bucati adaugate cu succes!",
                            Toast.LENGTH_SHORT);

                    localToastCounterBucati_Toast.setGravity(Gravity.TOP , 0, 240);
                    localToastCounterBucati_Toast.show();

                    globalETDiametru_EditText.setText("");
                    globalETLungime_EdiText.setText("");


                }

                else
                    {

                //instantiere bucata noua
                Bucata localBucataIntrodusa_Buc = new Bucata(globalLungimedeTrimis_String,globalDiametrudeTrimis_String,globalSpeciedeTrimis_String,globalContainerdeTrimis_String,globalVolumdeTrimis_String,globalIddeTrimis_String);

                //stocare in array
                globalarrayEntryBucati_AL.add(0,localBucataIntrodusa_Buc);

                //afisare toast
                Toast localToastCounterBucati_Toast = Toast.makeText(getApplicationContext(), "Bucata "+ globalIddeTrimis_String +  " adaugata cu succes!",
                        Toast.LENGTH_SHORT);

                localToastCounterBucati_Toast.setGravity(Gravity.TOP , 0, 240);
                localToastCounterBucati_Toast.show();

                //increment size of entries
                globalSizeArrayEntryBucati_Int += 1;
                globalIdBucata_Int = globalSizeArrayEntryBucati_Int + 1;

                globalETDiametru_EditText.setText("");
                globalETLungime_EdiText.setText("");

                    }

            }
        });
    }


    private void saveDatainSharedPref()
    {
        SharedPreferences localsharedPreferences_SP = getSharedPreferences(constKeyToSaveInSP_String, MODE_PRIVATE);
        SharedPreferences.Editor localEditor_Editor = localsharedPreferences_SP.edit();

        Gson localGson_GS = new Gson();

        String localJson_String = localGson_GS.toJson(globalarrayEntryBucati_AL);
        localEditor_Editor.putString(constLabelBucata_String, localJson_String);
        localEditor_Editor.apply();
    }


    private void loadDatafromSharedPref()
    {
        SharedPreferences localsharedPreferences_SP = getSharedPreferences(constKeyToSaveInSP_String, MODE_PRIVATE);
        Gson localGson_GS = new Gson();
        String localJson_String = localsharedPreferences_SP.getString(constLabelBucata_String,null);

        Type localtype_Type = new TypeToken<ArrayList<Bucata>>() {}.getType();

        globalarrayEntryBucati_AL = localGson_GS.fromJson(localJson_String,localtype_Type);

        if(globalarrayEntryBucati_AL == null )
        {
            globalarrayEntryBucati_AL = new ArrayList<>();
            globalSizeArrayEntryBucati_Int = 0;
            globalIdBucata_Int = globalSizeArrayEntryBucati_Int+1;

        }
        else
        {
            globalSizeArrayEntryBucati_Int = globalarrayEntryBucati_AL.size();
            globalIdBucata_Int = globalSizeArrayEntryBucati_Int+1;

        }

    }

    private void deletefromSharedPref()
    {
        SharedPreferences localsharedPreferences_SP = getSharedPreferences(constKeyToSaveInSP_String, MODE_PRIVATE);
        localsharedPreferences_SP.edit().clear().apply();

    }

    //functie care calculeaza volum
    private String CalculateVolume()
    {

        double localDiametru_Double;
        double localLungime_Double;
        double localVolum_Double;
        double localRaza_Double;
        double localDiametruinMetri_Double;
        String localretVal_String;

        //citire valori din ETs
        if(!globalETDiametru_EditText.getText().toString().trim().equals("") && globalETDiametru_EditText.getText().length() > 0) {
            localDiametru_Double = Double.parseDouble(globalETDiametru_EditText.getText().toString());
        } else {
            localDiametru_Double = 0;
        }
        if(!globalETLungime_EdiText.getText().toString().trim().equals("") && globalETLungime_EdiText.getText().length() > 0) {
            localLungime_Double = Double.parseDouble(globalETLungime_EdiText.getText().toString());
        } else {
            localLungime_Double = 0;
        }

        //calculeaza volum
        localDiametruinMetri_Double = (localDiametru_Double / 100);
        localRaza_Double = localDiametruinMetri_Double/2;
        localVolum_Double = ( round(Math.PI,2)* localRaza_Double * localRaza_Double  * localLungime_Double );

        //round
        localVolum_Double = round(localVolum_Double,2);

        localretVal_String = String.format(Locale.ENGLISH, "%.2f", localVolum_Double );

        return localretVal_String;
    }

    private AdapterView.OnItemSelectedListener globalListenerSPSPecie_LST = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if (position==globalSizeofSpeciiSpinner_Int) {
                // Set an EditText view to get user input
                final EditText localUserinput_ET = new EditText(InserareDateActivity.this);

                new AlertDialog.Builder(InserareDateActivity.this, R.style.AlertDialogCustom)
                        .setMessage("Introduceti noua specie:")
                        .setView(localUserinput_ET)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable localeditable_Editable = localUserinput_ET.getText();
                                String localUserResponse_String = localeditable_Editable.toString();

                                if(!localUserResponse_String.isEmpty() && localUserResponse_String.length() <= 9)
                                {
                                    globalarrayListSpinnerSpecie_AL.add(0, localUserResponse_String);

                                    globalSizeofSpeciiSpinner_Int = globalarrayListSpinnerSpecie_AL.size() - 1;
                                    globaladapterSPSpecie_AA.notifyDataSetChanged();

                                    globalSPSpecie_Spinner.setSelection(0);

                                    Toast ToastSpecieNoua_Toast = Toast.makeText(getApplicationContext(), "Specie adaugata cu succes!",
                                            Toast.LENGTH_SHORT);

                                    ToastSpecieNoua_Toast.setGravity(Gravity.TOP , 0, 240);
                                    ToastSpecieNoua_Toast.show();
                                }
                                else {
                                    globalSPSpecie_Spinner.setSelection(0);
                                    Toast ToastSpecieNouaFail_Toast = Toast.makeText(getApplicationContext(), "Nu ai introdus corect!",
                                            Toast.LENGTH_SHORT);

                                    ToastSpecieNouaFail_Toast.setGravity(Gravity.TOP , 0, 240);
                                    ToastSpecieNouaFail_Toast.show();

                                }
                            }
                        })
                        .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                globalSPSpecie_Spinner.setSelection(0);
                            }
                        }).show();
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.DeleteSP_Ins:

                new AlertDialog.Builder(InserareDateActivity.this, R.style.AlertDialogCustom)
                        .setMessage("Stergi date salvate automat?")
                        .setCancelable(false)
                        .setPositiveButton("Sterge", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                deletefromSharedPref();

                                Toast ToastStergereDateSP_Toast = Toast.makeText(getApplicationContext(), "Datele salvate automat au fost sterse!",
                                        Toast.LENGTH_SHORT);

                                ToastStergereDateSP_Toast.setGravity(Gravity.TOP , 0, 240);
                                ToastStergereDateSP_Toast.show();

                            }
                        })
                        .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                            }
                        }).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == 1)
            {
                if(resultCode == RESULT_OK)
                {

                    if(data == null)
                    {
                        globalarrayEntryBucati_AL = new ArrayList<Bucata>();
                        globalSizeArrayEntryBucati_Int = 0;
                        globalIdBucata_Int = globalSizeArrayEntryBucati_Int+1;

                    }
                    else
                    {
                        globalarrayEntryBucati_AL = data.getParcelableArrayListExtra(constLabelBucata_String);

                        assert globalarrayEntryBucati_AL != null;
                        globalSizeArrayEntryBucati_Int = globalarrayEntryBucati_AL.size();

                        globalIdBucata_Int = globalSizeArrayEntryBucati_Int+1;


                    }


                }

            }

    }


    private static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal localbd_BD = BigDecimal.valueOf(value);
        localbd_BD = localbd_BD.setScale(places, RoundingMode.HALF_UP);
        return localbd_BD.doubleValue();
    }


    private AdapterView.OnItemSelectedListener globalListenerSPCount_LST = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if (position== globalSizeofSpinnerCount_Int) {
                // Set an EditText view to get user input
                final EditText localUserinput_ET = new EditText(InserareDateActivity.this);
                localUserinput_ET.setInputType(InputType.TYPE_CLASS_NUMBER);
                new AlertDialog.Builder(InserareDateActivity.this, R.style.AlertDialogCustom)
                        .setMessage("Introduceti noua valoare:")
                        .setView(localUserinput_ET)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable localeditable_Editable = localUserinput_ET.getText();
                                String localUserResponse_String = localeditable_Editable.toString();

                                if(!localUserResponse_String.isEmpty() && !localUserResponse_String.equals("0"))
                                {

                                    String localValueX_String = "X";
                                    String localValueFinala_String = localValueX_String+localUserResponse_String;

                                    globalarrayListSpinnerCount_AL.add(0, localValueFinala_String);

                                    globalSizeofSpinnerCount_Int = globalarrayListSpinnerCount_AL.size() - 1;
                                    globaladapterSPCount_AA.notifyDataSetChanged();

                                    globalSPCount_Spinner.setSelection(0);

                                    Toast ToastCountNou_Toast = Toast.makeText(getApplicationContext(), "Numar adaugat cu succes!",
                                            Toast.LENGTH_SHORT);

                                    ToastCountNou_Toast.setGravity(Gravity.TOP , 0, 240);
                                    ToastCountNou_Toast.show();
                                }
                                else {
                                    globalSPCount_Spinner.setSelection(0);
                                    Toast ToastCountGresitsauzero_Toast = Toast.makeText(getApplicationContext(), " Nu ai introdus corect numarul!",
                                            Toast.LENGTH_SHORT);

                                    ToastCountGresitsauzero_Toast.setGravity(Gravity.TOP , 0, 240);
                                    ToastCountGresitsauzero_Toast.show();

                                }
                            }
                        })
                        .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                globalSPCount_Spinner.setSelection(0);
                            }
                        }).show();
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };




}

