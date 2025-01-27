import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import classnames from "classnames";
import { addProjectTask } from "../../../actions/backlogActions";
import PropTypes from "prop-types";

class AddPojectTask extends Component {

    constructor(props) {
        super(props);
        const { id } = this.props.match.params;
        this.state = {
            summary: "",
            acceptanceCriteria: "",
            status: "",
            priority: 0,
            dueDate: "",
            projectIdentifier: id,
            errors: {}
        }

        this.onSubmit = this.onSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors })
        }
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    onSubmit(e) {
        e.preventDefault();
        const { summary, acceptanceCriteria, status, priority, dueDate } = this.state;
        const newTask = {
            summary: summary,
            acceptanceCriteria: acceptanceCriteria,
            status: status,
            priority: priority,
            dueDate: dueDate,
        }

        this.props.addProjectTask(this.state.projectIdentifier, newTask, this.props.history);
    }

    render() {
        const { id } = this.props.match.params;
        const { summary, acceptanceCriteria, status, priority, dueDate, errors } = this.state;
        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${id}`} className="btn btn-light">
                                <i className="fas fa-arrow-circle-left mr-1"></i> Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Add Project Task</h4>
                            <p className="lead text-center">Project Name + Project Code</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input
                                        type="text"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.summary
                                        })}
                                        name="summary"
                                        placeholder="Project Task summary"
                                        value={summary}
                                        onChange={this.onChange}
                                    />
                                    {
                                        errors.summary && (
                                            <div className="invalid-feedback">{errors.summary}</div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <textarea
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.acceptanceCriteria
                                        })}
                                        placeholder="Acceptance Criteria"
                                        name="acceptanceCriteria"
                                        value={acceptanceCriteria}
                                        onChange={this.onChange}
                                    ></textarea>
                                    {
                                        errors.acceptanceCriteria && (
                                            <div className="invalid-feedback">{errors.acceptanceCriteria}</div>
                                        )
                                    }
                                </div>
                                <h6>Due Date</h6>
                                <div className="form-group">
                                    <input type="date"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.dueDate
                                        })}
                                        name="dueDate"
                                        value={dueDate}
                                        onChange={this.onChange}
                                    />
                                    {
                                        errors.dueDate && (
                                            <div className="invalid-feedback">{errors.dueDate}</div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <select
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.priority
                                        })}
                                        name="priority"
                                        value={priority}
                                        onChange={this.onChange}
                                    >
                                        <option value="">Select Priority</option>
                                        <option value={1}>High</option>
                                        <option value={2}>Medium</option>
                                        <option value={3}>Low</option>
                                    </select>
                                    {
                                        errors.priority && (
                                            <div className="invalid-feedback">{errors.priority}</div>
                                        )
                                    }
                                </div>

                                <div className="form-group">
                                    <select
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.status
                                        })}
                                        name="status"
                                        value={status}
                                        onChange={this.onChange}
                                    >
                                        <option value="">Select Status</option>
                                        <option value="TO_DO">TO DO</option>
                                        <option value="IN_PROGRESS">IN PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>
                                    {
                                        errors.status && (
                                            <div className="invalid-feedback">{errors.status}</div>
                                        )
                                    }
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

AddPojectTask.propTypes = {
    addProjectTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
})

export default connect(mapStateToProps, { addProjectTask })(AddPojectTask);