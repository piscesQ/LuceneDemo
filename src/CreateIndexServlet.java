import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author KoreQ
 * @version V1.0
 * @Copyright (c) 2016
 * @Description class description
 * @date 2016/1/6
 */
@WebServlet(name = "CreateIndexServlet")
public class CreateIndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("====-===========-==========" + System.getProperty("file.encoding"));
//        String projectName = (String)request.getSession().getAttribute("projectName");
//        System.out.println("KoreQ projectName = " + projectName);    //test normal
        System.out.println("USER_HOME = " + System.getProperties().getProperty("user.home"));   //test normal


        String documentPath = request.getParameter("document");
        String indexPath = request.getParameter("index");

        if (indexPath == null || indexPath.length() == 0) {
            indexPath = (String) request.getSession().getAttribute("defaultIndexPath");
        }

        request.getSession().setAttribute("documentPath", documentPath);   //lucene document path
        request.getSession().setAttribute("indexPath", indexPath);   //lucene index path

        System.out.println("KoreQ documentPath . " + documentPath);
        System.out.println("KoreQ indexPath . " + indexPath);
        try {
            createIndex(documentPath, indexPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("KoreQ create end . ");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * build index from documentPath
     *
     * @param doucumentPath source path
     * @param indexPath     dest path
     * @throws Exception
     */
    private void createIndex(String doucumentPath, String indexPath) throws Exception {
            /**/ /*  指明要索引文件夹的位置 */
        File fileDir = new File(doucumentPath);

        /**/ /*  这里放索引文件的位置  */
        File indexDir = new File(indexPath);
        Analyzer luceneAnalyzer = new StandardAnalyzer();
        IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer, true);
        File[] textFiles = fileDir.listFiles();
        long startTime = new Date().getTime();

        // 增加document到索引去
        for (int i = 0; i < textFiles.length; i++) {
            String body = "init Content";
            if (textFiles[i].isFile()) {
//                1、txt、doc、pdf、html、ppt、xls和xml；
                if (textFiles[i].getName().endsWith(".txt")) {   //txt文件
                    body = ReadContextUtil.readTxt(textFiles[i].getCanonicalPath());
                } else if (textFiles[i].getName().endsWith(".doc")) {
                    body = ReadContextUtil.readWord(textFiles[i].getCanonicalPath());
                } else if (textFiles[i].getName().endsWith(".pdf")) {
                    body = ReadContextUtil.readPdf(textFiles[i].getCanonicalPath());
                } else if (textFiles[i].getName().endsWith(".html") || textFiles[i].getName().endsWith(".htm")) {
                    body = ReadContextUtil.readHtml(textFiles[i].getCanonicalPath());
                } else if (textFiles[i].getName().endsWith(".ppt")) {
                    body = ReadContextUtil.readPowerPoint(textFiles[i].getCanonicalPath());
                } else if (textFiles[i].getName().endsWith(".xls")) {
                    body = ReadContextUtil.ReadExcel(textFiles[i].getCanonicalPath());
                } else if (textFiles[i].getName().endsWith(".xml")) {
                    body = ReadContextUtil.readTxt(textFiles[i].getCanonicalPath());
                } else {
                    System.out.println("KoreQ 不支持文件类型. ");
                }

                System.out.println("KoreQ File " + textFiles[i].getCanonicalPath() + " 正在被索引. ");
                System.out.println(body);

                Document document = new Document();
                Field FieldPath = new Field("path", textFiles[i].getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED);
                Field FieldBody = new Field("body", body, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS);
                document.add(FieldPath);
                document.add(FieldBody);
                indexWriter.addDocument(document);
            }
        }
        // optimize()方法是对索引进行优化
        indexWriter.optimize();
        indexWriter.close();

        // 测试一下索引的时间
        long endTime = new Date().getTime();
        System.out.println("KoreQ  耗时"
                + (endTime - startTime)
                + "  毫秒! source path"
                + fileDir.getPath());
    }
}
