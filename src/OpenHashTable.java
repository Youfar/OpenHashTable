import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

/**
 * Created by youfar on 15/6/22.
 * 需要添加读文件      -- success
 * 添加时间           -- success
 * 开放定址法         -- success按照算法导论的方法实现
 * 结果应该是什么样的
 */
public class OpenHashTable implements MyHashTable{

    /*private class Entry {
        String key;

        public Entry(String key){
            this.key = key;
        }
    }*/

    //final static int MAX = 500;

    //Entry[] table;
    String[] table;

    //int n = 0;

    int bucketSize;

    public OpenHashTable (int bucketSize) {
        this.table = new String[bucketSize];
        this.bucketSize = bucketSize;
        for(int i = 1; i < bucketSize; i++){
            table[i] = "NIL";
        }
    }

    //
    /*public boolean insert(String key){
        /*if(search(key) == true)
            return false;
        int value = this.myHash(key);
        /*while(!table[value].key.equals(key)){
            value = (value + 1) % this.bucketSize;
        }
        table[value] = new Entry(key);
        return true;
        for(int i = value; table[i] != null; i = (i+1) % this.bucketSize){
            if(table[i].equals(key)){
                break;
            } else {
                table[i] = key;
            }
        }

        return true;
    }*/

    //按照算法导论上的伪代码来执行
    public boolean insert(String key){
        //int value = this.myHash(key);
        for(int i = 0; i < this.bucketSize; i++){
            int j = this.myHash(key, i);
            if(table[j] == "NIL" || table[j] == "DELETED")
                table[j] = key;
                return true;
        }
        return true;
    }

    //查找函数，找到返回true,找不到返回false,有null错误
    /*public boolean search(String key){
        for(int i = 0; i < this.bucketSize; i++){
            if(table[i].equals(key)){
                return true;
            }
        }
        return false;
    }*/

    //按照算法导论上伪代码来执行,字符串匹配相等不能用==号
    public boolean search(String key){
        int i = 0;
        int j = this.myHash(key, i);

        while(table[j] != "NIL" && i != this.bucketSize){
            if(table[j].equals(key)){
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    public boolean delete(String key){

        for(int i = 0; i < this.bucketSize; i++){
            int j = this.myHash(key, i);
            if(table[j].equals(key)){
                table[j] = "DELETED";
                return true;
            }
        }
        return false;
    }

    public int myHash(String str, int value) {
        int sum = 0;
        for(int i = 0; i < str.length(); i++){
            sum += (int)str.charAt(i);
        }
        int hashValue = (sum + value) % this.bucketSize;
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
        double number = 600;
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
