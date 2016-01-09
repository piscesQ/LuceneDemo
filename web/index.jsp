<%@ page import="java.io.File" %>
<%@ page import="org.apache.lucene.search.Hits" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="org.apache.poi.hslf.record.ExAviMovie" %>
<%@ page import="bean.PageBean" %>
<%@include file="header.jsp"%>
<% /* Author: Andrew C. Oliver (acoliver2@users.sourceforge.net) */ %>
<%@ page contentType="text/html;charset=utf-8"%>

<div align="center">
    <%--<form name="search" action="results.jsp" method="get">--%>
		<%--<p>--%>
			<%--<input name="query" size="44"/>&nbsp;Search Criteria--%>
		<%--</p>--%>
		<%--<p>--%>
			<%--<input name="maxresults" size="4" value="100"/>&nbsp;Results Per Page&nbsp;--%>
			<%--<input type="submit" value="Search"/>--%>
		<%--</p>--%>
    <%--</form>--%>

<%-- ================ --%>

        <%
            session.setAttribute("projectName", "LuceneDemo");
            String USER_HOME = System.getProperties().getProperty("user.home");
            session.setAttribute("USER_HOME", USER_HOME);   //store USER_HOME into session
            String indexPath = USER_HOME + File.separator + "wcy" + File.separator + "LuceneIndex";
            session.setAttribute("defaultIndexPath", indexPath);   //default index path

            String documentPath = (String)session.getAttribute("documentPath");
            if(documentPath == null){
                documentPath = "";
            }
            String keyword = (String)session.getAttribute("keyword");
            if(keyword == null){
                keyword = "";
            }
            String strItemNum = (String)session.getAttribute("itemNum");
            if(strItemNum == null){
                strItemNum = "5";
            }
        %>

	<form  action="CreateIndexServlet" method="post">
        <table border="0" cellpadding="10">
           <tr><td>文档路径：</td><td><input id="document" type="text" name="document" size="44" value=<%=documentPath%>></td></tr>
           <tr><td>索引路径：</td><td><input id="index" type="text" name="index" size="44" value=<%=indexPath%>></td></tr>
           <tr align="center"><td colspan="2"><input id="createIndexSubmit" name="createIndexSubmit" type="submit" value="建立索引"></td></tr>
        </table>
    </form>
	
	<form  name="query" action="SearchResultServlet" method="post">
        <table border="0" cellpadding="10">
           <tr><td>查询关键字：</td><td><input id="keyword" type="text" name="keyword" size="20" value=<%=keyword%>></td>
               <td>每页条目：</td>
               <td>
                   <input type="text" name="item_num" size="5" value=<%=strItemNum%>>
                   <input id="current_page" type="hidden" name="current_page" width="0" value="1">
               </td>
           </tr>
           <tr align="center"><td colspan="4"><input type="submit" value="查询"></td></tr>
        </table>
    </form>
</div>

<div align="center">

    <%-- search result show --%>
    <%
//        int currentPageNum = 1;
//        if(session.getAttribute("currentPageNum") != null){
//            try {
//                currentPageNum = Integer.parseInt((String)session.getAttribute("currentPageNum"));
//            }catch (Exception e){
//                System.out.println("index.jsp currentPageNum format error");
//            }
//        }

        int itemNum = 5;
        try{
            itemNum = Integer.parseInt(strItemNum);
        }catch(Exception e){
            System.out.println("index.jsp strItemNum format error");
        }
        try{
            itemNum = Integer.parseInt((String)session.getAttribute("itemNum"));
        }catch(Exception e){
            System.err.println("itemNum format error");
        }
    %>

    <table border="0" cellpadding="10">
        <%
            PageBean pageBean = (PageBean)request.getAttribute("pageBean");
            if(pageBean != null && pageBean.getHits() != null && pageBean.getHits().length() > 0){
                Hits hits = pageBean.getHits();
                int beanCurrentPage = pageBean.getIntCurrentPage();
                int beanMaxPage = pageBean.getMaxPage();
        %>
        <tr><td align="center" width="60">序号</td><td align="center">文件路径</td></tr>
                <%
                for(int i =(beanCurrentPage - 1) * itemNum; i < itemNum * beanCurrentPage && i < hits.length(); i++){
                    Document document = hits.doc(i);
        %>
        <tr><td align="center" width="60"><%=i + 1%></td><td><%=document.getField("path").stringValue()%></td></tr>
        <%
                }%>
        <tr align="center" valign="bottom"><td colspan="2" height="50">
            <input id="firstPage" type="button" value="首页" onclick="refreshPageNum(1)">
            <input id="prePage" type="button" value="上一页" onclick="refreshPageNum(<%=beanCurrentPage - 1%>)">
            <input id="nextPage" type="button" value="下一页" onclick="refreshPageNum(<%=beanCurrentPage + 1%>)">
            <input id="lastPage" type="button" value="尾页" onclick="refreshPageNum(<%=beanMaxPage%>)">
        </td></tr>
        <%
            }
        %>

    </table>
</div>

<script>
    document.getElementById("createIndexSubmit").onclick = function(){
        var documentPath = document.getElementById("document").value;
        if(documentPath == null || documentPath.length == 0){
            alert("documentPath 不能为空！ " + documentPath);
        }else{
            document.createIndexSubmit.submit();
        }
    }
    function refreshPageNum(pageNum){
//        if(pageNum < 1 || pageNum > beanMaxPage){
//            return;
//        }
//        session.setAttribute("currentPageNum",pageNum);
        document.getElementById("current_page").value = pageNum;
        document.query.submit();
    }
//IOException=org.apache.pdfbox.exceptions.WrappedIOException
</script>
<%@include file="footer.jsp"%>
