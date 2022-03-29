const result = document.querySelector(".result")

let chosenStars = 0

function hoverStar(number) {
    lightAllStarsUpTo(number)
}

function leftStar() {
    lightAllStarsUpTo(chosenStars)
}

function clickStar(number) {
    result.innerText = `You rated ${number} stars!`
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
        allStars[i - 1].src = "/static/star.png"
    }

    // Släcker lampor 4=>5
    for (let i = number + 1; i <= 5; i++) {
        allStars[i - 1].src = "/static/star-gray.png"
    }
}