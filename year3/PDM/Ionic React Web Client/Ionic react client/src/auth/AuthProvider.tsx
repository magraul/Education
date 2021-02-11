import React, {useCallback, useContext, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import { getLogger } from '../core';
import { login as loginApi } from './authApi';
import { Plugins } from '@capacitor/core';
//import {ApplicationContext} from "../todo/ApplicationProvider";
const { Storage } = Plugins;


const log = getLogger('AuthProvider');

type LoginFn = (username?: string) => void;

export interface AuthState {
  authenticationError: Error | null;
  isAuthenticated: boolean;
  isAuthenticating: boolean;
  login?: LoginFn;
  pendingAuthentication?: boolean;
  username?: string;
  password?:string;
  token: string;
  logout?: any;
  openPostingPage?:LoginFn;
  openBorrowingPage?:LoginFn;
  isOpenPosting: boolean;
  isOpenBorrowing: boolean;
}

const initialState: AuthState = {
  isAuthenticated: false,
  isOpenBorrowing: false,
  isOpenPosting:false,
  isAuthenticating: false,
  authenticationError: null,
  pendingAuthentication: false,
  token: '',
};

export const AuthContext = React.createContext<AuthState>(initialState);

interface AuthProviderProps {
  children: PropTypes.ReactNodeLike,
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [state, setState] = useState<AuthState>(initialState);
  //const {setConnectionStatus} = useContext(ApplicationContext);
  const {isAuthenticated, isAuthenticating, authenticationError, pendingAuthentication, token, isOpenPosting, isOpenBorrowing} = state;
  const login = useCallback<LoginFn>(loginCallback, []);
  const logout = useCallback(logOutCallBack, [])
  const openPostingPage = useCallback<LoginFn>(postingOpenCallBack, [])
  const openBorrowingPage = useCallback<LoginFn>(borrowingOpenCallBack, [])

  useEffect(authenticationEffect, [pendingAuthentication]);
  useEffect(checkAlreadyLoggedIn, [])
  const value = {isAuthenticated, login, isAuthenticating, authenticationError, token, logout, openPostingPage, openBorrowingPage, isOpenPosting, isOpenBorrowing};
  log('render');
  function saveLocalUsername(username?: string) {
    (async  () => {
      const { Storage } = Plugins;
      await Storage.set({
        key: 'username',
        value: JSON.stringify({
          username: username,
        })
      });
    }) ()
  }
  function checkAlreadyLoggedIn() {
    (async () => {
      const { Storage } = Plugins;
      const res = await Storage.get({ key: 'token' });
      if (res.value) {
        setState({
          ...state,
          isAuthenticated:true,
          token: JSON.parse(res.value).token
        });
      } else {
        console.log('token not found');
        setState({
          ...state,
          isAuthenticated:false
        });
      }
    })();
  }

  return (
      <AuthContext.Provider value={value}>
        {children}
      </AuthContext.Provider>
  );

  function  postingOpenCallBack(username?: string): void {
console.log("hello posting")
    setState({...state, username,isAuthenticated:true, isOpenPosting:true, isOpenBorrowing: false})
    saveLocalUsername(username)
  }

  function borrowingOpenCallBack(username?:string):void {
    console.log("hello borrow")
    setState({...state, username, isAuthenticated:true, isOpenBorrowing: true, isOpenPosting: false})
    saveLocalUsername(username)
  }

  function loginCallback(username?: string, password?: string): void {
    log('login');
    setState({
      ...state,
      pendingAuthentication: true,
      username,
      password
    });

  }

  function logOutCallBack(): void {
    (async () => {
      //await Storage.remove({ key: 'token' });
      await Storage.clear();
      // if (setConnectionStatus) {
      //   await setConnectionStatus(false);
    //  }
    })();
    setState({...state, isAuthenticated: false})

  }

  function authenticationEffect() {
    let canceled = false;
    authenticate();
    return () => {
      canceled = true;
    }

    async function authenticate() {
      if (!pendingAuthentication) {
        log('authenticate, !pendingAuthentication, return');
        return;
      }
      try {
        log('authenticate...');
        setState({
          ...state,
          isAuthenticating: true,
        });
        const {username, password} = state;
        const {token} = await loginApi(username, password);
        if (canceled) {
          return;
        }
        log('authenticate succeeded');
        setState({
          ...state,
          token,
          pendingAuthentication: false,
          isAuthenticated: true,
          isAuthenticating: false,
        });
        // if (setConnectionStatus) {
        //   await setConnectionStatus(true);
        // }
        await Storage.set({
          key: 'token',
          value: JSON.stringify({
            token: token,
          })
        });
      } catch (error) {
        if (canceled) {
          return;
        }
        log('authenticate failed');
        setState({
          ...state,
          authenticationError: error,
          pendingAuthentication: false,
          isAuthenticating: false,
        });
      }
    }
  }
}
