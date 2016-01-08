<%@ page import="java.io.File" %>
<%@ page import="org.apache.lucene.search.Hits" %>
<%@ page import="org.apache.lucene.document.Document" %>
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
        %>

	<form  action="CreateIndexServlet" method="post">
        <table border="0" cellpadding="10">
           <tr><td>文档路径：</td><td><input id="document" type="text" name="document" size="44"></td></tr>
           <tr><td>索引路径：</td><td><input id="index" type="text" name="index" size="44" value=<%=indexPath%>></td></tr>
           <tr align="center"><td colspan="2"><input id="createIndexSubmit" type="submit" value="建立索引"></td></tr>
        </table>
    </form>
	
	<form  action="SearchResultServlet" method="post">
        <table border="0" cellpadding="10">
           <tr><td>查询关键字：</td><td><input type="text" name="keyword" size="20"></td><td>每页条目：</td><td><input type="text" name="item_num" size="5" value="5"></td></tr>
           <tr align="center"><td colspan="4"><input type="submit" value="查询"></td></tr>
        </table>
    </form>
</div>

<div align="center">

    <%-- search result show --%>
    <%
        int currentPageNum = 1;
        int itemNum = 5;
        try{
            itemNum = Integer.parseInt((String)session.getAttribute("itemNum"));
        }catch(Exception e){
            System.err.println("itemNum format error");
        }
    %>

    <table border="0" cellpadding="10">
        <%
            Hits hits = (Hits)request.getAttribute("hits");
            if(hits != null && hits.length() > 0){
                for(int i = 0; i < itemNum && i < hits.length(); i++){
                    Document document = hits.doc(i);
        %>
        <tr><td align="center" width="30"><%=i%></td><td><%=document.getField("path").stringValue()%></td></tr>
        <% 
                }
            }
        %>
        <tr align="center" valign="bottom"><td colspan="2" height="50">
            <input id="firstPage" type="button" value="首页">
            <input id="prePage" type="button" value="上一页">
            <input id="nextPage" type="button" value="下一页">
            <input id="lastPage" type="button" value="尾页">
        </td></tr>
    </table>


</div>

<script>
    document.getElementById("createIndexSubmit").onclick = createIndexButtonOnClick();
    function createIndexButtonOnClick(){
        var documentPath = document.getElementById("document").innerHTML;
        alert("documentPath = " + documentPath);
    }
</script>
<%@include file="footer.jsp"%>
