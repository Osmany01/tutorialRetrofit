package com.example.osmany.comvogellajavaretrofitgerrit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by osmany on 21/3/18.
 */

public class Answer {

    @SerializedName("answer_id")
    public int answerId;

    @SerializedName("is_accepted")
    public boolean accepted;

    public int score;

    @Override
    public String toString(){
        return answerId + "  - Score: " + score + "  - Accepted: " +  (accepted ? "YES" : "NO" );
    }
}
