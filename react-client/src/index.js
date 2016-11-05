import React from 'react'
import ReactDOM from 'react-dom'
import {Provider} from 'react-redux'
import './index.css'
import WizardForm from './WizardForm'
import App from './App'
import {createStore, combineReducers} from 'redux'
import {reducer as formReducer} from 'redux-form'
import {reducer as espdApp} from './modules/criteria'

const reducers = {
    espdApp,
    form: formReducer     // <---- Mounted at 'form'
}
const reducer = combineReducers(reducers)
const store = createStore(reducer)

const showResults = values =>
    new Promise(resolve => {
        setTimeout(() => {  // simulate server latency
            window.alert(`You submitted:\n\n${JSON.stringify(values, null, 2)}`)
            resolve()
        }, 500)
    })

ReactDOM.render(
    <Provider store={store}>
        <WizardForm onSubmit={showResults}/>
    </Provider>,
    document.getElementById('root')
)
