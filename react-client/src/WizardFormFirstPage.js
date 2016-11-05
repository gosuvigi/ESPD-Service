/**
 * Created by vigi on 10/2/2016 3:26 PM.
 */
import React from 'react'
import { connect } from 'react-redux'
import {Field, reduxForm} from 'redux-form'
import validate from './validate'
import renderField from './renderField'
import DropdownList from 'react-widgets/lib/DropdownList'
import SelectList from 'react-widgets/lib/SelectList'
import Multiselect from 'react-widgets/lib/Multiselect'
import 'react-widgets/dist/css/react-widgets.css'
import {loadExclusionCriteria} from './modules/criteria'

const colors = [{color: 'Red', value: 'ff0000'},
    {color: 'Green', value: '00ff00'},
    {color: 'Blue', value: '0000ff'}]

const renderDropdownList = ({input, ...rest}) =>
    <DropdownList {...input} {...rest}/>

const renderMultiselect = ({input, ...rest}) =>
    <Multiselect {...input}
                 onBlur={() => input.onBlur()}
                 value={input.value || []} // requires value to be an array
                 {...rest}/>

const renderSelectList = ({input, ...rest}) =>
    <SelectList {...input} onBlur={() => input.onBlur()} {...rest}/>

let WizardFormFirstPage = (props) => {
    const { handleSubmit, load, pristine, reset, submitting } = props
    return (
        <form onSubmit={handleSubmit}>
            <div>
                <button type="button" onClick={() => load()}>Load Criteria</button>
            </div>
            <Field name="firstName" type="text" component={renderField} label="First Name"/>
            <Field name="lastName" type="text" component={renderField} label="Last Name"/>
            <div>
                <button type="submit" className="next">Next</button>
            </div>
            <div>
                <label>Favorite Colors</label>
                <Field
                    name="favoriteColor"
                    component={renderDropdownList}
                    data={colors}
                    valueField="value"
                    textField="color"/>
            </div>
            <div>
                <label>Hobbies</label>
                <Field
                    name="hobbies"
                    component={renderMultiselect}
                    data={['Guitar', 'Cycling', 'Hiking']}/>
            </div>
            <div>
                <label>Sex</label>
                <Field
                    name="sex"
                    component={renderSelectList}
                    data={['male', 'female']}/>
            </div>
        </form>
    )
}

WizardFormFirstPage = reduxForm({
    form: 'wizard',              // <------ same form name
    destroyOnUnmount: false,     // <------ preserve form data
    validate
})(WizardFormFirstPage)

WizardFormFirstPage = connect(
    state => ({
        initialValues: state.exclusionCriteria
    }),
    {load: loadExclusionCriteria}
)(WizardFormFirstPage)

export default WizardFormFirstPage