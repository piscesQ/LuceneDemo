<%@ page import="java.io.File" %>
<%@include file="header.jsp"%>
<% /* Author: Andrew C. Oliver (acoliver2@users.sourceforge.net) */ %>
<%@ page contentType="text/html;charset=utf-8"%>

<div align="center">

    <%
        int currentPageNum = 1;
        int itemNum = 5;
        try{
            itemNum = Integer.parseInt((String)session.getAttribute("itemNum"));
        }catch(Exception e){
            System.err.println("itemNum format error");
        }
    %>

        <table border="0">
            <tr><td>文档路径：</td><td><input id="document" type="text" name="document" size="44"></td></tr>
            <tr align="center"><td colspan="2">
                <input id="firstPage" type="button" value="首页">
                <input id="prePage" type="button" value="上一页">
                <input id="nextPage" type="button" value="下一页">
                <input id="lastPage" type="button" value="尾页">
            </td></tr>
        </table>


</div>
<script>

</script>
<%@include file="footer.jsp"%>
