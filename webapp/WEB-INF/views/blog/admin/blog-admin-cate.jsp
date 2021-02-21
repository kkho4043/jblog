<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>

</head>

<body>
	<div id="wrap">

		<!-- 개인블로그 해더 -->
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>

		<div id="content">
			<ul id="admin-menu" class="clearfix">
				<li class="tabbtn"><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
				<li class="tabbtn selected"><a href="${pageContext.request.contextPath}/${authUser.id}/admin/cate">카테고리</a></li>
				<li class="tabbtn"><a href="${pageContext.request.contextPath}/${authUser.id}/admin/write">글작성</a></li>
			</ul>
			<!-- //admin-menu -->

			<div id="admin-content">

				<table id="admin-cate-list">
					<colgroup>
						<col style="width: 50px;">
						<col style="width: 200px;">
						<col style="width: 100px;">
						<col>
						<col style="width: 50px;">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>카테고리명</th>
							<th>포스트 수</th>
							<th>설명</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody id="cateList">
						<!-- 리스트 영역 -->
						<!-- 리스트 영역/ -->
					</tbody>
				</table>

				<table id="admin-cate-add">
					<colgroup>
						<col style="width: 100px;">
						<col style="">
					</colgroup>
					<tr>
						<td class="t">카테고리명</td>
						<td><input type="text" name="name" value="" id="cate-Name"></td>
					</tr>
					<tr>
						<td class="t">설명</td>
						<td><input type="text" name="desc" id="cate-dst"></td>
					</tr>
				</table>

				<div id="btnArea">
					<button id="btnAddCate" class="btn_l" type="button">카테고리추가</button>
				</div>
			</div>
			<!-- //admin-content -->
		</div>
		<!-- //content -->


		<!-- 개인블로그 푸터 -->
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>


	</div>
	<!-- //wrap -->
</body>

<script type="text/javascript">
//리스트 출럭 
	$("#documet").ready(function() {
		fetchList();
	});

	//카테고릭 추가
	$("#btnAddCate").on("click", function() {
		
		$.ajax({
			url : "${pageContext.request.contextPath}/${blogVo.id}/admin/cateadd",
			type : "post",
			//contentType : "application/json",
			//dataType : "json",
			data : {
				cateName: $("#cate-Name").val(),
				description:$("#cate-dst").val()},
			success : function(cateVo) {
				console.log(cateVo);
				
				render(cateVo, "down");//게스트 정보 출력
				$("#cate-Name").val(""); 
				$("#cate-dst").val("");
				/*성공시 처리해야될 코드 작성*/
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
		
	});
	
	//리스트 추가(출력)
	function render(cateVo, updown) {
		var str = "";
		str += '<tr>';
		str += '	<td>'+cateVo.cateNo+'</td>';
		str += '	<td>'+cateVo.cateName+'</td>';
		str += '	<td>'+cateVo.amount+'</td>';
		str += '	<td>'+cateVo.description+'</td>';
		str += '	<td class="text-center">';
		str += '			<img class="btnCateDel" src="${pageContext.request.contextPath}/assets/images/delete.jpg" data-no="'+cateVo.cateNo+'" data-amo="'+cateVo.amount+'"> ';
		str += '	</td>';
		str += '</tr>';
		

		if (updown == "down") {
			$("#cateList").prepend(str);
		} else if (updown == "up") {
			$("#cateList").append(str)
		} else {

		}
	}
	function fetchList() {
		$.ajax({

			url : "${pageContext.request.contextPath}/${blogVo.id}/admin/cateList",
			type : "post",
			//contentType : "application/json",
			//data : {},
			//dataType : "json",
			success : function(cateList) {
				if(cateList == null){
					console.log("페이징")
				}else{
					for (var i = 0; i < cateList.length; i++) {
						render(cateList[i], "up");
					}
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});

	}
	
	
	$("#cateList").on("click", ".btnCateDel", function() {
		var remove = $(this);
		$.ajax({
			url : "${pageContext.request.contextPath}/${blogVo.id}/admin/catedelete",
			type : "post",
			//contentType : "application/json",
			data : {cateNo:$(this).data("no"),
					amount:$(this).data("amo")},
			//dataType : "json",
			success : function(result) {
				if(result == 0){
					alert("글이있어 삭제할 수 없습니다.")
				}else{
					console.log($(this))
					remove.parents("tr").remove();
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		

	});
	
</script>



</html>