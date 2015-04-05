package ua.iparkh.floorboardloc.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.iparkh.floorboardloc.R;
import ua.iparkh.floorboardloc.adapter.AdapterBoardLoc;
import ua.iparkh.floorboardloc.model.ModelBoardLoc;

public class BoardLocActivity extends ActionBarActivity {
    TextView textView;
    ListView listView;

    private float la= (float) 5.41;
    private float lb= (float) 3.11;
    private float ld= (float) .015;
    private float pa= (float) 2.2;
    private float pb= (float) 0.207;
    private float pds;
    private float pd =0;

    int  rc;//     number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_loc_layout);
    ///////////////////

    ///////////////////
        // 1
        listView=(ListView) findViewById(R.id.listView);
        textView=(TextView) findViewById(R.id.textView);

        rc = (int) (lb/pb);

 //       textView.setText("*****");

        textView.setText(
                        String.format("Кімната\n довж.:%3.2f шир.:%3.2f пл.:%3.2f \nДошка\n довж.: %3.3f шир.:%3.3fпл.:%3.2f",la,lb,(la*lb),pa,pb,(pa*pb))+
                        "\nполос "+rc
                        );



        //2
        AdapterBoardLoc adapter = new AdapterBoardLoc(this,initData());
        //3
        listView.setAdapter(adapter);
    }

    private List<ModelBoardLoc> initData(){
        //
        //
        // 1



        List<ModelBoardLoc> list= new ArrayList<ModelBoardLoc>();
        // 2

        for(int i=1;i<=rc;i++) {
            pds=pd;
            ld=la-pd;
            pd=ld-((int)(ld/pa))*pa;
            pd=pa-pd;

            list.add(new ModelBoardLoc(
                    i,
                    "Полоса: "+i,
                            String.format("поч.:%3.2f",pds)
                            +" кільк.:"+((int) (ld/pa))
                            +String.format(" ост.:%3.2f",(pa-pd))
                            +String.format(" зал.:%3.2f",pd)));

        }

        // 3
        return list;

    }
}
