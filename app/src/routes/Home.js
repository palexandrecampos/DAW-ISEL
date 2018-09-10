import React from 'react'
import logo from '../checklist-on-clipboard.svg';
import '../App.css';
import Navbar from './Navbar'
import { Button } from "react-bootstrap";
import Cookies from 'universal-cookie'
import { UserManager } from 'oidc-client'
const cookies = new Cookies()

var mitreIDsettings = {
  authority: 'http://35.197.230.96/openid-connect-server-webapp',
  client_id: 'daw',
  redirect_uri: 'http://35.189.65.78/redirect',
  popup_redirect_uri: 'http://35.189.65.78/redirect',
  response_type: 'token id_token',
  scope: 'openid email profile',
  automaticSilentRenew: true,
  filterProtocolClaims: true,
  loadUserInfo: true
}

const mgr = new UserManager(mitreIDsettings)

class Home extends React.Component {
  constructor(props) {
    super(props);

    this.login = this.login.bind(this)

    this.state = {
      user: undefined
    }
 }

 login () {
  mgr.getUser()
      .then(user => {
        if (user) {
          this.setState({ user: user })
        } else {
          mgr.signinPopup()
            .then(user => {
              cookies.set('AuthCookie', user.access_token)
              this.props.history.push('/myPage')
            })
        }
})
}

  render() {
    return (
      <div>
        <Navbar />
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <h1 className="App-title">Welcome to Checklist Manager Application</h1>
          </header>
          <p className="App-intro">
            To start using this application, make Login and enjoy!
          </p>
          {this.state.user === undefined && <Button onClick={this.login}> Login </Button>}
        </div>
      </div>
    );
  }
}

export default Home;