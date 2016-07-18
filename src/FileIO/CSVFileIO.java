/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 *
 * @author A.Toshiki
 */
public class CSVFileIO {

    private long LONG_LIMIT;

    private String FilePath;
    private String FileName;

    private ArrayList<Long> LlongNumber;
    private ArrayList<String> LstrNumber;
    private ArrayList<Long> LlongNotContainsNumber;

    //初期化作業
    public CSVFileIO(String FileName) {
        this.LONG_LIMIT = 100000000L;

        this.FilePath = "/home/TAasamoto/NumberProject/CSV/";
        this.FileName = FileName;
        if (this.FileName == null || "".equals(this.FileName)) {
            this.FileName = "PrimeList.csv";
        }
        this.LlongNotContainsNumber = new ArrayList<>();
    }

    /**
     * 数値が格納された配列を出力する関数
     *
     * @return
     */
    public ArrayList<Long> getNumberList() {
            ArrayList<Long> LlongNumberList = new ArrayList<>();
            File file = new File(FilePath + FileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                for (String col : cols) {
                    if(isNumber(col)){
                        LlongNumberList.add(Long.parseLong(col));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return LlongNumberList;
        }
    }

    public void outCSVfile() {

        try {
            //出力先を作成する
            FileWriter fw = new FileWriter(FilePath + FileName, true);          //true : appendモード(行追加)
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            //ファイル
            for (long lnconNum : LlongNotContainsNumber) {

                if (lnconNum < LONG_LIMIT) {
                    String strNum = String.valueOf(lnconNum);
                    pw.println(strNum);
                }
            }
            //ファイルに書き出す
            pw.close();

            //csvファイルに含まれない数値リストの参照をはずし、新しくリストを作成する。
            LlongNotContainsNumber = new ArrayList<>();

            //終了メッセージを画面に出力する
            System.out.println("出力が完了しました。");

        } catch (IOException ex) {
            //例外時処理
            ex.printStackTrace();
        } finally {

        }
    }

    public void addNumberList(long longInputNum) {
        if (longInputNum < LONG_LIMIT) {
            LlongNotContainsNumber.add(longInputNum);
        }
    }

}
