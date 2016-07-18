/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numberproject;

import Help.Help;
import java.util.ArrayList;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author A.Toshiki
 */
public class NumberProject {

    /**
     * @param args the command line arguments
     * 機能を追加した場合、略称をformatメソッドにも記述すること。
     */
    public static void main(String[] args) {
        String strMainOption = "";                                              //「--」のついた、基本機能についての情報を格納する変数
        ArrayList<String> LstrSubOption = new ArrayList<>();                    //「-」のついた、基本機能追加オプションを格納するリスト
        ArrayList<Long> LlongNumberArgs = new ArrayList<>();                    //整数の引数を格納するリスト
        
        System.out.println("-------NumberProject Start-------");
        
        
        
        //引数を変数もしくは配列に格納する
        for(String input: args){
            
            //入力が数値の場合
            if(NumberUtils.isNumber(input)){
                
                //入力が負の値の場合、小数点がある場合、18桁以上の数値の場合
                if(input.contains("-") || input.contains(".") || input.length() > 18){
                    System.out.println("引数エラー : " + input + "は妥当性のない数値です。18桁までの正の整数を入力してください。");
                
                //入力が正しい数値の場合
                }else{
                    long NumberArgs = Long.valueOf(input);
                    LlongNumberArgs.add(NumberArgs);
                    System.out.println("引数 : " + input + "は正しい数値です。");
                }
                
            //入力がオプションの場合
            }else if(input.contains("-")){
                
                //入力オプションが基本機能指定の場合
                if(input.contains("--")){
                    
                    //ひとつ目の基本機能指定の場合
                    if("".equals(strMainOption)){
                        strMainOption = input;
                    
                    //複数回数目の基本機能指定の場合
                    }else{
                        System.out.println("引数エラー : すでに基本機能[" + strMainOption + "]が指定されています。");
                    }
                
                //入力オプションが基本機能追加オプションの場合
                }else{
                    boolean HasSameOption = false;
                    for(String tmpSubOption: LstrSubOption){
                        if(tmpSubOption == null)tmpSubOption = "";
                        if(tmpSubOption.equals(input))HasSameOption = true;
                    }
                    
                    if(HasSameOption){
                        System.out.println("引数エラー : すでに追加オプション[" + input + "]が指定されています。");
                    }else{
                        LstrSubOption.add(input);
                    }
                }
            }
        }
        
        //基本機能オプション内文字列整形
        strMainOption = formatMainOption(strMainOption);
        
        //各機能呼び出し
        switch (strMainOption){
            
            //概要ヘルプ呼び出しの場合
            case "--Help":
                Help help = new Help(strMainOption);
                help.SysOutPrintHelp();
                break;
            
                
            //PrimeNumber呼び出しの場合
            case "--PrimeNumber":
                break;
                
                
            //双子素数の場合
            case "--TwinPrimeNumber":
                break;
                
                
        }
        System.out.println("-------NumberProject End-------");
    }
    
    
    
    //基本機能オプション入力値整形メソッド
    //基本機能を追加したら、下記に略称を記述すること。
    private static String formatMainOption(String strMainOption){
        String strRtnFormatted = "";                                            //返り値設定用変数
        
        switch (strMainOption){
            
            //ヘルプ呼び出しの場合
            case "":
                strRtnFormatted = "--Help";
                break;
            case "--h":
                strRtnFormatted = "--Help";
                break;
            case "--help":
                strRtnFormatted = "--Help";
                break;
            case "--Help":
                strRtnFormatted = "--Help";
                break;
                
            //PrimeNumber呼び出しの場合
            case "--pn":
                strRtnFormatted = "--PrimeNumber";
                break;
            case "--PN":
                strRtnFormatted = "--PrimeNumber";
                break;
            case "--primenumber":
                strRtnFormatted = "--PrimeNumber";
                break;
            case "--PrimeNumber":
                strRtnFormatted = "--PrimeNumber";
                break;
                
            //TwinPrimeNumber呼び出しの場合
            case "--tp":
                strRtnFormatted = "--TwinPrimeNumber";
                break;
            case "--TP":
                strRtnFormatted = "--TwinPrimeNumber";
                break;
            case "--tpn":
                strRtnFormatted = "--TwinPrimeNumber";
                break;
            case "--TPN":
                strRtnFormatted = "--TwinPrimeNumber";
                break;
            case "--twinprimenumber":
                strRtnFormatted = "--TwinPrimeNumber";
                break;
            case "--TwinPrimeNumber":
                strRtnFormatted = "--TwinPrimeNumber";
                break;
        }
        
        return strRtnFormatted;
    }
    
}
