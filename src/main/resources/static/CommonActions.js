



function createCallout(calloutType, divToAdd, message){

    let callout = document.createElement("div");
    callout.className = "callout callout-" + calloutType;

    // callout.innerText = message;
    callout.innerHTML = message;

    let target = document.getElementById(divToAdd);

    target.append(callout);
}