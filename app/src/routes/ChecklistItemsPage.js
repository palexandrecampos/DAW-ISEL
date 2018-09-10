import React from "react";
import Navbar from './Navbar'
import { Col } from "react-bootstrap";
import { Container, CardBody, Card, CardImg } from 'reactstrap';
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import CreateChecklistItem from './CreateChecklistItem'
import ItemDescription from './ItemDescription'
import check from '../check.svg';
const cookies = new Cookies()


export default class ChecklistItemsPage extends React.Component {
    render(props) {
        const cookie = cookies.get('AuthCookie')
        const id = this.props.match.params.id
        const url = `http://35.189.65.78/api/${id}/items`
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
                                    if (json.entities) {
                                        return (
                                            <div>
                                                <Container>
                                                    <div>
                                                        {json.entities.map(item => (
                                                            <Col sm="3">
                                                                <Card>
                                                                    <CardImg top width="100%" height="100%" src={check} alt="Card image cap" />
                                                                    <CardBody className="text-center" >
                                                                        <ItemDescription checklistId={item.properties.checklistId} itemId={item.properties.itemId} itemName={item.properties.itemName} itemState={item.properties.itemState}/>
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
                                                <p> <strong> There isn´t items in this Checklist! </strong> </p>
                                            </Container>
                                        )
                                    }
                                }
                            }
                        />
                    )}
                />
                < CreateChecklistItem />
            </div >
        )
    }
}