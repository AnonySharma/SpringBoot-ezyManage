var nextButtonCash = document.getElementById("nextButtonCash");
var nextButtonOther = document.getElementById("nextButtonOther");

function cashHandler() {
	// if cash radio is checked, uncheck the other radio
	if (document.getElementById("cashRadio").checked) {
		document.getElementById("otherRadio").checked = false;
		nextButtonCash.classList.remove("hide");
		nextButtonOther.classList.add("hide");
	}
}

function otherHandler() {
	// if other radio is checked, uncheck the cash radio
	if (document.getElementById("otherRadio").checked) {
		document.getElementById("cashRadio").checked = false;
		nextButtonCash.classList.add("hide");
		nextButtonOther.classList.remove("hide");
	}
}
