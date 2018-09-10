import React from 'react'
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom'
import Cookies from 'universal-cookie';

import Home from './Home'
import ChecklistsList from './ChecklistsPage'
import TemplatesPage from './TemplatesPage'
import LoggedPage from './LoggedPage'
import ChecklistDetailsPage from './ChecklistDetailsPage'
import Logout from './Logout'
import TemplateDetailsPage from './TemplateDetailsPage'
import ChecklistItemsPage from './ChecklistItemsPage'
import ChecklistItemDetailsPage from './ChecklistItemDetailsPage'
import ChecklistsCreatedByATemplate from './ChecklistCreatedByATemplate'
import TemplateItemsPage from './TemplateItemsPage'
import TemplateItemDetailsPage from './TemplateItemDetailsPage'
import RedirectPage from './RedirectPage'

const cookies = new Cookies()

const checkAuth = () => {
    const token = cookies.get('AuthCookie')

    if (!token) {
        return false;
    }
    return true;
}

const AuthRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        checkAuth() ? (
            <Component {...props} />
        ) : (
                <Redirect to={{ pathname: '/home' }} />
            )
    )} />
)

export default () => (
    <BrowserRouter>
        <Switch>
            <Route exact path="/" render={(props) => (<Redirect to="/home" />)} />
            <Route path="/home" exact render={(props) => <Home {...props}/>} />
            <Route path="/redirect" render={(props) =><RedirectPage {...props}  />}  />
            <Route path="/logout" exact render={(props) => <Logout />} />
            <AuthRoute path="/myChecklists" component={ChecklistsList} />
            <AuthRoute path="/myPage" component={LoggedPage} />
            <AuthRoute exact path="/checklist/:id" component={ChecklistDetailsPage} />
            <AuthRoute exact path="/template/:id" component = {TemplateDetailsPage} />
            <AuthRoute path="/myTemplates" component = {TemplatesPage} />
            <AuthRoute exact path="/checklist/:id/items" component = {ChecklistItemsPage} />
            <AuthRoute exact path="/checklist/:checklistId/item/:itemId" component = {ChecklistItemDetailsPage} />
            <AuthRoute exact path="/:templateId/checklists" component = {ChecklistsCreatedByATemplate} />
            <AuthRoute exact path="/:templateId/templateItems" component = {TemplateItemsPage} />
            <AuthRoute exact path="/:templateId/templateItem/:templateItemId" component = {TemplateItemDetailsPage} />
        </Switch>
    </BrowserRouter>
)