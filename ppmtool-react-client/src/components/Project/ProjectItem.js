import React, { PureComponent } from 'react';
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteProject } from "../../actions/projectActions";

class ProjectItem extends PureComponent {

    onDeleteClick = id => {
        this.props.deleteProject(id);
    }

    render() {
        const { name, identifier, description } = this.props.project;
        return (
            <div className="container">
                <div className="card card-body bg-light mb-3">
                    <div className="row">
                        <div className="col-2">
                            <span className="mx-auto">{identifier}</span>
                        </div>
                        <div className="col-lg-6 col-md-4 col-8">
                            <h3>{name}</h3>
                            <p>{description}</p>
                        </div>
                        <div className="col-md-4 d-none d-lg-block">
                            <ul className="list-group">
                                <Link to={`/projectBoard/${identifier}`}>
                                    <li className="list-group-item board">
                                        <i className="fas fa-flag mr-1"> Project Board </i>
                                    </li>
                                </Link>
                                <Link to={`/updateProject/${identifier}`}>
                                    <li className="list-group-item update">
                                        <i className="fas fa-edit mr-1"> Update Project Info</i>
                                    </li>
                                </Link>

                                <li className="list-group-item delete" onClick={this.onDeleteClick.bind(this, identifier)}>
                                    <i className="fas fa-trash mr-1"> Delete Project</i>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

ProjectItem.propTypes = {
    deleteProject: PropTypes.func.isRequired
}

export default connect(null, { deleteProject })(ProjectItem);
