import React from "react"
import './App.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import PrimeReact from 'primereact/api';

PrimeReact.ripple = true;

const App = (props) => {

  return (
      <>
        {props.children}
      </>
  )
};

export default App;
