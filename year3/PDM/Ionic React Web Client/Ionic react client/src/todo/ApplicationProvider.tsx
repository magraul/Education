import React, {useCallback, useReducer} from "react";
import PropTypes from 'prop-types';

type ConnectedToServerFn = (status: boolean) => Promise<any>;
export interface ApplicationState {
    isConnectedToServer: boolean,
    setConnectionStatus?: ConnectedToServerFn
}

interface ActionProps {
    type: string,
    payload?: any,
}

const initialState: ApplicationState = {
    isConnectedToServer: false,
};

const CONNECTED_TO_SERVER = 'CONNECTED_TO_SERVER'
const DISCONNECTED_FROM_SERVER = 'DISCONNECTED_FROM_SERVER'

const reducer: (state: ApplicationState, action: ActionProps) => ApplicationState =
    (state, {type, payload}) => {
        switch (type) {
            case CONNECTED_TO_SERVER:
                console.log('$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$')
                return {...state, isConnectedToServer: true}
            case DISCONNECTED_FROM_SERVER:
                return {...state, isConnectedToServer: false}
            default:
                return state
        }
    }

    export const ApplicationContext = React.createContext<ApplicationState>(initialState);
interface ApplicationProviderProps {
    children: PropTypes.ReactNodeLike,
}

export const ApplicationProvider: React.FC<ApplicationProviderProps> = ({children}) =>{
    const [state, dispatch] = useReducer(reducer, initialState);
    const {isConnectedToServer} = state
    const setConnectionStatus = useCallback<ConnectedToServerFn>(setConnectionStatusCallback, []);

    const value = {isConnectedToServer, setConnectionStatus}
    return (
        <ApplicationContext.Provider value={value}>
            {children}
        </ApplicationContext.Provider>
    )

    async function setConnectionStatusCallback(status: boolean) {
        console.log(status)
        if (status) {
            dispatch({type: CONNECTED_TO_SERVER, payload: {status}})
            console.log(state)
        }
    }

}