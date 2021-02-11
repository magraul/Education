import React, {useContext, useEffect, useState} from 'react';
import {RouteComponentProps} from 'react-router';
import '../theme/itemList.css'
import {
    IonButton,
    IonButtons,
    IonCard,
    IonContent,
    IonFab,
    IonFabButton,
    IonHeader,
    IonIcon,
    IonInfiniteScroll,
    IonInfiniteScrollContent,
    IonItem,
    IonItemOption,
    IonItemOptions,
    IonItemSliding,
    IonLabel,
    IonList,
    IonLoading,
    IonMenu,
    IonMenuButton,
    IonMenuToggle,
    IonPage,
    IonRouterOutlet, IonSelectOption,
    IonTitle,
    IonToolbar, useIonViewWillEnter, IonSelect, IonSearchbar, IonAlert, IonCol
} from '@ionic/react';
import {add, personCircle, trash, helpCircle, star, logOut} from 'ionicons/icons';
import Item from './Item';
import {getLogger} from '../core';
import {ItemContext} from './ItemProvider';
import {AuthContext} from '../auth';
import { useNetwork } from './useNetwork';
const log = getLogger('ItemList');

const ItemList: React.FC<RouteComponentProps> = ({history}) => {
    const {items, fetching, fetchingError, deleteItem, getItemsC, disableInfiniteScroll, colors, savingError, saveItem} = useContext(ItemContext);
    const {logout} = useContext(AuthContext);
    const [filter, setFilter] = useState<string | undefined>(undefined);
    const [searchPutere, setsearchPutere] = useState<string>('');
    const { networkStatus } = useNetwork();


    const deleteTractor = (id?: string) => {
        let tractor = items?.find(t => t._id == id)
        if (tractor)
            if (deleteItem) {
                deleteItem(tractor).then(r => {
                })
            }
    }


    async function searchNext($event: CustomEvent<void>) {
        console.log(getItemsC)
        if(getItemsC) {
            (await getItemsC(filter ,false))
        }
        ($event.target as HTMLIonInfiniteScrollElement).complete();
    }

    // useEffect(() => {
    //     loadInLocalStorage()
    // }, [])

    useEffect(() => {
        console.log("in effect#############################################")
        if(getItemsC) {
            getItemsC(filter, true)
        }
    }, [filter]);

    log('render');
    return (
        <IonPage>
            <IonMenu menuId="meniu" contentId="content" className="menu-content-open">
                <IonContent id="content" className="menu-content-reveal">
                    <IonList>
                        <IonMenuToggle>
                            <IonItem routerLink="/login" onClick={() => logout()}>
                                <IonIcon icon={logOut} slot="start" onClick={() => logout()}/>
                                <IonButton className="sign-out-btn" expand="block" fill="outline">
                                    <IonLabel><b>Sign Out</b></IonLabel>
                                </IonButton>
                            </IonItem>
                        </IonMenuToggle>

                    </IonList>
                </IonContent>
            </IonMenu>
            <IonHeader>
                <IonToolbar>
                    <IonTitle><b>Tractoare</b></IonTitle>
                    <IonButtons slot="start">
                        <IonMenuButton autoHide={false} menu="meniu"/>
                    </IonButtons>
                    <IonButtons slot="end">
                        <IonLabel> {networkStatus.connected ? <div>ONLINE</div> : <div>OFFLINE</div>}</IonLabel>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>


            <IonContent fullscreen>
                <IonSearchbar
                    value={searchPutere}
                    debounce={1000}
                    onIonChange={e => setsearchPutere(e.detail.value!)}>
                </IonSearchbar>
                <IonLoading isOpen={fetching} message="Fetching items"/>
                <IonSelect value={filter} placeholder="Select Color" onIonChange={e => setFilter(e.detail.value)}>
                    {colors?.map(c => <IonSelectOption key={c} value={c}>{c}</IonSelectOption>)}
                </IonSelect>
                <IonAlert
                    isOpen={savingError != null}
                    header={"Data not sent"}
                    message={'Datele nu au putut fi trimise la server, dar au fost salvate local'}
                    buttons={["Dismiss"]}
                />
                {items && (
                    <IonList>
                        {items.filter(tr => tr.putere.indexOf(searchPutere) >= 0).map(({_id, title, putere, culoare, createdOn, version, conflict}) =>
                            <IonItemSliding key={_id}>
                                <IonItemOptions side="start">
                                    <IonItemOption color="danger" onClick={() => deleteTractor(_id)}><IonIcon
                                        slot="start" icon={trash}/></IonItemOption>
                                </IonItemOptions>
                                <IonItem>
                                    <Item key={_id} _id={_id} title={title} culoare={culoare} putere={putere} version={version}
                                          createdOn={createdOn} onEdit={id => history.push(`/tractor/${id}`)}/>
                                    {conflict && (<IonButton color="danger" onClick={() =>{if (saveItem) {saveItem({_id, title, culoare, putere, version: version+1, createdOn})}}}>Rezolva Conflict</IonButton>)}

                                </IonItem>
                            </IonItemSliding>
                        )}
                    </IonList>
                )}
                    <IonInfiniteScroll threshold="100px" disabled={disableInfiniteScroll}
                                       onIonInfinite={(e: CustomEvent<void>) => searchNext(e)}>
                        <IonInfiniteScrollContent
                            loadingText="Loading">
                        </IonInfiniteScrollContent>
                    </IonInfiniteScroll>
                {fetchingError && (
                    <div>{fetchingError.message || 'Failed to fetch items'}</div>
                )}
                <IonFab vertical="bottom" horizontal="end" slot="fixed">
                    <IonFabButton onClick={() => history.push('/tractor')}>
                        <IonIcon icon={add}/>
                    </IonFabButton>
                </IonFab>
            </IonContent>
        </IonPage>


    );
};

export default ItemList;
