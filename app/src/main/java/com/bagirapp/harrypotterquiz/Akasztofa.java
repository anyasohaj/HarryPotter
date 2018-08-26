package com.bagirapp.harrypotterquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Akasztofa extends AppCompatActivity {

    int probalkozas = 5;

    @BindView(R.id.firstNameButtons)
    LinearLayout firstNameLayout;
    @BindView(R.id.lastNameButton)
    LinearLayout lastNameLayout;
    @BindView(R.id.abcFirst)
    LinearLayout abcFirstLayout;
    @BindView(R.id.abcSecond)
    LinearLayout abcSecondLayout;
    @BindView(R.id.abcThird)
    LinearLayout abcThirdLayout;
    @BindView(R.id.akasztofa)
    TextView akasztas;
    @BindView(R.id.onceMore)
    Button onceMore;
    private static final String TAG = "Akasztofa";
    boolean isLast = false;
    Button wordLetterButton;
    String currentWord;
    String[] currentFirstLast = new String[2];
    int akaszt;
    int foundLetter;
    int wordSize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akasztofa);

        akaszt = probalkozas;

        ButterKnife.bind(this);

        QuestionDatabase database = new QuestionDatabase(this);
        ArrayList<Integer> indexek = database.getIndecies(4);

        int chosenId = indexek.get(kivalasztas(indexek.size()));

        felakaszt(akaszt);

        Question currentQuestion = database.getQuestionById(chosenId);
        currentWord = currentQuestion.getmQuestion();
        Log.v(TAG, "A megfejtendő szó: " + currentWord);
        currentWord = currentWord.toLowerCase();
        currentFirstLast = takeApart(currentWord);

        wordSize = currentWord.length();


        String abcFirstString = getResources().getString(R.string.abc_first_part);
        displayAbc(abcFirstString, abcFirstLayout, 100);
        String abcSecondString = getResources().getString(R.string.abc_second_part);
        displayAbc(abcSecondString, abcSecondLayout, 200);
        String abcThirdString = getResources().getString(R.string.abc_third_part);
        displayAbc(abcThirdString, abcThirdLayout, 300);

        int wordLetterButtonId = 400;
        for (int i=0; i < currentWord.length(); i++ ) {
            Button letterButton = new Button(this);
            letterButton.setId(wordLetterButtonId++);
            letterButton.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
            if (currentWord.charAt(i) == ' ') {
                isLast = true;
                wordSize--;
            }else if (isLast) {
                    lastNameLayout.addView(letterButton);
                    Log.v(TAG, " Most hozzáadom a  " + currentWord.charAt(i));
                } else  {
                    firstNameLayout.addView(letterButton);
                    Log.v(TAG, " Most hozzáadom a kereszttnévhez a  " + currentWord.charAt(i));
                }
            }

    }

    public void displayAbc(String part, LinearLayout layout, int abcButtonId){
        int abcLetterCounter = 0;

        for (int i = 0; i < part.length(); i++){
            final Button letterButton = new Button(this);
            letterButton.setId(abcButtonId++);
            letterButton.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
            Log.v(TAG, "A letterButton id-je: " + letterButton.getId());
            String letter = String.valueOf(part.charAt(abcLetterCounter));
            letterButton.setText(letter);
            Log.v(TAG, "A "+ i + "dik betű a "+ part.charAt(abcLetterCounter));
            abcLetterCounter++;
            layout.addView(letterButton);

            letterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (akaszt > 0 && foundLetter!= currentWord.length()-1) {
                        boolean talalat = false;
                        char clickedLetter = letterButton.getText().toString().charAt(0);
                        String currentLetter = letterButton.getText().toString();

                        for (int i = 0; i < currentFirstLast[0].length(); i++) {
                            int currentButtonId = 400 + i;
                            if (currentFirstLast[0].charAt(i) == clickedLetter) {
                                wordLetterButton = (Button) firstNameLayout.findViewById(currentButtonId);
                                wordLetterButton.setText(currentLetter);
                                foundLetter++;
                                talalat = true;
                            }
                        }
                        for (int i = currentFirstLast[0].length(); i < currentWord.length() - 1; i++) {
                            int currentButtonId = 401 + i;
                            if (currentFirstLast[1].charAt(i - currentFirstLast[0].length()) == clickedLetter) {
                                wordLetterButton = (Button) lastNameLayout.findViewById(currentButtonId);
                                wordLetterButton.setText(currentLetter);
                                foundLetter++;
                                talalat = true;
                            }
                        }

                        letterButton.setVisibility(View.INVISIBLE);
                        if (!talalat) {
                            akaszt--;
                            felakaszt(akaszt);

                        }
                        if (foundLetter == wordSize){
                            Toast.makeText(getApplicationContext(), "WOW, sikerült!!!", Toast.LENGTH_LONG).show();
                            akasztas.setTextSize(32);
                            akasztas.setText("Gratulálok, eltaláltad! :D");
                            next();

                        }
                    }
                }

            });
        }

    }

    public String[] takeApart(String currentWord){
        String[] firstLast = new String[2];
        firstLast = currentWord.split(" ");
        Log.v(TAG, "A kettébontott szavak első fele:  " + firstLast[0] + " és a második fele: " + firstLast[1]);
        return firstLast;
    }

    public void felakaszt(int lepes){
        if (lepes > 0) {
            akasztas.setText(" Van még " + lepes + " próbálkozásod");
        }else {
            akasztas.setTextSize(32);
            akasztas.setText("Fel vagy akasztva. Meghaltál...");
            next();
        }
    }

    public int kivalasztas(int arraySize){
        Random rand = new Random();

        return    rand.nextInt(arraySize);
    }

    public void  next(){
        onceMore.setVisibility(View.VISIBLE);
        onceMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.akasztofa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.reload) {
            Intent reloadIntent = getIntent();
            finish();
            startActivity(reloadIntent);
            return true;  }

        return super.onOptionsItemSelected(item);
    }

}
