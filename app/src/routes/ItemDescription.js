import React from "react";
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import { Link } from "react-router-dom"
import { Button, CardTitle, CardText } from "reactstrap"
const cookies = new Cookies()


export default class ChecklistItemDetailsPage extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            toChange: false
        }

        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({ toChange: true})
    }


    render(props) {
        if (this.state.toChange) {
            const cookie = cookies.get('AuthCookie')
            const checklistId = window.location.pathname.split('/')[2]
            const itemId = this.props.itemId
            const url = `http://35.189.65.78/api/${checklistId}/editChecklistItemState/${itemId}`
            const header = {
                method: 'PUT',
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
                        onJson={json => (
                            <div>
                                <CardTitle>
                                    <strong>Item Name: <Link to={{ pathname: `/checklist/${json.properties.checklistId}/item/${json.properties.itemId}` }}>
                                    {json.properties.itemName} </Link> </strong>
                                </CardTitle>
                                <CardText>
                                    <strong> State: </strong>
                                        {json.properties.itemState}
                                </CardText>
                            </div>
                        )
                        }
                    />
                )} />
            )
        }

        return (
            <div>
                <CardTitle>
                    <strong>Item Name: <Link to={{ pathname: `/checklist/${this.props.checklistId}/item/${this.props.itemId}` }}>
                    {this.props.itemName} </Link> </strong>
                </CardTitle>
                <CardText>
                    <strong> State: </strong>
                        {this.props.itemState}
                </CardText>
                {this.props.itemState === "uncompleted" && 
                        <Button color="info" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Change Item State</Button>}
            </div >
        )
    }
}