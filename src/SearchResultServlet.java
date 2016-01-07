import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import sun.rmi.runtime.Log;

/**
 * @author YanBin yanbin@zhongsou.com
 * @version V1.0
 * @Copyright (c) 2016 zhongsou
 * @Description class description
 * @date 2016/1/6
 */
@WebServlet(name = "SearchResultServlet")
public class SearchResultServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");
        System.out.println("yanbin search ! . keyword " +keyword);
        try {
            searchResult(keyword);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void searchResult(String keyword) throws IOException, ParseException {
        System.out.println("yanbin search begin. ");
        Hits hits = null;
        Query query = null;
//        IndexSearcher searcher = new IndexSearcher("/home/yan/work/LuceneIndex");
        IndexSearcher searcher = new IndexSearcher("D:\\LuceneIndex");

        Analyzer analyzer = new StandardAnalyzer();
        try {
            QueryParser qp = new QueryParser("body", analyzer);
            query = qp.parse(keyword);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (searcher != null) {
            hits = searcher.search(query);
            System.out.println("yanbin ， hits.length():" + hits.length());
            if (hits.length() > 0) {
                System.out.println(" find :" + hits.length() + "  result! ");
            }
            for(int i = 0; i < hits.length(); i++){
                Document document = hits.doc(i);
                System.out.println(i + " 路径 ：" + document.getField("path"));
                System.out.println(i + " 内容 ：" + document.getField("body"));
            }
        }
        System.out.println("yanbin search end. ");
    }
}
