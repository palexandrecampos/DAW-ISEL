import React from "react";
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import { Redirect } from "react-router-dom"
import { Button } from 'react-bootstrap'
const cookies = new Cookies()


export default class CreateTemplatePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            templateItemName: "",
            templateItemDescription: "",
            templateId: "",
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
        return this.state.templateItemName.length > 0 && this.state.templateItemDescription.length > 0;
    }


    handleChange(e) {
        this.setState(
            { [e.target.name]: e.target.value }
        )

    }

    render(props) {
        if (this.state.toCreateAndRedirect) {
            const cookie = cookies.get('AuthCookie')
            const id = window.location.pathname.split('/')[1]
            const url = `http://35.189.65.78/api/${id}/createTemplateItem`
            const header = {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': `Bearer ${cookie}`,
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({
                    templateItemName: this.state.templateItemName,
                    templateItemDescription: this.state.templateItemDescription,
                })
            }

            return (<HttpGet
                url={url}
                headers={header}
                render={(result) => (
                    <HttpGetSwitch result={result}
                        onJson={json => {
                            cookies.set('AuthCookie', cookie)
                            return (<Redirect to={`/template/${id}`} />);
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
                        <h1 className="text-center">Create Template Item</h1>
                        <form className="form-horizontal panel-body" onSubmit={this.handleSubmit}>
                            <div className="form-group">
                                <input type="text"
                                    value={this.state.templateItemName}
                                    className="form-control"
                                    name="templateItemName"
                                    placeholder="Template Item Name"
                                    onChange={this.handleChange} />
                            </div>
                            <div className="form-group">
                                <input
                                    type="text"
                                    value={this.state.templateItemDescription}
                                    className="form-control"
                                    name="templateItemDescription"
                                    placeholder="Template Item Description"
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