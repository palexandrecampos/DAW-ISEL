import React from "react";
import { Nav, Navbar, NavItem } from "react-bootstrap";
import Cookies from "universal-cookie";
const cookies = new Cookies()

export default class NavbarComponent extends React.Component {

  render(props) {
    if (cookies.get('AuthCookie')) {
      return (
        <div>
          <Navbar>
            <Nav pullRight>
            <NavItem eventKey={1} href="/myChecklists">
                My Checklists
              </NavItem>
              <NavItem eventKey={2} href="/myTemplates">
                My Checklists Templates
              </NavItem>
              <NavItem eventKey={3} href="/logout">
                Logout
              </NavItem>
            </Nav>
          </Navbar>
        </div>)
    }
    return (
      <div>
        <Navbar>
        </Navbar>
      </div>
    )
  }
};