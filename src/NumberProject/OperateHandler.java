/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NumberProject;

import Help.Help;
import MathUtils.Prime;
import java.util.ArrayList;

/**
 *
 * @author A.Toshiki
 */
public class OperateHandler {
    private String strMainOption = "";                                              //「--」のついた、基本機能についての情報を格納する変数
    private ArrayList<String> LstrSubOption = new ArrayList<>();                    //「-」のついた、基本機能追加オプションを格納するリスト
    private ArrayList<Long> LlongNumberArgs = new ArrayList<>();                    //整数の引数を格納するリスト
    
    public OperateHandler(String strMainOption, ArrayList<String> LstrSubOption, ArrayList<Long> LlongNumberArgs){
        this.LlongNumberArgs = LlongNumberArgs;
        this.LstrSubOption = LstrSubOption;
        this.strMainOption = strMainOption;
    }
    
    
    public void doOperate(){
        
        switch (strMainOption){
            
            //概要ヘルプ呼び出しの場合
            case "--Help":
                Help help = new Help(strMainOption);
                help.SysOutPrintHelp();
                break;
            
                
            //PrimeNumber呼び出しの場合
            case "--PrimeNumber":
                Prime objPrimeNumber = new Prime(LstrSubOption, LlongNumberArgs);
                objPrimeNumber.doOperate();
                break;
                
                
            //双子素数の場合
            case "--TwinPrimeNumber":
                break;
                
                
        }
    }
    

    
    
    
    
    
}
