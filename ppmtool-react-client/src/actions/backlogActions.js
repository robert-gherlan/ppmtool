import axios from "axios";
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK } from "./types";

/**
 * 
 * @param {*} backlogId 
 * @param {*} projectTask 
 * @param {*} history 
 */
export const addProjectTask = (backlogId, projectTask, history) => async dispatch => {
    try {
        await axios.post(`/api/backlog/${backlogId}`, projectTask);
        history.push(`/projectBoard/${backlogId}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (error) {
        // When an invalid project task object is send to the server
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}

/**
 * 
 * @param {*} backlogId 
 */
export const getBacklog = (backlogId) => async dispatch => {
    try {
        const response = await axios.get(`/api/backlog/${backlogId}`);
        dispatch({
            type: GET_BACKLOG,
            payload: response.data
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}

/**
 * 
 * @param {*} backlogId 
 * @param {*} projectTaskId 
 * @param {*} history 
 */
export const getProjectTask = (backlogId, projectTaskId, history) => async dispatch => {
    try {
        const response = await axios.get(`/api/backlog/${backlogId}/project-task/${projectTaskId}`);
        dispatch({
            type: GET_PROJECT_TASK,
            payload: response.data
        })
    } catch (error) {
        history.push("/dashboard");
    }
}

/**
 * 
 * @param {*} backlogId 
 * @param {*} projectTaskId 
 * @param {*} updatedProjectTask 
 * @param {*} history 
 */
export const updateProjectTask = (backlogId, projectTaskId, updatedProjectTask, history) => async dispatch => {
    try {
        await axios.patch(`/api/backlog/${backlogId}/project-task/${projectTaskId}`, updatedProjectTask);
        history.push(`/projectBoard/${backlogId}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}

/**
 * Delete a project atsk based on the provided indentifiers as parameter.
 * @param {*} backlogId The backlog identifier.
 * @param {*} projectTaskId  The project task identifier.
 */
export const deleteProjectTask = (backlogId, projectTaskId) => async dispatch => {
    if (window.confirm(`You are deliting the Project task ${projectTaskId}, be careful that this action cannot be undone`)) {
        await axios.delete(`/api/backlog/${backlogId}/project-task/${projectTaskId}`);
        dispatch({
            type: DELETE_PROJECT_TASK,
            payload: projectTaskId
        })
    }
}