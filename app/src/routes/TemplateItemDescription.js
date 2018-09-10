import React from "react";
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import { Link } from "react-router-dom"
import { Button, CardText, CardTitle } from "reactstrap"
const cookies = new Cookies()


export default class ChangeTemplateItemState extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            toChange: false
        }

        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({ toChange: true })
    }


    render(props) {
        if (this.state.toChange) {
            const cookie = cookies.get('AuthCookie')
            const templateId = window.location.pathname.split('/')[1]
            const templateItemId = this.props.templateItemId
            const url = `http://35.189.65.78/api/${templateId}/editTemplateItemState/${templateItemId}`
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
                                    <strong>Item Name: <Link to={{ pathname: `/${json.properties.templateId}/templateItem/${json.properties.templateItemId}` }}>
                                        {json.properties.templateItemName} </Link> </strong>
                                </CardTitle>
                                <CardText>
                                    <strong> State: </strong>
                                        {json.properties.templateItemState}
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
                    <strong>Item Name: <Link to={{ pathname: `/${this.props.templateId}/templateItem/${this.props.templateItemId}` }}>
                        {this.props.templateItemName} </Link> </strong>
                </CardTitle>
                <CardText>
                    <strong> State: </strong>
                    {this.props.templateItemState}
                </CardText>
                {this.props.templateItemState === "uncompleted" &&
                        <Button color="info" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Change Item State</Button>}
            </div >
        )
    }
}