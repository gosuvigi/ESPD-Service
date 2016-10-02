/**
 * Created by vigi on 10/2/2016 3:26 PM.
 */
import React from 'react'
import { Field, reduxForm } from 'redux-form'
import validate from './validate'
import renderField from './renderField'
import DropdownList from 'react-widgets/lib/DropdownList'
import SelectList from 'react-widgets/lib/SelectList'
import Multiselect from 'react-widgets/lib/Multiselect'
import 'react-widgets/dist/css/react-widgets.css'

const colors = [ { color: 'Red', value: 'ff0000' },
    { color: 'Green', value: '00ff00' },
    { color: 'Blue', value: '0000ff' } ]

const renderDropdownList = ({ input, ...rest }) =>
    <DropdownList {...input} {...rest}/>

const renderMultiselect = ({ input, ...rest }) =>
    <Multiselect {...input}
                 onBlur={() => input.onBlur()}
                 value={input.value || []} // requires value to be an array
                 {...rest}/>

const renderSelectList = ({ input, ...rest }) =>
    <SelectList {...input} onBlur={() => input.onBlur()} {...rest}/>

const WizardFormFirstPage = (props) => {
    const { handleSubmit } = props
    return (
        <form onSubmit={handleSubmit}>
            <Field name="firstName" type="text" component={renderField} label="First Name"/>
            <Field name="lastName" type="text" component={renderField} label="Last Name"/>
            <div>
                <button type="submit" className="next">Next</button>
            </div>
            <div>
                <label>Favorite Color</label>
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
                    data={[ 'Guitar', 'Cycling', 'Hiking' ]}/>
            </div>
            <div>
                <label>Sex</label>
                <Field
                    name="sex"
                    component={renderSelectList}
                    data={[ 'male', 'female' ]}/>
            </div>
        </form>
    )
}

export default reduxForm({
    form: 'wizard',              // <------ same form name
    destroyOnUnmount: false,     // <------ preserve form data
    validate
})(WizardFormFirstPage)