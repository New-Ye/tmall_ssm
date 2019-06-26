<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<title>模仿天猫官网</title>

<div class="homepageDiv">
		<%--categoryAndcarousel.jsp分类和轮播：
			1.categoryMenu.jsp竖状分类菜单
			2.productsAsideCategorys.jsp竖状分类菜单右侧的推荐产品列表
			3.carousel.jsp轮播
		--%>
	<%@include file="categoryAndcarousel.jsp"%>

		<%--homepageCategoryProducts.jsp：总体17种分类以及每种分类对应的5个产品--%>
	<%@include file="homepageCategoryProducts.jsp"%>	
</div>





