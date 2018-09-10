import React from "react";
import Cookies from 'universal-cookie';
import { Redirect } from 'react-router-dom'
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

export default class Logout extends React.Component {
    
    render(){
        cookies.remove('AuthCookie')
        mgr.signoutPopup().then(_ => true)
        return(
            <Redirect to = '/home' />
        )
    }
}