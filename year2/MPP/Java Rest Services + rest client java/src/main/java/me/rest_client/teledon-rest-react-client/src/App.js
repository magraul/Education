import React from 'react';
import './App.css';
import Tabel from './Caz';
import CazForm from "./CazForm";
import {GetCazuri, DeleteCaz, AddCaz} from './utils/rest-calls'



class App extends React.Component {

  constructor(props){
    super(props);
    this.state={cazuri:[],
      deleteF:this.deleteF.bind(this),
      addF:this.addF.bind(this),
    }
    console.log('UserApp constructor')
  }

  addF(caz){
    AddCaz(caz)
        .then(_ => GetCazuri())
        .then(cazuri => this.setState({cazuri}))
        .catch(erorr => console.log('eroare add ',erorr));
  }

  deleteF(caz){
    console.log("in delete " + caz)
    DeleteCaz(caz)
        .then(_ => GetCazuri())
        .then(cazuri => this.setState({cazuri}))
        .catch(error=>console.log('eroare delete', error));
  }


  componentDidMount(){
    console.log('inside componentDidMount')
    GetCazuri().then(cazuri=>this.setState({cazuri}));
  }

  render(){
    return(
        <div className="App">
          <h1>Teledon</h1>
            <h2>Management Cazuri Caritabile</h2>
            <CazForm addF = {this.state.addF}/>
          <Tabel cazuri={this.state.cazuri} deleteF={this.state.deleteF}/>
            <br/>
            <br/>

        </div>

    );
  }
}

export default App;