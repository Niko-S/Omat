import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Lists from './grocery_list';
import Planner from './backpack_planner';


function App() {
  return (
    <div>
      <Planner />
    </div>
  )
}

// RENDER DOM
ReactDOM.render(
  <App />,
  document.getElementById('root')
);