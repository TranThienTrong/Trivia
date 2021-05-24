package com.patecan.trivia.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.patecan.trivia.controller.MySingleton;
import com.patecan.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.patecan.trivia.controller.MySingleton.TAG;
import static com.patecan.trivia.controller.MySingleton.getInstance;

public class QuestionBank {

    ArrayList<Question> questionsList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListRespone callback) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                Question question = new Question();

                                question.setmAnswer(response.getJSONArray(i).get(0).toString());
                                question.setmAnswerTrue(response.getJSONArray(i).getBoolean(1));
                                questionsList.add(question);


                                Log.d("JSON", "onResponse: " +question);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("Callback", "Đang Respone");
                        if (questionsList.size()==response.length()){
                            Log.d("Callback", "Đã load xong");
                            callback.processFinish(questionsList);
                            Log.d("Callback", "Sau khi CallBack trong Question Bank");
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        MySingleton.getInstance().addToRequestQueue(jsonArrayRequest);
        Log.d("Callback", "Xong GetQuestion()");
        return questionsList;
    }

}
