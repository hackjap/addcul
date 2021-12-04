package com.jsp.addcul.activity.translate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jsp.addcul.R;
import com.jsp.addcul.activity.config.BasicActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TranslationActivity extends BasicActivity {

    Button btTranslate;
    EditText etSource;
    TextView tvResult;
    Spinner spinner;

    String sourceLang = "ko";
    String targetLang = "ru";

    String[] items = {"인도네시아어","베트남어","태국어","영어","일본어", "중국어"};
    String[] langs= {"id", "vi", "th", "en", "ja", "zh-cn"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        etSource = (EditText) findViewById(R.id.et_source);
        tvResult = (TextView) findViewById(R.id.textView);
        btTranslate = (Button) findViewById(R.id.button2);
        spinner = (Spinner) findViewById(R.id.trans_id);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);


        ImageView ivTran = findViewById(R.id.img_translate);
        TextView tvTran = findViewById(R.id.tv_translate);
        ivTran.setImageResource(R.drawable.ic_translate_yellow_24dp);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_translate, items);
        adapter.setDropDownViewResource(R.layout.item_translate);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetLang = langs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvResult.setText("다시 선택해주세요!");
            }
        });


        if (firebaseUser == null) { // 로그인 상태가 아닐때

        } else {
            TextView tvMyinfo = (TextView) findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_account_circle_black_24dp);
            tvMyinfo.setText("내정보");

        }


        //번역 실행버튼 클릭이벤트
        btTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //소스에 입력된 내용이 있는지 체크하고 넘어가자.
                if (etSource.getText().toString().length() == 0) {
                    Toast.makeText(TranslationActivity.this, "번역할 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    etSource.requestFocus();
                    return;
                } else {
                    //실행버튼을 클릭하면 AsyncTask를 이용 요청하고 결과를 반환받아서 화면에 표시하도록 해보자.
                    NaverTranslateTask asyncTask = new NaverTranslateTask();
                    String sText = etSource.getText().toString();
                    asyncTask.execute(sText);
                }
            }
        });

        //음성인식 실행버튼 이벤트
        //이건 나중에...
    }

    //ASYNCTASK
    public class NaverTranslateTask extends AsyncTask<String, Void, String> {

        public String resultText;
        //Naver
        String clientId = "d_ZAKMHlxNP9t1SSQ5RX";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "L_y_INPySQ";//애플리케이션 클라이언트 시크릿값";
        //언어선택도 나중에 사용자가 선택할 수 있게 옵션 처리해 주면 된다.
        String sourceLang = "ko";
        String targetLang = "vi";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //AsyncTask 메인처리
        @Override
        protected String doInBackground(String... strings) {
            //네이버제공 예제 복사해 넣자.
            //Log.d("AsyncTask:", "1.Background");

            String sourceText = strings[0];

            try {
                //String text = URLEncoder.encode("만나서 반갑습니다.", "UTF-8");
                String text = URLEncoder.encode(sourceText, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=" + sourceLang + "&target=" + targetLang + "&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                //System.out.println(response.toString());
                return response.toString();

            } catch (Exception e) {
                //System.out.println(e);
                Log.d("error", e.getMessage());
                return null;
            }
        }

        //번역된 결과를 받아서 처리
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //최종 결과 처리부
            //Log.d("background result", s.toString()); //네이버에 보내주는 응답결과가 JSON 데이터이다.

            //JSON데이터를 자바객체로 변환해야 한다.
            //Gson을 사용할 것이다.

            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonElement rootObj = parser.parse(s.toString())
                    //원하는 데이터 까지 찾아 들어간다.
                    .getAsJsonObject().get("message")
                    .getAsJsonObject().get("result");
            //안드로이드 객체에 담기
            TranslatedItem items = gson.fromJson(rootObj.toString(), TranslatedItem.class);
            //Log.d("result", items.getTranslatedText());
            //번역결과를 텍스트뷰에 넣는다.
            tvResult.setText(items.getTranslatedText());
            Log.e("ja : " , items.getTranslatedText().toString());
        }


        //자바용 그릇
        private class TranslatedItem {
            String translatedText;

            public String getTranslatedText() {
                return translatedText;
            }
        }
    }
}

