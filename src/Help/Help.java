/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Help;

import java.io.File;

/**
 * Help
 * If use NumberProject without option or use with help option,
 * print how to use on console.
 * 
 * please put "help.txt" in the same directory.
 * 
 * Helpクラス
 * コンソールにヘルプオプションが入力された場合、もしくはオプションがない場合、
 * 標準出力に使い方情報を出力する。
 * 
 * @author A.Toshiki
 */
public class Help {
    private File file;                                                          //ファイルオープン用
    private String strMainOption;                                               //引数確認用
    /**
     * インスタンス生成時
     * 引数に何が入っているかを確認する。nullでも動作する。
     * 
     * @param strMainOption 
     */
    public Help(String strMainOption){
        this.strMainOption = strMainOption;
        if(strMainOption == null){
            strMainOption = "";
        }
    }
    
    /**
     * 呼び出し時の引数によって表示する内容が変わる。
     * 同じパッケージの別のクラスのメソッドを呼び出す形でヘルプを出力する。
     * コンソールで呼び出される機能すべてのヘルプを同パッケージ内に作成すること。
     */
    public void SysOutPrintHelp(){
        switch (strMainOption){
            case "--Help":                                                      //引数がない場合
                Overview overview = new Overview();
                overview.printHelp();
                
                break;
            
            
            
            
            
            
            
            
            
        }
        
        
        
        
        
        
    }
    
    
    
}
