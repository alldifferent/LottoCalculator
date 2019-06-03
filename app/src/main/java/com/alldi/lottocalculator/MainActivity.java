package com.alldi.lottocalculator;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alldi.lottocalculator.databinding.ActivityMainBinding;

import java.math.BigInteger;
import java.util.Random;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;
    int[] numArray = new int[6];
    int[] tempNum = new int[6];
    int[] newNum = new int[6];
    int[] desOrderNum = new int[6];
    BigInteger totcount = new BigInteger("0");
    BigInteger firstCount = new BigInteger("0");
    BigInteger thirdCount = new BigInteger("0");
    BigInteger secondCount = new BigInteger("0");
    BigInteger forthCount = new BigInteger("0");
    BigInteger fifthCount = new BigInteger("0");
    BigInteger payMoney = new BigInteger("0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        act.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                for(int i = 0; i < 10000; i++){

                    checkRank();
//                }

            }
        });

    }

    @Override
    public void setValues() {


    }

    @Override
    public void bindViews() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }

    void makeNum() {

        int i = 0;
        int j = 0;
        int count = 0;

        while (true){

            numArray[i] = (int) ((Math.random() * 45) + 1);

            if (i == 0){
                tempNum[0] = numArray[0];
                newNum[0] = tempNum[0];
                i++;
                count++;
                continue;
            }

            j = 0;
            while (j <= (i-1)){

                if (tempNum[j] == numArray[i]){
                    j = i;
                }else {

                    if (j == (i-1)){
                        tempNum[i] = numArray[i];
                        newNum[i] = tempNum[i];
                        i++;
                        j++;
                        count++;
                    }

                    j++;
                }
            }

            if (count == 6){
                break;
            }

        }


    }

    public void desOrder(){

        makeNum();

        int temp = 0;

        for (int i = 0; i < desOrderNum.length; i++){

            desOrderNum[i] = newNum[i];

        }

        for (int i = 0; i < desOrderNum.length; i++){

            for (int j = i+1; j < desOrderNum.length; j++){

                if (desOrderNum[i] > desOrderNum[j]){
                    temp = desOrderNum[j];
                    desOrderNum[j] = desOrderNum[i];
                    desOrderNum[i] = temp;
                }

            }

        }

        act.number1Txt.setText(String.format("%d", desOrderNum[0]));
        act.number2Txt.setText(String.format("%d", desOrderNum[1]));
        act.number3Txt.setText(String.format("%d", desOrderNum[2]));
        act.number4Txt.setText(String.format("%d", desOrderNum[3]));
        act.number5Txt.setText(String.format("%d", desOrderNum[4]));
        act.number6Txt.setText(String.format("%d", desOrderNum[5]));

    }

    void checkRank(){

        desOrder();

        int[] resultNum = new int[7];

        resultNum[0] = Integer.parseInt(act.resultNum1Txt.getText().toString());
        resultNum[1] = Integer.parseInt(act.resultNum2Txt.getText().toString());
        resultNum[2] = Integer.parseInt(act.resultNum3Txt.getText().toString());
        resultNum[3] = Integer.parseInt(act.resultNum4Txt.getText().toString());
        resultNum[4] = Integer.parseInt(act.resultNum5Txt.getText().toString());
        resultNum[5] = Integer.parseInt(act.resultNum6Txt.getText().toString());
        resultNum[6] = Integer.parseInt(act.resultNum7Txt.getText().toString());

        BigInteger price = new BigInteger("1000");
        payMoney = payMoney.add(price);

        act.payTxt.setText(String.format("사용금액: %,4d원", payMoney));

        for (int i = 0; i < desOrderNum.length; i++){
            Log.d("숫자", desOrderNum[i]+"");
        }

        int count = 0;

        for (int i = 0; i < desOrderNum.length; i++){

            for (int j = 0; j < desOrderNum.length; j++){

                if (resultNum[i] == desOrderNum[j]){

                    count++;

                }

            }

        }

        BigInteger fifthReward = new BigInteger("5000");
        BigInteger forthReward = new BigInteger("50000");
        BigInteger firstReward = new BigInteger("4800000000");
        BigInteger secondReward = new BigInteger("50000000");
        BigInteger thirdReward = new BigInteger("1400000");
        BigInteger countAdd = new BigInteger("1");

        if (count < 3){
            act.rankTxt.setText("꽝");
        }else if (count == 3){
            act.rankTxt.setText("5등");
            totcount = totcount.add(fifthReward);
            fifthCount = fifthCount.add(countAdd);
        }else if (count == 4){
            act.rankTxt.setText("4등");
            totcount = totcount.add(forthReward);
            forthCount = forthCount.add(countAdd);
        }else if (count == 5){
            if (resultNum[6] == desOrderNum[0] || resultNum[6] == desOrderNum[1] || resultNum[6] == desOrderNum[2] || resultNum[6] == desOrderNum[3] || resultNum[6] == desOrderNum[4] || resultNum[6] == desOrderNum[5]){
                act.rankTxt.setText("2등");
                secondCount = secondCount.add(countAdd);
                totcount = totcount.add(secondReward);
            }else {
                act.rankTxt.setText("3등");
                totcount = totcount.add(thirdReward);
                thirdCount = thirdCount.add(countAdd);
            }
        }else if (count == 6){
            act.rankTxt.setText("1등");
            totcount = totcount.add(firstReward);
            firstCount = firstCount.add(countAdd);
        }

        act.makeMoneyTxt.setText(String.format("1등 %d회 , 2등 %d회, 3등 %d회, 4등 %d회, 5등 %d회", firstCount, secondCount, thirdCount, forthCount, fifthCount));
        act.countTxt.setText(String.format("누적 당첨금액: 총 %,4d원", totcount));


    }


}
