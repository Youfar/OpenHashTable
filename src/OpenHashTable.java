import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

/**
 * Created by youfar on 15/6/22.
 * 需要添加读文件      -- success
 * 添加时间           -- success
 * 开放定址法怎么回事，现在的实现就是把单词插到数组上
 * 结果应该是什么样的
 */
public class OpenHashTable implements MyHashTable{

    private class Entry {
        String key;

        public Entry(String key){
            this.key = key;
        }
    }

    //final static int MAX = 500;

    Entry[] table;

    //int n = 0;

    int bucketSize;

    public OpenHashTable (int bucketSize) {
        this.table = new Entry[bucketSize];
        this.bucketSize = bucketSize;
    }

    public boolean insert(String key){
        /*if(search(key) == true)
            return false;*/
        int value = this.myHash(key);
        while(!table[value].key.equals(key)){
            value = (value + 1) % this.bucketSize;
        }
        table[value] = new Entry(key);
        return true;
    }

    //查找函数，找到返回true,找不到返回false
    public boolean search(String key){
        for(int i = 0; i < this.bucketSize; i++){
            if(table[i].key.equals(key)){
                return true;
            }
        }
        return false;
    }

    public boolean delete(String key){
        /*if(n <= 0){
            System.err.println("this data is not insert");
            System.exit(1);
        }*/
        for(int i = 0; i < this.bucketSize; i++){
            if(table[i].key.equals(key)){
                if(i+1 < this.bucketSize) {
                    table[i] = table[i + 1];
                    return true;
                }
            }
        }
        table[--this.bucketSize] = null;
        return false;
    }

    public int myHash(String str) {
        int sum = 0;
        for(int i = 0; i < str.length(); i++){
            sum += (int)str.charAt(i);
        }
        int hashValue = sum%this.bucketSize;
        return hashValue;
        //return sum % this.bucketSize;
    }
   

    /*public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.bucketSize; i++){
            sb.append("key").append(i).append(" -- ").append(table[i].key).append("\n");
        }
        return sb.toString();
    }*/

    public String readFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("subwordlist.txt"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while(line != null){
            sb.append(line);
            sb.append("\n");
            line = br.readLine();

        }
        br.close();
        return sb.toString();
    }


    public static void main(String[] args) {
        int dataNumber = 500;
        double number = 500;
        double loadFactor = dataNumber / number;

        OpenHashTable table = new OpenHashTable(600);
        //System.out.println(table.toString());
        try {
            String result = table.readFile();
            String[] resultArray = result.split("\\n");

            System.out.println("load factor is " + loadFactor);

            //insert
            long insertBeginNanoTime = System.nanoTime();
            for(int i = 0; i < resultArray.length; i++){
                table.insert(resultArray[i]);
            }
            long insertEndNanoTime = System.nanoTime();
            long insertCostNanoTime = insertEndNanoTime - insertBeginNanoTime;
            System.out.println("insert use " + insertCostNanoTime + " ns");
            //System.out.println(table.toString());

            //search
            String searchKey = new String("Abele");
            boolean searchResult;

            long searchBeginTime = System.nanoTime();
            searchResult = table.search(searchKey);
            long searchEndTime = System.nanoTime();
            long searchCostTime = searchEndTime - searchBeginTime;

            if(searchResult != false){
                System.out.println(searchKey + " search success, use " + searchCostTime + " ns");
            } else {
                System.out.println(searchKey + " search failed");
            }

            //delete
            String deleteKey = new String("Angela");
            boolean deleteResult;

            long deleteBeginTime = System.nanoTime();
            deleteResult = table.delete(deleteKey);
            long deleteEndTime = System.nanoTime();
            long deleteCostTime = deleteEndTime - deleteBeginTime;

            if(deleteResult){
                System.out.println(deleteKey + " delete success, use " + deleteCostTime + " ns");
            } else {
                System.out.println(deleteKey + " delete failed");
            }
            //System.out.println(table.toString());

            File writename = new File("2output9.txt");
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            String s1 = String.valueOf(loadFactor);
            String s2 = String.valueOf(insertCostNanoTime);
            String s3 = String.valueOf(searchCostTime);
            String s4 = String.valueOf(deleteCostTime);
            out.write(s1 + "\t" + s2 + "\t" + s3 + "\t" + s4);
            out.flush();
            out.close();

        } catch (IOException e) {
            System.out.println("IO error");
        }


        /*table.insert(1, "one");
        table.insert(10, "ten");
        table.insert(2, "two");
        table.insert(5, "five");
        System.out.println(table.toString());

        //search
        String searchKey = new String("Abele");
        boolean x = table.search(10);
        if(x != false){
            System.out.println("find");
        } else {
            System.out.println("not found");
        }
        System.out.println();

        //delete
        table.delete(2);
        System.out.println(table.toString());
        table.delete(5);
        System.out.println(table.toString());*/



    }


}
