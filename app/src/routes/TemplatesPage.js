import React from "react";
import { Col } from "react-bootstrap";
import { Container, CardBody, Card, CardImg, CardTitle, CardText, Button } from 'reactstrap';
import Cookies from 'universal-cookie';
import Navbar from './Navbar'
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import CreateTemplate from './CreateTemplate'
import logo from '../checklist-on-clipboard.svg';
const cookies = new Cookies()


export default class LoggedPage extends React.Component {
    constructor(props) {
        super(props);

        this.getTemplateDetails = this.getTemplateDetails.bind(this)

    }

    getTemplateDetails(id) {
        this.props.history.push(`/template/${id}`)
    }

    render(props) {
        const cookie = cookies.get('AuthCookie')
        const url = `http://35.189.65.78/api/myTemplates`
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
                            onJson={json => {
                                if (json.entities) {
                                    return (
                                        <div>
                                            <Container>
                                                <div>
                                                    {json.entities.map(template => (
                                                        <Col sm="3">
                                                            <Card>
                                                                <CardImg top width="100%" height="100%" src={logo} alt="Card image cap" />
                                                                <CardBody className="text-center" >
                                                                    <CardTitle>
                                                                        <strong> Name: </strong>
                                                                        {template.properties.checklistTemplateName}
                                                                    </CardTitle>
                                                                    <CardText>
                                                                        <strong>Description: </strong>
                                                                        {template.properties.templateDescription}
                                                                    </CardText>
                                                                    <Button color="info" onClick={() => this.getTemplateDetails(template.properties.templateId)} style={{ marginBottom: '1rem' }}>
                                                                        See Template Details
                                                                    </Button>
                                                                </CardBody>
                                                            </Card>
                                                        </Col>
                                                    ))}
                                                </div>
                                            </Container>
                                        </div>
                                    )
                                }
                                else {
                                    return (
                                        <Container>
                                            <p> <strong> Not Checklist Templates to Show! </strong> </p>
                                        </Container>
                                    )
                                }
                            }
                            }
                        />
                    )}
                />
                < CreateTemplate />
            </div >
        )
    }
}