package com.ivo.mas.system.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public class ComonUtils {

    public static void CheckPath(String path){
        File folder = new File(path);
        // 文件夹路径不存在
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static void CheckFile(String path){
        File file = new File(path);
        // 文件夹路径不存在
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
