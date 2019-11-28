package com.example.demo.controller;



import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description  nginx日志分析
 * @Author zhangzhiqiang1
 * @Date 2019/11/6 11:03
 * @Version 1.0
 **/
public class LogParse {
    public static void main(String[] args) throws IOException {

        //String fileName = "D:/3.txt";
        //LogParse.readFileByLines(fileName);
       readFile02();


    }
    public static String parseLine(String str){

        StringBuffer buf = new StringBuffer("");
        String pattern ="([\\d\\.]+) ([\\d\\.]+) - ([\\d\\.]+) \\[(\\S+)\\S+\\] (\\w+) ([\\w\\.]+) \"(\\w+) ([^\\\"]*) ([^\\\"]*)\" (\\d+) ([\\d\\.]+) (\\d+) \"([^\\\"]*)\" \"([^\\\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" -";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(str);
        if(matcher.find()){

            System.out.println(String.format("the real ip:%s", matcher.group(1)));// the real ip
            System.out.println(String.format("the real ip:%s", matcher.group(2)));// the real ip
            System.out.println(String.format("time_local:%s", matcher.group(3)));
            System.out.println(String.format("method:%s", matcher.group(4)));
            System.out.println(String.format("path:%s", matcher.group(5)));
            System.out.println(String.format("protocol:%s", matcher.group(6)));
            System.out.println(String.format("status:%s", matcher.group(7)));
            System.out.println(String.format("body_bytes_sent:%s", matcher.group(8)));
            System.out.println(String.format("http_referer:%s", matcher.group(9)));// http_referer
            System.out.println(String.format("http_user_agent:%s", matcher.group(10)));// http_user_agent
            System.out.println(String.format("request_length:%s", matcher.group(11)));// request_length
            System.out.println(String.format("request_time:%s", matcher.group(12)));// request_time
            System.out.println(String.format("proxy_upstream_name:%s", matcher.group(13)));// proxy_upstream_name
            System.out.println(String.format("upstream_addr_host:%s", matcher.group(14)));// upstream_addr_host
            System.out.println(String.format("upstream_addr_port:%s", matcher.group(15)));// upstream_addr_port
            System.out.println(String.format("upstream_response_length:%s", matcher.group(16)));// upstream_response_length
            System.out.println(String.format("upstream_response_time:%s", matcher.group(17)));// upstream_response_time

        }
        return  buf.toString();
    }

    public static void readFileByLines(String fileName){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            //一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null){
                //显示行号
                System.out.println("line " + line + ": " + parseLine(tempString));

                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    /**
     * 一行一行读取文件，解决读取中文字符时出现乱码
     *
     * 流的关闭顺序：先打开的后关，后打开的先关，
     *       否则有可能出现java.io.IOException: Stream closed异常
     *
     * @throws IOException
     */
    //@Test
    public static void readFile02() throws IOException {
        //需要查询的url
        String exp="/doctorapp/medicine/selectDoctorAuditMedicinesList";
        //本地日志文件地址
        String filePath="D:/4.txt";
        //区分manager和tuangou端口
        String regex=":8092\" ";

        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "UTF-8"));
        String line="";
        String[] arrs=null;
        List<Double> list = new ArrayList<Double>();
        List<Double> listMax = new ArrayList<Double>();
        while ((line=br.readLine())!=null) {
            if (line.contains(exp)) {
                arrs = line.split(regex);
                for (String i : arrs) {
                    if (arrs[1].equals(i)){
                        String[] split = i.split(" ");
                        list.add(Double.valueOf(split[0].substring(1, split[0].length()-1)));
                    }
                }
            }
        }
        System.out.println("接口名称："+exp);
        System.out.println("最大时间："+ Collections.max(list)+"s");
        System.out.println("最小时间："+Collections.min(list)+"s");
        double sum = 0;
        double maxcount=0;
        for(int i=0;i<list.size();i++){
            sum += list.get(i).doubleValue();
        }
        double avg =sum / list.size();
        for(int i=0;i<list.size();i++){
            if (list.get(i).doubleValue()>avg){
                maxcount++;
            }

        }
        System.out.println("平均时间："+avg+"s");
        System.out.println("每天调用次数："+list.size()+"次");
        System.out.println("每天大于平均时间的调用次数"+maxcount+"次");

        br.close();

    }


}