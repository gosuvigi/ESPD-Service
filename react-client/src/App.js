import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {

    constructor(props) {
        super(props)

        this.state = {
            values: []
        }
    }

    componentDidMount() {
        return fetch('/espd/api/test', {
            accept: 'application/json',
        }).then(response => {
            if (response.status >= 200 && response.status < 300) {
                return response;
            } else {
                const error = new Error(`HTTP Error ${response.statusText}`)
                error.status = response.statusText
                error.response = response
                console.log(error)
                throw error
            }
        }).then(response => {
            return response.json()
        }).then((values) => (
            this.setState({
                values: values
            })
        ));
    }

    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h2>Welcome to React</h2>
                </div>
                <p className="App-intro">
                    To get started, edit <code>src/App.js</code> and save to reload.
                </p>
                <div>Values from the /api:</div>
                {
                    this.state.values.map((value, idx) => (
                        <div key={idx}>{value}</div>
                    ))
                }
            </div>
        );
    }

}

export default App;
