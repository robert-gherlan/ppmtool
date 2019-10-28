import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createProject } from "../../actions/projectActions";
import classnames from 'classnames';

class AddProject extends Component {

    constructor() {
        super();
        this.state = {
            identifier: "",
            name: "",
            description: "",
            startDate: "",
            endDate: "",
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    // life cycle hooks
    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();
        var { identifier, name, description, startDate, endDate } = this.state;
        const newProject = {
            identifier: identifier,
            name: name,
            description: description,
            startDate: startDate,
            endDate: endDate
        }

        this.props.createProject(newProject, this.props.history);
    }

    render() {
        var { identifier, name, description, startDate, endDate, errors } = this.state;

        return (
            <div>
                <div className="project">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h5 className="display-4 text-center">Create Project form</h5>
                                <hr />
                                <form onSubmit={this.onSubmit}>
                                    <div className="form-group">
                                        <input type="text"
                                            name="name"
                                            value={name}
                                            onChange={this.onChange}
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.name
                                            })}
                                            placeholder="Project Name"
                                        />
                                        {
                                            errors.name && (
                                                <div className="invalid-feedback">{errors.name}</div>
                                            )
                                        }
                                    </div>

                                    <div className="form-group">
                                        <input type="text"
                                            name="identifier"
                                            value={identifier}
                                            onChange={this.onChange}
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.identifier
                                            })}
                                            placeholder="Unique Project ID"
                                        />
                                        {
                                            errors.identifier && (
                                                <div className="invalid-feedback">{errors.identifier}</div>
                                            )
                                        }
                                    </div>

                                    <div className="form-group">
                                        <textarea
                                            name="description"
                                            value={description}
                                            onChange={this.onChange}
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.description
                                            })}
                                            placeholder="Project Description">
                                        </textarea>
                                        {
                                            errors.description && (
                                                <div className="invalid-feedback">{errors.description}</div>
                                            )
                                        }
                                    </div>

                                    <h6>Start Date</h6>
                                    <div className="form-group">
                                        <input type="date"
                                            name="startDate"
                                            value={startDate}
                                            onChange={this.onChange}
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.startDate
                                            })}
                                        />
                                        {
                                            errors.startDate && (
                                                <div className="invalid-feedback">{errors.startDate}</div>
                                            )
                                        }
                                    </div>

                                    <h6>Estimated End Date</h6>
                                    <div className="form-group">
                                        <input type="date"
                                            name="endDate"
                                            value={endDate}
                                            onChange={this.onChange}
                                            className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.endDate
                                            })}
                                        />
                                        {
                                            errors.endDate && (
                                                <div className="invalid-feedback">{errors.endDate}</div>
                                            )
                                        }
                                    </div>

                                    <input type="submit" className="btn btn-primary btn-block mt-4" value="Save" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

AddProject.propTypes = {
    createProject: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
})

export default connect(mapStateToProps, { createProject })(AddProject);