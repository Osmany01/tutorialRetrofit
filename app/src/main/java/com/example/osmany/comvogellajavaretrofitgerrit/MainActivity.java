package com.example.osmany.comvogellajavaretrofitgerrit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private StackOverflowAPI stackoverflowAPI;

   private String token;
   private Button authenticateButton;
   private Spinner questionsSpiner;
   private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionsSpiner = findViewById(R.id.questions_spinner);
        authenticateButton = findViewById(R.id.authenticate_button);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity  .this));
        createStackoverflowAPI();
        stackoverflowAPI.getQuestions().enqueue(questionsCallback);
        final List<Answer> answers = FakeDataProvider.getAnswers();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(answers);
        recyclerView.setAdapter(recyclerViewAdapter);

        questionsSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Spinner Item selected", Toast.LENGTH_LONG).show();

                Question question = (Question) parent.getAdapter().getItem(position);
                stackoverflowAPI.getAnswersForQuestions(question.questionId).enqueue(answersCallback);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<Question> questions = FakeDataProvider.getQuestions();
        ArrayAdapter<Question> arrayAdapter = new ArrayAdapter<Question>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,questions);
        questionsSpiner.setAdapter(arrayAdapter);
    }

    private void createStackoverflowAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StackOverflowAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        stackoverflowAPI = retrofit.create(StackOverflowAPI.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(token!=null){
            authenticateButton.setEnabled(false);
        }
    }

    public void onClick(View view) {

        switch (view.getId()){
            case android.R.id.text1:
                if (token!=null){
                }else{
                    Toast.makeText(MainActivity.this,"You need authentificate first",Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.authenticate_button:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1){
            token = data.getStringExtra("token");
        }
    }

    Callback<ListWraper<Question>> questionsCallback = new Callback<ListWraper<Question>>() {
        @Override
        public void onResponse(Call<ListWraper<Question>> call, Response<ListWraper<Question>> response) {
            if(response.isSuccessful()){
                ListWraper<Question> questions = response.body();
                ArrayAdapter<Question> arrayAdapter = new ArrayAdapter<Question>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,questions.items);
                questionsSpiner.setAdapter(arrayAdapter);
            }else{
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<ListWraper<Question>> call, Throwable t) {

        }
    };

    Callback<ListWraper<Answer>> answersCallback = new Callback<ListWraper<Answer>>() {
        @Override
        public void onResponse(Call<ListWraper<Answer>> call, Response<ListWraper<Answer>> response) {
            if (response.isSuccessful()){
                List<Answer> answers = new ArrayList<>();
                answers.addAll(response.body().items);
                recyclerView.setAdapter(new RecyclerViewAdapter(answers));
            }else{
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<ListWraper<Answer>> call, Throwable t) {

            t.printStackTrace();

        }
    };

    Callback<ResponseBody> upvoteCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response.isSuccessful()){
                Toast.makeText(MainActivity.this, "Upvote successful", Toast.LENGTH_LONG).show();
            }else{
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                Toast.makeText(MainActivity.this, "You already upvoted this answer", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            t.printStackTrace();
        }
    };

}
