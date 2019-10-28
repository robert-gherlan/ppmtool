import React, { Component } from 'react';
import { connect } from "react-redux";
import { getProjectTask, updateProjectTask } from "../../../actions/backlogActions"
import PropTypes from "prop-types";
import { Link } from "react-router-dom";
import classnames from "classnames";

class UpdateProjectTask extends Component {

    constructor(props) {
        super(props);
        const { projectTaskId } = this.props.match.params;
        this.state = {
            id: "",
            summary: "",
            acceptanceCriteria: "",
            projectSequence: "",
            status: "",
            priority: 0,
            dueDate: "",
            createdAt: "",
            projectIdentifier: projectTaskId,
            errors: {}
        }

        this.onSubmit = this.onSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    componentDidMount() {
        const { backlogId, projectTaskId } = this.props.match.params;
        this.props.getProjectTask(backlogId, projectTaskId, this.props.history);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }

        const {
            id,
            summary,
            acceptanceCriteria,
            projectSequence,
            status,
            priority,
            dueDate,
            projectIdentifier,
            createdAt
        } = nextProps.project_task;

        this.setState({
            id,
            summary,
            acceptanceCriteria,
            projectSequence,
            status,
            priority,
            dueDate,
            projectIdentifier,
            createdAt
        })
    }

    onSubmit(e) {
        e.preventDefault();
        const { id, summary, acceptanceCriteria, status, priority, dueDate, projectIdentifier, projectSequence, createdAt } = this.state;
        const updatedProjectTask = {
            id: id,
            summary: summary,
            acceptanceCriteria: acceptanceCriteria,
            projectSequence: projectSequence,
            status: status,
            priority: priority,
            dueDate: dueDate,
            projectIdentifier: projectIdentifier,
            createdAt: createdAt
        }

        this.props.updateProjectTask(projectIdentifier, projectSequence, updatedProjectTask, this.props.history);
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    render() {
        const { summary, acceptanceCriteria, status, priority, dueDate, projectIdentifier, projectSequence, errors } = this.state;
        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${projectIdentifier}`} className="btn btn-light">
                                <i className="fas fa-arrow-circle-left mr-1"></i> Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Update Project Task</h4>
                            <p className="lead text-center">Project Name: {projectIdentifier} | Project Task ID: {projectSequence}</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text"
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
                                    >
                                    </textarea>
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
                                        <option value={0}>Select Priority</option>
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

                                <input type="submit" className="btn btn-primary btn-block mt-4" value="Update" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

UpdateProjectTask.propTypes = {
    getProjectTask: PropTypes.func.isRequired,
    updateProjectTask: PropTypes.func.isRequired,
    project_task: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    project_task: state.backlog.project_task,
    errors: state.errors
})

export default connect(mapStateToProps, { getProjectTask, updateProjectTask })(UpdateProjectTask);