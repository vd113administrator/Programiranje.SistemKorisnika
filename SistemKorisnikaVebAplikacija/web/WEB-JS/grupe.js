/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var korisniciInHTML = "";
var grupeInHTML = "";
var korisniciUpHTML = ""; 
var grupeUpHTML = "";
var grupeBroj = 0;

function viewKorisniciIn() {
	if(korisniciInHTML === "") korisniciInHTML = document.getElementById("polje_in").innerHTML;
        document.getElementById("polje_in").innerHTML = korisniciInHTML; 
        if(grupeInHTML === "") grupeInHTML = document.getElementById("grupe_in").innerHTML;
        document.getElementById("grupe_in").innerHTML = ""; 
}

function viewKorisniciUp() {
	if(korisniciUpHTML === "") korisniciUpHTML = document.getElementById("polje_up").innerHTML;
        document.getElementById("polje_up").innerHTML = korisniciUpHTML;
        if(grupeUpHTML === "") grupeUpHTML = document.getElementById("grupe_up").innerHTML;
        document.getElementById("grupe_up").innerHTML = "";
}


function viewGrupeUp() {
        if(korisniciUpHTML === "") korisniciUpHTML = document.getElementById("polje_up").innerHTML; 
        document.getElementById("polje_up").innerHTML = "";
        if(grupeUpHTML === "") grupeUpHTML = document.getElementById("grupe_up").innerHTML;
        document.getElementById("grupe_up").innerHTML = grupeUpHTML.replace("none","block");
}

function viewGrupeIn() {
	if(korisniciInHTML === "") korisniciInHTML = document.getElementById("polje_in").innerHTML;
        document.getElementById("polje_in").innerHTML = "";
        if(grupeInHTML === "") grupeInHTML = document.getElementById("grupe_in").innerHTML;
        document.getElementById("grupe_in").innerHTML = grupeInHTML.replace("none","block");
}

function novaGrupaPrenos(){
        var x = document.forms['unos_grupa'].groupname.value;
        document.forms['opcije_grupa'].grupe_polje.value = x;
}

function generisanjeHTMLCekiranjaGrupa(){
    document.write("<input type='hidden' name='hd' id='hd' value='"+grupeBroj+"'/>\n");
    for(i=0; i<grupeBroj; i++)
        document.write("\t<input type='hidden' name='hd"+i+"' id='hd"+i+"' value=':false'/>\n");
}

function akcijaFN(i){ 
    var x = document.getElementById('db'+i).checked;
    var n = document.getElementById('gname'+i).innerHTML; 
    while(n.search('&lt;') !== -1) n = n.replace('&lt;','<');
    while(n.search('&gt;') !== -1)  n = n.replace('&gt;','>');
    while(n.search('&amp;') !== -1) n = n.replace('&amp;','&');
    document.getElementById('hd'+i).value=n+":"+x;
}
