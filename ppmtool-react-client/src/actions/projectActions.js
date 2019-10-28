import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

/**
 * Create a
 * @param {*} project 
 * @param {*} history 
 */
export const createProject = (project, history) => async dispatch => {
    try {
        await axios.post("/api/project", project);
        history.push("/dashboard");
        // Becauese everything works correctly then we remove all the errorr from state.
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (error) {
        // When an invalid object is send to the server
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}

/**
 * Get all the projects found.
 */
export const getProjects = () => async dispatch => {
    const result = await axios.get("/api/project");
    dispatch({
        type: GET_PROJECTS,
        payload: result.data
    })
}

/**
 * Get a project identified by provided identifier.
 * @param {*} id The id used to identify a project on server.
 * @param {*} history 
 */
export const getProject = (id, history) => async dispatch => {
    try {
        const result = await axios.get(`/api/project/${id}`);
        dispatch({
            type: GET_PROJECT,
            payload: result.data
        })
    } catch (error) {
        // When the project is not found on the server
        history.push("/dashboard")
    }
}

/**
 * Delete a project identified by provided identifier.
 * @param {*} id The id used to identify a project on server which will be deleted.
 */
export const deleteProject = (id) => async dispatch => {
    try {
        if (window.confirm("Are you sure? This will delete the project and all the data related with it.")) {
            await axios.delete(`/api/project/${id}`);
            dispatch({
                type: DELETE_PROJECT,
                payload: id
            })
        }
    } catch (error) {
        // When an error occured during deleting action
    }
}