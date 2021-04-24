package com.cxylk.classpath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname CompositeEntry
 * @Description 由更小的Entry组成，正好可以表示成[]Entry
 * @Author likui
 * @Date 2021/4/23 14:24
 **/
public class CompositeEntry implements Entry{
    private List<Entry> entries=new ArrayList<>();

    public CompositeEntry(String pathList){
        for (String path : pathList.split(File.pathSeparator)) {
            entries.add(Entry.create(path));
        }
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        for (Entry entry : entries) {
            try {
                return entry.readClass(className);
            }catch (Exception ignored){

            }
        }
        throw new Exception("class not found："+className);
    }
}
