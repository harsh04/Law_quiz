package website.codeforfun.practiceQuestion.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import website.codeforfun.practiceQuestion.R;
import website.codeforfun.practiceQuestion.other.QuestionsData;

public class QuestionActivity extends AppCompatActivity {

    private final List<QuestionsData> questionsDataList = new ArrayList<>();
    private List<String> optionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OptionAdapter mAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference root;
    TextView question, progress;

    int questionNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        question = (TextView) findViewById(R.id.question);
        progress = (TextView) findViewById(R.id.progress);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_options);
        mAdapter = new OptionAdapter(optionList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        final String position = String.valueOf(this.getIntent().getIntExtra("position",1));
        final String ch_name = this.getIntent().getStringExtra("ch_name");
        setTitle(ch_name);
        final String ch_num = "chapter"+position;


        firebaseDatabase = FirebaseDatabase.getInstance();
        root = firebaseDatabase.getReference("data");
        final ValueEventListener chapterDataListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot chapterSnapshot = dataSnapshot.child(ch_num);
                Iterable<DataSnapshot> chapterQue = chapterSnapshot.getChildren();
                for(DataSnapshot ds : chapterQue){
                    if(ds.getKey().equalsIgnoreCase("ch_name")){
                        continue;
                    }
                    QuestionsData q = ds.getValue(QuestionsData.class);
                    questionsDataList.add(q);
                    Log.d("que", String.valueOf(q.getA()));
                }
                loadData(questionNum);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        root.addValueEventListener(chapterDataListner);

        for( QuestionsData q : questionsDataList){
            Log.d("que",q.getQ());
            Toast.makeText(this,q.getA(),Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(final int questionNum) {
        optionList.clear();
        progress.setText("Progress : "+questionNum+"/"+questionsDataList.size());
        question.setText(questionsDataList.get(questionNum).getQ());
        optionList.add("A.  "+questionsDataList.get(questionNum).getO1());
        optionList.add("B.  "+questionsDataList.get(questionNum).getO2());
        optionList.add("C.  "+questionsDataList.get(questionNum).getO3());
        optionList.add("D.  "+questionsDataList.get(questionNum).getO4());
        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(final View view, int position) {
                if((position+1) == questionsDataList.get(questionNum).getA()){
                    view.setBackgroundColor(getResources().getColor(R.color.light_green_primary));
                    final TextView text = (TextView) view.findViewById(R.id.option_text);
                    text.setTextColor(getResources().getColor(R.color.white));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadData(questionNum+1);
                            view.setBackgroundColor(getResources().getColor(R.color.white));
                            text.setTextColor(getResources().getColor(R.color.gray_deep));
                        }
                    }, 900);
                }else{
                    final TextView text = (TextView) view.findViewById(R.id.option_text);
                    view.setBackgroundColor(getResources().getColor(R.color.google_red));
                    text.setTextColor(getResources().getColor(R.color.white));
                    recyclerView.setClickable(false);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadData(questionNum+1);
                            view.setBackgroundColor(getResources().getColor(R.color.white));
                            text.setTextColor(getResources().getColor(R.color.gray_deep));
                        }
                    }, 1200);
                }

            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
