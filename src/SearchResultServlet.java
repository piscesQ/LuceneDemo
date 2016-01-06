import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("yanbin search ! . ");
        String keyword = request.getParameter("keyword");
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
        IndexSearcher searcher = new IndexSearcher("/home/yan/work/LuceneIndex");

        Analyzer analyzer = new StandardAnalyzer();
        try {
            QueryParser qp = new QueryParser("body", analyzer);
            query = qp.parse(keyword);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (searcher != null) {
            hits = searcher.search(query);
            if (hits.length() > 0) {
                System.out.println(" 找到: " + hits.length() + "  个结果! ");
            }
        }
    }
}
