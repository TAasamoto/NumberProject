/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MathUtils.Prime;

import java.util.ArrayList;
import FileIO.CSVFileIO;
import java.io.IOException;

/**
 *
 * @author A.Toshiki
 */
public class Prime {

    private ArrayList<String> LstrSubOption;                                    //「-」のついた、基本機能追加オプションを格納するリスト
    private ArrayList<Long> LlongNumberArgs;                                    //整数の引数を格納するリスト
    private ArrayList<String> tmpLstrSubOption;                                 //追加オプション一時保存用配列
    private ArrayList<Long> LlongPrimeList;                                     //素数配列格納リスト
    private ArrayList<Long> LlongDiffPrimeList;                                 //素数差分配列格納リスト
    private long longRepeatLimit;                                               //addLimit回数制限値
    private String filenamePrimeList;                                           //PrimeList.csv
    private String filenameDiffPrimeList;                                       //DiffPrimeList.csv
    private String filenameMaxPrime;                                            //MaxPrime.csv
    
    
    public Prime(ArrayList<String> LstrSubOption, ArrayList<Long> LlongNumberArgs) {
        this.tmpLstrSubOption = LstrSubOption;
        this.LlongNumberArgs = LlongNumberArgs;
        longRepeatLimit = 500000;                                                  //繰り返し入力回数制限
        filenameDiffPrimeList = "DiffPrimeList.csv";
        filenamePrimeList = "PrimeList.csv";
        filenameMaxPrime = "MaxPrime.csv";
        formatSubOption("");
    }
    
    /**
     * 機能実行メソッド
     * 
     */
    public void doOperate(){
        String strSubOption = LstrSubOption.get(0);
        System.out.println(strSubOption);
        switch(strSubOption){
            
            //チェックメソッド呼び出し
            case "-Check":
                long longCheckNumber = LlongNumberArgs.get(0);
                checkPrime(longCheckNumber);
                break;
                
            //複数回素数計算メソッド呼び出し
            case "-AddRepeat":
                long longRepeatNum = LlongNumberArgs.get(0);
                addPrimeList(longRepeatNum);
                break;
                
            //差分csvファイル出力めメソッド呼び出し
            case "-Diff":
                outDiffPrimeList();
                break;
                
            //素数csvファイル出力メソッド呼び出し
            case "-OutputPrimeList":
                outDiffPrimeList();
                break;
            
        }
    }
    
    /**
     * CSVファイルから現在の計算できている素数の最大値を取得する
     * @return 現在の素数最大値
     */
    public long getMaxPrimefromCSV(){
            CSVFileIO fileMaxPrime = new CSVFileIO(filenameMaxPrime);
            ArrayList<Long> LlongPL = fileMaxPrime.getNumberList();
            return LlongPL.get(0);
    }
    
    /**
     * 素数差分リストから素数リストを生成
     * @return
     */
    public ArrayList<Long> getLlongPrimeListfromDiff(){
        
        //素数差分csvファイル読み込み
        CSVFileIO fileDiffPrimeList = new CSVFileIO(filenameDiffPrimeList);
        
        //素数をリストに格納
        LlongDiffPrimeList = fileDiffPrimeList.getNumberList();
        
        //返り値の作成
        ArrayList<Long> rtnLlongPrimeList = new ArrayList<>();
        long longPrime = 2;
        rtnLlongPrimeList.add(longPrime);
        for(long longDiffPrime: LlongDiffPrimeList){
            longPrime += longDiffPrime;
            rtnLlongPrimeList.add(longPrime);
        }
        
        //出力
        return rtnLlongPrimeList;
    }
    
    /**
     * DiffPrimeListからPrimeListを出力する。
     */
    public void OutputLongPrimeListfromDiff(){
        
        CSVFileIO filePrimeList = new CSVFileIO(filenamePrimeList);
        for(long longPrime: getLlongPrimeListfromDiff()){
            filePrimeList.addNumberList(longPrime);
        }
        filePrimeList.outCSVfile(false);
    }
    
    
    
    
    

    //素数かどうかを調べるメソッド。再起計算を行うので注意。

    /**
     * 素数かどうかを調べるメソッド。再起処理中に見つけた素数は新しく登録します。
     * @param longCheckNumber
     * @return
     */
    public boolean checkPrime(long longCheckNumber){
        
        //初期値設定
        boolean boolRtnCheckPrime = true;
        long longDiffPrime;
        LlongPrimeList = getLlongPrimeListfromDiff();
        CSVFileIO fileDiffPrimeList = new CSVFileIO(filenameDiffPrimeList);
        CSVFileIO fileMaxPrime = new CSVFileIO(filenameMaxPrime);
        
        System.out.println("checkPrime : " + longCheckNumber);
        
        //既存の素数リストにない場合
        if(!LlongPrimeList.contains(longCheckNumber)){
            
            //既存の素数の最大値
            long longMaxPrimeNumber = getMaxPrimefromCSV();
            
            //計算制限値
            long longCheckLimit = getCheckLimit(longCheckNumber);
            
            //調べる数値の平方根が既存の素数の最大値より小さい場合
            if(longCheckLimit < longMaxPrimeNumber){
                
                for(long PrimeNum: LlongPrimeList){
                    if(PrimeNum > longCheckLimit)break;
                    
                    //素数で割り切れる場合は素数でない
                    if(longCheckNumber % PrimeNum == 0)boolRtnCheckPrime = false;
                }
                
            //調べる数値の平方根が既存の素数の最大値より大きい場合
            //(= 調べるべき数値が既存の数値リストにない場合)
            }else{
                long tmplongMaxPrimeNumber = longMaxPrimeNumber;
                
                //因数分解を試みる数値が調べる数値の平方根よりも小さい間
                while(tmplongMaxPrimeNumber <= longCheckLimit){
                    tmplongMaxPrimeNumber += 2;
                    
                    //除算値が素数である場合
                    if(checkPrime(tmplongMaxPrimeNumber)){
                        
                        //素数で割り切れる場合は素数でない
                        if(longCheckNumber % tmplongMaxPrimeNumber == 0)boolRtnCheckPrime = false;
                        
                        //新しく素数がわかったので、csvファイルに追加
                        longDiffPrime = tmplongMaxPrimeNumber - longMaxPrimeNumber;
                        fileDiffPrimeList.addNumberList(longDiffPrime);
                        fileMaxPrime.addNumberList(tmplongMaxPrimeNumber);
                        System.out.println(tmplongMaxPrimeNumber + "を素数リストに追加します。");
                    }
                //新しくわかった素数を追加保存
                fileDiffPrimeList.outCSVfile(true);
                fileMaxPrime.outCSVfile(false);
                }
            }
        }
        
        //素数判別結果出力
        if(boolRtnCheckPrime){
            System.out.println(String.valueOf("素数チェック : [ " + longCheckNumber) + " ]は素数です。");
        }else{
            System.out.println(String.valueOf("素数チェック : [ " + longCheckNumber) + " ]は素数ではありません。");
        }
        
        return boolRtnCheckPrime;
    }
    
    /**
     * 指定の回数だけ、素数リストの最大値から素数判定を行うメソッド
     * 
     * @param longRepeatNum
     * 判定を行う回数
     * 
     */
    public void addPrimeList(long longRepeatNum){
        
        CSVFileIO fileDiffPrimeList = new CSVFileIO(filenameDiffPrimeList);
        CSVFileIO fileMaxPrime = new CSVFileIO(filenameMaxPrime);
        LlongPrimeList = getLlongPrimeListfromDiff();
        
        //制限値以上の場合は制限値まで回数を減らす。
        if(longRepeatNum > longRepeatLimit)longRepeatNum = longRepeatLimit;
        
        //既存の素数の最大値
        long longMaxPrimeNumber = getMaxPrimefromCSV();
        System.out.println(longMaxPrimeNumber);
        
        //ループ用変数宣言
        long tmplongMaxPrimeNumber = longMaxPrimeNumber;
        
        //規定の回数だけ素数を計算する
        for(long i = 0L; i < longRepeatNum; i++){
            tmplongMaxPrimeNumber += 2;
            
            //素数の場合はリストに追加
            if(checkPrime(tmplongMaxPrimeNumber)){
                fileDiffPrimeList.addNumberList(tmplongMaxPrimeNumber - longMaxPrimeNumber);
                System.out.println(tmplongMaxPrimeNumber + "を素数リストに追加します。");
                longMaxPrimeNumber = tmplongMaxPrimeNumber;
            }
        }
        //新しくわかった素数を追加保存
        fileMaxPrime.addNumberList(tmplongMaxPrimeNumber);
        fileDiffPrimeList.outCSVfile(true);
        fileMaxPrime.outCSVfile(false);
    }
    
    /**
     * 素数の差分をとったリストをcsvファイルに出力します。
     * 
     */
    public void outDiffPrimeList(){
        
        CSVFileIO csvDPL = new CSVFileIO(filenameDiffPrimeList);
        CSVFileIO csvPL = new CSVFileIO(filenamePrimeList);
        LlongPrimeList = csvPL.getNumberList();
        LlongDiffPrimeList = csvDPL.getNumberList();
        
        long longSmallNum = 0;
        long longBigNum = 0;
        
        for(long longPrime: LlongPrimeList){
            System.out.println(longPrime);
            longSmallNum = longBigNum;
            longBigNum = longPrime;
            if(longSmallNum != 0){
                csvDPL.addNumberList(longBigNum - longSmallNum);
            }
        }
        System.out.println(longBigNum + " " + longSmallNum);
        csvDPL.outCSVfile(false);
    }
    
    
    
    
    
    
    
    
    
    //追加オプション整形メソッド
    //機能を追加したら略称をこのメソッドに書くこと。
    public String formatSubOption(String strSubOption){
        String rtnstrSubOption = "";
        LstrSubOption = new ArrayList<>();
        for(String so: tmpLstrSubOption){
            switch(so){
                
                //checkオプション指定時
                case "-c":
                    LstrSubOption.add("-Check");
                    rtnstrSubOption = "-Check";
                    break;
                case "-C":
                    LstrSubOption.add("-Check");
                    rtnstrSubOption = "-Check";
                    break;
                case "-check":
                    LstrSubOption.add("-Check");
                    rtnstrSubOption = "-Check";
                    break;
                case "-Check":
                    LstrSubOption.add("-Check");
                    rtnstrSubOption = "-Check";
                    break;
                
                //addオプション指定時
                case "-a":
                    LstrSubOption.add("-Add");
                    rtnstrSubOption = "-Add";
                    break;
                case "-A":
                    LstrSubOption.add("-Add");
                    rtnstrSubOption = "-Add";
                    break;
                case "-add":
                    LstrSubOption.add("-Add");
                    rtnstrSubOption = "-Add";
                    break;
                case "-Add":
                    LstrSubOption.add("-Add");
                    rtnstrSubOption = "-Add";
                    break;
                
                //addrepeatオプション指定時
                case "-ar":
                    LstrSubOption.add("-AddRepeat");
                    rtnstrSubOption = "-AddRepeat";
                    break;
                case "-AR":
                    LstrSubOption.add("-AddRepeat");
                    rtnstrSubOption = "-AddRepeat";
                    break;
                case "-addrepeat":
                    LstrSubOption.add("-AddRepeat");
                    rtnstrSubOption = "-AddRepeat";
                    break;
                case "-AddRepeat":
                    LstrSubOption.add("-AddRepeat");
                    rtnstrSubOption = "-AddRepeat";
                    break;
                    
                
                //Diffオプション指定時
                case "-d":
                    LstrSubOption.add("-Diff");
                    rtnstrSubOption = "-Diff";
                    break;
                case "-D":
                    LstrSubOption.add("-Diff");
                    rtnstrSubOption = "-Diff";
                    break;
                case "-diff":
                    LstrSubOption.add("-Diff");
                    rtnstrSubOption = "-Diff";
                    break;
                case "-Diff":
                    LstrSubOption.add("-Diff");
                    rtnstrSubOption = "-Diff";
                    break;
                    
                
                case "-opl":
                    LstrSubOption.add("-OutputPrimeList");
                    rtnstrSubOption = "-OutputPrimeList";
                    break;
                case "-OPL":
                    LstrSubOption.add("-OutputPrimeList");
                    rtnstrSubOption = "-OutputPrimeList";
                    break;
                case "-OutPrimeList":
                    LstrSubOption.add("-OutputPrimeList");
                    rtnstrSubOption = "-OutputPrimeList";
                    break;
                case "-outprimelist":
                    LstrSubOption.add("-OutputPrimeList");
                    rtnstrSubOption = "-OutputPrimeList";
                    break;
                case "-outputprimelist":
                    LstrSubOption.add("-OutputPrimeList");
                    rtnstrSubOption = "-OutputPrimeList";
                    break;
                case "-OutputPrimeList":
                    LstrSubOption.add("-OutputPrimeList");
                    rtnstrSubOption = "-OutputPrimeList";
                    break;
                
                    
                    
                    
                //
                default:
                    LstrSubOption.add("-AddRepeat");
                    LlongNumberArgs.add(1L);
                    rtnstrSubOption = "-AddRepeat";
                    break;
            }
        }
        return rtnstrSubOption;
    }
    
    
    //素数チェックの最大値を決める。
    //任意の素数ではない数の因数の真ん中の数値は限りなくその値の平方根に近い。
    //つまりもしも素数でない場合、因数はその数値の平方根以下の数値を一つ以上持つ。
    //よって、平方根以下の数値を計算しないために制限を出す。
    //このメソッドは、引数の数値の平方根に一番近い整数を返す。
    private Long getCheckLimit(long longCheckNumber){
        long rtnlongLimitNumber = longCheckNumber;
        
        for(int i = 0; i < 10; i++){
            rtnlongLimitNumber = ((rtnlongLimitNumber + (longCheckNumber / rtnlongLimitNumber + 1)) / 2) + 1;
        }
        return rtnlongLimitNumber;
    }
    
}
