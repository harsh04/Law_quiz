package website.codeforfun.practiceQuestion.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import website.codeforfun.practiceQuestion.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<String> chapterName;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sno, title;

        public MyViewHolder(View view) {
            super(view);
            sno = (TextView) view.findViewById(R.id.sno);
            title = (TextView) view.findViewById(R.id.title);
        }
    }


    public ListAdapter(List<String> chapterName) {
        this.chapterName = chapterName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String snum = String.valueOf(position+1);;
        holder.sno.setText(snum);
        holder.title.setText(chapterName.get(position));
    }

    @Override
    public int getItemCount() {
        return chapterName.size();
    }
}
