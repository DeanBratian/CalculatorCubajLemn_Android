package com.Cubaj.CubajLemnRotund;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;



/*
Naming Convention:

Funtion: FunctieBuna()
LocalVar: localNumeVar_tip
GlobalVar: globalNumeVar_tip
Const: const_NumeConst_tip

 */

public class VizualizareDateActivity extends AppCompatActivity {

    //constante
    private static final String constLabelBucata_String = "bucata";
    private static final String constLabelBucataOrdonata_String = "bucataordonata";
    private static final String constKeyToSaveInSP_String = "CubajLemn";

    private static final String consttotalFata_String = "totalbucfata";
    private static final String consttotalSpate_String = "totalbucspate";

    private static final String consttotalVolFata_String = "totalVolfata";
    private static final String consttotalVolSpate_String = "totalVolspate";

    //controale
    private TextView globalTVTotalFata_TextView;
    private TextView globalTVTotalSpate_TextView;
    private ListView globalLVObiecte_ListView;
    private androidx.appcompat.widget.Toolbar globalTBToolbar_TB;

    //array si array adapter
    private static ArrayList<Bucata> globalBucatiPrimite_AL = new ArrayList<>();
    private BucataListAdapter globalAdaptorBucatiPrimite_BLA;

    private ArrayList<BucataOrdonata> globalBucatiOrdonateFataDeTrimis_AL = new ArrayList<>();
    private ArrayList<BucataOrdonata> globalBucatiOrdonateSpateDeTrimis_AL = new ArrayList<>();
    private ArrayList<BucataOrdonata> globalBucatiOrdonateTotalDeTrimis_AL = new ArrayList<>();

    private String globaltotalBucSpate_String = "";
    private String globaltotalBucFata_String= "";

    private String globaltotalVolumFata_String = "";
    private String globaltotalVolumSpate_String = "";


    @Override
    protected void onPause() {
        super.onPause();
        deletefromSharedPref();
        saveDatainSharedPref();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vizualizare_layout, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_date);

        //clear sent ALs
        globalBucatiOrdonateFataDeTrimis_AL.clear();
        globalBucatiOrdonateSpateDeTrimis_AL.clear();
        globalBucatiOrdonateTotalDeTrimis_AL.clear();

        //globalBucatiPrimite_AL.clear();

        Intent localIntent_Intent = getIntent();
        globalBucatiPrimite_AL = localIntent_Intent.getParcelableArrayListExtra(constLabelBucata_String);

        assert globalBucatiPrimite_AL != null;
        globalAdaptorBucatiPrimite_BLA = new BucataListAdapter(this,R.layout.adapter_list_layout_bucata, globalBucatiPrimite_AL);

        //gasire controale
        globalLVObiecte_ListView = findViewById(R.id.listviewbucatiordonate);
        globalTVTotalFata_TextView = findViewById(R.id.textViewTotalFata);
        globalTVTotalSpate_TextView = findViewById(R.id.textViewTotalSpate);

        //setare custom toolbar din layout
        globalTBToolbar_TB = findViewById(R.id.Toolbar);
        setSupportActionBar(globalTBToolbar_TB);

        //adaptor si listener pentru LV
        globalLVObiecte_ListView.setAdapter(globalAdaptorBucatiPrimite_BLA);
        globalLVObiecte_ListView.setOnItemLongClickListener(globalOnClickLungLista_LST);

        //calculare total
        CalculareTotalPeContaineresiAfisare(globalBucatiPrimite_AL);

        //handle buton catre pagina de inserare
        HandlebutonInserare();
    }


    // schimba activiy catre pagina de inserare
    private void HandlebutonInserare()
    {
        Button butonInserare = (Button) findViewById(R.id.buttonVizualizare);
        butonInserare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pass result to caller activity
                AddDataToResponseIntentAndSetResult();
                finish();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void CalculareTotalPeContaineresiAfisare(ArrayList<Bucata> arrayList)
    {

        double localTotalFata_Double = 0;
        double localTotalSpate_Double = 0;

        int localBucatiFata_Int = 0;
        int localBucatiSpate_Int = 0;

        String localtotalVSpate_String;
        String localtotalVFata_String;


        for (Bucata buc : arrayList)
        {

            if(buc.getContainer().equals("Fata"))
            {
                localBucatiFata_Int+=1;
                localTotalFata_Double+= Double.parseDouble(buc.getVolum());
            }
            else if(buc.getContainer().equals("Spate"))
            {
                localBucatiSpate_Int+=1;
                localTotalSpate_Double+= Double.parseDouble(buc.getVolum());
            }
            else
            {

            }

        }

        localTotalFata_Double = round(localTotalFata_Double,2);
        localTotalSpate_Double = round(localTotalSpate_Double,2);

        localtotalVFata_String = String.format(Locale.ENGLISH, "%.2f", localTotalFata_Double);
        localtotalVSpate_String = String.format(Locale.ENGLISH, "%.2f", localTotalSpate_Double);

        globaltotalVolumFata_String = localtotalVFata_String;
        globaltotalVolumSpate_String = localtotalVSpate_String;

        globaltotalBucFata_String = Integer.toString(localBucatiFata_Int);
        globaltotalBucSpate_String = Integer.toString(localBucatiSpate_Int);

        globalTVTotalFata_TextView.setText("Fata: " + globaltotalBucFata_String +" buc., Volum: " + localtotalVFata_String + getString(R.string.textmc));
        globalTVTotalSpate_TextView.setText("Spate: " + globaltotalBucSpate_String +" buc., Volum: " + localtotalVSpate_String + getString(R.string.textmc));


    }

    private ListView.OnItemLongClickListener globalOnClickLungLista_LST  = new ListView.OnItemLongClickListener(){

        String localIdToast;
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            new AlertDialog.Builder(VizualizareDateActivity.this, R.style.AlertDialogCustom)
                    .setMessage("Stergi bucata selectata?")
                    .setCancelable(false)
                    .setPositiveButton("Sterge", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Bucata localBuc_Bucata = globalBucatiPrimite_AL.get(position);

                            Toast ToastStergereBucata_Toast = Toast.makeText(getApplicationContext(), "Bucata cu L = " + localBuc_Bucata.getLungime() + " si D = " + localBuc_Bucata.getDiametru() + " a fost stearsa!",
                                    Toast.LENGTH_SHORT);

                            ToastStergereBucata_Toast.setGravity(Gravity.TOP , 0, 240);
                            ToastStergereBucata_Toast.show();

                            globalBucatiPrimite_AL.remove(position);

                            ReasignIdAfterDelete();

                            globalAdaptorBucatiPrimite_BLA.notifyDataSetChanged();

                            CalculareTotalPeContaineresiAfisare(globalBucatiPrimite_AL);

                            //pass result to caller activity
                            AddDataToResponseIntentAndSetResult();


                        }
                    })
                    .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {


                        }
                    }).show();

            return false;
        }
    };


    private void saveDatainSharedPref()
    {
        SharedPreferences localsharedPreferences_SP = getSharedPreferences(constKeyToSaveInSP_String, MODE_PRIVATE);
        SharedPreferences.Editor localEditor_Editor = localsharedPreferences_SP.edit();

        Gson localGson_GS = new Gson();

        String localJson_String = localGson_GS.toJson(globalBucatiPrimite_AL);
        localEditor_Editor.putString(constLabelBucata_String, localJson_String);
        localEditor_Editor.apply();
    }


    private void deletefromSharedPref()
    {
        SharedPreferences localsharedPreferences_SP = getSharedPreferences(constKeyToSaveInSP_String, MODE_PRIVATE);
        localsharedPreferences_SP.edit().clear().apply();
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.minusD_Viz:

                final double[] DiametruNou = {0};

                if(globalBucatiPrimite_AL.size() > 0 )
                {
                final EditText localUserinput_ET = new EditText(VizualizareDateActivity.this);
                localUserinput_ET.setInputType(InputType.TYPE_CLASS_NUMBER);

                new AlertDialog.Builder(VizualizareDateActivity.this, R.style.AlertDialogCustom)
                        .setMessage("Introduceti dimensiune de scazut din Diametru (toate bucatile):")
                        .setView(localUserinput_ET)
                        .setCancelable(false)
                        .setPositiveButton("Scade", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable localeditable_Editable = localUserinput_ET.getText();
                                String localUserResponse_String = localeditable_Editable.toString();

                                if(!localUserResponse_String.isEmpty() && globalBucatiPrimite_AL.size() > 0 && !localUserResponse_String.equals("0"))
                                {
                                    DiametruNou[0] = Double.parseDouble(localUserResponse_String);

                                    if(CanRemoveFromDiameters(DiametruNou[0])) {
                                        RemoveFromDiametersAndRecalculateVolume(DiametruNou[0]);

                                        AddDataToResponseIntentAndSetResult();

                                        Toast ToastDiametruMinus_Toast = Toast.makeText(getApplicationContext(), "Diametre scazute cu " + localUserResponse_String + " cm! ",
                                                Toast.LENGTH_SHORT);

                                        ToastDiametruMinus_Toast.setGravity(Gravity.TOP, 0, 240);
                                        ToastDiametruMinus_Toast.show();
                                    }

                                    else
                                    {
                                                Toast ToastDiametruMinusFail_Toast = Toast.makeText(getApplicationContext(), "Diametru rezultat mai mic ca zero!",
                                                Toast.LENGTH_SHORT);

                                        ToastDiametruMinusFail_Toast.setGravity(Gravity.TOP , 0, 240);
                                        ToastDiametruMinusFail_Toast.show();

                                    }

                                }
                                else {
                                    Toast ToastDiametruMinusFail_Toast = Toast.makeText(getApplicationContext(), "Nu poti scadea diametrele!",
                                            Toast.LENGTH_SHORT);

                                    ToastDiametruMinusFail_Toast.setGravity(Gravity.TOP , 0, 240);
                                    ToastDiametruMinusFail_Toast.show();

                                }

                            }
                        })
                        .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();

                }
                else {

                    Toast ToastListaGoala_Toast = Toast.makeText(getApplicationContext(), "Nu poti scadea diametrele!",
                            Toast.LENGTH_SHORT);

                    ToastListaGoala_Toast.setGravity(Gravity.TOP , 0, 240);
                    ToastListaGoala_Toast.show();

                }

                return true;


            case R.id.Sterge_Viz:

                new AlertDialog.Builder(VizualizareDateActivity.this, R.style.AlertDialogCustom)
                        .setMessage("Stergi toata lista?")
                        .setCancelable(false)
                        .setPositiveButton("Sterge", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if((globalBucatiPrimite_AL !=null && globalBucatiPrimite_AL.size() > 0))
                                {

                                    DeleteWholeList();

                                    AddDataToResponseIntentAndSetResult();

                                    Toast ToastListaGoala_Toast = Toast.makeText(getApplicationContext(), "Lista stersa!",
                                            Toast.LENGTH_SHORT);

                                    ToastListaGoala_Toast.setGravity(Gravity.TOP , 0, 240);
                                    ToastListaGoala_Toast.show();
                                }
                                else {

                                    Toast ToastListaGoala_Toast = Toast.makeText(getApplicationContext(), "Nu poti sterge lista!",
                                            Toast.LENGTH_SHORT);

                                    ToastListaGoala_Toast.setGravity(Gravity.TOP , 0, 240);
                                    ToastListaGoala_Toast.show();

                                }
                            }
                        })
                        .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();

                return true;

            case R.id.VizRaport_Viz:


                Intent localIntentTransfer_Intent = new Intent(VizualizareDateActivity.this, VizualizareRaportActivity.class);

                if( (globalBucatiPrimite_AL !=null && globalBucatiPrimite_AL.size() > 0)  )
                {

                    GenerateReportItems();

                    localIntentTransfer_Intent.putParcelableArrayListExtra(constLabelBucataOrdonata_String, globalBucatiOrdonateTotalDeTrimis_AL );

                    localIntentTransfer_Intent.putExtra(consttotalFata_String,globaltotalBucFata_String);
                    localIntentTransfer_Intent.putExtra(consttotalSpate_String,globaltotalBucSpate_String);

                    localIntentTransfer_Intent.putExtra(consttotalVolFata_String,globaltotalVolumFata_String);
                    localIntentTransfer_Intent.putExtra(consttotalVolSpate_String,globaltotalVolumSpate_String);

                    startActivityForResult(localIntentTransfer_Intent,1);


                }
                else
                {

                    Toast localToastNimicDeAfisat_Toast = Toast.makeText(getApplicationContext(), " Raport: Nimic de afisat!",
                            Toast.LENGTH_SHORT);

                    localToastNimicDeAfisat_Toast.setGravity(Gravity.TOP , 0, 240);
                    localToastNimicDeAfisat_Toast.show();

                }

                return true;

            case R.id.plusD_Viz:

                final double[] DiametruNouPlus = {0};

                if(globalBucatiPrimite_AL.size() > 0 )
                {
                    final EditText localUserinput_ET = new EditText(VizualizareDateActivity.this);
                    localUserinput_ET.setInputType(InputType.TYPE_CLASS_NUMBER);

                    new AlertDialog.Builder(VizualizareDateActivity.this, R.style.AlertDialogCustom)
                            .setMessage("Introduceti dimensiune de adaugat la Diametru (toate bucatile):")
                            .setView(localUserinput_ET)
                            .setCancelable(false)
                            .setPositiveButton("Adauga", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Editable localeditable_Editable = localUserinput_ET.getText();
                                    String localUserResponse_String = localeditable_Editable.toString();

                                    if(!localUserResponse_String.isEmpty() && globalBucatiPrimite_AL.size() > 0 && !localUserResponse_String.equals("0"))
                                    {
                                        DiametruNouPlus[0] = Double.parseDouble(localUserResponse_String);

                                        AddToDiameterAndRecalculateVolume(DiametruNouPlus[0]);

                                        AddDataToResponseIntentAndSetResult();

                                        Toast ToastDiametruPlus_Toast = Toast.makeText(getApplicationContext(), "Diametre crescute cu " + localUserResponse_String + " cm! ",
                                                Toast.LENGTH_SHORT);

                                        ToastDiametruPlus_Toast.setGravity(Gravity.TOP , 0, 240);
                                        ToastDiametruPlus_Toast.show();


                                    }
                                    else {
                                        Toast ToastDiametruPlusFail_Toast = Toast.makeText(getApplicationContext(), "Valoare necorespunzatoare!",
                                                Toast.LENGTH_SHORT);

                                        ToastDiametruPlusFail_Toast.setGravity(Gravity.TOP , 0, 240);
                                        ToastDiametruPlusFail_Toast.show();

                                    }

                                }
                            })
                            .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();

                }
                else {

                    Toast ToastListaGoala_Toast = Toast.makeText(getApplicationContext(), "Nu poti creste diametrele!",
                            Toast.LENGTH_SHORT);

                    ToastListaGoala_Toast.setGravity(Gravity.TOP , 0, 240);
                    ToastListaGoala_Toast.show();

                }
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    //refactor
    private void RemoveFromDiametersAndRecalculateVolume(double dimensiuneDeScazut)
    {

        double localDiametru_Double = 0;
        double localLungime_Double = 0;
        double localVolum_Double = 0;
        double localRaza_Double = 0;
        double localDiametruinMetri_Double = 0;

        String localVolumNouBucata_String;

        double localDiametruNouBuc_Double = 0;


        for (Bucata buc : globalBucatiPrimite_AL)
        {
            localDiametruNouBuc_Double = Double.parseDouble(buc.getDiametru()) - dimensiuneDeScazut;

            if(localDiametruNouBuc_Double > 0)
            {
                buc.setDiametru(Integer.toString( (int) localDiametruNouBuc_Double));

                localLungime_Double = Double.parseDouble(buc.getLungime());

                localDiametru_Double = localDiametruNouBuc_Double;
                localDiametruinMetri_Double = (localDiametru_Double / 100);
                localRaza_Double = localDiametruinMetri_Double/2;

                localVolum_Double = ( round(Math.PI,2) * localRaza_Double * localRaza_Double  * localLungime_Double );

                localVolum_Double = round(localVolum_Double,2);

                localVolumNouBucata_String = String.format(Locale.ENGLISH, "%.2f", localVolum_Double);

                buc.setVolum(localVolumNouBucata_String);
            }

        }

        globalAdaptorBucatiPrimite_BLA.notifyDataSetChanged();

        CalculareTotalPeContaineresiAfisare(globalBucatiPrimite_AL);

    }

    private void DeleteWholeList()
    {
        globalBucatiPrimite_AL.clear();
        globalAdaptorBucatiPrimite_BLA.notifyDataSetChanged();

        CalculareTotalPeContaineresiAfisare(globalBucatiPrimite_AL);

    }

    private void AddDataToResponseIntentAndSetResult()
    {
        Intent localIntentResponseToCaller_Intent = new Intent();
        if( (globalBucatiPrimite_AL !=null && globalBucatiPrimite_AL.size() > 0) )
        {
            localIntentResponseToCaller_Intent.putParcelableArrayListExtra(constLabelBucata_String, globalBucatiPrimite_AL);
            setResult(RESULT_OK, localIntentResponseToCaller_Intent);

        }
        else
        {

            localIntentResponseToCaller_Intent.putParcelableArrayListExtra(constLabelBucata_String, new ArrayList<Bucata>());
            setResult(RESULT_OK, localIntentResponseToCaller_Intent);
        }

    }


    private void ReasignIdAfterDelete()
    {
        int localindex_Int=0;
        int localsize_Int;
        for(Bucata buc: globalBucatiPrimite_AL)
        {
            localsize_Int = globalBucatiPrimite_AL.size()-localindex_Int;

            buc.setId(Integer.toString(localsize_Int));
           localindex_Int+=1;

        }

    }

    //when coming back from viz report
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                //clear sent ALs
                globalBucatiOrdonateFataDeTrimis_AL.clear();
                globalBucatiOrdonateSpateDeTrimis_AL.clear();
                globalBucatiOrdonateTotalDeTrimis_AL.clear();

            }

        }

    }


    private void GenerateReportItems()
    {

        //split into fata/spate + salvare specii unice pentru total pe specii
        SplitIntoFataSpate(globalBucatiPrimite_AL);

        //determinare nr bucati si calculare volum total
        DetermineUniquesAndSetNrBucAndVolum(globalBucatiOrdonateFataDeTrimis_AL);
        DetermineUniquesAndSetNrBucAndVolum(globalBucatiOrdonateSpateDeTrimis_AL);


        //sort by Species, then Lengths, then Diameters
        Collections.sort(globalBucatiOrdonateFataDeTrimis_AL, new BucataOrdonataComparator());
        Collections.sort(globalBucatiOrdonateSpateDeTrimis_AL, new BucataOrdonataComparator());


        //add them to final list
        globalBucatiOrdonateTotalDeTrimis_AL.addAll(globalBucatiOrdonateFataDeTrimis_AL);
        globalBucatiOrdonateTotalDeTrimis_AL.addAll(globalBucatiOrdonateSpateDeTrimis_AL);

    }



    private void DetermineUniquesAndSetNrBucAndVolum(ArrayList<BucataOrdonata> arrayList)
    {

        ArrayList<Integer> local_IndexdeSters_AL = new ArrayList<>();

        int localDuplicatesFound_Int;

        for(int i = 0; i<arrayList.size(); i++)
        {

            localDuplicatesFound_Int = 0;
            for(int j = i+1; j<arrayList.size();j++)
            {

                if(arrayList.get(i).equals(arrayList.get(j)))
                {
                    //store indexes to be deleted
                    local_IndexdeSters_AL.add(0,j);

                    //increment found index
                    localDuplicatesFound_Int++;

                }

            }


            //if i-th entry has duplicates, change nrbuc and volum
            if(localDuplicatesFound_Int > 0 && Integer.parseInt(arrayList.get(i).getDiametru()) <= 20)
            {
                BucataOrdonata BucataDeModificat_BO = arrayList.get(i);

                //number of duplicates found + 1 (the original buc)
                int localNumarBucatiNou_Int = localDuplicatesFound_Int + 1;

                double localVolumBucata_Double = Double.parseDouble(BucataDeModificat_BO.getVolumBucata());

                //new volume = original volume of the buc * number of occurences +1
                double localVolumBucatiTotal_Double =  (localVolumBucata_Double * (localDuplicatesFound_Int + 1));

                localVolumBucatiTotal_Double = round(localVolumBucatiTotal_Double,2);

                //convert to string
                String localVolumBucatiTotal_String = String.format(Locale.ENGLISH, "%.2f", localVolumBucatiTotal_Double);


                //set volum and nrbuc
                BucataDeModificat_BO.setNumarBucati(Integer.toString(localNumarBucatiNou_Int));
                BucataDeModificat_BO.setVolumTotal(localVolumBucatiTotal_String);

                //update in arraylist
                arrayList.set(i,BucataDeModificat_BO);

            }
            else
            {
                // unique entry, set volum total = volumbucata
                BucataOrdonata BucataDeModificat_BO = arrayList.get(i);

                BucataDeModificat_BO.setVolumTotal(BucataDeModificat_BO.getVolumBucata());

                arrayList.set(i,BucataDeModificat_BO);

            }


        }

        //create hashset of indexes to be deleted
        HashSet<Integer> localUniqueIndexes_HS = new HashSet<>(local_IndexdeSters_AL);

        //create arraylist from hashset
        ArrayList<Integer> localUniqueIndexesDescending_AL = new ArrayList<>(localUniqueIndexes_HS);

        //sort descending
        Collections.sort(localUniqueIndexesDescending_AL, Collections.reverseOrder());


        //delete duplicates
        for(int IndexDeSters: localUniqueIndexesDescending_AL)
        {
            if(Integer.parseInt(arrayList.get(IndexDeSters).getDiametru()) <= 20) {

                arrayList.remove(IndexDeSters);
            }
        }

    }


    private void SplitIntoFataSpate(ArrayList<Bucata> arrayList)
    {

        //parse for species and split in fata spate - first found in bucati primite first put in ordonate
        for(Bucata Buc: arrayList)
        {

            if(Buc.getContainer().equals("Fata"))
            {
                BucataOrdonata BucOrdFata_BO = new BucataOrdonata(Integer.toString(1),Buc.getLungime(),Buc.getDiametru(),Buc.getSpecie(),Buc.getContainer(),Buc.getVolum(), "-");
                globalBucatiOrdonateFataDeTrimis_AL.add(BucOrdFata_BO);
            }

            else if (Buc.getContainer().equals("Spate"))
            {
                BucataOrdonata BucOrdSpate_BO = new BucataOrdonata(Integer.toString(1),Buc.getLungime(),Buc.getDiametru(),Buc.getSpecie(),Buc.getContainer(),Buc.getVolum(), "-");
                globalBucatiOrdonateSpateDeTrimis_AL.add(BucOrdSpate_BO);
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


    @Override
    public void onBackPressed()
    {

        Toast localButonBack_Toast = Toast.makeText(getApplicationContext(), "Navigheaza folosind butonul dedicat!",
                Toast.LENGTH_LONG);

        localButonBack_Toast.setGravity(Gravity.TOP , 0, 240);
        localButonBack_Toast.show();

    }


    private void AddToDiameterAndRecalculateVolume(double dimensiuneDeAdaugat)
    {

        double localDiametru_Double = 0;
        double localLungime_Double = 0;
        double localVolum_Double = 0;
        double localRaza_Double = 0;
        double localDiametruinMetri_Double = 0;

        String localVolumNouBucata_String;

        double localDiametruNouBuc_Double = 0;


        for (Bucata buc : globalBucatiPrimite_AL)
        {
            localDiametruNouBuc_Double = Double.parseDouble(buc.getDiametru()) + dimensiuneDeAdaugat;

            if(localDiametruNouBuc_Double > 0)
            {
                buc.setDiametru(Integer.toString( (int) localDiametruNouBuc_Double));

                localLungime_Double = Double.parseDouble(buc.getLungime());

                localDiametru_Double = localDiametruNouBuc_Double;
                localDiametruinMetri_Double = (localDiametru_Double / 100);
                localRaza_Double = localDiametruinMetri_Double/2;

                localVolum_Double = ( round(Math.PI,2) * localRaza_Double * localRaza_Double  * localLungime_Double );

                localVolum_Double = round(localVolum_Double,2);

                localVolumNouBucata_String = String.format(Locale.ENGLISH, "%.2f", localVolum_Double);

                buc.setVolum(localVolumNouBucata_String);
            }

        }

        globalAdaptorBucatiPrimite_BLA.notifyDataSetChanged();

        CalculareTotalPeContaineresiAfisare(globalBucatiPrimite_AL);

    }

    private boolean CanRemoveFromDiameters(double dimensiuneDeScazut)
    {

        ArrayList<Double> localAllDiameters_AL = new ArrayList<>();
        boolean localretVal_boolean;
        double local_lowest_diameter_double = 0;

        for(Bucata Buc: globalBucatiPrimite_AL)
        {
            localAllDiameters_AL.add(Double.parseDouble(Buc.getDiametru()));
        }

        local_lowest_diameter_double = Collections.min(localAllDiameters_AL);

        if(local_lowest_diameter_double-dimensiuneDeScazut<=0)
        {
            localretVal_boolean = false;
        }
        else {
            localretVal_boolean = true;
        }

        return localretVal_boolean;

    }
}






