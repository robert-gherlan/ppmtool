import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./store";
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";

// Import Bootstrap.
import "bootstrap/dist/css/bootstrap.min.css";
import "jquery/dist/jquery";
import "bootstrap/dist/js/bootstrap";

// Import Components.
import AddProject from "./components/Project/AddProject";
import UpdateProject from "./components/Project/UpdateProject";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard"
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Login from "./components/UserManagement/Login";
import Register from "./components/UserManagement/Register";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions"
import SecuredRoute from "./securityUtils/SecuredRoute";

const jwtToken = localStorage.jwtToken;
if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken
  })

  const currentTime = Date.now() / 1000;
  if (decoded_jwtToken.exp < currentTime) {
    store.dispatch(logout());
  }
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          {
            // Public routes
          }
          <Route exact path="/" component={Landing} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          {
            // Private routes(secured)
          }
          <Switch>
            <SecuredRoute exact path="/dashboard" component={Dashboard} />
            <SecuredRoute exact path="/addProject" component={AddProject} />
            <SecuredRoute exact path="/updateProject/:id" component={UpdateProject} />
            <SecuredRoute exact path="/projectBoard/:id" component={ProjectBoard} />
            <SecuredRoute exact path="/addProjectTask/:id" component={AddProjectTask} />
            <SecuredRoute exact path="/updateProjectTask/backlog/:backlogId/project-task/:projectTaskId" component={UpdateProjectTask} />
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;