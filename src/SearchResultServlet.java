import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import bean.PageBean;
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
        String itemNum = request.getParameter("item_num");
        String currentPage = request.getParameter("current_page");
        int intCurrentPage = Integer.parseInt(currentPage);
        if (intCurrentPage < 1) {
            intCurrentPage = 1;
        }
        System.out.println("-------=============" + currentPage);
        String indexPath = (String)request.getSession().getAttribute("indexPath");
        if(indexPath == null || indexPath.length() == 0){
            indexPath = (String)request.getSession().getAttribute("defaultIndexPath");
        }

        request.getSession().setAttribute("keyword",keyword);
        request.getSession().setAttribute("itemNum",itemNum);

        System.out.println("yanbin search ! . keyword " +keyword);
        try {
            Hits hits = searchResult(keyword, indexPath);
            PageBean pageBean = new PageBean(String.valueOf(intCurrentPage), itemNum, hits);
            if (intCurrentPage > pageBean.getMaxPage()) {
                pageBean.setIntCurrentPage(pageBean.getMaxPage());
            }
            request.setAttribute("pageBean", pageBean);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private Hits searchResult(String keyword, String indexPath) throws IOException, ParseException {
        System.out.println("yanbin search begin. ");
        Hits hits = null;
        Query query = null;
        IndexSearcher searcher = new IndexSearcher(indexPath);

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
                System.out.println(i + " 路径 ：" + document.getField("path").stringValue());
                System.out.println(i + " 内容 ：" + document.getField("body"));
            }
        }
        System.out.println("yanbin search end. ");
        return hits;
    }
}
