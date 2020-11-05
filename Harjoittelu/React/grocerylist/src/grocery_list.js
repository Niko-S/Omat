import React from 'react';


export default class Lists extends React.Component {

   constructor(props) {
      super(props);
      this.state = {
         lists: [],
         textInput: '',
      };

      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
   }

   handleChange(event) {
      this.setState({ textInput: event.target.value });
   }

   handleSubmit(event) {
      event.preventDefault();
      this.addNewList(this.state.textInput);
   }

   addNewList(name) {
      if (name) {
         this.setState({
            lists: [...this.state.lists, <ShoppingList name={name} key={name} />],
            textInput: ''
         });
      }
   }

   render() {
      return (
         <div>
            <div className="lists">
               {this.state.lists}
            </div>
            <form onSubmit={this.handleSubmit}>
               <input type="text" value={this.state.textInput} onChange={this.handleChange} />
               <input type="submit" value="Add new list" />
            </form>
         </div>

      );
   }

}

class ShoppingList extends React.Component {

   constructor(props) {
      super(props);
      this.state = {
         datas: [],
         textInput: '',
      }

      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
   }

   add(data) {
      if (data && !this.state.datas.includes(data)) {
         this.setState({
            datas: [...this.state.datas, this.state.textInput],
         });
      }
   }

   deleteElement(key) {
      this.setState({ datas: this.state.datas.filter(item => item !== key) })
   }


   handleChange(event) {
      this.setState({ textInput: event.target.value });
   }

   handleSubmit(event) {
      event.preventDefault();
      this.add(this.state.textInput);
      this.setState({ textInput: '' });
   }

   render() {
      const list = this.state.datas.map((data) => <li key={data} onClick={() => this.deleteElement(data)}>{data}</li>)
      return (
         <div className="list">
            <h2>{this.props.name}</h2>
            <ul>
               {list}
            </ul>
            <form onSubmit={this.handleSubmit}>
               <input type="text" value={this.state.textInput} onChange={this.handleChange} />
               <input type="submit" value="Add" />
            </form>
         </div>
      );
   }
}