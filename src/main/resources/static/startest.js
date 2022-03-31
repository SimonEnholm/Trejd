var x = document.getElementById("FormInput5");
var lat = document.getElementById("latitude");
var long = document.getElementById("longitude");

function getLocation() {

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    x.placeholder = "Geolocation is not supported by this browser.";
  }
}

async function showPosition(position) {

   let response = await fetch('https://geocode.xyz/'+position.coords.latitude+','+position.coords.longitude+'?json=1&auth=118676552131904169980x39799')
   let theData = await response.json();
   lat.value =  position.coords.latitude;
   long.value = position.coords.longitude;
    x.value = theData.city;
}

async function setLocation(){

    let response = await fetch('https://geocode.xyz/'+x.value+'?json=1&auth=118676552131904169980x39799');
    let responseCode = await response.status;
    if(responseCode==200){
        let theData = await response.json();
        if(theData.error!=null){
            alert("no such city exists");
        }
        else{
            console.log(theData.latt);
            lat.value =  theData.latt;
            long.value = theData.longt;
        }
    }
}








//jfdklsjkfs


const result = document.querySelector(".result")

let chosenStars = 0

function hoverStar(number) {
    lightAllStarsUpTo(number)
}

function leftStar() {
    lightAllStarsUpTo(chosenStars)
}

function clickStar(number) {
    result.innerText = `Du gav Trejden ${number} stjärnor!`
    chosenStars = number
}

function resetStars() {
    result.innerHTML = "&nbsp;"
    chosenStars = 0
    lightAllStarsUpTo(chosenStars)
}

function lightAllStarsUpTo(number) {

    const allStars = document.querySelectorAll(".movie .rating img")

    // Tänder stjärnor 1=>3
    for (let i = 1; i <= number; i++) {
        allStars[i - 1].src = "/star.png"
    }

    // Släcker lampor 4=>5
    for (let i = number + 1; i <= 5; i++) {
        allStars[i - 1].src = "/star-gray.png"
    }
}