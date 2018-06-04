   <html>
	<head>
	 <title> Search House </title>
	
	</head>
	
	
	<body>
	
	 <form action="result.php" method="post">
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
	  <input type="submit" value="Search">
	  <input type="hidden" name="sort" id="sort" value="">
	  <input type="hidden" name="sortIndex" id="sortIndex" value="5">
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
	  //Select * from db -> push into temp array -> push tempArray into target array.
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
			if (!in_array($pieces[0], $cityList) ) {
			 array_push($cityList, $pieces[0]);
			}
		}
		echo json_encode($cityList);
	  ?>;
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
	   var cookie = //["temp", "temp"];
	   <?php
	     if (isset ($_COOKIE['city']) ) {
	      $cookieList = array();
		  array_push($cookieList, $_COOKIE['city']); 
		  array_push($cookieList, $_COOKIE['priceFrom']); 
		  array_push($cookieList, $_COOKIE['priceTo']); 
		  array_push($cookieList, $_COOKIE['minRum']); 
		  array_push($cookieList, $_COOKIE['maxRum']);
		  array_push($cookieList, $_COOKIE['minArea']); 		  
		  array_push($cookieList, $_COOKIE['maxArea']); 		  
		  array_push($cookieList, $_COOKIE['minAvgift']); 		  
		  array_push($cookieList, $_COOKIE['maxAvgift']); 		  
		  echo json_encode($cookieList);
		 }
	   ?>;
	 
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
	   if (option.text === cookie[0]) {
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
	   if (option.text === cookie[1]) {
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
	  defaultOptionPriceTo.text = "Price To  ";
	  priceToList.appendChild(defaultOptionPriceTo);
	  for (var i = 0; i < priceTo.length; i ++) {
	   const option = document.createElement("option");
	   option.name = "priceTo" + i;
	   option.text = priceTo[i];
	   if (option.text === cookie[2]) {
	    option.selected = "selected";
	   }
	   priceToList.appendChild(option);
	  }	
	  priceToDiv.appendChild(priceToList);
	  
	  var minRumSelect = document.getElementById("rumMin");
	  for (var mr = 0; mr < minRumSelect.length; mr ++ ) {
	   if (minRumSelect[mr].text === cookie[3]) {
	    minRumSelect[mr].selected = "selected";
	   }
	  }
	  var maxRumSelect = document.getElementById("rumMax");
	  for (var mxr = 0; mxr < minRumSelect.length; mxr ++ ) {
	   if (maxRumSelect[mxr].text === cookie[4]) {
		maxRumSelect[mxr].selected = "selected";
	   }
	  }
	  
	  var minAreaSelect = document.getElementById("areaMin");
	  for (var marea = 0; marea < minAreaSelect.length; marea ++ ) {
	   if (minAreaSelect[marea].text === cookie[5]) {
		minAreaSelect[marea].selected = "selected";
	   }
	  }
	  var maxAreaSelect = document.getElementById("areaMax");
	  for (var mxarea = 0; mxarea < maxAreaSelect.length; mxarea ++ ) {
	   if (maxAreaSelect[mxarea].text === cookie[6]) {
	    maxAreaSelect[mxarea].selected = "selected";
	   }
	  }
	  var minAvgiftSelect = document.getElementById("avgiftMin");
	  for (var mavgift = 0; mavgift < minAvgiftSelect.length; mavgift ++ ) {
	   if (minAvgiftSelect[mavgift].text === cookie[7]) {
		minAvgiftSelect[mavgift].selected = "selected";
	   }
	  }
	  var maxAvgiftSelect = document.getElementById("avgiftMax");
	  for (var mxavgift = 0; mxavgift < maxAvgiftSelect.length; mxavgift ++ ) {
	   if (maxAvgiftSelect[mxavgift].text === cookie[8]) {
		maxAvgiftSelect[mxavgift].selected = "selected";
	   }
	  }
	 </script>
	
	</body>
   </html>