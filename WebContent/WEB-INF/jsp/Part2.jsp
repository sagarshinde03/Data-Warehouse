<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<spring:url value="/resources/css/design.css" var="coreCss" />
<spring:url value="/resources/jquery/jquery-2.1.4.min.js" var="jQuery" />
<spring:url value="/resources/jquery/validations_part2.js" var="validationPart2" />
<link rel="stylesheet" type="text/css" href="${coreCss}">
<script src="${jQuery}"></script>
<script src="${validationPart2}"></script>
<title>Data Warehouse</title>
<script type="text/javascript">
	$(document).ready(function(){
		validationPart2.start();
	});
	
</script>
<body>
	<div id="part2Header" class="header">
		<div id="headerLine"></div>
		<div id="headerTabs">
			<a href="/DataWarehouse/part1"><button id="part1">Part1</button></a>
			<a><button id="part2" class="active">Part2</button></a>
		</div>
		<div>
			<div id="headerLeft">
				<span>Data Warehouse/OLAP System</span>
			</div>
		</div>
	</div>
	<div id="part2Content" class="content">
		<div id="errorPart2"><span></span></div>
		<ul id="tabs">
			<li id="tab-7">Query 1</li>
			<li id="tab-8">Query 2</li>
		</ul>
		<div id="query7" class="page">
			<h3> Given a specific disease, find the informative genes.</h3>
			Disease Name:
			<select id="optionSelectQ7" data-key="Q7">
				<option selected value="NA">Select</option>
			</select>
			<button id="q7Run" data-rid="Q7" data-type="query">RUN</button>
			<div id="resultQ7" class="result"></div>
		</div>
		<div id="query8" class="page">
			<h3> Use informative genes to classify a new patient .</h3>
			Disease Name:
			<select id="optionSelectQ8-1" data-key="Q8-1">
				<option selected value="NA">Select</option>
			</select>
			<!-- Patient ID:
			<select id="optionSelectQ8-2" data-key="Q8-2">
				<option selected value="NA">Select</option>
			</select> -->
			<button id="q8Run" data-rid="Q8" data-type="query">RUN</button>
			<div id="resultQ8" class="result"></div>
		</div>
	</div>
	<div id="part2Footer" class="footer">
		<div class="leftfooter">Developer Team ></div>
		<div class="rightfooter">
			<div>Rahul Derashri</div>
			<div>Hitpal Kaur</div>
			<div>Sagar Shinde</div>
		</div>
	</div>
</body>
</html>