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

	<form  action="CreateIndexServlet" method="post">
        <table border="0">
           <tr><td>文档路径：</td><td><input type="text" name="document" size="44"></td></tr>
           <tr><td>索引路径：</td><td><input type="text" name="index" size="44" value="D:\LuceneIndex"></td></tr>
           <tr align="center"><td colspan="2"><input type="submit" value="建立索引"></td></tr>
        </table>
    </form>
	
	<form  action="SearchResultServlet" method="post">
        <table border="0">
           <tr><td>查询关键字：</td><td><input type="text" name="keyword" size="20"></td><td>每页条目：</td><td><input type="text" name="item_num" size="5" value="5"></td></tr>
           <tr align="center"><td colspan="4"><input type="submit" value="查询"></td></tr>
        </table>
    </form>
</div>
<%@include file="footer.jsp"%>
