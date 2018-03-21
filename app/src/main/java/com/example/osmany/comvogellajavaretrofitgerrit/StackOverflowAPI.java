package com.example.osmany.comvogellajavaretrofitgerrit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by osmany on 21/3/18.
 */

public interface StackOverflowAPI {

    String BASE_URL = "https://api.stackexchange.com";

    @GET("/2.2/questions?order=desc&sort=votes&site=stackoverflow&tagged=android&filter=withbody")
    Call<ListWraper<Question>> getQuestions();

    @GET("/2.2/questions/{id}/answers?order=desc&sort=votes&site=stackoverflow")
    Call<ListWraper<Answer>> getAnswersForQuestions(@Path("id")String questionsId);

}
