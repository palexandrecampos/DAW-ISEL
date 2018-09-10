import React from "react";
import Navbar from './Navbar'
import { Container } from 'reactstrap';
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import { Redirect } from "react-router-dom"
import { Button } from "reactstrap"
const cookies = new Cookies()


export default class ChecklistItemDetailsPage extends React.Component {
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


    render() {
        if (this.state.toDelete) {
            const cookie = cookies.get('AuthCookie')
            const checklistId = this.props.match.params.checklistId
            const itemId = this.props.match.params.itemId
            const url = `http://35.189.65.78/api/${checklistId}/deleteItem/${itemId}`
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
                            return (<Redirect to={`/checklist/${checklistId}/items`} />);
                        }
                        }
                    />
                )} />
            )
        }

        const cookie = cookies.get('AuthCookie')
        const checklistId = this.props.match.params.checklistId
        const itemId = this.props.match.params.itemId
        const url = `http://35.189.65.78/api/${checklistId}/item/${itemId}`
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
                                                        <p> <strong>Item Name: </strong> {json.properties.itemName} </p>
                                                        <p> <strong>Item Description: </strong> {json.properties.itemDescription} </p>
                                                        <p> <strong>Item State: </strong>{json.properties.itemState} </p>
                                                        <p> <strong>Item Id: </strong> {json.properties.itemId} </p>
                                                        <Button color="danger" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Delete Item</Button>
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