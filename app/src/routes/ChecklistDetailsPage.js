import React from "react";
import Navbar from './Navbar'
import { Container, Button } from 'reactstrap';
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import { Link, Redirect } from "react-router-dom"
const cookies = new Cookies()


export default class ChecklistDetails extends React.Component {
    constructor(props) {
        super(props)

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
            const url = `http://35.189.65.78/api/deleteChecklist/${id}`
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
                            return (<Redirect to={'/myChecklists'} />);
                        }
                        }
                    />
                )} />
            )
        }

        const cookie = cookies.get('AuthCookie')
        const id = this.props.match.params.id
        const url = `http://35.189.65.78/api/myChecklist/${id}`
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
                                                        <p> <strong>Checklist Name: </strong> {json.properties.checklistName} </p>
                                                        <p> <strong>Template Id Used to Create Checklist: </strong> {json.properties.templateId} </p>
                                                        <p> <strong>Checklist Id: </strong>{json.properties.checklistId} </p>
                                                        <p> <strong>Checklist completionDate: </strong>{json.properties.completionDate} </p>
                                                        <p> <strong> <Link to={{ pathname: `/checklist/${json.properties.checklistId}/items` }}>
                                                            See Checklist Items </Link> </strong> </p>
                                                        <Button color="danger" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Delete Checklist</Button>
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
            </div >
        )
    }
}