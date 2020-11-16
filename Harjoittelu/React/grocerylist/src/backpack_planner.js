import React from 'react';
import './backpack_style.css'

export default class Planner extends React.Component {

   constructor(props) {
      super(props);
      this.state = {
         tiers: [],
         datas: [],
         selectedCountries: []
      };
   }

   componentDidMount() {
      fetch("https://restcountries.eu/rest/v2/all")
         .then(resp => resp.json())
         .then(data => {
            console.log(data);
            this.setState({ datas: data });
            this.init();
         })
         .catch(err => console.log("Error: " + err));
   }

   init() {
      let countries = [];

      while (countries.length < 3) {
         let country = this.randomCountry();
         countries = [...countries, country];
      }

      this.setState({
         tiers: [...this.state.tiers, countries],
         selectedCountries: this.state.selectedCountries.concat(countries),
      });
      console.log("Selected countries:", this.state.selectedCountries);
   }

   randomCountry() {
      let country;
      do {
         let random = Math.floor(Math.random() * (this.state.datas.length - 1));
         country = this.state.datas[random];
      }
      while (country.borders.length < 2);
      return country;
   }

   generateNewTier(country) {
      let countries = [];
      country.borders.forEach(element => {
         let countryIsSelected = this.state.selectedCountries.includes(selectedCountry => selectedCountry.alpha3Code === element)
         if (!countryIsSelected) {
            let foundCountry = this.state.datas.find(data => data.alpha3Code === element);
            countries.push(foundCountry);
         }
      });
      this.setState({ tiers: [...this.state.tiers, countries] });
      console.log("Tiers:", this.state.tiers);
   }

   render() {
      return (
         <div>
            {this.state.tiers.map((tier, i) => <Tier
               key={i}
               countries={tier}
               passedFunc={country => {
                  if (i === this.state.tiers.length - 1) {
                     this.generateNewTier(country)
                  }
               }} />)}
         </div>
      );
   }

}

class Tier extends React.Component {

   constructor(props) {
      super(props);

      this.state = {
         selected: false,
      };
   }

   render() {
      return (
         <div className="tier">
            {this.props.countries.map(country => <Country key={country.name} data={country} passedFunc={country => this.props.passedFunc(country)} />)}
         </div>
      );
   }
}

class Country extends React.Component {

   constructor(props) {
      super(props);

      this.state = { selected: false, color: 'white' };


      this.onSelected = this.onSelected.bind(this);
   }

   onSelected() {
      this.setState((prevState => ({ selected: true, color: 'green' })));
      this.props.passedFunc(this.props.data);
   }

   onDeselected() {
      this.setState((prevState => ({ selected: false, color: 'white' })));
   }

   render() {
      return (
         <div className="country" onClick={this.onSelected} style={{ backgroundColor: this.state.color }}>
            <img src={this.props.data.flag} alt={this.props.data.name} />
            <p><b>{this.props.data.name}:</b> {this.props.data.capital}</p>
            <p> Code: {this.props.data.alpha3Code}</p>
         </div>
      );
   }

}