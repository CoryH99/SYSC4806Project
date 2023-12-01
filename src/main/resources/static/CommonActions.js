



function createCallout(calloutType, divToAdd, message){

    let callout = document.createElement("div");
    callout.className = "callout callout-" + calloutType;

    // callout.innerText = message;
    callout.innerHTML = message;

    let target = document.getElementById(divToAdd);

    target.append(callout);
}

function toggleDiv(divToShowOrHide) {
    let x = document.getElementById(divToShowOrHide);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function viewForm(divToHide, showForm){
    toggleDiv(divToHide);
    toggleDiv(showForm);

}