package website.codeforfun.practiceQuestion.activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import website.codeforfun.practiceQuestion.R;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.MyViewHolder> {

    private List<String> optionList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView option;
        public CardView card;

        public MyViewHolder(View view) {
            super(view);
            option = (TextView) view.findViewById(R.id.option_text);        }
    }


    public OptionAdapter(List<String> optionList) {
        this.optionList = optionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String option_val = optionList.get(position);
        holder.option.setText(option_val);
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }
}
