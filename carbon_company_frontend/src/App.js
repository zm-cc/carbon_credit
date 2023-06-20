import React, {Component} from 'react';
import './css/App.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import Login from './js/UserService/Login';
import Page from './js/TravelCompany/Page';
import AllCompanies from './js/AuthenticationInstitution/AllCompanies';
import TravelRecord from './js/TravelCompany/TravelRecord';
import Manager from './js/AuthenticationInstitution/Manager';
import Register from "./js/UserService/Register";
import ManagerDetail from "./js/AuthenticationInstitution/ManagerDetail";

class App extends Component {
    render() {
        return (
            <div className="App-header">
                <Router>
                    <div>
                        <Route exact path="/" component={Login}/>
                        <Route path="/Page" component={Page}/>
                        <Route path="/TravelRecord" component={TravelRecord}/>
                        <Route path="/AllCompanies" component={AllCompanies}/>
                        <Route path="/Manager" component={Manager}/>
                        <Route path="/Register" component={Register}/>
                        <Route path="/ManagerDetail" component={ManagerDetail}/>
                    </div>
                </Router>
            </div>
        );
    }
}

export default App;
