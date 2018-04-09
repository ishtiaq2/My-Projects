"use strict";

var ship4 = 0;
var ship4_index = 0;
var ship3 = 0;
var ship3_index = 0;
var ship33 = 0;
var ship33_index = 0;
var ship2 = 0;
var ship2_index = 0;
var ship22 = 0;
var ship22_index = 0;
var shipPlaced = 0;
var hideShips = 0;
var hitCount = 0;
var fires = 0;
var activePlayer = "player1";
var player1 = "player1";
var player2 = "player2";


var gameboardContainer1 = document.getElementById("gameboard1");
var gameboardContainer2 = document.getElementById("gameboard2");
var gameBoard1 = []; 
var gameBoard2 = []; 
var matrix;
var boardExist = 0;

function createBoard() {
	if (boardExist == 0) {
		matrix = prompt("Please enter Matrix Size, Should be < 10, >=4");
		var gbWidth = 50 * matrix;
		gameboardContainer1.style.width = gbWidth + "px";
		gameboardContainer1.style.height = gbWidth + "px";
		gameboardContainer2.style.width = gbWidth + "px";
		gameboardContainer2.style.height = gbWidth + "px";
					
		for (var r = 0; r < matrix; r++) {
			for (var c = 0 ; c < matrix; c++ ) {
				var square1 = document.createElement("div");
				var square2 = document.createElement("div");
				gameboardContainer1.appendChild(square1);
				gameboardContainer2.appendChild(square2);
				
				var topPosition = r * 50;
				var leftPosition = c * 50;
				
				square1.id = "s" + r + c;
				square1.addEventListener("click", placeShip, false); 
				square2.id = "t" + r + c;
				square2.addEventListener("click", placeShip, false); 
				
				square1.style.top = topPosition + "px";
				square1.style.left = leftPosition + "px";
				square2.style.top = topPosition + "px";
				square2.style.left = leftPosition + "px";
				gameBoard1[c] = new Array(0);	
				gameBoard2[c] = new Array(0);	
			}
		}


		for (var r = 0; r < matrix; r++) {
			for (var c = 0 ; c < matrix; c++ ) {
				gameBoard1[r][c] = 0;
				gameBoard2[r][c] = 0;
			}
		}
		boardExist = 1;
	} else {
		clearBoard();
	}
}

function placeShip(e) {
	var board = e.target.id.substring(0,1);
	if (board === "s") {
		placeShip1(e);
	} else if (board === "t") {
		placeShip2(e);
	}
	
}

function placeShip1(e) {
	if (hideShips == 1) {
		fire1(e);
	} else {
		var row = e.target.id.substring(1,2);
		var col = e.target.id.substring(2,3);
		
		var c = parseInt(gameBoard1[row][col]);
		if (c > 0 ) {
			if (c == 4) {
				removeShip1(e, 4, ship4_index);
				ship4 = 0;
				ship4_index = 0;
			}
			if (c == 3) {
				removeShip1(e, 3, ship3_index);
				ship3 = 0;
				ship3_index = 0;
			}
			if (c == 33) {
				removeShip1(e, 33, ship33_index);
				ship33 = 0;
				ship33_index = 0;
			}
			if (c == 2) {
				removeShip1(e, 2, ship2_index);
				ship2 = 0;
				ship2_index = 0;
			}
			if (c == 22) {
				removeShip1(e, 22, ship22_index);
				ship22 = 0;
				ship22_index = 0;
			}
		} else {
			if (ship4 == 0) {
				placeShip41(e, 4);
			} else if ( ship3 == 0) {
				placeShip41(e, 3);
			} else if ( ship33 == 0) {
				placeShip41(e, 33);
			} else if ( ship2 == 0) {
				placeShip41(e, 2);
			} else if ( ship22 == 0) {
				placeShip41(e, 22);
			} else {
				if ( (ship4 == 1) & (ship3 == 1) & (ship33 == 1) & 
					 (ship2 == 1) & (ship22 == 1) ) { 
					shipPlaced = 1;
					alert("All ships has been placed");
				}
			}
		} 
	}
}

function placeShip41(e, length) {
	var ln;
	if (length == 33) {
		ln = 3;
	} else if (length == 22) {
		ln = 2;
	} else {
		ln = length
	}
	var row = e.target.id.substring(1,2);
	var col = e.target.id.substring(2,3);
	var cols = parseInt(col);
	var exist = alreadyExist(row, col, ln);
		
	if ( (col <= (matrix - ln)) & !exist) {
		for (var x = cols ; x < cols + ln; x++) {
			var squareId = "s" + row + x;
			gameBoard1[row][x] = length;
			document.getElementById(squareId).style.backgroundColor = "lightblue";
		}
				
		if (length == 4) {
			ship4 = 1;
			ship4_index = "s" + row + col;
		} else if (length == 3) {
			ship3 = 1;
			ship3_index = "s" + row + col;
		} else if (length == 33 ) {
			ship33 = 1;
			ship33_index = "s" + row + col;
		} else if (length == 2 ) {
			ship2 = 1;
			ship2_index = "s" + row + col;
		} else if (length == 22 ) {
			ship22 = 1;
			ship22_index = "s" + row + col;
		}

		var mydiv = document.getElementById("status");
		if (mydiv.childNodes[1] != null) {
			mydiv.removeChild(mydiv.childNodes[1]);
		}
		mydiv.appendChild(document.createTextNode(row+", " +col + "," +ln + "," + exist));
		
	} else {
			alert("No Space");
	}
}

function alreadyExist(row, col, length) {
	var r = parseInt(row);
	var c = parseInt(col);
	var exist = 0;
	for (var x = c ; x < c + length; x++) {
		if (gameBoard1[r][x] > 0 ) {
			exist = 1;
			alert(r + "," + c + ", " + length + ", " + x);
		}
	}
	return Boolean(exist);
}

function removeShip1(e, length, index) {
	var ln;
	if (length == 33) {
		ln = 3;
	} else if (length == 22) {
		ln = 2;
	} else {
		ln = length
	}
	var indexRow = index.substring(1,2);
	var indexCol = index.substring(2,3);
	var indexC = parseInt(indexCol);
	for (var x = indexCol ; x < indexC + ln; x++) {
		gameBoard1[indexRow][x] = "0";
		var squareId = "s" + indexRow + x;
		document.getElementById(squareId).style.backgroundColor = "#f6f8f9";
	}
	shipPlaced = 0;
}

function hideShip() {
	/*var mydiv = document.getElementById("table");
	if (mydiv.childNodes[1] != null) {
       mydiv.removeChild(mydiv.childNodes[1]);
    }
		
	var table = document.createElement("Table");
	table.setAttribute('border', '1');
	var body = document.getElementById('table');
	var tbdy = document.createElement('tbody');
	
	for (var x = 0; x < matrix; x++) {
		var tr = document.createElement("tr");
		for (var y = 0; y < matrix; y++) {
			var td = document.createElement("td");
			td.appendChild(document.createTextNode(gameBoard1[x][y]));
			tr.appendChild(td)
		}
		 tbdy.appendChild(tr);
	}
	table.appendChild(tbdy);
    body.appendChild(table)*/
	if (shipPlaced === 1) {
		for (var r = 0; r < matrix; r++) {
			for (var c = 0 ; c < matrix; c++ ) {
				//gameboardContainer.appendChild(square);
				var squareId = "s" + r + c;
				document.getElementById(squareId).style.backgroundColor = "#f6f8f9";
			}
		}
	hideShips = 1;
	}
	if ( (hideShips === 1) & (hideShips2 === 1)) {
		activePlayer = player1;
		var mydiv1 = document.getElementById("turn");
		if (mydiv1.childNodes[1] != null) {
			mydiv1.removeChild(mydiv1.childNodes[1]);
		}
		mydiv1.appendChild(document.createTextNode("Player 1"));
	}
}

function fire1(e) {
	if (activePlayer === player1) {
		var row = e.target.id.substring(1,2);
		var col = e.target.id.substring(2,3);
		fires++;
		if ( (gameBoard1[row][col] == 0) ) {
			e.target.style.background = '#bbb';
			if (hitCount2 < 14) {
				activePlayer = "player2";
				var mydiv1 = document.getElementById("turn");
				if (mydiv1.childNodes[1] != null) {
					mydiv1.removeChild(mydiv1.childNodes[1]);
				}
				mydiv1.appendChild(document.createTextNode("Player 2"));
			}
		} else if (gameBoard1[row][col] > 1) {
			gameBoard1[row][col] = -1;
			e.target.style.background = 'red';
			hitCount++;
		}
		var mydiv = document.getElementById("status");
		if (mydiv.childNodes[1] != null) {
		   mydiv.removeChild(mydiv.childNodes[1]);
		}
		mydiv.appendChild(document.createTextNode(hitCount + " Fires: " + fires));
			
		if (hitCount >= 14) {
			alert("All enemy battleships have been destroyed! You win!");
			shipPlaced = 0;
			activePlayer = "player2";
			var mydiv1 = document.getElementById("turn");
			if (mydiv1.childNodes[1] != null) {
				mydiv1.removeChild(mydiv1.childNodes[1]);
			}
			mydiv1.appendChild(document.createTextNode("Player 2"));
		}
		e.stopPropagation();
	} else {
		alert ("Player 2 turn");
	}
}

function clearBoard() {
	for (var r = 0; r < matrix; r++) {
		for (var c = 0 ; c < matrix; c++ ) {
			
			var squareId1 = "s" + r + c;
			var squareId2 = "t" + r + c;
			var square1 = document.getElementById(squareId1);
			var square2 = document.getElementById(squareId2);
			gameboardContainer1.removeChild(square1);
			gameboardContainer2.removeChild(square2);
			gameBoard1[r][c] = "0";
			gameBoard2[r][c] = "0";
		}
	}
	boardExist = 0;
	ship4 = 0;
	ship4_index = 0;
	ship3 = 0;
	ship3_index = 0;
	ship33 = 0;
	ship33_index = 0;
	ship2 = 0;
	ship2_index = 0;
	ship22 = 0;
	ship22_index = 0;
	shipPlaced = 0;
	hideShips = 0;
	hitCount = 0;
	fires = 0;
	
	ship44 = 0;
	ship44_index = 0;
	ship3_1 = 0;
	ship3_1_index = 0;
	ship33_1 = 0;
	ship33_1_index = 0;
	ship2_1 = 0;
	ship2_1_index = 0;
	ship22_1 = 0;
	ship22_1_index = 0;
	shipPlaced2 = 0;
	hideShips2 = 0;
	hitCount2 = 0;
	fires2 = 0;
	var mydiv = document.getElementById("status");
	if (mydiv.childNodes[1] != null) {
	   mydiv.removeChild(mydiv.childNodes[1]);
	}
	
	var mydiv1 = document.getElementById("turn");
		if (mydiv1.childNodes[1] != null) {
			mydiv1.removeChild(mydiv1.childNodes[1]);
		}
	
}

//////////////////////////////////////////////////////////////////////////////

var ship44 = 0;
var ship44_index = 0;
var ship3_1 = 0;
var ship3_1_index = 0;
var ship33_1 = 0;
var ship33_1_index = 0;
var ship2_1 = 0;
var ship2_1_index = 0;
var ship22_1 = 0;
var ship22_1_index = 0;
var shipPlaced2 = 0;
var hideShips2 = 0;
var hitCount2 = 0;
var fires2 = 0;

function placeShip2(e) {
	if (hideShips2 == 1) {
		fire2(e);
	} else {
		var row = e.target.id.substring(1,2);
		var col = e.target.id.substring(2,3);
		
		var c = parseInt(gameBoard2[row][col]);
		if (c > 0 ) {
			if (c == 4) {
				removeShip2(e, 4, ship44_index);
				ship44 = 0;
				ship44_index = 0;
			}
			if (c == 3) {
				removeShip2(e, 3, ship3_1_index);
				ship3_1 = 0;
				ship3_1_index = 0;
			}
			if (c == 33) {
				removeShip2(e, 33, ship33_1_index);
				ship33_1 = 0;
				ship33_1_index = 0;
			}
			if (c == 2) {
				removeShip2(e, 2, ship2_1_index);
				ship2_1 = 0;
				ship2_1_index = 0;
			}
			if (c == 22) {
				removeShip2(e, 22, ship22_1_index);
				ship22_1 = 0;
				ship22_1_index = 0;
			}
		} else {
			if (ship44 == 0) {
				placeShip42(e, 4);
			} else if ( ship3_1 == 0) {
				placeShip42(e, 3);
			} else if ( ship33_1 == 0) {
				placeShip42(e, 33);
			} else if ( ship2_1 == 0) {
				placeShip42(e, 2);
			} else if ( ship22_1 == 0) {
				placeShip42(e, 22);
			} else {
				if ( (ship44 == 1) & (ship3_1 == 1) & (ship33_1 == 1) & 
					 (ship2_1 == 1) & (ship22_1 == 1) ) { 
					shipPlaced2 = 1;
					alert("All ships has been placed");
				}
			}
		} 
	}
}

function placeShip42(e, length) {
	var ln;
	if (length == 33) {
		ln = 3;
	} else if (length == 22) {
		ln = 2;
	} else {
		ln = length
	}
	var row = e.target.id.substring(1,2);
	var col = e.target.id.substring(2,3);
	var cols = parseInt(col);
	var exist = alreadyExist2(row, col, ln);
		
	if ( (col <= (matrix - ln)) & !exist) {
		for (var x = cols ; x < cols + ln; x++) {
			var squareId = "t" + row + x;
			gameBoard2[row][x] = length;
			document.getElementById(squareId).style.backgroundColor = "lightblue";
		}
				
		if (length == 4) {
			ship44 = 1;
			ship44_index = "t" + row + col;
		} else if (length == 3) {
			ship3_1 = 1;
			ship3_1_index = "t" + row + col;
		} else if (length == 33 ) {
			ship33_1 = 1;
			ship33_1_index = "t" + row + col;
		} else if (length == 2 ) {
			ship2_1 = 1;
			ship2_1_index = "t" + row + col;
		} else if (length == 22 ) {
			ship22_1 = 1;
			ship22_1_index = "t" + row + col;
		}

		var mydiv = document.getElementById("status2");
		if (mydiv.childNodes[1] != null) {
			mydiv.removeChild(mydiv.childNodes[1]);
		}
		mydiv.appendChild(document.createTextNode(row+", " +col + "," +ln + "," + exist));
		
	} else {
			alert("No Space");
	}
}

function alreadyExist2(row, col, length) {
	var r = parseInt(row);
	var c = parseInt(col);
	var exist = 0;
	for (var x = c ; x < c + length; x++) {
		if (gameBoard2[r][x] > 0 ) {
			exist = 1;
			alert(r + "," + c + ", " + length + ", " + x);
		}
	}
	return Boolean(exist);
}

function removeShip2(e, length, index) {
	var ln;
	if (length == 33) {
		ln = 3;
	} else if (length == 22) {
		ln = 2;
	} else {
		ln = length
	}
	var indexRow = index.substring(1,2);
	var indexCol = index.substring(2,3);
	var indexC = parseInt(indexCol);
	for (var x = indexCol ; x < indexC + ln; x++) {
		gameBoard2[indexRow][x] = "0";
		var squareId = "t" + indexRow + x;
		document.getElementById(squareId).style.backgroundColor = "#f6f8f9";
	}
	shipPlaced2 = 0;
}

function hideShip2() {
	
	if (shipPlaced2 === 1) {
		for (var r = 0; r < matrix; r++) {
			for (var c = 0 ; c < matrix; c++ ) {
				//gameboardContainer.appendChild(square);
				var squareId = "t" + r + c;
				document.getElementById(squareId).style.backgroundColor = "#f6f8f9";
			}
		}
	hideShips2 = 1;
	}
	if ( (hideShips === 1) & (hideShips2 === 1)) {
		activePlayer = player1;
		var mydiv1 = document.getElementById("turn");
		if (mydiv1.childNodes[1] != null) {
			mydiv1.removeChild(mydiv1.childNodes[1]);
		}
		mydiv1.appendChild(document.createTextNode("Player 1"));
	}
		
}

function fire2(e) {
	if (activePlayer === player2) {
		var row = e.target.id.substring(1,2);
		var col = e.target.id.substring(2,3);
		fires2++;
		if ( (gameBoard2[row][col] == 0) ) {
			e.target.style.background = '#bbb';
			if (hitCount < 14) {
				activePlayer = player1;
				var mydiv1 = document.getElementById("turn");
				if (mydiv1.childNodes[1] != null) {
					mydiv1.removeChild(mydiv1.childNodes[1]);
				}
				mydiv1.appendChild(document.createTextNode("Player 1"));
			}		
		} else if (gameBoard2[row][col] > 1) {
			gameBoard2[row][col] = -1;
			e.target.style.background = 'red';
			hitCount2++;
			
		}
		var mydiv = document.getElementById("status2");
		if (mydiv.childNodes[1] != null) {
		   mydiv.removeChild(mydiv.childNodes[1]);
		}
		mydiv.appendChild(document.createTextNode(hitCount2 + " Fires: " + fires2));
			
		if (hitCount2 >= 14) {
			alert("All enemy battleships have been destroyed! You win!");
			shipPlaced2 = 0;
			activePlayer = player1;
			var mydiv1 = document.getElementById("turn");
			if (mydiv1.childNodes[1] != null) {
				mydiv1.removeChild(mydiv1.childNodes[1]);
			}
			mydiv1.appendChild(document.createTextNode("Player 1"));
		}
		e.stopPropagation();
	} else { 
		alert("Player 1 turn");
	}
}
