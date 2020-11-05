var countries;
var countryItems;
var tier = 0;
var visitedCountries = [];

var textInput = document.getElementById("syote");
var travellerList = document.getElementById("matkustajat");
var countryList = document.getElementById("maat");
var mainDiv = document.getElementById("main");
var firstTier = document.getElementById("tier");
var submitButton = document.getElementById("nappula");

fetch("https://restcountries.eu/rest/v2/all")
    .then(resp => resp.json())
    .then(data => init(data))
    .then(() => populateFirstTier(createCountryCard(randomCountry())))
    .catch(err => console.log("Error: " + err));

function init(data) {
    console.log(data);
    countries = data;
}


submitButton.addEventListener("click", (event) => {
    event.preventDefault();
    setTraveller(textInput)
})

textInput.addEventListener("keypress", (event) => {

    submitButton.disabled = textInput.value.length < 1;

})
textInput.addEventListener("keydown", (event) => {
    if (event.keyCode === 13) {
        event.preventDefault();
    }
    if (event.keyCode === 8) {
        submitButton.disabled = textInput.value.length < 3;
    }
})

function setTraveller(textInput) {
    var str = textInput.value;

    if (str.length > 0) {
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(str));
        travellerList.appendChild(li);

        textInput.value = "";
        submitButton.disabled = true;
    }
}

function randomCountry() {
    do {
        var random = Math.round(Math.random() * 250);
        var country = countries[random];
    }
    while (country.borders.length < 2);
    return country;
}

function populateFirstTier() {
    for (var i = 0; i < 3; i++) {
        firstTier.appendChild(createCountryCard(randomCountry()));
    }
}

function populateNewTier(countryData) {
    var div = document.createElement("div");
    div.className = "tier";
    var h3 = document.createElement("h3");

    if (!canContinue(countryData)) {
        h3.appendChild(document.createTextNode("Maat loppu!"));
        div.appendChild(h3);
    }
    else { 
        tier++;
        h3.appendChild(document.createTextNode("Taso " + tier));
        div.appendChild(h3);

        var borders = countryData.borders;
        for (var i = 0; i < borders.length; i++) {
            var borderCountry = borders[i];
            if (!isVisited(findByCode(borderCountry))) {
                div.appendChild(createCountryCard(findByCode(borderCountry)));
            }
        }
    }
    main.appendChild(div);
}

function createCountryCard(countryData) {
    var div = document.createElement("div");
    div.setAttribute("id", "maa");
    div.style.display = "block";

    var flag = document.createElement("img");
    flag.style.border = "solid";
    flag.src = countryData.flag;

    var nameAndCapital = document.createElement("p");
    var name = document.createElement("span");
    name.style.fontWeight = "bold";
    name.appendChild(document.createTextNode(countryData.name + ": "));
    var capital = document.createElement("span");
    capital.appendChild(document.createTextNode(countryData.capital));
    nameAndCapital.appendChild(name);
    nameAndCapital.appendChild(capital);

    var currenciesText = document.createElement("span");
    currenciesText.style.fontWeight = "bold";
    currenciesText.appendChild(document.createTextNode("Currencies: "));

    var bordersText = document.createElement("p");
    var firstSpan = document.createElement("span");
    firstSpan.style.fontWeight = "bold";
    firstSpan.appendChild(document.createTextNode("Borders: "));
    bordersText.appendChild(firstSpan);
    bordersText.appendChild(getBorders(countryData));


    div.appendChild(flag);
    div.appendChild(nameAndCapital);
    div.appendChild(currenciesText);
    div.appendChild(getCurrencies(countryData));
    div.appendChild(bordersText);

    div.className = "countryCard";
    div.style.boxShadow = "5px 5px 20px";

    div.addEventListener("click", function () {
        div.className = "countryCard selected";
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(countryData.alpha3Code));
        countryList.appendChild(li);

        visitedCountries.push(countryData.alpha3Code);

        populateNewTier(countryData);
    });

    return div;
}

function isVisited(countryData) {
    var code = countryData.alpha3Code;
    for (var i = 0; i < visitedCountries.length; i++) {
        if (code == visitedCountries[i]) {
            return true;

        }
    }
    return false;
}

function canContinue(countryData) {
    var borderCountries = countryData.borders;

    for (var i = 0; i < borderCountries.length; i++) {
        var borderCountry = borderCountries[i];
        if (!visitedCountries.includes(borderCountry) ){
            return true;
        }
    }
    return false;
}

function findByCode(code) {
    for (var i = 0; i < countries.length; i++) {
        var country = countries[i];
        if (country.alpha3Code == code) {
            return country;
        }
    }
}

function getCurrencies(countryData) {
    var list = document.createElement("ul");
    var currencies = countryData.currencies;

    for (var i = 0; i < currencies.length; i++) {
        var currency = currencies[i];
        var name = currency.name;
        var code = currency.code;

        if (code == null) {
            code = "";
        }

        var li = document.createElement("li");
        li.appendChild(document.createTextNode(name + ", (" + code + ")"));

        list.appendChild(li);
    }
    return list;
}

function getBorders(countryData) {
    var borders = countryData.borders;

    var textSpan = document.createElement("span");

    for (var i = 0; i < borders.length; i++) {
        var borderCode = borders[i];
        textSpan.appendChild(document.createTextNode(borderCode + " "));
    }
    return textSpan;
}
