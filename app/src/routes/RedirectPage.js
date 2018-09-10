import React from 'react'

import { UserManager } from 'oidc-client'

export default class redirectBox extends React.Component {
    componentDidMount() {
        const mgr = new UserManager({})
        mgr.signinPopupCallback()
    }

    render(){
        return(
            <div>
                <h1> Redirecting.. </h1>
            </div>
        )
    }
}