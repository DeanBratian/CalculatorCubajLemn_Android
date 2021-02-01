package com.Cubaj.CubajLemnRotund;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class VizualizareRaportActivity extends AppCompatActivity {

    private static final String constLabelBucataOrdonata_String = "bucataordonata";

    private static final String consttotalFata_String = "totalbucfata";
    private static final String consttotalSpate_String = "totalbucspate";

    private static final String consttotalVolFata_String = "totalVolfata";
    private static final String consttotalVolSpate_String = "totalVolspate";


    //array si array adapter
    private ArrayList<BucataOrdonata> globalBucatiOrdonatePrimiteTotal_AL = new ArrayList<>();
    private BucataOrdonataListAdapter globalAdaptorBucatiOrdonatePrimite_BLA;

    private ArrayList<String> GlobalSpeciiDistincte_Fata_AL = new ArrayList<>();
    private ArrayList<String> GlobalSpeciiDistincte_Spate_AL = new ArrayList<>();

    private LinkedHashMap<String, String> GlobalTotaluriPeSpeciiFata_HM = new LinkedHashMap<>();
    private LinkedHashMap<String, String> GlobalTotaluriPeSpeciiSpate_HM = new LinkedHashMap<>();

    private LinkedHashMap<String, Integer> GlobalNumaratoarePeSpeciiFata_HM = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> GlobalNumaratoarePeSpeciiSpate_HM = new LinkedHashMap<>();

    private StringBuilder globalValoriTotalFata_SB;
    private StringBuilder globalValoriTotalSpate_SB;

    private String globaltotalBucSpate_String = "";
    private String globaltotalBucFata_String= "";

    private String globaltotalVolSpate_String = "";
    private String globaltotalVolFata_String= "";

    //controale
    private ListView globalLVObiecteOrdonate_ListView;
    private TextView globalTVTotaluriFata_TextView;
    private TextView globalTVTotaluriSpate_TextView;
    private androidx.appcompat.widget.Toolbar globalTBToolbar_TB;

    private TextView globalTVTotalFata_TextView;
    private TextView globalTVTotalSpate_TextView;


    //hold flags for color change
    private ArrayList<Boolean> global_Flag_ColorChange_AL = new ArrayList<>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_raport_layout, menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_raport);

        System.out.println("oncreate called");
        //clear arraylists

        GlobalSpeciiDistincte_Spate_AL.clear();
        GlobalSpeciiDistincte_Fata_AL.clear();

        GlobalTotaluriPeSpeciiFata_HM.clear();
        GlobalTotaluriPeSpeciiSpate_HM .clear();

        GlobalNumaratoarePeSpeciiFata_HM.clear();
        GlobalNumaratoarePeSpeciiSpate_HM .clear();

        globalValoriTotalFata_SB = new StringBuilder();
        globalValoriTotalSpate_SB = new StringBuilder();

        globalBucatiOrdonatePrimiteTotal_AL.clear();

        //setare custom toolbar din layout
        globalTBToolbar_TB = findViewById(R.id.Toolbar);
        setSupportActionBar(globalTBToolbar_TB);

        Intent localIntent_Intent = getIntent();

        globalBucatiOrdonatePrimiteTotal_AL = localIntent_Intent.getParcelableArrayListExtra(constLabelBucataOrdonata_String);

        globaltotalBucFata_String = localIntent_Intent.getStringExtra(consttotalFata_String);
        globaltotalBucSpate_String = localIntent_Intent.getStringExtra(consttotalSpate_String);

        globaltotalVolFata_String = localIntent_Intent.getStringExtra(consttotalVolFata_String);
        globaltotalVolSpate_String = localIntent_Intent.getStringExtra(consttotalVolSpate_String);

        DetermineUniqueSpecies();

        //calculate totals
        CalculateTotalsOnSpeciesFata(GlobalSpeciiDistincte_Fata_AL, GlobalTotaluriPeSpeciiFata_HM,GlobalNumaratoarePeSpeciiFata_HM);
        CalculateTotalsOnSpeciesSpate(GlobalSpeciiDistincte_Spate_AL, GlobalTotaluriPeSpeciiSpate_HM,GlobalNumaratoarePeSpeciiSpate_HM);

        //build string and display in TV
        BuildStringTotaluriFata(GlobalSpeciiDistincte_Fata_AL,GlobalTotaluriPeSpeciiFata_HM);
        BuildStringTotaluriSpate(GlobalSpeciiDistincte_Spate_AL,GlobalTotaluriPeSpeciiSpate_HM);

        globalLVObiecteOrdonate_ListView = findViewById(R.id.listviewbucatiordonate);
        globalTVTotaluriFata_TextView = findViewById(R.id.textViewTotaluriFata);
        globalTVTotaluriSpate_TextView = findViewById(R.id.textViewTotaluriSpate);

        globalTVTotalFata_TextView = findViewById(R.id.textViewTotalFata);
        globalTVTotalSpate_TextView = findViewById(R.id.textViewTotalSpate);


        //initialize color changer
        for(int local_counter =0; local_counter< globalBucatiOrdonatePrimiteTotal_AL.size();local_counter++)
        {
            global_Flag_ColorChange_AL.add(local_counter,false);
        }

        globalAdaptorBucatiOrdonatePrimite_BLA = new BucataOrdonataListAdapter(this,R.layout.adapter_list_layout_bucata_ordonata, globalBucatiOrdonatePrimiteTotal_AL,global_Flag_ColorChange_AL);

        globalLVObiecteOrdonate_ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        globalLVObiecteOrdonate_ListView.setAdapter(globalAdaptorBucatiOrdonatePrimite_BLA);

        // on long click listener
        globalLVObiecteOrdonate_ListView.setOnItemLongClickListener(globalOnClickLungLista_LST);

        globalTVTotaluriFata_TextView.setText(globalValoriTotalFata_SB);
        globalTVTotaluriSpate_TextView.setText(globalValoriTotalSpate_SB);

        String localStringFata_String = getString (R.string.fata);
        String localStringSpate_String = getString (R.string.spate);

        globalTVTotalFata_TextView.setText(localStringFata_String + " " + globaltotalBucFata_String +" buc.");
        globalTVTotalSpate_TextView.setText(localStringSpate_String + " " + globaltotalBucSpate_String +" buc.");



        HandlebutonVizualizare();

//        StringBuilder sb = new StringBuilder();
//
//        for(BucataOrdonata bo: globalBucatiOrdonatePrimiteTotal_AL)
//        {
//            sb.append(bo.getNumarBucati());
//            sb.append(",");
//            sb.append(bo.getLungime());
//            sb.append(",");
//            sb.append(bo.getDiametru());
//            sb.append(",");
//            sb.append(bo.getSpecie());
//            sb.append(",");
//            sb.append(bo.getContainer());
//            sb.append(",");
//            sb.append(bo.getVolumBucata());
//            sb.append(",");
//            sb.append(bo.getVolumTotal());
//            sb.append("\n");
//
//
//        }
//        System.out.println(sb);


        //pass result to caller activity
        AddDataToResponseIntentAndSetResult();

    }


    private void HandlebutonVizualizare()
    {
        Button butonInserare = findViewById(R.id.buttonVizualizare);

        butonInserare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(VizualizareRaportActivity.this, R.style.AlertDialogCustom)
                        .setMessage("Iesi din raport?")
                        .setCancelable(false)
                        .setPositiveButton("Iesi", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                //pass result to caller activity
                                AddDataToResponseIntentAndSetResult();
                                global_Flag_ColorChange_AL.clear();
                                finish();
                            }
                        })
                        .setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();

            }
        });
    }

    private void AddDataToResponseIntentAndSetResult()
    {
        Intent localIntent_Intent = new Intent();
        setResult(RESULT_OK, localIntent_Intent);
    }



    private void DetermineUniqueSpecies()
    {

        for(BucataOrdonata BucOrd: globalBucatiOrdonatePrimiteTotal_AL)
        {

            if( BucOrd.getContainer().equals("Fata"))
            {
                GlobalSpeciiDistincte_Fata_AL.add(BucOrd.getSpecie());
            }
            else if(BucOrd.getContainer().equals("Spate"))
            {
                GlobalSpeciiDistincte_Spate_AL.add(BucOrd.getSpecie());
            }

        }

        //get unqiue species
        GlobalSpeciiDistincte_Fata_AL = removeDuplicatesFromAL(GlobalSpeciiDistincte_Fata_AL);
        GlobalSpeciiDistincte_Spate_AL = removeDuplicatesFromAL(GlobalSpeciiDistincte_Spate_AL);

        Collections.sort(GlobalSpeciiDistincte_Fata_AL, new StringComparator() );
        Collections.sort(GlobalSpeciiDistincte_Spate_AL, new StringComparator() );


    }


    private static <String> ArrayList<String> removeDuplicatesFromAL(ArrayList<String> list)
    {

        // Create a new ArrayList
        ArrayList<String> localnewList = new ArrayList<>();

        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it
            if (!localnewList.contains(element)) {

                localnewList.add(element);
            }
        }

        // return the new list
        return localnewList;
    }


    private void CalculateTotalsOnSpeciesFata(ArrayList<String> arrayListFata, LinkedHashMap<String, String> HMTotaluriFata, LinkedHashMap<String, Integer>HMNumaratoareFata)
    {
         double localTotalSpecie_Double = 0;
         String localTotalSpecie_String;
         int localCounterbucati_int = 0 ;


        for(String Specie : arrayListFata)
        {

            for(BucataOrdonata BucOrdBO: globalBucatiOrdonatePrimiteTotal_AL)
            {

                if(BucOrdBO.getSpecie().equals(Specie) && BucOrdBO.getContainer().equals("Fata") )
                {
                    localTotalSpecie_Double += Double.parseDouble(BucOrdBO.getVolumTotal());
                    localCounterbucati_int += Integer.parseInt(BucOrdBO.getNumarBucati());

                }


            }

            localTotalSpecie_Double = round(localTotalSpecie_Double, 2);
            localTotalSpecie_String = String.format(Locale.ENGLISH, "%.2f", localTotalSpecie_Double );

            HMTotaluriFata.put(Specie,localTotalSpecie_String);

            HMNumaratoareFata.put(Specie,localCounterbucati_int);

            localCounterbucati_int =0;
            localTotalSpecie_Double = 0;

        }

    }

    private void CalculateTotalsOnSpeciesSpate(ArrayList<String> arrayListSpate, LinkedHashMap<String, String> HMTotaluriSpate,LinkedHashMap<String, Integer>HMNumaratoareSpate)
    {
        double localTotalSpecie_Double = 0;
        String localTotalSpecie_String;
        int localCounterbucati_int = 0 ;

        for(String Specie : arrayListSpate)
        {

            for(BucataOrdonata BucOrdBO: globalBucatiOrdonatePrimiteTotal_AL)
            {
                if(BucOrdBO.getSpecie().equals(Specie) && BucOrdBO.getContainer().equals("Spate"))
                {
                    localTotalSpecie_Double += Double.parseDouble(BucOrdBO.getVolumTotal());

                    localCounterbucati_int += Integer.parseInt(BucOrdBO.getNumarBucati());
                }

            }
            localTotalSpecie_Double = round(localTotalSpecie_Double, 2);
            localTotalSpecie_String = String.format(Locale.ENGLISH, "%.2f", localTotalSpecie_Double );

            HMTotaluriSpate.put(Specie,localTotalSpecie_String);

            HMNumaratoareSpate.put(Specie,localCounterbucati_int);

            localTotalSpecie_Double = 0;
            localCounterbucati_int = 0;

        }

    }


    private static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal localbd_BD = BigDecimal.valueOf(value);
        localbd_BD = localbd_BD.setScale(places, RoundingMode.HALF_UP);
        return localbd_BD.doubleValue();
    }


    private void BuildStringTotaluriFata(ArrayList<String> arrayListFata, LinkedHashMap<String, String> HMTotaluriFata)
    {
        Set<String> localKeys_Set = HMTotaluriFata.keySet();
        int localForCounter_Int = 0;

        for(String key: localKeys_Set)
        {
            localForCounter_Int +=1;

            globalValoriTotalFata_SB.append(key);
            globalValoriTotalFata_SB.append(": ");
            globalValoriTotalFata_SB.append(HMTotaluriFata.get(key));
            globalValoriTotalFata_SB.append(" mc");

            if(localForCounter_Int != arrayListFata.size())
            {
                globalValoriTotalFata_SB.append(", ");
            }

            if(localForCounter_Int % 2 == 0)
            {
                globalValoriTotalFata_SB.append("\n");
            }

        }

    }

    private void BuildStringTotaluriSpate(ArrayList<String> arrayListSpate, LinkedHashMap<String, String> HMTotaluriSpate)
    {
        Set<String> localKeys_Set = HMTotaluriSpate.keySet();
        int localForCounter_Int = 0;

        for(String key: localKeys_Set)
        {
            localForCounter_Int +=1;

            globalValoriTotalSpate_SB.append(key);
            globalValoriTotalSpate_SB.append(": ");
            globalValoriTotalSpate_SB.append(HMTotaluriSpate.get(key));
            globalValoriTotalSpate_SB.append(" mc");

            if(localForCounter_Int != arrayListSpate.size())
            {
                globalValoriTotalSpate_SB.append(", ");
            }

            if(localForCounter_Int % 2 == 0)
            {
                globalValoriTotalSpate_SB.append("\n");
            }

        }

    }


    @Override
    public void onBackPressed()
    {

        Toast localButonBack_Toast = Toast.makeText(getApplicationContext(), "Navigheaza folosind butonul dedicat!",
                Toast.LENGTH_LONG);

        localButonBack_Toast.setGravity(Gravity.TOP , 0, 240);
        localButonBack_Toast.show();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.SaveReport_Rep:

                final EditText localUserinput_ET = new EditText(VizualizareRaportActivity.this);
                localUserinput_ET.setInputType(InputType.TYPE_CLASS_TEXT);

                String currentDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());

                StringBuilder local_numeFisier_SB = new StringBuilder();
                local_numeFisier_SB.append(currentDate);
                local_numeFisier_SB.append("_Raport");

                localUserinput_ET.setText(local_numeFisier_SB.toString());

                new AlertDialog.Builder(VizualizareRaportActivity.this, R.style.AlertDialogCustom)
                        .setMessage("Introduceti numele raportului \n(fara spatii):")
                        .setView(localUserinput_ET)
                        .setCancelable(false)
                        .setPositiveButton("Salveaza", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable localeditable_Editable = localUserinput_ET.getText();
                                String localUserResponse_String = localeditable_Editable.toString();

                                if(!localUserResponse_String.isEmpty() && !localUserResponse_String.contains(" "))
                                {

                                    exportRaportCSV(localUserResponse_String,createSBCSV(GlobalTotaluriPeSpeciiFata_HM,GlobalTotaluriPeSpeciiSpate_HM,GlobalNumaratoarePeSpeciiFata_HM,GlobalNumaratoarePeSpeciiSpate_HM));


                                }
                                else
                                    {
                                        Toast localToastSpatii_Toast = Toast.makeText(getApplicationContext(), "Numele nu poate contine spatii!",
                                                Toast.LENGTH_LONG);

                                        localToastSpatii_Toast.setGravity(Gravity.TOP , 0, 240);
                                        localToastSpatii_Toast.show();
                                    }

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




    private void exportRaportCSV(String FileName, StringBuilder Bucati)
    {

        try {
            //saving the file into device
            FileOutputStream out = openFileOutput(FileName + ".csv", Context.MODE_PRIVATE);
            out.write((Bucati.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), FileName + ".csv");
            Uri path = FileProvider.getUriForFile(context, "com.Cubaj.CubajLemnRotund.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setDataAndType(path,"text/csv");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Raport Cubaj"));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private StringBuilder createSBCSV(LinkedHashMap<String, String> HMTotaluriFata, LinkedHashMap<String, String> HMTotaluriSpate,LinkedHashMap<String, Integer> HMNumaratorFata,LinkedHashMap<String, Integer> HMNumaratorSpate)
    {
        Set<String> localKeysSpate_Set = HMTotaluriSpate.keySet();
        Set<String> localKeysFata_Set = HMTotaluriFata.keySet();

        StringBuilder localRaport_SB = new StringBuilder();

        localRaport_SB.append("Fata:");
        localRaport_SB.append("\n");
        localRaport_SB.append("#Buc, L [m], D [cm], Specie, Stocat, VT [mc], VB [mc]");
        localRaport_SB.append("\n");

        // adauga bucati fata
        for(BucataOrdonata BO: globalBucatiOrdonatePrimiteTotal_AL)
        {
            if(BO.getContainer().equals("Fata"))
            {
                localRaport_SB.append(BO.getNumarBucati()).append(",").append(BO.getLungime()).append(",").append(BO.getDiametru()).append(",").append(BO.getSpecie()).append(",").append(BO.getContainer()).append(",").append(BO.getVolumTotal()).append(",").append(BO.getVolumBucata());
                localRaport_SB.append("\n");
            }

        }

        localRaport_SB.append("\n");
        localRaport_SB.append("Totaluri Fata:");
        localRaport_SB.append("\n");

        //adauga totaluri fata

        for(String key: localKeysFata_Set)
        {
            localRaport_SB.append(key).append(" (").append(HMNumaratorFata.get(key).toString()).append(" buc.) ").append(",").append(HMTotaluriFata.get(key)).append(" [mc]");
            localRaport_SB.append("\n");

        }
        localRaport_SB.append("\n");
        localRaport_SB.append("Volum Total: ").append(",").append(globaltotalVolFata_String).append(" [mc]");
        localRaport_SB.append("\n");
        localRaport_SB.append("#Total: ").append(",").append(globaltotalBucFata_String).append(" buc.");

        //spacing
        localRaport_SB.append("\n");
        localRaport_SB.append("\n");
        localRaport_SB.append("\n");

        //spate
        localRaport_SB.append("Spate:");
        localRaport_SB.append("\n");
        localRaport_SB.append("#Buc, L [m], D [cm], Specie, Stocat, VT [mc], VB [mc]");
        localRaport_SB.append("\n");


        // adauga bucati spate
        for(BucataOrdonata BO: globalBucatiOrdonatePrimiteTotal_AL)
        {
            if(BO.getContainer().equals("Spate"))
            {
                localRaport_SB.append(BO.getNumarBucati()).append(",").append(BO.getLungime()).append(",").append(BO.getDiametru()).append(",").append(BO.getSpecie()).append(",").append(BO.getContainer()).append(",").append(BO.getVolumTotal()).append(",").append(BO.getVolumBucata());
                localRaport_SB.append("\n");
            }

        }

        localRaport_SB.append("\n");
        localRaport_SB.append("Totaluri Spate:");
        localRaport_SB.append("\n");

        //adauga totaluri spate

        for(String key: localKeysSpate_Set)
        {
            localRaport_SB.append(key).append(" (").append(HMNumaratorSpate.get(key).toString()).append(" buc.) ").append(",").append(HMTotaluriSpate.get(key)).append(" [mc]");
            localRaport_SB.append("\n");

        }

        localRaport_SB.append("\n");
        localRaport_SB.append("Volum Total: ").append(",").append(globaltotalVolSpate_String).append(" [mc]");
        localRaport_SB.append("\n");
        localRaport_SB.append("#Total: ").append(",").append(globaltotalBucSpate_String).append(" buc.");

        return localRaport_SB;
    }



    private ListView.OnItemLongClickListener globalOnClickLungLista_LST  = new ListView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,  int position, long id)
        {

            String localIdBucataSelectata_String = String.valueOf(position+1);
            if(global_Flag_ColorChange_AL.get(position).equals(false))
            {
                global_Flag_ColorChange_AL.set(position,true);

                Toast localBucataMarcata_Toast = Toast.makeText(getApplicationContext(), "Pozitia " + localIdBucataSelectata_String +" a fost selectata!",
                        Toast.LENGTH_LONG);

                localBucataMarcata_Toast.setGravity(Gravity.TOP , 0, 240);
                localBucataMarcata_Toast.show();
            }
            else
            {
                global_Flag_ColorChange_AL.set(position,false);

                Toast localBucataMarcata_Toast = Toast.makeText(getApplicationContext(), "Pozitia " + localIdBucataSelectata_String +" a fost deselectata!",
                        Toast.LENGTH_LONG);

                localBucataMarcata_Toast.setGravity(Gravity.TOP , 0, 240);
                localBucataMarcata_Toast.show();
            }



            globalAdaptorBucatiOrdonatePrimite_BLA.setElementeSelectate(global_Flag_ColorChange_AL);

            globalAdaptorBucatiOrdonatePrimite_BLA.notifyDataSetChanged();



//            System.out.println(globalLVObiecteOrdonate_ListView.getAdapter().getCount());
//            System.out.println(position);
//
//            int poz =0;
//            for(boolean b :global_Flag_ColorChange_AL)
//            {
//                System.out.println("poz"+":"+ poz+b);
//                poz++;
//            }

            return false;
        }


    };

    @Override
    protected void onPause() {
        super.onPause();
    }


}
