/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MathUtils;

import java.util.ArrayList;
import FileIO.CSVFileIO;

/**
 *
 * @author A.Toshiki
 */
public class Prime {

    private ArrayList<String> LstrSubOption;                                    //「-」のついた、基本機能追加オプションを格納するリスト
    private ArrayList<Long> LlongNumberArgs;                                    //整数の引数を格納するリスト
    private ArrayList<String> tmpLstrSubOption;                                 //追加オプション一時保存用配列
    private ArrayList<Long> LlongPrimeList;                                     //素数配列格納リスト
    
    
    
    public Prime(ArrayList<String> LstrSubOption, ArrayList<Long> LlongNumberArgs) {
        this.tmpLstrSubOption = LstrSubOption;
        this.LlongNumberArgs = LlongNumberArgs;
        formatSubOption("");
    }
    
    
    
    
    
    
    public void doOperate(){
        String strSubOption = LstrSubOption.get(0);
        switch(strSubOption){
            case "-Check":
                long longCheckNumber = LlongNumberArgs.get(0);
                checkPrime(longCheckNumber);
        }
    }
    
    
    
    
    
    
    

    //素数かどうかを調べるメソッド。再起計算を行うので注意。
    public boolean checkPrime(long longCheckNumber){
        
        //初期値設定
        boolean boolRtnCheckPrime = true;
        CSVFileIO csvFile = new CSVFileIO("");
        LlongPrimeList = csvFile.getNumberList();
        
        //既存の素数リストにない場合
        if(!LlongPrimeList.contains(longCheckNumber)){
            
            //既存の素数の最大値
            long longNumber = LlongPrimeList.get(LlongPrimeList.size() - 1);
            
            //調べる数値の平方根が既存の素数の最大値より小さい場合
            if(getCheckLimit(longCheckNumber) < longNumber){
                
                for(long PrimeNum: LlongPrimeList){
                    if(PrimeNum > getCheckLimit(longCheckNumber))break;
                    
                    //素数で割り切れる場合は素数でない
                    if(longCheckNumber % longNumber == 0)boolRtnCheckPrime = false;
                }
                
            //調べる数値の平方根が既存の素数の最大値より大きい場合
            //(= 調べるべき数値が既存の数値リストにない場合)
            }else{
                
                //因数分解を試みる数値が調べる数値の平方根よりも小さい間
                while(longNumber <= getCheckLimit(longCheckNumber)){
                    longNumber += 2;
                    
                    //除算値が素数である場合
                    if(checkPrime(longNumber)){
                        
                        //素数で割り切れる場合は素数でない
                        if(longCheckNumber % longNumber == 0)boolRtnCheckPrime = false;
                        
                        //新しく素数がわかったので、csvファイルに追加
                        csvFile.addNumberList(longNumber);
                    }
                //新しくわかった素数を追加保存
                csvFile.outCSVfile();
                }
            }
        }
        
        //素数かどうか判別する
        if(boolRtnCheckPrime){
            System.out.println(String.valueOf("素数チェック : [ " + longCheckNumber) + " ]は素数です。");
        }else{
            System.out.println(String.valueOf("素数チェック : [ " + longCheckNumber) + " ]は素数ではありません。");
        }
        
        return boolRtnCheckPrime;
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
            rtnlongLimitNumber = (longCheckNumber + (rtnlongLimitNumber / 2 + 1) / 2 + 1);
        }
        return rtnlongLimitNumber;
    }
    
}
