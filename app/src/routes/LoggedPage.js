import React from "react";
import { Jumbotron } from 'reactstrap';
import Navbar from './Navbar'

export default class LoggedPage extends React.Component {

    render(props) {
        return (
            <div>
                <Navbar />
                <Jumbotron>
                    <h1 style={{ marginLeft: '1rem' }}> Welcome to Checklist Manager Application!</h1>
                </Jumbotron>
            </div>
        )
    }
};