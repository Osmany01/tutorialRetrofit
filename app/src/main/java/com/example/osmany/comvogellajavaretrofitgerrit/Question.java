package com.example.osmany.comvogellajavaretrofitgerrit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by osmany on 21/3/18.
 */

public class Question {

    private String title;
    private String body;

    @SerializedName("question_id")
    public String questionId;

    @Override
    public String toString(){
        return(title);
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setBody(String body){
        this.body = body;
    }
}
