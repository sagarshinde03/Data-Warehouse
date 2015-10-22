<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<spring:url value="/resources/css/design.css" var="coreCss" />
<spring:url value="/resources/jquery/jquery-2.1.4.min.js" var="jQuery" />
<spring:url value="/resources/jquery/validations_part1.js" var="validationPart1" />
<link rel="stylesheet" type="text/css" href="${coreCss}">
<script src="${jQuery}"></script>
<script src="${validationPart1}"></script>
<title>Data Warehouse</title>
<script type="text/javascript">
	$(document).ready(function(){
		validationPart1.start();
	});
	
</script>
</head>
<body>
	<div id="part1Header" class="header">
		<div id="headerLine"></div>
		<div id="headerTabs">
			<a><button id="part1" class="active">Part1</button></a>
			<a href="/DataWarehouse/part2"><button id="part2">Part2</button></a>
		</div>
		<div>
			<div id="headerLeft">
				<span>Data Warehouse/OLAP System</span>
			</div>
			<!-- <div id="headerRight">
			</div> -->
		</div>
	</div>
	<div id="part1Content" class="content">
		<div id="errorPart1"><span></span></div>
		<ul id="tabs">
			<li id="tab-1">Query 1</li>
			<li id="tab-2">Query 2</li>
			<li id="tab-3">Query 3</li>
			<li id="tab-4">Query 4</li>
			<li id="tab-5">Query 5</li>
			<li id="tab-6">Query 6</li>
		</ul>
		<div id="query1" class="page">
			<h3> Query 1: List the number of patients who had 'tumor' (disease description), 'leukemia' (disease type) and 
			'ALL' (disease name), separately.</h3>
			<h3>Please select the options using select box</h3>
			<select id="mainSelect" data-key="Q1-F1">
				<option selected value="NA">Select</option>
				<option value="DD">Disease Description</option>
				<option value="DT">Disease Type</option>
				<option value="DN">Disease Name</option>
			</select>
			<select id="optionSelectQ1">
				<option selected value="NA">Select</option>
			</select>
			<button id="q1Run" data-rid="Q1">RUN</button>
			<div id="resultQ1" class="result"></div>
		</div>
		<div id="query2" class="page" >
			<h3> Query 2: List the types of drugs which have been applied to patients with 'tumor'.</h3>
			Disease Description:
			<select id="optionSelectQ2" data-key="Q2">
				<option selected value="NA">Select</option>
			</select>
			<button id="q2Run" data-rid="Q2" data-type="query">RUN</button>
			<div id="resultQ2" class="result"></div>
		</div>
		<div id="query3" class="page">
			<h3> Query 3: For each sample of  patients with 'ALL' list the mRNA values (expression) of probes in cluster 
			id '00002' for each experiment with measure unit id = '001'.</h3>
			Disease Name:
			<select id="optionSelectQ3-1" data-key="Q3-1">
				<option selected value="NA">Select</option>
			</select>
			Cluster ID:
			<select id="optionSelectQ3-2" data-key="Q3-2">
				<option selected value="NA">Select</option>
			</select>
			<button id="q3Run" data-rid="Q3" data-type="query">RUN</button>
			<div id="resultQ3" class="result"></div>
		</div>
		<div id="query4" class="page">
			<h3> Query 4: For probes belonging to GO with id = '0012502', calculate the t-statistics of the expression 
			values between patients with 'ALL' and patients without 'ALL'.</h3>
			Disease Name:
			<select id="optionSelectQ4-1" data-key="Q4-1">
				<option selected value="NA">Select</option>
			</select>
			Go ID:
			<select id="optionSelectQ4-2" data-key="Q4-2">
				<option selected value="NA">Select</option>
			</select>
			<button id="q4Run" data-rid="Q4" data-type="query">RUN</button>
			<div id="resultQ4" class="result"></div>
		</div>
		<div id="query5" class="page">
			<h3> Query 5: For probes belonging to GO with id='0007154', calculate the F-statistics of the expression 
			values among patients with 'ALL', 'AML', 'colon tumor' and 'breast tumor'.</h3>
			<!-- Disease Name:
			<select id="optionSelectQ5-1" data-key="Q5-1">
				<option selected value="NA">Select</option>
			</select> -->
			Go ID:
			<select id="optionSelectQ5-2" data-key="Q5-2">
				<option selected value="NA">Select</option>
			</select>
			<button id="q5Run" data-rid="Q5" data-type="query">RUN</button>
			<div id="resultQ5" class="result"></div>
		</div>
		<div id="query6" class="page">
			<h3> Query 6: For probes belonging to GO with id='0007154', calculate the average correlation of the 
			expression values between two patients with 'ALL', and calculate the average correlation of the expression 
			values between one 'ALL' patient and one 'AML' patient.</h3>
			Go ID:
			<select id="optionSelectQ6-1" data-key="Q6-1">
				<option selected value="NA">Select</option>
			</select>
			<button id="q6Run" data-rid="Q6" data-type="query">RUN</button>
			<div id="resultQ6" class="result"></div>
		</div>
	</div>
	<div id="part1Footer" class="footer">
		<div class="leftfooter">Developer Team ></div>
		<div class="rightfooter">
			<div>Rahul Derashri</div>
			<div>Hitpal Kaur</div>
			<div>Sagar Shinde</div>
		</div>
	</div>
</body>
</html>