let dd2 = document.getElementById("dropdown1");
let dd3 = document.getElementById("dropdown2");
dd2.style.visibility = "hidden";
dd3.style.visibility = "hidden";
let btn = document.getElementById("buttonCreateUser");
btn.style.marginTop="-150px";

function displayNewDropdown(i){
   if(i!=0){
        document.getElementById("dropdown"+i).style.visibility = "visible";
        document.getElementById("dropdown"+i).style.visibility = "visible";
        if (i==1){
            btn.style.marginTop="-70px";
        }
        if (i==2){
            btn.style.marginTop="10px";
        }
    }
}