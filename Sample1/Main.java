package Sample;

import cn.papercheck.algorithm.PaperManager;
import cn.papercheck.algorithm.check.CheckTask;
import cn.papercheck.algorithm.pojo.Paper;
import cn.papercheck.algorithm.pojo.PaperLibrary;
import cn.papercheck.algorithm.pojo.PaperLibraryCore;
import cn.papercheck.authentication.Auth;

import java.io.File;
import java.io.IOException;

/**
 * SDK入门使用范例
 */
public class Main2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取机器码
        System.out.println(Auth.getMachineCode());
        //设置注册码
        PaperManager.INSTANCE.setRegCode("50v9T+1Qz8wpYi/HEQtzcsbLHRJx2B6vvqG+xvDfitKHi2zayzfKyuG6kW/3iJvo=");

        //构加载、建论文库
        PaperLibraryCore paperLibrary = new PaperLibrary();
        File folder = new File("C:\\Users\\admin\\Desktop\\library"); //论文库所在文件夹
        for (File file : folder.listFiles()) { //遍历文件夹中文件
            Paper paper = new Paper(file); //加载为Paper
            paper.setId("001").setTitle("论文标题").setAuthor("论文作者").setYear("论文年份").setSource("论文来源"); //设置论文各项信息
            paperLibrary.addByPaper(paper); //添加到论文库
        }
        paperLibrary.build(); //构建论文库

        //读取转换待查重的论文
        Paper toCheckPaper = new Paper(new File("C:\\Users\\admin\\Desktop\\test.docx"));

        //构建并启动任务
        CheckTask checkTask = PaperManager.INSTANCE
                .getCheckTaskBuilder() //获取查重任务构造器
                .setLibrary(paperLibrary) //设置论文库
                .setToCheckPaper(toCheckPaper) //设置待查论文
                .build(); //构建任务，返回checkTask对象
        checkTask.start(); //启动任务
        checkTask.join(); //等待查重结束

        //保存查重报告
        checkTask.getReporter().saveAsFile("C:\\Users\\admin\\Desktop\\报告1.mht", 1); //保存查重报告1（全文标红）
        checkTask.getReporter().saveAsFile("C:\\Users\\admin\\Desktop\\报告2.mht", 2); //保存查重报告2（原文对照）
    }
}