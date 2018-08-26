package com.bagirapp.harrypotterquiz;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bagirapp.harrypotterquiz.eredmenyData.EredmenyContract;
import com.bagirapp.harrypotterquiz.eredmenyData.EredmenyDbHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class    QuizActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.question)
    public TextView questionText;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.submit)
    Button submitButton;
    QuestionDatabase mDb;
    @BindView(R.id.tick)
            ImageView tickImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.counter)
            TextView counterText;
  public TextView popupText;
  public TextView ertekeloText;
  @BindView(R.id.quizLayout)
          LinearLayout quizLayout;
  PopupWindow mPopupWindow;
    int numberOfQuestions = 4;
    int questionCounter = 0;
    int point = 0;
    boolean isEnd = false;
    ArrayList<Question> currentQuestionList = new ArrayList<>();
    Context context;
    int level;
    int progressSzorzo;
    EredmenyDbHelper mDbHelper;
    String date;
    private static final int EREDMENYEK = 100;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        Date currentTime = Calendar.getInstance().getTime();
        date = currentTime.toString();
        Log.v(TAG, "Az aktuális dátum: " + date);
        progressSzorzo = 100/numberOfQuestions;
        context = getApplicationContext();
        progressBar.setProgress(questionCounter);
        counterText.setText(questionCounter+"/" + numberOfQuestions + ".");

        PreferenceManager.setDefaultValues(this, R.xml.settings_main, false);
        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String setLevel  = sharedPrefs.getString(
                getString(R.string.settings_level_key),
                getString(R.string.settings_level_default)
        );

        if (setLevel.equals(getString(R.string.settings_level_beginner_value))) {level = 1;}
        if (setLevel.equals(getString(R.string.settings_level_advanced_value))) {level = 2;}
        if (setLevel.equals(getString(R.string.settings_level_professional_value))) {level = 3;}


        mDb = new QuestionDatabase(QuizActivity.this);
        currentQuestionList = chooseQuestion(level);

        loadQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnd){
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    mPopupWindow.dismiss();
                }else{
                if (numberOfQuestions > questionCounter) {
                    progressBar.setProgress(questionCounter * progressSzorzo);
                    loadQuestion();
                    tickImage.setVisibility(View.INVISIBLE);
                }else{ if (!isEnd){
                    for (int i = 0; i < numberOfQuestions; i++){
                       if (currentQuestionList.get(i).getmRightAnswer().equals(currentQuestionList.get(i).getUserAnswer())){
                           point++;
                        }
                    }
                    isEnd = true;}
                    popUp(v);

                    submitButton.setText("Új játék");

                   mDbHelper = new EredmenyDbHelper(context);
                   Log.v(TAG, "Létrehozta a helpert");

                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(EredmenyContract.EredmenyEntry.COLUMN_LEVEL, level);
                    values.put(EredmenyContract.EredmenyEntry.COLUMN_POINTS, point);
                    values.put(EredmenyContract.EredmenyEntry.COLUMN_PRODUCT_DATE, date);

  /*                  Uri uri = getContentResolver().insert(EredmenyContract.EredmenyEntry.CONTENT_URI, values);

                    long id = db.insert(EredmenyContract.EredmenyEntry.TABLE_NAME, null, values);
                    Toast.makeText(context, "A mentett elem id-je: " + id, Toast.LENGTH_LONG).show();
                */
            }}}
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Question changedAnswer = currentQuestionList.get((checkedId/100)-1);
                changedAnswer.setUserAnswer(changedAnswer.getmAllAnswer().get(checkedId%100));

            }
        });

    }

    public void loadQuestion(){
        radioGroup.removeAllViews();
        final Question currentQuestion = currentQuestionList.get(questionCounter);
        String questionWithNumber =  currentQuestion.getmQuestion();
        questionText.setText(questionWithNumber);
        counterText.setText(questionCounter+"/" + numberOfQuestions + ".");
        int id = (questionCounter + 1)  * 100;
        Log.v(TAG, "A RadioButton azonosítója: " + id);
        ArrayList<String> possibleAnswers = new ArrayList<>();
        possibleAnswers = currentQuestion.getmAllAnswer();
        for (final String answer : possibleAnswers) {
            RadioButton rb = new RadioButton(this);
            rb.setId(id++);
            rb.setText(answer);
            rb.setTextAppearance(context, R.style.radioGroupStyle);
            final int azonosito = rb.getId();
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentQuestion.getmRightAnswer().equals(answer)) {
                        tickImage.setImageDrawable(getDrawable(R.drawable.ic_action_tick));
                        tickImage.setVisibility(View.VISIBLE);
                    } else {
                        tickImage.setImageDrawable(getDrawable(R.drawable.ic_action_cancel));
                        tickImage.setVisibility(View.VISIBLE);
                    }
                    Log.v(TAG, " AZ id: "+ azonosito + "Viszont a használt id: " + ((questionCounter )*100) + "plusz az i") ;
                    enableButtons();
                        progressBar.setProgress(questionCounter * progressSzorzo);


                }
            });
            Log.v(TAG, "A válasz: " + answer);
            radioGroup.addView(rb);
        }
        questionCounter++;
    }

    /*public ArrayList<Question> pickQuestions(){
        ArrayList<Question> listOfQuestions = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < maxDatabaseId + 1; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        for (int i = 0; i<numberOfQuestions; i++){
            Question question = mDb.getQuestionById(list.get(i));
            listOfQuestions.add(question);
        }
        return listOfQuestions;
    }*/


    public ArrayList<Question> chooseQuestion(int level){
        ArrayList<Question> currentList = new ArrayList<>();
        QuestionDatabase database = new QuestionDatabase(this);
        ArrayList<Integer> indexek = database.getIndecies(level);

       Collections.shuffle(indexek);

       for (int i = 0; i < numberOfQuestions; i++) {
           Question question = database.getQuestionById(indexek.get(i));
           currentList.add(question);
       }
       return currentList;

       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quiz_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.results:
                SQLiteDatabase database = mDbHelper.getReadableDatabase();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = matcher.match(uri);
        switch (match) {
            case EREDMENYEK:
                return insertResult(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Uri insertResult(Uri uri, ContentValues values) {
        String date = values.getAsString(EredmenyContract.EredmenyEntry.COLUMN_PRODUCT_DATE);

        Integer level = values.getAsInteger(EredmenyContract.EredmenyEntry.COLUMN_LEVEL);

        Integer points = values.getAsInteger(EredmenyContract.EredmenyEntry.COLUMN_POINTS);


        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(EredmenyContract.EredmenyEntry.TABLE_NAME, null, values);
        Log.e(TAG, "The book is saved with id: " + id);

        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }

        context.getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    public void databaseQuery(){
        
    }

    public void popUp(View view){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup,null);


            mPopupWindow = new PopupWindow(
                    popupView,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
        //mPopupWindow.setContentView(popupView);
        popupText = (TextView)mPopupWindow.getContentView().findViewById(R.id.popupText);
        ertekeloText = (TextView)mPopupWindow.getContentView().findViewById(R.id.ertekelo);
        String ertekelo = "A " + numberOfQuestions + adjustRag(numberOfQuestions) + " " + point + " pontot szereztél.";
        popupText.setText(ertekelo );
        ertekeloText.setText(ertekeloText(point));
        progressBar.setProgress(questionCounter * progressSzorzo);
        enableButtons();

        if(Build.VERSION.SDK_INT>=21){
                mPopupWindow.setElevation(5.0f);
            }

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPopupWindow.dismiss();
                return true;

            /*// Get a reference for the custom view close button
            ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

            // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Dismiss the popup window
                    mPopupWindow.dismiss();*/
                }

    });
                mPopupWindow.showAtLocation(quizLayout, 17, 0, 0);
    }
    public String adjustRag(int questions){
        String rag;
        int ones = questions%10;
        switch (ones){
            case 1: case 2: case 4: case 5: case 7: case 9: rag = "-ből";
            break;
            case 3: case 6: case 8: rag = "-ból";
            break;
            default: switch (questions){
                case 10: case 40: case 50: case 70: case 90: rag = "-ből";
                break;
                default: rag = "-ból";
            }
        }
        return rag;
    }

    public String ertekeloText(int gotPoint){
        String ertekelo;
        float numberOfQuestionsFloat = (float) numberOfQuestions;
        float performanceInPercent = gotPoint/(numberOfQuestionsFloat/100);
        Log.v(TAG, "A teljesítmény  1%-a: "+ performanceInPercent);
        if (performanceInPercent > 80){
            ertekelo = getResources().getString(R.string.quiz_result_excellent);
        }else if (performanceInPercent > 60){
            ertekelo = getResources().getString(R.string.quiz_result_good);
        }else if (performanceInPercent > 40){
            ertekelo = getResources().getString(R.string.quiz_result_notbad);
        }else if (performanceInPercent > 20){
            ertekelo = getResources().getString(R.string.qiuz_result_more);
        }
        else {ertekelo = getResources().getString(R.string.quiz_result_beginner);}
        return ertekelo;

    }
    public void enableButtons(){
        for (int i = 0; i < 4; i++) {
            RadioButton button = (RadioButton) radioGroup.findViewById((questionCounter * 100)+i);
            button.setEnabled(false);
        }
    }

}

