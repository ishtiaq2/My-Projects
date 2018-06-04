<?php
//$domain = ($_SERVER['HTTP_HOST'] != 'localhost') ? $_SERVER['HTTP_HOST'] : false;
//setcookie('city', $_POST['city'], time()+60*60*24*365, '/', $domain, false);
setcookie('city', $_POST['city'], time()+60*60*24*365);
setcookie('priceFrom', $_POST['priceFrom'], time()+60*60*24*365);
setcookie('priceTo', $_POST['priceTo'], time()+60*60*24*365);
setcookie('minRum', $_POST['rumMin'], time()+60*60*24*365);
setcookie('maxRum', $_POST['rumMax'], time()+60*60*24*365);
setcookie('minArea', $_POST['areaMin'], time()+60*60*24*365);
setcookie('maxArea', $_POST['areaMax'], time()+60*60*24*365);
setcookie('minAvgift', $_POST['avgiftMin'], time()+60*60*24*365);
setcookie('maxAvgift', $_POST['avgiftMax'], time()+60*60*24*365);
?>
   <html>
	<head>
	 <title> Search House </title>
    </head>
		
	<body>
	
	 <form action="result.php" method="post" id="form1">
	  City <div id="cityDiv"></div>
	  <p></p>
	  Price From <div id="priceFromDiv"> </div>
	  <p></p>
	  Price To <div id="priceToDiv"> </div>
	  <p></p>
	  Min Room <div id="rumMinDiv"> <select id="rumMin" name="rumMin"> 
	  <option name="rumMin"> 1 </option>
	  <option name="rumMin"> 2 </option>
	  <option name="rumMin"> 3 </option>
	  <option name="rumMin"> 4 </option>
	  <option name="rumMin"> 5 </option>
	  <option name="rumMin"> 6 </option>
	  </select> 
	  </div>
	  <p></p>
	  Max Room <div id="rumMaxDiv"> <select id="rumMax" name="rumMax">
	  <option name="rumMax"> 1 </option>
	  <option name="rumMax"> 2 </option>
	  <option name="rumMax"> 3 </option>
	  <option name="rumMax"> 4 </option>
	  <option name="rumMax"> 5 </option>
	  <option name="rumMax"> 6 </option>
	  </select> 
	  </div>
	  <p></p>
	  Min Area <div id="areaMinDiv"> <select id="areaMin" name="areaMin"> 
	  <option name="areaMin"> 30 </option>
	  <option name="areaMin"> 60 </option>
	  <option name="areaMin"> 130 </option>
	  <option name="areaMin"> 160 </option>
	  </select>
	  </div>
	  <p></p>
	  Max Area <div id="areaMaxDiv"> <select id="areaMax" name="areaMax">
	  <option name="areaMax"> 30 </option>
	  <option name="areaMax"> 60 </option>
	  <option name="areaMax"> 130 </option>
	  <option name="areaMax"> 160 </option>
	  </select>
	  </div>
	  <p></p>
	  Min Avgift <div id="avgiftMinDiv"> <select id="avgiftMin" name="avgiftMin"> 
	  <option name="avgiftMin"> 1234 </option>
	  <option name="avgiftMin"> 2345 </option>
	  <option name="avgiftMin"> 3456 </option>
	  <option name="avgiftMin"> 4456 </option>
	  </select>
	  </div>
	  <p></p>
	  Max Avgift <div id="avgiftMaxDiv"> <select id="avgiftMax" name="avgiftMax">
	  <option name="avgiftMax"> 1234 </option>
	  <option name="avgiftMax"> 2345 </option>
	  <option name="avgiftMax"> 3456 </option>
	  <option name="avgiftMax"> 4456 </option>
	  </select>
	  </div>
	  <p></p>
	  <p></p>
	 
	  <input type="hidden" name="sort" id="sort" value="asc" />
	  <input type="hidden" name="sortIndex" id="sortIndex" value="5" />
	   <input type="button" value="Search" onclick="sortDefault()" />
	 </form>	 
	 
	 <script type="text/javascript">
	 
	 var sqlTable = 
	  <?php 
	   $host = 'localhost';
	   $db   = 'search_bostader';
	   $user = 'admin';
	   $pass = 'yourpass';
	   $charset = 'utf-8';
	  
	   $DB = array();
	   $dsn = "mysql:host=$host;dbname=$db;characterset=$charset";
	   try {
	    $pdo = new PDO($dsn, $user, $pass);
	   } catch (PDOException $e) {
		 print "Error!: " . $e->getMessage() . "<br/>";
		 die();
	   }
	  
	  $stmt = $pdo->query('SELECT * FROM bostader');
	  while ($sqlrow = $stmt->fetch()){
		  $temp = array();
		  array_push($temp, $sqlrow['lan']);
		  array_push($temp, $sqlrow['objekttyp']);
		  array_push($temp, $sqlrow['adress']);
		  array_push($temp, $sqlrow['area']);
		  array_push($temp, $sqlrow['rum']);
		  array_push($temp, $sqlrow['pris']);
		  array_push($temp, $sqlrow['avgift']);
		  array_push($DB, $temp);
		//echo $sqlrow['lan'] . "\n";
	  }
	  echo json_encode($DB);	  
	  ?>;
	  
	  var cities = 
	  <?php 	  
	   /*$DB = array 
	    (
	     array('Stockholm','Bostadsrätt','Polhemsgatan 1',30,1,1000,1234),
		 array('Stockholm','Bostadsrätt','Polhemsgatan 2',60,2,2000,2345),
		 array('Stockholm','Villa','Storgatan 1',130,5,3000,3456),
		 array('Stockholm','Villa','Storgatan 2',160,6,4000,3456),
		 array('Stockholm','Bostadsrätt','Gamlastan 3',30,4,4000,4456),	 
		 array('Stockholm','Villa','Storgatan 3',160,6,4000,3456),
		 array('Stockholm','Bostadsrätt','Gamlastan 3',30,1,4000,4456),	 
		 array('Stockholm','Bostadsrätt','Gamlastan 3',30,1,4000,4456),	 
		 array('Uppsala','Bostadsrätt','Gröna gatan 1',30,1,500,1234),
		 array('Uppsala','Bostadsrätt','Gröna gatan 2',60,2,600,2345),
		 array('Uppsala','Villa','Kungsängsvägen 1',130,5,700,3456),
		 array('Uppsala','Villa','Kungsängsvägen 2',160,6,800,3456),
		 array('Uppsala','Villa','Kungsängsvägen 3',160,6,800,3456),
		 array('Göteborg','Bostadsrätt','Polhemsgatan 1',30,1,1000,1234),
		 array('Göteborg','Bostadsrätt','Polhemsgatan 2',60,2,2000,2345),
		 array('Göteborg','Villa','Storgatan 1',130,5,3000,3456),
		 array('Göteborg','Villa','Storgatan 2',160,6,4000,3456),
		 array('Göteborg','Villa','Storgatan 3',160,6,4000,3456),
		 array('Malmo','Bostadsrätt','Malmo 3',30,1,4000,3456)	 
		  
	    );*/
		$cityList = array();
		for ($row = 0; $row < count($DB); $row ++) {
			$pieces = array_slice($DB[$row], 0, 7);
			if ( !in_array($pieces[0], $cityList)) {
			 array_push($cityList, $pieces[0]);
			}
		}
		echo json_encode($cityList);
	  ?>;
	  
	  /*var arrayFormat = 
	   <?php 
	    echo json_encode($DB);
	   ?>;*/
	  var priceFrom = 
	   <?php
	    $php_priceFrom = array("500", "600", "700", "800", "1000");
	    echo json_encode($php_priceFrom);
	   ?>;
	  var priceTo = 
	   <?php 
	    $php_priceTo = array("800", "1000", "2000", "3000", "4000");
	    echo json_encode($php_priceTo);
	   ?>;
	   
	   var city = "<?php
			echo $_POST["city"];
		?>";
		
		var priceF = "<?php
			echo $_POST["priceFrom"];
		?>";
		var priceT = "<?php
			echo $_POST["priceTo"];
		?>";
		var rumMin = "<?php
			echo $_POST["rumMin"];
		?>";
		var rumMax = "<?php
			echo $_POST["rumMax"];
		?>";
		var areaMin = "<?php
			echo $_POST["areaMin"];
		?>";
		var areaMax = "<?php
			echo $_POST["areaMax"];
		?>";
		var avgiftMin = "<?php
			echo $_POST["avgiftMin"];
		?>";
		var avgiftMax = "<?php
			echo $_POST["avgiftMax"];
		?>";
		
		var result = 
		<?php 
		 $filteredList = array();
		 for ($row = 0; $row < count($DB); $row ++) {
			$pieces = array_slice($DB[$row], 0, 7);
			if (intval($pieces[6]) >= intval($_POST["avgiftMin"]) && intval($pieces[6]) <= intval($_POST["avgiftMax"]) && intval($pieces[5]) >= intval($_POST["priceFrom"]) && intval($pieces[5]) <= intval($_POST["priceTo"]) && intval($pieces[4]) >= intval($_POST["rumMin"]) && intval($pieces[4]) <= intval($_POST["rumMax"]) && intval($pieces[3]) >= intval($_POST["areaMin"]) && intval($pieces[3]) <= intval($_POST["areaMax"]) && $_POST['city'] == $pieces[0]) {
				array_push($filteredList, $DB[$row]);
			}
		 }
		 for ($colRt = 0; $colRt < count($filteredList); $colRt ++ ) {
					for ($colSc = 0; $colSc < count($filteredList); $colSc++ ) {
						$piece1 = array_slice($filteredList[$colRt], 0, 7);
						$piece2 = array_slice($filteredList[$colSc], 0, 7);
						if ( intval($piece1[5]) < intval($piece2[5]) ) {
							$temp = $filteredList[$colRt];
							$filteredList[$colRt] = $filteredList[$colSc];
							$filteredList[$colSc] = $temp;
						}							
					}
		 }		
		$sortBy = $_POST["sort"];	
		if ( $sortBy === "asc" ) { 
			    $index = $_POST["sortIndex"];
				for ($colRoot = 0; $colRoot < count($filteredList); $colRoot ++ ) {
					for ($colSec = 0; $colSec < count($filteredList); $colSec++ ) {
						$pieces1 = array_slice($filteredList[$colRoot], 0, 7);
						$pieces2 = array_slice($filteredList[$colSec], 0, 7);
						if ( intval($pieces1[$index]) > intval($pieces2[$index]) ) {
							$temp = $filteredList[$colRoot];
							$filteredList[$colRoot] = $filteredList[$colSec];
							$filteredList[$colSec] = $temp;
						}							
					}
				}
			} else if ($sortBy === "des") {
				$index = $_POST["sortIndex"];
				for ($colRoot = 0; $colRoot < count($filteredList); $colRoot ++ ) {
					for ($colSec = 0; $colSec < count($filteredList); $colSec++ ) {
						$pieces1 = array_slice($filteredList[$colRoot], 0, 7);
						$pieces2 = array_slice($filteredList[$colSec], 0, 7);
						if ( intval($pieces1[$index]) < intval($pieces2[$index]) ) {
							$temp = $filteredList[$colRoot];
							$filteredList[$colRoot] = $filteredList[$colSec];
							$filteredList[$colSec] = $temp;
						}							
					}
				}
			}
		echo json_encode($filteredList);
		?>;
		
		var srt = 
		 "<?php 
			if ( $sortBy === "asc" ) { 
				echo "des";
			} else {
				echo "asc";
			}
		 ?>";
		 document.getElementById("sort").value = srt;
	  	  	  	  
	  var cityDiv = document.getElementById("cityDiv");
	  const cityList = document.createElement("select");
	  cityList.setAttribute("id", "city");
	  cityList.setAttribute("name", "city");
	  const defaultOption = document.createElement("option");
	  defaultOption.value = "";
	  defaultOption.selected = "selected";
	  defaultOption.text = "Choose City";
	  cityList.appendChild(defaultOption);
	  for (var i = 0; i < cities.length; i++) {
	   const option = document.createElement("option");
	   option.name = "city";
	   option.text = cities[i];
	   if (option.text === city) { 
		   option.selected = "selected";
	   }
	   cityList.appendChild(option);
	  }
	  cityDiv.appendChild(cityList);
	  	
	  var priceFromDiv = document.getElementById("priceFromDiv");
	  const priceFromList = document.createElement("select");
	  priceFromList.setAttribute("id", "priceFrom");
	  priceFromList.setAttribute("name", "priceFrom");
	  const defaultOptionPriceFrom = document.createElement("option");
	  defaultOptionPriceFrom.value = "";
	  defaultOptionPriceFrom.selected = "selected";
	  defaultOptionPriceFrom.text = "Price From";
	  priceFromList.appendChild(defaultOptionPriceFrom);
	  for (var i = 0; i < priceFrom.length; i ++) {
	   const option = document.createElement("option");
	   option.name = "priceFrom" + i;
	   option.text = priceFrom[i];
	   if (option.text === priceF) {
		   option.selected = "selected";
	   }
	   priceFromList.appendChild(option);
	  }	
	  priceFromDiv.appendChild(priceFromList);
	 
	  var priceToDiv = document.getElementById("priceToDiv");
	  const priceToList = document.createElement("select");
	  priceToList.setAttribute("id", "priceTo");
	  priceToList.setAttribute("name", "priceTo");
	  const defaultOptionPriceTo = document.createElement("option");
	  defaultOptionPriceTo.value = "";
	  defaultOptionPriceTo.selected = "selected";
	  defaultOptionPriceTo.text = "Price To";
	  priceToList.appendChild(defaultOptionPriceTo);
	  for (var i = 0; i < priceTo.length; i ++) {
	   const option = document.createElement("option");
	   option.name = "priceTo" + i;
	   option.text = priceTo[i];
	   if (option.text === priceT) {
		   option.selected = "selected";
	   }
	   priceToList.appendChild(option);
	  }	
	  priceToDiv.appendChild(priceToList);  
	  	  	  
	  var minRumSelect = document.getElementById("rumMin");
	  for (var mr = 0; mr < minRumSelect.length; mr ++ ) {
		  if (minRumSelect[mr].text === rumMin) {
			  minRumSelect[mr].selected = "selected";
		  }
	  }
	  var maxRumSelect = document.getElementById("rumMax");
	  for (var mxr = 0; mxr < maxRumSelect.length; mxr ++ ) {
		  if (minRumSelect[mxr].text === rumMax) {
			  maxRumSelect[mxr].selected = "selected";
		  }
	  }
	  
	  var minAreaSelect = document.getElementById("areaMin");
	  for (var ma = 0; ma < minAreaSelect.length; ma ++ ) {
		  if (minAreaSelect[ma].text === areaMin) {
			  minAreaSelect[ma].selected = "selected";
		  }
	  }
	  var maxAreaSelect = document.getElementById("areaMax");
	  for (var mxa = 0; mxa < maxAreaSelect.length; mxa ++ ) {
		  if (maxAreaSelect[mxa].text === areaMax) {
			  maxAreaSelect[mxa].selected = "selected";
		  }
	  }
	  
	  var minAvgiftSelect = document.getElementById("avgiftMin");
	  for (var mav = 0; mav < minAvgiftSelect.length; mav ++ ) {
		  if (minAvgiftSelect[mav].text === avgiftMin) {
			  minAvgiftSelect[mav].selected = "selected";
		  }
	  }
	  var maxAvgiftSelect = document.getElementById("avgiftMax");
	  for (var mxav = 0; mxav < maxAvgiftSelect.length; mxav ++ ) {
		  if (maxAvgiftSelect[mxav].text === avgiftMax) {
			  maxAvgiftSelect[mxav].selected = "selected";
		  }
	  }
		document.write("<h2>City: " + city + "</h2>");
		document.write("<table border=1>");
		document.write("<tr> <th> Lan </th>");
		document.write("<th> Type </th>");
		document.write("<th> Address </th>");
		document.write("<th> <a href='javascript:;' onclick=sort('area');> Area </a> </th>");
		document.write("<th> <a href='javascript:;' onclick=sort('rum');> Room </a> </th>");
		document.write("<th> <a href='javascript:;' onclick=sort('pris');> Price </a> </th>");
		document.write("<th> <a href='javascript:;' onclick=sort('avgift');> Avgift </a> </th> </tr>");
		
		for (var r = 0; r < result.length; r++) {
			document.write("<tr>");
			for (var c = 0; c < 7; c++ ) {
				
				document.write("<td>" + result[r][c] +"</td>");
			}
			document.write("</tr>");
		}
		document.write("</table>");
		
		function sort(index) {
			if (index === "pris") {
			 document.getElementById("sortIndex").value = "5";
			} else if (index === "rum") {
			 document.getElementById("sortIndex").value = "4";
			} else if (index === "area") {
			 document.getElementById("sortIndex").value = "3";
			}else if (index === "avgift") {
			 document.getElementById("sortIndex").value = "6";
			}
			document.getElementById('form1').submit();
		}
		
		function sortDefault() {
			document.getElementById("sortIndex").value = "5";
			document.getElementById("sort").value = "";
			document.getElementById('form1').submit();
		}
		
	</script>
		
	</body>
	
</html>