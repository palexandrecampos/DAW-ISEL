import React from "react";
import Navbar from './Navbar'
import { Col } from "react-bootstrap";
import { Container, CardBody, Card, CardImg, CardTitle, Button } from 'reactstrap';
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import CreateChecklistPage from './CreateChecklist'
import logo from '../checklist-on-clipboard.svg';
const cookies = new Cookies()


export default class LoggedPage extends React.Component {

    constructor(props) {
        super(props);

        this.getChecklistDetails = this.getChecklistDetails.bind(this)

    }

    getChecklistDetails(id) {
        this.props.history.push(`/checklist/${id}`)
    }

    render(props) {
        const cookie = cookies.get('AuthCookie')
        const url = `http://35.189.65.78/api/myChecklists`
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
                                        <Container>
                                            <div>
                                                <Container fuild>
                                                    <div>
                                                        {json.entities.map(checklist => (
                                                            <Col sm="3">
                                                                <Card>
                                                                    <CardImg top width="100%" height="100%" src={logo} alt="Card image cap" />
                                                                    <CardBody className="text-center" >
                                                                        <CardTitle>
                                                                            <strong> Name: </strong>
                                                                            {checklist.properties.checklistName}
                                                                        </CardTitle>
                                                                        <Button color="info" onClick={() => this.getChecklistDetails(checklist.properties.checklistId)} style={{ marginBottom: '1rem' }}>
                                                                            See Checklist Details
                                                                    </Button>
                                                                    </CardBody>
                                                                </Card>
                                                            </Col>
                                                        ))}
                                                    </div>
                                                </Container>
                                            </div>
                                        </Container>
                                    );
                                }
                                else {
                                    return (
                                        <Container>
                                            <p> <strong> Not Checklists to Show! </strong> </p>
                                        </Container>
                                    );
                                }
                            }
                            }
                        />
                    )}
                />
                <CreateChecklistPage />
            </div >
        );
    }
}