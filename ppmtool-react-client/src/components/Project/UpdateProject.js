import React, { Component } from 'react';
import { getProject, createProject } from "../../actions/projectActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class UpdateProject extends Component {

    constructor() {
        super();
        this.state = {
            id: "",
            name: "",
            identifier: "",
            description: "",
            startDate: "",
            endDate: "",
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors })
        }
        const { id, name, identifier, description, startDate, endDate } = nextProps.project;
        this.setState({ id, name, identifier, description, startDate, endDate })
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        this.props.getProject(id, this.props.history);
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();
        const { id, name, identifier, description, startDate, endDate } = this.state;
        const updateProject = {
            id: id,
            name: name,
            identifier: identifier,
            description: description,
            startDate: startDate,
            endDate: endDate,
        }

        this.props.createProject(updateProject, this.props.history);
    }

    render() {
        const { name, identifier, description, startDate, endDate, errors } = this.state;
        return (
            <div className="project">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h5 className="display-4 text-center">Update Project Form</h5>
                            <hr />
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input
                                        type="text"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.name
                                        })}
                                        placeholder="Project Name"
                                        name="name"
                                        value={name}
                                        onChange={this.onChange}
                                    />
                                    {
                                        errors.name && (
                                            <div className="invalid-feedback">{errors.name}</div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <input
                                        type="text"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.identifier
                                        })}
                                        placeholder="Unique Project ID"
                                        disabled readOnly
                                        name="identifier"
                                        value={identifier}
                                    />
                                    {
                                        errors.identifier && (
                                            <div className="invalid-feedback">{errors.identifier}</div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <textarea
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.description
                                        })}
                                        placeholder="Project Description"
                                        name="description"
                                        value={description}
                                        onChange={this.onChange}
                                    />
                                    {
                                        errors.description && (
                                            <div className="invalid-feedback">{errors.description}</div>
                                        )
                                    }
                                </div>
                                <h6>Start Date</h6>
                                <div className="form-group">
                                    <input
                                        type="date"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.startDate
                                        })}
                                        name="startDate"
                                        value={startDate}
                                        onChange={this.onChange}
                                    />
                                    {
                                        errors.startDate && (
                                            <div className="invalid-feedback">{errors.startDate}</div>
                                        )
                                    }
                                </div>
                                <h6>Estimated End Date</h6>
                                <div className="form-group">
                                    <input
                                        type="date"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.endDate
                                        })}
                                        name="endDate"
                                        value={endDate}
                                        onChange={this.onChange}
                                    />
                                    {
                                        errors.endDate && (
                                            <div className="invalid-feedback">{errors.endDate}</div>
                                        )
                                    }
                                </div>

                                <input
                                    type="submit"
                                    className="btn btn-primary btn-block mt-4"
                                />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

UpdateProject.propTypes = {
    getProject: PropTypes.func.isRequired,
    createProject: PropTypes.func.isRequired,
    project: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    project: state.project.project,
    errors: state.errors
});

export default connect(mapStateToProps, { getProject, createProject })(UpdateProject);