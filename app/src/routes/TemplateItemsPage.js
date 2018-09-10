import React from "react";
import Navbar from './Navbar'
import { Col } from "react-bootstrap";
import { Container, CardBody, Card, CardImg } from 'reactstrap';
import Cookies from 'universal-cookie';
import HttpGet from './Http-get'
import HttpGetSwitch from './Http-Get-Switch'
import CreateTemplateItem from './CreateTemplateItem'
import TemplateItemDescription from './TemplateItemDescription'
import check from '../check.svg';
const cookies = new Cookies()


export default class TemplateItemsPage extends React.Component {
    render(props) {
        const cookie = cookies.get('AuthCookie')
        const id = this.props.match.params.templateId
        const url = `http://35.189.65.78/api/${id}/templateItems`
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
                                                                        <TemplateItemDescription templateId={item.properties.templateId} templateItemId={item.properties.templateItemId} 
                                                                            templateItemName={item.properties.templateItemName} templateItemState={item.properties.templateItemState}/>
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
                                                <p> <strong> There isn´t items in this Template! </strong> </p>
                                            </Container>
                                        )
                                    }
                                }
                            }
                        />
                    )}
                />
                < CreateTemplateItem />
            </div >
        )
    }
}