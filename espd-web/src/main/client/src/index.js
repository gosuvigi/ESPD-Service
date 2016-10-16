import React from 'react'
import ReactDOM from 'react-dom'
import {Provider} from 'react-redux'
import './index.css'
import WizardForm from './WizardForm'
import {createStore, combineReducers} from 'redux'
import {reducer as formReducer} from 'redux-form'

const reducers = {
    // ... your other reducers here ...
    form: formReducer     // <---- Mounted at 'form'
}
const reducer = combineReducers(reducers)
const store = createStore(reducer)

ReactDOM.render(
    <Provider store={store}>
        <WizardForm/>
    </Provider>,
    document.getElementById('root')
)
