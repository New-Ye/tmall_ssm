<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
	<%--随机挑选一个产品作为推荐产品，来进行高亮显示--%>
$(function(){
	$("div.productsAsideCategorys div.row a").each(function(){
		var v = Math.round(Math.random() *6);
		if(v == 1)
			$(this).css("color","#87CEFA");
	});
});

</script>
<c:forEach items="${cs}" var="c">
	<%--先取出每个分类--%>
	<div cid="${c.id}" class="productsAsideCategorys">
	 
		<c:forEach items="${c.productsByRow}" var="ps">
			<%--取出每个分类的productsByRow集合--%>
			<div class="row show1">
				<c:forEach items="${ps}" var="p">
					<%--根据productsByRow集合，取出每个产品，把产品的subTitle信息里的第一个单词取出来显示--%>
					<c:if test="${!empty p.subTitle}">
						<a href="foreproduct?pid=${p.id}">
							<c:forEach items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
								<c:if test="${st.index==0}">
									${title}
								</c:if>
							</c:forEach>
						</a>
					</c:if>
				</c:forEach>
				<div class="seperator"></div>
			</div>		
		</c:forEach>
	</div>			
</c:forEach>
	
