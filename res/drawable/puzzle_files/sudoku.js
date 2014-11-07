// JavaScript Document
var m_sSudoku;
var m_sSolution;
var m_sSudokuPause;
var m_sSudokuTest;
var m_sPencil;
var m_sSudokuInit;
var xmlhttp;
var m_selectedCell;
var m_selectedValue;
var m_lastCell;
var m_x;
var m_y;
var m_sLevel;
var m_iReveals;
var m_bgInit;
var m_phpPath = '/app/sudoku/';
var m_iLevel;
var m_Solved;
var m_penMode;
var m_penVal;
var m_lastPenVal;
var m_gameStarted;
var m_bAutoCheck;
var m_bSymmetric;
var m_iSudoku;

//undo section
var undoStack = new Array(100);
var undoCmd = new Array(200);
var undoCount;
var undoSeq;
var undoMode;

//timer variables
var m_tStarted;
var m_tObject;
var m_tSecs;
var m_tMins;
var m_tCheckFlash;
var m_tCheckCell;

//scoring
var m_scBase1 = 240;
var m_scBase2 = 480;
var m_scBase3 = 720;
var m_scBase4 = 960;
var m_scBase5 = 1200;
var m_scRevPen = 10;
var m_scRevPenInc = 5;

//key-press handling
if( document.layers ) {
   document.captureEvents( Event.KEYPRESS );
   document.captureEvents( Event.KEYDOWN );
}
document.onkeydown = handle_keys;
document.onkeypress = ignore_keys;

//////////////////////
// sudoku functions //
//////////////////////
//function finished_sudoku()
//{
//	if (m_Solved==true) 
//	{
//		alert("Yes, you did it! Try a new or even harder one!");
//		return;
//	}
//	m_sSudoku = read_grid(false);
//	htmlIsSolved();
//}


//function create_new()
//{
//	var e = document.getElementById("txtLevel");
//	m_iLevel = e.value;
//	//e = document.getElementById("btnNew");
//	//e.disabled = true;
//	htmlGetNewSudoko(m_iLevel);	
//}

function reveal_cell()
{
	if(!m_selectedCell) return;
	htmlGetRevealCell();
}

function checkProgress()
{
    var sudokuString = read_grid();
    ajaxGetSolveSudoku(sudokuString);
}
function toSolver() {
    m_sudoku = read_grid_export();
    window.location = '/sudoku-solver?sudoku='+m_sudoku;
}

//////////////////////
//  Grid functions  //
//////////////////////

function update_grid_pause(sSudoku)
{
	var x, y, val, iTab = 1, iPos;
	var e, p, sElement, sPElement;
	var asPencil = m_sPencil.split(",");
	for (var i = 0; i < 81; i++)
    {
    	x = parseInt(i % 9) + 1;
        y = parseInt(i / 9) + 1;
        iPos = sSudoku.indexOf(",");
        val = sSudoku.substring(0, iPos);
        
        sSudoku = sSudoku.substring(iPos + 1, sSudoku.length);
        sElement = "txtC" + x + y;
        sPElement = "pen" + x + y;
        e = document.getElementById(sElement);
        p = document.getElementById(sPElement);
        p.innerHTML = asPencil[i];
        if (val != 0)
        {
        	e.value = val;
        } else {
        	e.value = "";
        }
        iPos = i;
    }
}
function update_grid(sSudoku)
{
    var x, y, val, iTab = 1, iPos;
    var e, d, sElement, sPen;
    m_sSudokuTest = sSudoku;

    for (var i = 0; i < 81; i++)
    {
        x = parseInt(i % 9) + 1;
        y = parseInt(i / 9) + 1;
        val = sSudoku.substring(i, i + 1);
        sElement = getIdForNbr(i);
        sPen = getPenForNbr(i);
        e = document.getElementById(sElement);
        d = document.getElementById(sPen);
        remove_event(e,"click",cell_clicked);
        remove_event(d,"click",cell_clicked);
        remove_event(e,"blur",cell_leave);
                remove_event(e,"focus",cell_enter);
        add_event(e,"click",cell_clicked);
        add_event(d,"click",cell_clicked);
        add_event(e,"blur",cell_leave);
        add_event(e,"focus",cell_enter);
        if (val != 0)
        {
            if(e.className.match("sudokuinputdark")){
                    e.className = "sudokuinputdark givenCell";
            }else{
                    e.className = "sudokuinput givenCell";
            }
            e.value = val;
            setReadOnly(e);
        }
        else
        {
            if(e.className.match("sudokuinputdark")){
                    e.className = "sudokuinputdark entryCell";
            }else{
                    e.className = "sudokuinput entryCell";
            }
            e.value = "";
            removeReadOnly(e);
        }
    }
    for (var x = 1; x <= 9; x++)
    {
    	for (var y = 1; y <= 9; y++)
    	{
            sElement = "txtC" + y + x;
            e = document.getElementById(sElement);
            val = e.value;
            if (val=="")
            {
                e.tabIndex = iTab;
                iTab++;
            } else {
                e.tabIndex = 100;
            }
    	}
    }
}
function clear_grid(bBreak)
{
    var x, y, val;
    var e, p, sElement, sPElement;
    var sBreak = "                            mypuzzle  .org              game    paused";
    
    for (var i = 0; i < 81; i++)
    {
        x = parseInt(i % 9) + 1;
        y = parseInt(i / 9) + 1;
        val = sBreak.substring(i, i + 1);
        sElement = "txtC" + x + y;
        sPElement= "pen" + x + y;
        e = document.getElementById(sElement);
        p = document.getElementById(sPElement);
        p.innerHTML = "";
        if (bBreak==true) {
                e.value = val;
        } else {
                e.value = "";
                e.style.backgroundColor = "";
        }
    }
}

function setReadOnly(element){
	// For IE
	if (window.ActiveXObject) {   
		element.setAttribute('readOnly', true);
	// Other browsers
	}else{
		element.setAttribute('readonly', true); 	
	}
}

function removeReadOnly(element){
	// For IE
	if (window.ActiveXObject) {   
		element.removeAttribute('readOnly'); 
	// Other browsers
	}else{
		element.removeAttribute('readonly'); 	
	}
}
function read_grid(bSeperator)
{
	var x, y, val;
	var e, sElement, sPElement, sPencil = "", sSudoku = "";
	
	for (var i = 0; i < 81; i++)
    {
    	x = parseInt(i % 9) + 1;
        y = parseInt(i / 9) + 1;
        sElement = "txtC" + x + y;
        sPElement = "pen" + x + y;
        e = document.getElementById(sElement);
        val = e.value;
        if (IsEmpty(e)) {
        	val = "0"; 
        }
        sSudoku += '' + val;
        e = document.getElementById(sPElement);
        val = e.innerHTML;
        sPencil += val;
        sPencil += ",";
        if (bSeperator) {
        	sSudoku += ",";
        }
    }
    m_sPencil = sPencil;
    
    return(sSudoku);
}
function read_grid_export()
{
	var x, y, val;
	var e, p, sElement, sPElement, sSudoku = "", sPencil = "";
	
	for (var i = 0; i < 81; i++)
    {
    	x = parseInt(i % 9) + 1;
        y = parseInt(i / 9) + 1;
        sElement = "txtC" + x + y;
        sPElement = "pen" + x + y;
        e = document.getElementById(sElement);
        p = document.getElementById(sPElement);
        val = e.value;
        if (val.length > 1) 
        	val = "0";
        if (IsEmpty(e)) {
        	val = "0"; 
        }
        sSudoku += '' + val;
        val = p.innerHTML;
        sPencil += val;
        sPencil += ",";
        
    }
    m_sPencil = sPencil;
    return(sSudoku);
    
}

////////////////////////////
//  validation functions  //
////////////////////////////

function cell_clicked(e)
{
	var sCell, oCell;
	
	oCell = find_target(e);
	if (oCell.id.substring(0, 3) == "pen") {
		if (!m_bAutoCheck || check_isvalid(m_selectedCell))
		{	
			m_selectedCell.className = m_selectedCell.className.replace(/ selectedCell/g,"");
		}
		if (m_selectedCell.className.indexOf("errorCell") != -1 && check_isvalid(m_selectedCell))
		{
			m_selectedCell.className = m_selectedCell.className.replace(/ errorCell/g,"");
		}
		sCell = oCell.id.replace(/pen/g,"txtC");
		m_selectedCell = document.getElementById(sCell);
		selectCell(m_selectedCell.id);
	} else {
		m_selectedCell = oCell;
	}
	
	var bReadOnly;
	if (window.ActiveXObject) {  
		bReadOnly = m_selectedCell.getAttribute('readonly');
	} else {
		bReadOnly = m_selectedCell.getAttribute('readOnly');
	}
	if (m_penMode==true && !bReadOnly) {
		enterValue(m_selectedCell.id, m_penVal);
		if (!undoMode) undoSeq++;
	}
}
function cellIsReadOnly()
{
	var bReadOnly;
	if (window.ActiveXObject) {  
		bReadOnly = m_selectedCell.getAttribute('readonly');
	} else {
		bReadOnly = m_selectedCell.getAttribute('readOnly');
	}
	return(bReadOnly);
}

function cell_enter(e)
{
	if (m_selectedCell)
	{
		if (m_selectedCell.className.indexOf("selectedCell") != 0)
		{
			m_selectedCell.className = m_selectedCell.className.replace(/ selectedCell/g,"");
		}
	}
	
	m_selectedCell = find_target(e);
	selectCell(m_selectedCell.id);
}

function cell_leave(e)
{
	m_selectedCell = find_target(e);
	
	if (!m_bAutoCheck || check_isvalid(m_selectedCell))
	{
		m_selectedCell.style.backgroundColor = "";
	}
	if (m_selectedCell.className.indexOf("errorCell") != -1 && check_isvalid(m_selectedCell))
	{
		m_selectedCell.className = m_selectedCell.className.replace(/ errorCell/g,"");
	}
}

/////////////////////////////
//  php receive functions  //
/////////////////////////////

//get new sudoku
//function htmlGetNewSudoko(iLevel)
//{
//    var url = m_phpPath + 'new_sudoku.php';
//
//    if (window.ActiveXObject) {              // for IE
//         xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
//         }
//    else if (window.XMLHttpRequest) {   // for other browsers{
//         xmlhttp=new XMLHttpRequest();
//    }
//
//    xmlhttp.onreadystatechange=xmlhttpAswer_New;
//    xmlhttp.open("POST",url,true);
//
//    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//    xmlhttp.send('level=' + iLevel);
//
//}
//function xmlhttpAswer_New()
//{
//	if (xmlhttp.readyState == 4)
//    {
//        if(xmlhttp.status == 200)
//        {
//			var newSudoku = xmlhttp.responseText;
//			m_sSudoku = newSudoku;
//			m_sSudokuInit = newSudoku;
//			update_grid(newSudoku, false);
//			document.getElementById("btnNew").disabled = false;
//		    document.getElementById("btnPause").disabled = false;
//		    
//		    //start timer.
//			timer_stop();
//			m_tSecs = 0;
//			timer_start();
//			m_tStarted = 1;
//			m_iReveals = 0;
//			m_Solved = false;
//			m_gameStarted = true;
//        }
//        else
//        {
//            alert("Error loading page\n"+ xmlhttp.status +":"+ xmlhttp.statusText);
//        }
//    }
//}

//check, whether puzzle has been solved
//function htmlIsSolved()
//{
//    var url = m_phpPath + 'solved.php';
//    var frm;
//
//    if (window.ActiveXObject) {              // for IE
//         xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
//         }
//    else if (window.XMLHttpRequest) {   // for other browsers{
//         xmlhttp=new XMLHttpRequest();
//    }
//    
//    xmlhttp.onreadystatechange=xmlhttpAswer_IsSolved;
//    xmlhttp.open("POST",url,true);
//
//    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//    var params = 'sudoku=' + m_sSudoku;
//    xmlhttp.send(params);
//
//}
//function xmlhttpAswer_IsSolved()
//{
//	if (xmlhttp.readyState == 4)
//    {
//        if(xmlhttp.status == 200)
//        {
//           var cellVal = xmlhttp.responseText;
//           if (cellVal == 1) // value is invalid
//           {
//           	   timer_stop();
//           	   
//           	   alert("Congratulations, you have solved this sudoku!");
//           	   m_Solved = true;
//           	   
//           }
//           else //value is valid
//           {
//           		alert("You are not finished yet!");
//           }
//        }
//        else
//        {
//            alert("Error loading page\n"+ xmlhttp.status +":"+ xmlhttp.statusText);
//        }
//    }
//}

///////////////////////////
//   event functions     //
///////////////////////////
function add_event(element, evType, func) {
	if (element.addEventListener) {
		element.addEventListener(evType, func, false);
		return true;
	}
	else if (element.attachEvent) {
		var r = element.attachEvent('on' + evType, func);
		return r;
	}
	else {
		element['on' + evType] = func;
	}
}
function remove_event(element, evType, func) {
	if (element.removeEventListener) {
		element.removeEventListener(evType, func, false);
		return true;
	}
	else if (element.detachEvent) {
		var r = element.detachEvent('on' + evType, func);
		return r;
	}
	else {
		elm['on' + evType] = '';
	}
}
    
//Returns the target of the event.
function find_target(e) {
	var element;
	if (!e) var e = window.event;
	if (e.target) element = e.target;
	else if (e.srcElement) element = e.srcElement;
	if (element.nodeType == 3) element = element.parentNode;// Safari bug
	return element;
}

function watch_keys(e) {
	if (!e) var e = window.event;
	if (e.keyCode) code = e.keyCode;
	else if (e.which) code = e.which;
	var character = String.fromCharCode(code);

	//If the pressed key IS a number
	if(isNaN(character)) {
		alert(character + " is not a number.");
	}
}

///////////////////////////
//   timer functions     //
///////////////////////////
function timer_start()
{
	if (!m_tSecs) m_tSecs = 0;
	
	m_tSecs++;
	var s = m_tSecs % 60;
	var m = Math.floor(m_tSecs/60);
	if(s<10) s = "0" + s;
	if(m<10) m = "0" + m;  
	
	var e = document.getElementById("time");

	e.innerHTML = "Time: " + m + ":" + s;
	m_tObject = window.setTimeout("timer_start()",1000);
	
}
function timer_stop()
{
	if (m_tObject) window.clearTimeout(m_tObject);
}
function timer_switch()
{
	if (!m_sSudoku) return;
	
	if (m_tStarted){
		timer_stop();
		m_sSudokuPause = read_grid(true);
		clear_grid(true);
		m_tStarted = false;
		e = document.getElementById("btnPause");
		e.value = "Go on";
	} else {
		clear_grid(false);
		update_grid_pause(m_sSudokuPause);
		timer_start();
		m_tStarted = true;
		e = document.getElementById("btnPause");
		e.value = "Pause";
	}
}
function timer_reset()
{
	timer_stop();
	
	var e = document.getElementById("time");
	e.innerHTML = "Time: 00:00";
}
function is_paused(){
	if (m_tStarted){
		return false;
	} else {
		return true;
	}
}

//debug status information
function status_set(message)
{
	var e = document.getElementById("status");
	e.innerHTML = message;
}

function IsEmpty(aTextField) {
   if ((aTextField.value.length==0) ||
   (aTextField.value==null)) {
      return true;
   }
   else {return false;}
}
///////////////////
// pen functions //
///////////////////

function togglePen() {
	
	if (m_penMode==true) {
		tooglePenOff();
	} else {
		tooglePenOn();
	}
}

function tooglePenOn() {
	var e;
	m_penVal = 1;
	e = document.getElementById("btnTogglePen");
	e.value = "Drop Pen";
	m_penMode = true;
	enablePen();
}

function tooglePenOff() {
	var e;
	e = document.getElementById("btnTogglePen");
	e.value = "Pick Pen";
	m_penMode = false;
	document.getElementById('divPen').innerHTML = "<br></br><br></br><br></br>";
}

function chosePencil(sPen) {
	var e;
	var sElement, sTest;
	var bChanged;
	
	m_lastPenVal = m_penVal;
	m_penVal = sPen;
	if (m_penVal==m_lastPenVal) {
		bChanged = false;
	} else {
		bChanged = true;
	}
	
	sElement = "btnPen" + m_penVal;
	e = document.getElementById(sElement);
	e.className += " selpenButton";
	
	if (bChanged==true) {
		sElement = "btnPen" + m_lastPenVal;
		e = document.getElementById(sElement);
		sTest = e.className.replace(/ selpenButton/g,"");
		e.className = sTest;
	}
}
function enablePen() {
	var sHTML;
	
	sHTML =  "<table>";
	sHTML += "  <tr>";
	sHTML += "    <td>&nbsp;&nbsp;</td>";
	sHTML += "    <td><input name=\"btnPen1\" type=\"button\" id=\"btnPen1\" value=\"1\" class=\"penButton selpenButton\" onClick=\"chosePencil('1')\" /></td>";
	sHTML += "    <td><input name=\"btnPen2\" type=\"button\" id=\"btnPen2\" value=\"2\" class=\"penButton\" onClick=\"chosePencil('2')\" /></td>";
	sHTML += "    <td><input name=\"btnPen3\" type=\"button\" id=\"btnPen3\" value=\"3\" class=\"penButton\" onClick=\"chosePencil('3')\" /></td>";
	sHTML += "  </tr>";
	sHTML += "  <tr>";
	sHTML += "    <td>&nbsp;&nbsp;</td>";
	sHTML += "    <td><input name=\"btnPen4\" type=\"button\" id=\"btnPen4\" value=\"4\" class=\"penButton\" onClick=\"chosePencil('4')\" /></td>";
	sHTML += "    <td><input name=\"btnPen5\" type=\"button\" id=\"btnPen5\" value=\"5\" class=\"penButton\" onClick=\"chosePencil('5')\" /></td>";
	sHTML += "    <td><input name=\"btnPen6\" type=\"button\" id=\"btnPen6\" value=\"6\" class=\"penButton\" onClick=\"chosePencil('6')\" /></td>";
	sHTML += "    </tr>";
	sHTML += "    <tr>";
	sHTML += "    <td>&nbsp;&nbsp;</td>";
	sHTML += "    <td><input name=\"btnPen7\" type=\"button\" id=\"btnPen7\" value=\"7\" class=\"penButton\" onClick=\"chosePencil('7')\" /></td>";
	sHTML += "    <td><input name=\"btnPen8\" type=\"button\" id=\"btnPen8\" value=\"8\" class=\"penButton\" onClick=\"chosePencil('8')\" /></td>";
	sHTML += "    <td><input name=\"btnPen9\" type=\"button\" id=\"btnPen9\" value=\"9\" class=\"penButton\" onClick=\"chosePencil('9')\" /></td>";
	sHTML += "    </tr>";
	sHTML += "</table>";
	document.getElementById('divPen').innerHTML = sHTML;
}

/* level change */
function changeLevel()
{
	var e = document.getElementById("selLevel");
	var iLevel = e.value;
	var sSudoku = read_grid(false);
	
	if (m_gameStarted && sSudoku != m_sSudokuInit && m_Solved == false) {
		if (confirm("This will end your current game. Are you sure?") == false) {
			e.value = m_iLevel;
			return(false);
		}
	}
	
	m_iLevel = iLevel;
	switch(m_iLevel){
		case "7" :m_sLevel = "Diabolic";break;
		case "6" :m_sLevel = "Very Hard";break;
		case "5" :m_sLevel = "Hard";break;
		case "4" :m_sLevel = "Moderate";break;
		case "3" :m_sLevel = "Mild";break;
		case "2" :m_sLevel = "Easy";break;
		case "1" :m_sLevel = "Simple";break;
	}
	clear_grid();
	timer_reset();
	m_gameStarted = false;
	//set html properties to new level info	
	var e = document.getElementById("txtLevel");
	e.value = m_iLevel;
	
	//load suitable sudokus in list
	//htmlLoadSudokuList(iLevel);	
        ajaxLoadSudokuList(iLevel);
	return(true);
}

function ajaxLoadSudokuList(iLevel) {
    
    if ($('#chkSymmetric').attr('checked'))
        m_bSymmetric = 1;
    else
        m_bSymmetric = 0;
    
    var sUrl = m_phpPath + 'sudokuLoadList.php?iLevel='+iLevel+'&symmetric=' + m_bSymmetric;
    $.get(sUrl, function(data) {
            $("#sudokuList").html(data);
            $("#selSudoku").addClass('sudokuSelect');
            sudokuChange();
    })
}
function sudokuChange()
{
	var e = document.getElementById("selSudoku");
	var iSudoku = e.value;
        
	var sSudoku = read_grid(false);
	
	if (m_gameStarted && sSudoku != m_sSudokuInit && m_Solved == false) {
		if (confirm("This will end your current game. Are you sure?") == false) {
			e.value = m_iSudoku;
			return;
		}
	}
	m_gameStarted = false;
	m_iSudoku = iSudoku;
	ajaxGetSudoku(m_iSudoku);
}

function ajaxGetSudoku(iSudoku)
{
    var sUrl = m_phpPath + 'sudokuGet.php?iSudoku='+iSudoku;
    $.get(sUrl, function(data) {
        m_sSudoku = data;
        m_sSudokuInit = data;
        clear_grid(false);
        update_grid(data, false);
        $('#btnPause').disabled = false;
        
        //start timer.
        timer_stop();
        m_tSecs = 0;
        timer_start();
        m_tStarted = 1;
        m_iReveals = 0;
        m_Solved = false;
        m_gameStarted = true;
        //re-initialize undo vars
        undoCount = 0;
        undoSeq = 0;
        undoMode = false;
        undoStack = new Array(100);
        undoCmd = new Array(200);

        selectCell("txtC11");
        ajaxGetSolution(m_iSudoku);
    })
}

function ajaxGetSolution(iSudoku)
{
    var sUrl = m_phpPath + 'sudokuGetSolution.php?iSudoku='+iSudoku;
    $.get(sUrl, function(data) {
        m_sSolution = data;        
    })
}
function ajaxGetSolveSudoku(sudokuString)
{
    var sUrl = m_phpPath + 'solve_sudoku.php?sudoku='+sudokuString;
    $.get(sUrl, function(data) {
  
        if (data.indexOf("conflict") > 0 || data.indexOf("has not been solved") > 0) {
            alert("Mhm, not really. Maybe undo some steps or start over again.");
        }
        else
            alert("Everything is good.");
    })
    
}

function exportSudoku()
{
	var sSudoku = read_grid_export();	
	window.open(m_phpPath + "sudokuExport.php?sudoku=" + sSudoku, "mywindow","menubar=0,resizable=0,width=670,height=500");
}


function printSudoku()
{	
	var sSudoku = read_grid_export();	
	var sQry = m_phpPath + "sudokuPrint.php?sudoku=" + sSudoku + '&size=1&id=' + m_iSudoku + "&pencil=" + m_sPencil;
	window.open(sQry, "mywindow","menubar=1,resizable=1,width=750,height=850");

}
//*********************************************//
//*******key handling**************************//
//*********************************************//
function handle_keys(e) {
   if (!e) {
      e = window.event;
   }
   
   if (e.target) { 
      t = e.target;
   }
   else {
      if (e.srcElement) {
         t = e.srcElement;
      }
   }
   
	if (t.className.indexOf("sudokuinput") != 0) return true;

   var chr;
   var charCode = (e.charCode) ? e.charCode :
                  ((e.keyCode) ? e.keyCode :
                  ((e.which) ? e.which : 0));

   if( charCode >= 97 && charCode <= 105 ) {
       // deal with number keypad
       charCode = charCode - 48;
   }
   if( charCode >= 81 && charCode <= 90 ) {
       // ctrl + numbers in OSX
       charCode = charCode - 32;
   }
   if( (charCode >= 49 && charCode <= 57) // numbers
       || charCode == 32 ) { // space
       chr = String.fromCharCode( charCode );
   }
   //aktuelle zelle bestimmen
   var c = m_selectedCell.id;
   //aktueller nummer bestimmen
   var i = getNbrForId(c);
   var iTemp = i;

   switch( charCode ) {
   case 37:
   case 63234: // safari oddity
       // left arrow
       i--;
       break;
   case 38:
   case 63232:
       // up arrow
       i -= 9;
       break;
   case 9: // tab
   case 39:
   case 63235:
       // right arrow
       i++;
       break;
   case 40:
   case 63233:
       // down arrow
       i++; // make i a number
       i += 8;
       break;
   case 8:
   case 46:
       // delete
       	if (!cellIsReadOnly())
       	{
       		if (m_selectedCell.value == "")
	       	{
	       		pencil_deleteAll(m_selectedCell.id);
	       		if (!undoMode) undoSeq++;
	       	} else
	       	{
	       		deleteValue(m_selectedCell.id, m_selectedValue);
	       		if (!undoMode) undoSeq++;
	       	}	       		
       	}
       	
       
	default:
		if( ((chr >= '0') && (chr <= '9' )) || (chr == ' ') ) 
       	{
		   	if( e.ctrlKey || e.altKey ) 
		   	{
				if (!cellIsReadOnly())
       			{
       				if (m_selectedCell.value != "")
       				{
       					deleteValue(m_selectedCell.id);
       				}
		       		pencil_add(m_selectedCell.id, chr );
		       		if (!undoMode) undoSeq++;
		       	}
		   }
		   else 
		   {
		   		if (!cellIsReadOnly())
       			{
       				enterValue(m_selectedCell.id, chr);
       				if (!undoMode) undoSeq++;
		   		}
		       //inputNumber(chr);
	           //    if( document.draw.automoveon.checked ) {
			   //i++;
	           //    }
		   }
       }
       else if ( e.ctrlKey && charCode == 58)
       {
       		undo();
       }
       else 
       {
	   		return false;
       }
   }

   if( i < 0 ) {
      i += 81;
   }
   if( i > 80 ) {
      i -= 81;
   }
   inputID = getIdForNbr(i);
   selectCell(inputID);
   return false;
}

function ignore_keys(e) {
   if (!e) {
      e = window.event;
   }

   var targ;
   if (e.target) { 
      targ = e.target;
   }
   else {
      if (e.srcElement) {
         targ = e.srcElement;
      }
   }

   if (t.className.indexOf("sudokuinput") != 0) return true;

   return false;
}

function getNbrForId(sId)
{
	var x = parseInt(sId.substr(4,1));
	var y = parseInt(sId.substr(5,1));
	var i = (y * 9 - 9 + x);
	return(i-1);
}
function getIdForNbr(iNbr)
{
	x = parseInt(iNbr % 9) + 1;
    y = parseInt(iNbr / 9) + 1;
    return ("txtC" + x + y);
}
function getPenForNbr(iNbr)
{
	x = parseInt(iNbr % 9) + 1;
    y = parseInt(iNbr / 9) + 1;
    return ("pen" + x + y);
}

function enterValue(sCell, sVal)
{
	//save pencilmarks
	
	//delete pencilmarks
	pencil_deleteAll(sCell);
	var cell = document.getElementById(sCell);
	cell.value = sVal;
	
	var iNbr = getNbrForId(sCell);
	var sHelp = m_sSudokuTest.substr(0, iNbr) + sVal;
	sHelp +=  m_sSudokuTest.substr(iNbr + 1, 100);
	m_sSudokuTest = sHelp;
	
	m_selectedValue = sVal;
	if (!undoMode) undo_add(1, sCell, sVal);
	if (m_bAutoCheck && !check_isvalid(cell))
	{
		if (cell.className.indexOf("errorCell") == -1)
		{
			cell.className += " errorCell";
		}
		
	} else
	{
		if (cell.className.indexOf("errorCell") != -1)
		{
			cell.className = cell.className.replace(/ errorCell/g,"");
		}
	}
	
	//check for solution
	check_finished();
}
function check_finished()
{
	//var sCurrSudoku = read_grid(false);
	var sCurrSudoku = m_sSudokuTest;
	if (sCurrSudoku == m_sSolution)
	{
		timer_stop();
           	   
   		alert("Congratulations, you have solved this sudoku!");
   		m_Solved = true;
   	   	
	}
	
}
function deleteValue(sCell)
{
	var cell = document.getElementById(sCell);
	var sVal = cell.value;
	if (sVal == "") sVal = m_selectedValue;

	if (!undoMode) undo_add(2, sCell, sVal);
	
	cell.value = "";
}

function selectCell(sCell)
{
	var cell = document.getElementById(sCell);
	m_selectedValue = cell.value;
	cell.focus();
	if (cell.className.indexOf("selectedCell") == -1)
	{
		cell.className += " selectedCell";
	}
	
	m_selectedCell = cell;
}
function toogle_symmetric()
{
	var e = document.getElementById("chkSymmetric");
	
	if (e.checked==1)
	{
		m_bSymmetric = 1;
	} else 
	{
		m_bSymmetric = 0;
	}
	var bChg = changeLevel();
	if (!bChg)
	{
		e.checked = !e.checked;
	}
}
//*********************************************//
//*******automatic checking/validation*********//
//*********************************************//
function toogle_checking()
{
	var e = document.getElementById("chkChecking");
	if (e.checked==1)
	{
		m_bAutoCheck = true;
	} else 
	{
		m_bAutoCheck = false;
	}
}
function check_value(cell)
{
	if (cell.value.length != 1 || cellIsReadOnly()) return;
	
	if ( !check_isvalid(cell) && cell.className.indexOf("errorCell") == -1)
	{
		cell.className += " errorCell";
	}
	
}
function check_isvalid(cell)
{
	if (cell.value.length != 1 || cellIsReadOnly()) return(true);
	
	if (! check_valIsUnique(cell) )
	{
		return(false);
	}
	
	return(true);
}
function check_valIsUnique(cell)
{
	var x, y, v;
	var bExists;
	var e, sElement, sSudoku = "";
	var val = parseInt(cell.value);
	var col = parseInt(cell.id.substr(4,1));
	var row = parseInt(cell.id.substr(5,1));
	
	//check columns in that row
	for (var i = 1; i <= 9; i++)
    {
    	x = i;
        //y = parseInt(i / 9) + 1;
        y = row;
        sElement = "txtC" + x + y;
        v = document.getElementById(sElement).value;
        if (v == val && x != col)
        {
        	check_showProblem(sElement);
        	return(false);
        }
    }
    //check rows in that column
	for (var i = 1; i <= 9; i++)
    {
    	x = col;
        y = i;
        sElement = "txtC" + x + y;
        v = document.getElementById(sElement).value;
        if (v == val && y != row)
        {
        	check_showProblem(sElement);
        	return(false);
        }
    }
    //check cells in that block
    
    var block = parseInt(3 * Math.floor((row - 1) / 3) + Math.floor((col - 1) / 3));
    var sHelp = block;
	for (var i = 0; i < 9; i++)
    {
    	x = parseInt(Math.floor(block / 3) * 3 + i % 3 + 1);
    	y = parseInt(Math.floor(block % 3) * 3 + i / 3 + 1);
    	
        sElement = "txtC" + y + x;
        //alert(sElement);
        sHelp += ";" + sElement;

        v = document.getElementById(sElement).value;
        if (v == val && (x != row || y != col))
        {
        	check_showProblem(sElement);
        	return(false);
        }
        
    }
    //alert(sHelp);
    return(true);
}
function check_showProblem(sCell)
{
	m_tCheckCell = document.getElementById(sCell);
	m_tCheckCell.className += " errorCell";
	m_tCheckFlash = window.setTimeout("check_hideProblem()",500);
}
function check_hideProblem()
{
	m_tCheckCell.className = m_tCheckCell.className.replace(/ errorCell/g,"");
	if (m_tCheckFlash) window.clearTimeout(m_tCheckFlash);
}
//*************************************//
//*******handling pencilmarks *********//
//*************************************//
function pencil_getObject(sCell)
{
	var oCell = document.getElementById(sCell);
	var sPen = 'pen' + oCell.id.substr(4, 2);
	var oPen = document.getElementById(sPen);
	return(oPen);
}

function pencil_deleteAll(sCell)
{
	var oPen = pencil_getObject(sCell);
	if (!undoMode) undo_add(4, sCell, oPen.innerHTML);
	oPen.innerHTML = "";
}
function pencil_set(sCell, sNbrs)
{
	var oCell = document.getElementById(sCell);
	var oPen = pencil_getObject(sCell);
	oPen.innerHTML = sNbrs
}

function pencil_add(sCell, sNbr)
{
	var oCell = document.getElementById(sCell);

	//first delete value, because it sees^ms to be unclear
	oCell.value = "";
	var oPen = pencil_getObject(sCell);
	var sPen = oPen.innerHTML;
	
	if (!sPen) sPen = "";
	sPen = sPen.toLowerCase();
	sPen = sPen.replace("<br><br>", "");
	sPen = sPen.replace("<br>", "");
	var iNewPen = parseInt(sNbr);
	var iFnd;
	var bAdded = false;
	var sHelp = "";
	
	if (sPen.indexOf(sNbr) != -1)
	{
		sHelp = sPen.replace(sNbr, "");
	}else
	{
		for (var i = 0; i < sPen.length; i++)
	    {
	    	iFnd = parseInt(sPen.substr(i, 1));
	    	
	    	if (iFnd < iNewPen)  
	    	{
	    		sHelp += iFnd;
	    	}
	    	if (iFnd > iNewPen)
	    	{
	    		if (!bAdded) sHelp += iNewPen;
	    		sHelp += iFnd;
	    		bAdded = true;
	    	}
	    	
	    	if (iFnd == iNewPen) bAdded = true;
	    }
	    if (sHelp == "" || !bAdded) sHelp += sNbr;
	    if (sHelp == "" && bAdded) sHelp = "";
	}
	if (sHelp.length > 4)
	{
		//add linebreak
		sHelp = sHelp.substr(0,4) + "<br></br>" + sHelp.substr(4,4);
	}
	//alert(sHelp);
    oPen.innerHTML = sHelp;
    if (!undoMode) undo_add(3, sCell, sNbr);
	//alert(sNbr + " for " + oPen.id);	
}

//******************************//
//******* undo section *********//
//******************************//
function undo_add(iType, sCell, sParams)
{
	//iType = 1 means add number
	//iType = 2 means delete number
	//iType = 3 means add pencilmarks
	//iType = 4 means delete pencilmarks
	
	var sCommand = undoSeq + ";" + iType + ";" + sCell + ";" + sParams;
	undoCmd[undoCount] = sCommand;
	undoCount++;
	//undo_debug();
}
function undo()
{
    var sSeq = "0", sLastSeq = "";
    var sCommand = "";
    var asCmd;

    undoMode = true; //dont track those changes by now

    for (var i = undoCount-1; i >= 0; i--)
    {
        sCommand = undoCmd[i];
        asCmd = sCommand.split(";");
        sSeq = asCmd[0];
        if (sLastSeq == "")
        {
            sLastSeq = sSeq;
        }
        if (sLastSeq != sSeq) break;
        if (asCmd[1] == "1") //
        {
            deleteValue(asCmd[2], asCmd[3]);
        }
        if (asCmd[1] == "2") //
        {
            enterValue(asCmd[2], asCmd[3]);
        }
        if (asCmd[1] == "3") //
        {
            pencil_add(asCmd[2], asCmd[3]);
        }
        if (asCmd[1] == "4") //
        {
            pencil_set(asCmd[2], asCmd[3]);
        }
        undoCmd[i] = "";
        undoCount--;
        sLastSeq = sSeq;
        selectCell(asCmd[2])
    }
    undoMode = false; //enable tracking of future changes 
}
function undo_debug()
{
	var sHelp = "";
	for (var i = 0; i < undoCount; i++)
    {
    	sHelp += undoCmd[i] + "\n";
    }
    alert(sHelp);
}
//************************************//
//******* candidates section *********//
//************************************//
function fillCandidates()
{
	var x, y, val;
	var e, sElement, sPElement, sPencil = "", sSudoku = "";
	
	for (var i = 0; i < 81; i++)
    {
    	x = parseInt(i % 9) + 1;
        y = parseInt(i / 9) + 1;
        sElement = "txtC" + x + y;
        e = document.getElementById(sElement);
        val = e.value;
        
        if (IsEmpty(e)) {
        	pencil_deleteAll(e.id);
        	for (var t = 1; t <= 9; t++) {
        		if (candidateIsValid(e, t)) {
        			pencil_add(e.id, t);
        		}	
        	}
        }
    }
	
}
function candidateIsValid(cell, candidate)
{
	var x, y, v;
	var bExists;
	var e, sElement, sSudoku = "";
	var val = candidate;
	var col = parseInt(cell.id.substr(4,1));
	var row = parseInt(cell.id.substr(5,1));
	
	//check columns in that row
	for (var i = 1; i <= 9; i++)
    {
    	x = i;
        //y = parseInt(i / 9) + 1;
        y = row;
        sElement = "txtC" + x + y;
        v = document.getElementById(sElement).value;
        if (v == val && x != col)
        {
        	return(false);
        }
    }
    //check rows in that column
	for (var i = 1; i <= 9; i++)
    {
    	x = col;
        y = i;
        sElement = "txtC" + x + y;
        v = document.getElementById(sElement).value;
        if (v == val && y != row)
        {
        	return(false);
        }
    }
    //check cells in that block
    
    var block = parseInt(3 * Math.floor((row - 1) / 3) + Math.floor((col - 1) / 3));
    var sHelp = block;
	for (var i = 0; i < 9; i++)
    {
    	x = parseInt(Math.floor(block / 3) * 3 + i % 3 + 1);
    	y = parseInt(Math.floor(block % 3) * 3 + i / 3 + 1);
    	
        sElement = "txtC" + y + x;
        sHelp += ";" + sElement;

        v = document.getElementById(sElement).value;
        if (v == val && (x != row || y != col))
        {
        	return(false);
        }
        
    }
    return(true);
}