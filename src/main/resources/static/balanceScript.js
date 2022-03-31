var input = document.getElementById("FormInput6");

function checkUnderBalance(balance){

    if((balance-input.value)< -50){
        alert("Ditt saldo är för lågt. Ut och jobba med dig soffpotatis! Du kan inte ligga under 50 timmar. Ditt saldo är idag: "+balance+" timmar");
        input.value = 0;
    }
}