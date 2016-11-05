/**
 * Created by vigi on 11/5/2016 7:48 PM.
 */
import {combineReducers} from 'redux'
// Actions
export const EXCLUSION_CRITERIA_LOAD = 'EXCLUSION_CRITERIA_LOAD'
export const SELECTION_CRITERIA_LOAD = 'SELECTION_CRITERIA_LOAD'

// Action Creators
export function loadExclusionCriteria() {
    const json = require('../../json/exclusionCriteria.json');
    console.log('json:' + JSON.stringify(json))
    return { type: EXCLUSION_CRITERIA_LOAD,  json }
}

export function loadSelectionCriteria() {
    const selectionCriteria = []
    return { type: SELECTION_CRITERIA_LOAD,  selectionCriteria }
}

// Reducers

function exclusionCriteria(state = {}, action) {
    switch (action.type) {
        case EXCLUSION_CRITERIA_LOAD:
            return Object.assign({}, state, {exclusionCriteria: action.exclusionCriteria})
        default:
            return state
    }
}

function selectionCriteria(state = {}, action) {
    switch (action.type) {
        case SELECTION_CRITERIA_LOAD:
            return Object.assign({}, state, {selectionCriteria: action.selectionCriteria})
        default:
            return state
    }
}

const reducer = combineReducers({
    exclusionCriteria,
    selectionCriteria
})

export default reducer
