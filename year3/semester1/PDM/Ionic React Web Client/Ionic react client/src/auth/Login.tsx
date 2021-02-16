import React, {useContext, useEffect, useState} from 'react';
import { Redirect } from 'react-router-dom';
import { RouteComponentProps } from 'react-router';
import {
    IonAlert,
    IonButton, IonCol,
    IonContent,
    IonGrid,
    IonHeader, IonIcon,
    IonInput, IonItem, IonLabel,
    IonLoading,
    IonPage, IonRow,
    IonTitle,
    IonToolbar
} from '@ionic/react';
import { AuthContext } from './AuthProvider';
import { getLogger } from '../core';
import {personCircle} from "ionicons/icons";
import { Plugins } from '@capacitor/core';
const { Storage } = Plugins;

const log = getLogger('Login');

interface LoginState {
  username?: string;
}

export const Login: React.FC<RouteComponentProps> = ({ history }) => {
  const { isAuthenticated, isAuthenticating, login, authenticationError,openPostingPage, openBorrowingPage, isOpenPosting, isOpenBorrowing } = useContext(AuthContext);
  const [state, setState] = useState<LoginState>({});
  useEffect(showKeys, [])
  const { username } = state;
  // const handleLogin = () => {
  //   log('handleLogin...');
  //   login?.(username);
  // };

  const handlePosting = () => {
      openPostingPage?.(username);
  };

  const handleBorrowing = () => {
    openBorrowingPage?.(username);
  };
  log('render');
  function showKeys(){
      (async () => {
          const { keys } = await Storage.keys();
          console.log('Keys found in log in component', keys);
      })()
  }


  // if (isAuthenticated) {
  //   return <Redirect to={{ pathname: '/' }} />
  // }
    if(isOpenPosting){
        return <Redirect to={{pathname: '/post'}} />
    }

    if(isOpenBorrowing){
        return  <Redirect to={{pathname:'/borrow'}}/>
    }
  return (
      <IonPage>
          <IonHeader>
              <IonToolbar>
                  <IonTitle>Autentificare</IonTitle>
              </IonToolbar>
          </IonHeader>
          <IonContent fullscreen className="ion-padding ion-text-center">
              <IonGrid>
                  <IonRow>
                      <IonCol>
                          <IonLoading isOpen={isAuthenticating}/>
                          <IonAlert
                              isOpen={authenticationError != null}
                              header={"Error!"}
                              message={'Username sau password gresite'}
                              buttons={["Dismiss"]}
                          />
                      </IonCol>
                  </IonRow>
                  <IonRow>
                      <IonCol>
                          <IonIcon style={{fontSize: "80px", color: "#0040ff"}}
                                   icon={personCircle}/>
                      </IonCol>
                  </IonRow>
                  <IonRow>
                      <IonCol>
                          <IonItem>
                              <IonLabel position="floating">Your Name</IonLabel>
                              <IonInput
                                  value={username}
                                  onIonChange={e => setState({
                                      ...state,
                                      username: e.detail.value || ''
                                  })}/>
                          </IonItem>
                      </IonCol>
                  </IonRow>

                  {/*<IonRow>*/}
                  {/*    <IonCol>*/}
                  {/*        <IonItem>*/}
                  {/*            <IonLabel position="floating">Password</IonLabel>*/}
                  {/*            <IonInput*/}
                  {/*                type="password"*/}
                  {/*                value={password}*/}
                  {/*                onIonChange={e => setState({*/}
                  {/*                    ...state,*/}
                  {/*                    password: e.detail.value || ''*/}
                  {/*                })}/>*/}
                  {/*        </IonItem>*/}
                  {/*    </IonCol>*/}
                  {/*</IonRow>*/}

                  <IonRow>
                      <IonCol>
                          <IonButton expand="block" onClick={handlePosting}>Posteaza ceva</IonButton>
                      </IonCol>
                      <IonCol>
                          <IonButton expand="block" onClick={handleBorrowing}>Imprumuta ceva</IonButton>
                      </IonCol>
                  </IonRow>
              </IonGrid>
          </IonContent>
      </IonPage>
  );
};
