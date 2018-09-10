import React from "react";
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import { Redirect } from "react-router-dom"
import { Button } from 'react-bootstrap'
const cookies = new Cookies()


export default class CreateChecklistPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            checklistName: "",
            completionDate: "",
            toCreateAndRedirect: ""
        }

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault()
        this.setState(() => ({ toCreateAndRedirect: true }))
    }

    validateForm() {
        return this.state.checklistName.length > 0 && this.state.completionDate.length > 0;
    }


    handleChange(e) {
        this.setState(
            { [e.target.name]: e.target.value }
        )

    }

    render(props) {
        if (this.state.toCreateAndRedirect) {
            const cookie = cookies.get('AuthCookie')
            const url = `http://35.189.65.78/api/createChecklist`
            const header = {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': `Bearer ${cookie}`,
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({
                    checklistName: this.state.checklistName,
                    completionDate: this.state.completionDate,
                })
            }

            return (<HttpGet
                url={url}
                headers={header}
                render={(result) => (
                    <HttpGetSwitch result={result}
                        onJson={json => {
                            cookies.set('AuthCookie', cookie)
                            return (<Redirect to={'/checklist/' + json.properties.checklistId} />);
                        }
                        }
                    />
                )} />
            )
        }

        return (
            <div>
                <div>
                    <div className="container panel panel-default" id="login-box">
                        <h1 className="text-center">Create Checklist</h1>
                        <form className="form-horizontal panel-body" onSubmit={this.handleSubmit}>
                            <div className="form-group">
                                <input type="text"
                                    value={this.state.checklistName}
                                    className="form-control"
                                    name="checklistName"
                                    placeholder="Checklist Name"
                                    onChange={this.handleChange} />
                            </div>
                            <div className="form-group">
                                <input
                                    type="date"
                                    value={this.state.checklistDate}
                                    className="form-control"
                                    name="completionDate"
                                    placeholder="Checklist Date"
                                    onChange={this.handleChange} />
                            </div>
                            <Button
                                block
                                type="submit"
                                disabled={!this.validateForm()}
                            > Create
                            </Button>
                        </form>
                    </div>
                </div>
            </div >
        )
    }
}