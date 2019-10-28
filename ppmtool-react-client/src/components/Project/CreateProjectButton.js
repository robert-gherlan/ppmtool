import React from 'react';
import { Link } from "react-router-dom";
const CreateProjectButton = () => {
    return (
        <React.Fragment>
            <Link to="/addProject" className="btn btn-primary">
                <i className="fas fa-plus-circle mr-1"> Add New Project</i>
            </Link>
        </React.Fragment>
    );
}

export default CreateProjectButton;