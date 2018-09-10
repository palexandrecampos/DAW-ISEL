import React from "react";
import Navbar from './Navbar'
import { Container, Button } from 'reactstrap';
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import CreateChecklistThroughATemplate from './CreateChecklistThroughATemplate'
import { Link, Redirect } from "react-router-dom"
const cookies = new Cookies()


export default class LoggedPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            toDelete: false
        }

        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({ toDelete: true })
    }
    render(props) {

        if (this.state.toDelete) {
            const cookie = cookies.get('AuthCookie')
            const id = this.props.match.params.id
            const url = `http://35.189.65.78/api/deleteChecklistTemplate/${id}`
            const header = {
                method: 'DELETE',
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': `Bearer ${cookie}`,
                    'Access-Control-Allow-Origin': '*'
                }
            }

            return (<HttpGet
                url={url}
                headers={header}
                render={(result) => (
                    <HttpGetSwitch result={result}
                        onJson={json => {
                            cookies.set('AuthCookie', cookie)
                            return (<Redirect to={'/myTemplates'} />);
                        }
                        }
                    />
                )} />
            )
        }

        const cookie = cookies.get('AuthCookie')
        const id = this.props.match.params.id
        const url = `http://35.189.65.78/api/template/${id}`
        const header = {
            headers: {
                'Authorization': `Bearer ${cookie}`,
                'Access-Control-Allow-Origin': '*'
            }
        }
        return (
            <div>
                <Navbar />
                <HttpGet
                    url={url}
                    headers={header}
                    render={(result) => (
                        <HttpGetSwitch result={result}
                            onJson={
                                json => {
                                    return (
                                        <Container>
                                            <ul>
                                                <div>
                                                    <li>
                                                        <p> <strong>Template Name: </strong> {json.properties.checklistTemplateName} </p>
                                                        <p> <strong>Template Id: </strong>{json.properties.templateId} </p>
                                                        <p> <strong>Template Description: </strong>{json.properties.templateDescription} </p>
                                                        <p> <strong> <Link to={{ pathname: `/${json.properties.templateId}/checklists` }}>
                                                            See Checklist Created By This Template </Link> </strong> </p>
                                                        <p> <strong> <Link to={{ pathname: `/${json.properties.templateId}/templateItems` }}>
                                                            See Template Items </Link> </strong> </p>
                                                        <Button color="danger" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Delete Template</Button>
                                                    </li>
                                                </div>
                                            </ul>
                                        </Container>
                                    )
                                }
                            }
                        />
                    )}
                />
                < CreateChecklistThroughATemplate />
            </div>
        )
    }
}