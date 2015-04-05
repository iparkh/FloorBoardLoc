package ua.iparkh.floorboardloc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.iparkh.floorboardloc.R;
import ua.iparkh.floorboardloc.model.ModelBoardLoc;

/**
 */

public class AdapterBoardLoc  extends BaseAdapter{
    private List<ModelBoardLoc> list;
    private LayoutInflater layoutInflater;


    public AdapterBoardLoc(Context context,List<ModelBoardLoc> list) {
        this.list = list;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =convertView;

        if(view==null) {
            view = layoutInflater.inflate(R.layout.layout_item_board_loc, parent, false);

        }

        ModelBoardLoc modelBoardLoc=getModelBoardLoc(position);

        TextView textView=  (TextView) view.findViewById(R.id.textViewItemBoardLoc);
        textView.setText(modelBoardLoc.getName());

        TextView textViewDescr=  (TextView) view.findViewById(R.id.textViewItemBoardLocDescr);
        textViewDescr.setText(modelBoardLoc.getDescription());

        return view;
    }

    private ModelBoardLoc getModelBoardLoc(int position){

        return (ModelBoardLoc) getItem(position);

    }

}
