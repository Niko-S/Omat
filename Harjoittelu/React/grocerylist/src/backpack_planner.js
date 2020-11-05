import React from 'react';
import './backpack_style.css'

export default class Planner extends React.Component {

   constructor(props) {
      super(props);
      this.state = {
         datas: [],
         tiers: [],
      };
   }

   componentDidMount() {
      fetch("https://restcountries.eu/rest/v2/all")
         .then(resp => resp.json())
         .then(data => {
            this.setState({ datas: data });
            this.init();
         })
         .catch(err => console.log("Error: " + err));
   }

   init() {
      var countries = [];
      while (countries.length < 3) {
         var country = this.randomCountry();
         countries = [...countries, country];
         this.setState({ datas: this.state.datas.filter(element => element !== country) });

      }
      console.log(countries);
      this.setState({ tiers: [...this.state.tiers, countries] });
      console.log(this.state.tiers);
   }

   randomCountry() {
      do {
         var random = Math.floor(Math.random() * this.state.datas.length);
         var country = this.state.datas[random];
      }
      while (country.borders.length < 2);
      return country;
   }

   render() {
      return (
         <div>
            {this.state.tiers.map((tier, i) => <Tier key={i} countries={tier} />)}
         </div>
      );
   }

}

class Tier extends React.Component {

   constructor(props) {
      super(props);

      this.state = { selected: false };
   }

   render() {
      return (
         <div className="tier">
            {this.props.countries.map(country => <Country key={country.name} data={country} />)}
         </div>
      );
   }
}

class Country extends React.Component {

   constructor(props) {
      super(props);

      this.state = { selected: false };
      this.onSelected = this.onSelected.bind(this);
   }

   onSelected() {
      this.setState((prevState => ({ selected: !prevState.selected })));
   }

   render() {
      return (
         <div className="country" onClick={this.onSelected}>
            <img src={this.props.data.flag} />
            <p><b>{this.props.data.name}:</b> {this.props.data.capital}</p>
            <p> Code: {this.props.data.alpha3Code}</p>
         </div>
      );
   }

}